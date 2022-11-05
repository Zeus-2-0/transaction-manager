package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionAttributeDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 7:00 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionAttributesHelper {

    /**
     * Create transaction attributes
     * @param attributeDtos
     * @param transaction
     */
    void createTransactionAttributes(List<TransactionAttributeDto> attributeDtos,
                                     Transaction transaction);
}
