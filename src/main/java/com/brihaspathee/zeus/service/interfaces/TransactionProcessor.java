package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.web.model.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:23 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionProcessor {

    /**
     * Process the transaction
     * @param transactionDto
     */
    void processTransaction(TransactionDto transactionDto) throws JsonProcessingException;
}
