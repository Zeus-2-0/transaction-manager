package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionBrokerDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:58 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface BrokerHelper {

    /**
     * Create the broker
     * @param brokerDto
     * @param transaction
     */
    void createBroker(TransactionBrokerDto brokerDto,
                      Transaction transaction);
}
