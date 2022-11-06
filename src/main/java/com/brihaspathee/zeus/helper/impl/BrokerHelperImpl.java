package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Broker;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.BrokerRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionBrokerDto;
import com.brihaspathee.zeus.helper.interfaces.BrokerHelper;
import com.brihaspathee.zeus.mapper.interfaces.BrokerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 10:56 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BrokerHelperImpl implements BrokerHelper {

    /**
     * Broker Mapper Instance
     */
    private final BrokerMapper brokerMapper;

    /**
     * Broker Repository Instance
     */
    private final BrokerRepository brokerRepository;

    /**
     * Create the broker
     * @param brokerDto
     * @param transaction
     */
    @Override
    public Broker createBroker(TransactionBrokerDto brokerDto,
                             Transaction transaction) {
        if(brokerDto != null){
            Broker broker = brokerMapper.brokerDtoToBroker(brokerDto);
            broker.setTransaction(transaction);
            return brokerRepository.save(broker);
        }else{
            return null;
        }

    }
}
