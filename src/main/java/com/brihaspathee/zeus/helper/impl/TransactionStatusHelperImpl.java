package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import com.brihaspathee.zeus.domain.repository.TransactionStatusRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionStatusDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionStatusHelper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionStatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, November 2022
 * Time: 6:08 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionStatusHelperImpl implements TransactionStatusHelper {

    /**
     * Transaction status mapper instance
     */
    private final TransactionStatusMapper statusMapper;

    /**
     * Transaction status repository instance to perform CRUD operations
     */
    private final TransactionStatusRepository statusRepository;


    /**
     * Create the transaction status
     * @param transactionStatus
     * @param transactionProcessingStatus
     * @param transaction
     */
    @Override
    public List<TransactionStatus> createStatus(String transactionStatus,
                             String transactionProcessingStatus,
                             Transaction transaction) {
//        Optional<TransactionStatus> status =
//                statusRepository.findFirstByTransactionOrderByStatusSeqDesc(transaction);
        List<TransactionStatus> statuses = statusRepository.findAllByTransaction(transaction);
        Optional<TransactionStatus> status = statuses
                .stream()
                .max(
                        Comparator.comparing(TransactionStatus :: getStatusSeq))
                .stream()
                .findFirst();
        if(status.isEmpty()){
            TransactionStatus newStatus = TransactionStatus.builder()
                    .transaction(transaction)
                    .statusSeq(1)
                    .transactionStatusTypeCode(transactionStatus)
                    .processingStatusTypeCode(transactionProcessingStatus)
                    .build();
            statuses.add(statusRepository.save(newStatus));
        }else{
            TransactionStatus newStatus = TransactionStatus.builder()
                    .transaction(transaction)
                    .statusSeq(status.get().getStatusSeq() + 1)
                    .transactionStatusTypeCode(transactionStatus)
                    .processingStatusTypeCode(transactionProcessingStatus)
                    .build();
            statuses.add(statusRepository.save(newStatus));
        }
        return statuses;
    }

    /**
     * Update the status of the transaction
     * @param transactionStatusDto
     */
    @Override
    public void updateTransactionStatus(TransactionStatusDto transactionStatusDto) {
        TransactionStatus status = statusMapper.statusDtoToStatus(transactionStatusDto);
        statusRepository.save(status);
    }
}
