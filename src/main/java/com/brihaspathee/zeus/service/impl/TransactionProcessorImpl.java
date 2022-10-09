package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.broker.producer.AccountProducer;
import com.brihaspathee.zeus.broker.producer.TransactionValidationProducer;
import com.brihaspathee.zeus.service.interfaces.TransactionProcessor;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.web.model.AccountDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final AccountProducer accountProducer;

    /**
     * Process transaction
     * @param transactionDto
     */
    @Override
    public void processTransaction(TransactionDto transactionDto) {
        transactionValidationProducer.publishTransaction(transactionDto);
        AccountDto accountDto = AccountDto.builder()
                .accountNumber(ZeusRandomStringGenerator.randomString(15))
                .lineOfBusinessTypeCode(transactionDto.getLineOfBusinessTypeCode())
                .build();
        accountProducer.publishAccount(accountDto);
    }
}
