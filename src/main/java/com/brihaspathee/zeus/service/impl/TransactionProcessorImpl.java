package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.broker.producer.AccountProcessingProducer;
import com.brihaspathee.zeus.broker.producer.TransactionValidationProducer;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionStatusHelper;
import com.brihaspathee.zeus.service.interfaces.AccountMatchService;
import com.brihaspathee.zeus.service.interfaces.TransactionProcessor;
import com.brihaspathee.zeus.service.interfaces.TransactionService;
import com.brihaspathee.zeus.broker.message.AccountProcessingRequest;
import com.brihaspathee.zeus.web.model.DataTransformationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:24 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionProcessorImpl implements TransactionProcessor {

    /**
     * Transaction validation producer instance to send the message to transaction validation
     */
    private final TransactionValidationProducer transactionValidationProducer;

    /**
     * Account producer instance to send the message to the member management service
     */
    private final AccountProcessingProducer accountProcessingProducer;

    /**
     * Transaction service instance to create the record
     */
    private final TransactionService transactionService;

    /**
     * Account match service instance
     */
    private final AccountMatchService accountMatchService;

    /**
     * Transaction status helper instance
     */
    private final TransactionStatusHelper transactionStatusHelper;

    /**
     * Process transaction
     * @param dataTransformationDto
     */
    @Override
    public Mono<Void> processTransaction(DataTransformationDto dataTransformationDto) throws JsonProcessingException {
        log.info("Transaction received for processing:{}", dataTransformationDto);
        TransactionDto transactionDto = transactionService.createTransaction(dataTransformationDto);
        log.info("Transaction after inserting to tables:{}", transactionDto);
        transactionStatusHelper.createStatus("PROCESSING",
                "PROCESSING",
                Transaction.builder()
                        .transactionSK(transactionDto.getTransactionSK())
                        .build());
        transactionValidationProducer.publishTransaction(transactionDto);
        String accountNumber = accountMatchService.matchAccount(transactionDto);
        log.info("Matched Account Number:{}", accountNumber);
        AccountProcessingRequest accountProcessingRequest = AccountProcessingRequest.builder()
                .accountNumber(accountNumber)
                .transactionDto(transactionDto)
                .build();
        accountProcessingProducer.publishAccount(accountProcessingRequest);
        return Mono.empty();
    }
}
