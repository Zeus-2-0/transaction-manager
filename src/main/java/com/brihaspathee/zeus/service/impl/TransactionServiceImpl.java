package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.*;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.helper.interfaces.*;
import com.brihaspathee.zeus.mapper.interfaces.TransactionDetailMapper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionMapper;
import com.brihaspathee.zeus.service.interfaces.TransactionMemberService;
import com.brihaspathee.zeus.service.interfaces.TransactionService;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.web.model.DataTransformationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, November 2022
 * Time: 3:41 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    /**
     * Transaction mapper instance
     */
    private final TransactionMapper transactionMapper;

    /**
     * Transaction repository to perform CRUD operations
     */
    private final TransactionRepository transactionRepository;

    /**
     * Member Service instance to perform operations on member
     */
    private final TransactionMemberService memberService;

    /**
     * Transaction detail helper instance
     */
    private final TransactionDetailHelper detailHelper;

    /**
     * Payer Helper instance
     */
    private final PayerHelper payerHelper;

    /**
     * Sponsor Helper instance
     */
    private final SponsorHelper sponsorHelper;

    /**
     * Broker helper instance
     */
    private final BrokerHelper brokerHelper;

    /**
     * Transaction attributes helper instance
     */
    private final TransactionAttributesHelper attributesHelper;

    /**
     * Transaction rate helper instance
     */
    private final TransactionRateHelper rateHelper;

    /**
     * Transaction status helper
     */
    private final TransactionStatusHelper statusHelper;

    /**
     * Create the transaction
     * @param dataTransformationDto
     */
    @Override
    public void createTransaction(DataTransformationDto dataTransformationDto) {
        TransactionDto transactionDto = dataTransformationDto.getTransactionDto();
        // Transaction transaction = transactionMapper.transactionDtoToTransaction(transactionDto);
        // log.info("Transaction Entity:{}", transaction);
        Transaction transaction = transactionRepository.save(Transaction.builder()
                        .zfcn(transactionDto.getZfcn())
                        .ztcn(transactionDto.getZtcn())
                        .transactionSourceTypeCode(transactionDto.getTransactionSourceTypeCode())
                        .transactionReceivedDate(transactionDto.getTransactionReceivedDate())
                .build());
        log.info("Transaction SK created:{}", transaction.getTransactionSK());
        statusHelper.createStatus("RECEIVED", "RECEIVED", transaction);
        detailHelper.createTransactionDetail(transactionDto.getTransactionDetail(), transaction);
        payerHelper.createPayer(transactionDto.getPayer(), transaction);
        sponsorHelper.createSponsor(transactionDto.getSponsor(), transaction);
        brokerHelper.createBroker(transactionDto.getBroker(), transaction);
        attributesHelper.createTransactionAttributes(transactionDto.getTransactionAttributes(), transaction);
        rateHelper.createTransactionRates(transactionDto.getTransactionRates(), transaction);
        memberService.createMember(transactionDto.getMembers(), transaction);
    }
}
