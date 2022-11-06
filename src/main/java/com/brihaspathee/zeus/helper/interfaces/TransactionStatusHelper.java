package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionStatus;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, November 2022
 * Time: 5:58 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionStatusHelper {


    /**
     * Create a new transaction status for the transaction
     * @param transactionStatus
     * @param transactionProcessingStatus
     */
    List<TransactionStatus> createStatus(String transactionStatus,
                      String transactionProcessingStatus,
                      Transaction transaction);
}
