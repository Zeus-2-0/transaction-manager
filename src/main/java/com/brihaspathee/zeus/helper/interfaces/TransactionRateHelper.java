package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionRate;
import com.brihaspathee.zeus.dto.transaction.TransactionRateDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:59 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionRateHelper {

    /**
     * Create transaction rates
     * @param rateDtos
     * @param transaction
     */
    List<TransactionRate> createTransactionRates(List<TransactionRateDto> rateDtos,
                                                 Transaction transaction);
}
