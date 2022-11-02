package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.Broker;
import com.brihaspathee.zeus.dto.transaction.TransactionBrokerDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface BrokerMapper {

    /**
     * Convert broker entity to broker dtos
     * @param broker
     * @return
     */
    TransactionBrokerDto brokerToBrokerDto(Broker broker);

    /**
     * Convert broker dto to broker entity
     * @param brokerDto
     * @return
     */
    Broker brokerDtoToBroker(TransactionBrokerDto brokerDto);

    /**
     * Convert broker entities to broker dtos
     * @param brokers
     * @return
     */
    List<TransactionBrokerDto> brokersToBrokerDtos(List<Broker> brokers);

    /**
     * Convert broker dtos to broker entities
     * @param brokerDtos
     * @return
     */
    List<Broker> brokerDtosToBrokers(List<TransactionBrokerDto> brokerDtos);
}
