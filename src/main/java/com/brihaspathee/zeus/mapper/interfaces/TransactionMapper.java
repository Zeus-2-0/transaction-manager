package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:37 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionMapper {

    /**
     * Convert transaction entity to transaction dto
     * @param transaction
     * @return
     */
    TransactionDto transactionToTransactionDto(Transaction transaction);

    /**
     * Convert transaction dto to transaction entity
     * @param transactionDto
     * @return
     */
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    /**
     * Convert transaction entities to transaction dtos
     * @param transactions
     * @return
     */
    List<TransactionDto> transactionsToTransactionDtos(List<Transaction> transactions);

    /**
     * Convert transaction dto to transaction entities
     * @param transactionDtos
     * @return
     */
    List<Transaction> transactionDtosToTransactions(List<TransactionDto> transactionDtos);
}
