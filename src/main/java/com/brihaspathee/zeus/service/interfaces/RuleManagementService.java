package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.validator.TransactionValidationResult;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, February 2024
 * Time: 6:10 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface RuleManagementService {

    /**
     * Save the rules that are processed for the transactons
     * @param transactionDto
     * @param transactionValidationResult
     */
    boolean saveTransactionRules(TransactionDto transactionDto, TransactionValidationResult transactionValidationResult);
}
