package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.broker.message.AccountProcessingResponse;
import com.brihaspathee.zeus.broker.producer.AccountProcessingProducer;
import com.brihaspathee.zeus.broker.producer.TransactionValidationProducer;
import com.brihaspathee.zeus.constants.TransactionProcessingStatusValue;
import com.brihaspathee.zeus.constants.TransactionStatusValue;
import com.brihaspathee.zeus.constants.TransactionTypes;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionStatusHelper;
import com.brihaspathee.zeus.service.interfaces.*;
import com.brihaspathee.zeus.broker.message.AccountProcessingRequest;
import com.brihaspathee.zeus.validator.TransactionValidationResult;
import com.brihaspathee.zeus.web.model.DataTransformationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

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
     * Transaction member service instance
     */
    private final TransactionMemberService transactionMemberService;

    /**
     * Account match service instance
     */
    private final AccountMatchService accountMatchService;

    /**
     * Transaction status helper instance
     */
    private final TransactionStatusHelper transactionStatusHelper;

    /**
     * Rule management service instance
     */
    private final RuleManagementService ruleManagementService;

    /**
     * The environment in which the service is running
     */
    private final Environment environment;

    /**
     * Process transaction
     * @param dataTransformationDto
     */
    @Override
    public Mono<Void> processTransaction(DataTransformationDto dataTransformationDto) throws JsonProcessingException {
        log.info("Transaction received for processing:{}", dataTransformationDto);
        TransactionDto transactionDto = transactionService.createTransaction(dataTransformationDto);
        log.info("Transaction after inserting to tables:{}", transactionDto);
        transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSING.toString(),
                TransactionProcessingStatusValue.PROCESSING.toString(),
                Transaction.builder()
                        .transactionSK(transactionDto.getTransactionSK())
                        .build());
        transactionValidationProducer.publishTransaction(transactionDto);
//        String accountNumber = accountMatchService.matchAccount(transactionDto);
//        log.info("Matched Account Number:{}", accountNumber);
//        AccountProcessingRequest accountProcessingRequest = AccountProcessingRequest.builder()
//                .accountNumber(accountNumber)
//                .transactionDto(transactionDto)
//                .build();
//        accountProcessingProducer.publishAccount(accountProcessingRequest);
        return Mono.empty();
    }

    /**
     * Process transaction rule validation results
     * @param transactionValidationResult
     */
    @Override
    public void processValidationRuleResults(TransactionValidationResult transactionValidationResult) throws JsonProcessingException {
        log.info("Rules validations are received, starting to process the results");
        String ztcn = transactionValidationResult.getZtcn();
        TransactionDto transactionDto = transactionService.getTransactionByZtcn(ztcn);
        log.info("Transaction for which the results are received:{}",transactionDto);
        boolean validationsPassed = ruleManagementService.saveTransactionRules(transactionDto, transactionValidationResult);
        if(validationsPassed){
            AccountDto matchedAccount = accountMatchService.matchAccount(transactionDto);
            // if all the transaction related validations have passed
            // if the transaction is CHANGE or ADD
            if(transactionDto.getTransactionDetail().getTransactionTypeCode().equals(TransactionTypes.ADD.toString()) ||
            transactionDto.getTransactionDetail().getTransactionTypeCode().equals(TransactionTypes.CHANGE.toString())){
                // Populate the member rates if not present
                populateRates(matchedAccount, transactionDto);
            }
            // Populate the entity code if the service is running in a test environment
            populateTestEntityCodes(transactionDto,
                    transactionValidationResult.getTestTransactionDto());
            // Send transaction to APS
            if(matchedAccount!=null){
                sendTransactionToAPS(matchedAccount.getAccountNumber(), transactionDto);
            }else{
                sendTransactionToAPS(null, transactionDto);
            }
            transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSING.toString(),
                    TransactionProcessingStatusValue.SENT_TO_APS.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        }else{
            transactionStatusHelper.createStatus(TransactionStatusValue.EXCEPTION.toString(),
                    TransactionProcessingStatusValue.EXCEPTION.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        }
    }

    @Override
    public void processAPSResponse(AccountProcessingResponse accountProcessingResponse) {
        String ztcn = accountProcessingResponse.getZtcn();
        TransactionDto transactionDto = transactionService.getTransactionByZtcn(ztcn);
        String apsResponseCode = accountProcessingResponse.getResponseCode();
        log.info("APS Response Code Received:{}", apsResponseCode);
        if(apsResponseCode.equals("8000001")){
            transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSING.toString(),
                    TransactionProcessingStatusValue.SENT_FOR_VALIDATION.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        }else if(apsResponseCode.equals("8000002")){
            transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSING.toString(),
                    TransactionProcessingStatusValue.SENT_TO_MMS.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        } else if(apsResponseCode.equals("8000003")){
            transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSED.toString(),
                    TransactionProcessingStatusValue.SENT_TO_PB.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        }else if(apsResponseCode.equals("8000004")){
            transactionStatusHelper.createStatus(TransactionStatusValue.PROCESSED.toString(),
                    TransactionProcessingStatusValue.PROCESSED.toString(),
                    Transaction.builder()
                            .transactionSK(transactionDto.getTransactionSK())
                            .build());
        }
    }

    /**
     * Populate the rates for members
     * @param matchedAccount - the account that was matched for the transaction
     * @param transactionDto - the transaction dto
     */
    private void populateRates(AccountDto matchedAccount, TransactionDto transactionDto){
        transactionMemberService.populateMemberRates(matchedAccount, transactionDto);
    }

    /**
     * Send the transaction to APS for further processing
     * @param accountNumber - the account number if present that was matched with the transaction
     * @param transactionDto
     * @throws JsonProcessingException
     */
    private void sendTransactionToAPS(String accountNumber, TransactionDto transactionDto) throws JsonProcessingException {
        log.info("Matched Account Number:{}", accountNumber);
        AccountProcessingRequest accountProcessingRequest = AccountProcessingRequest.builder()
                .accountNumber(accountNumber)
                .transactionDto(transactionDto)
                .build();
        accountProcessingProducer.publishAccount(accountProcessingRequest);
    }

    /**
     * Populate the entity codes if the service is running in an
     * integration test environment
     * @param finalTransaction
     * @param originalTransaction
     */
    private void populateTestEntityCodes(TransactionDto finalTransaction,
                                         TransactionDto originalTransaction){
        if(Arrays.asList(environment.getActiveProfiles()).contains("int-test")){
            if(originalTransaction.getEntityCodes() != null && !originalTransaction.getEntityCodes().isEmpty()){
                finalTransaction.setEntityCodes(originalTransaction.getEntityCodes());
            }
            originalTransaction.getMembers().forEach(transactionMemberDto -> {
                if(transactionMemberDto.getEntityCodes() !=null && !transactionMemberDto.getEntityCodes().isEmpty()){
                    // Find the member in the final transaction dto
                    TransactionMemberDto memberDto = finalTransaction.getMembers()
                            .stream()
                            .filter(member -> member.getTransactionMemberCode()
                                    .equals(transactionMemberDto.getTransactionMemberCode()))
                            .findFirst()
                            .orElseThrow();
                    memberDto.setEntityCodes(transactionMemberDto.getEntityCodes());
                }

            });
        }
    }
}
