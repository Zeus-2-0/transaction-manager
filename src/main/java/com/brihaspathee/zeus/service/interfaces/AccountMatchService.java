package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, February 2024
 * Time: 6:53 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface AccountMatchService {

    /**
     * Match and return the account number of the account that matches the transaction
     * @param transactionDto
     * @return
     */
    String matchAccount(TransactionDto transactionDto);
}
