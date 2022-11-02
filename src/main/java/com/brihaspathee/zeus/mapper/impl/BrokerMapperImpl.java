package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.Broker;
import com.brihaspathee.zeus.dto.transaction.TransactionBrokerDto;
import com.brihaspathee.zeus.mapper.interfaces.BrokerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 6:36 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BrokerMapperImpl implements BrokerMapper {

    /**
     * Convert broker entity to broker dtos
     * @param broker
     * @return
     */
    @Override
    public TransactionBrokerDto brokerToBrokerDto(Broker broker) {
        if (broker == null){
            return null;
        }
        TransactionBrokerDto brokerDto = TransactionBrokerDto.builder()
                .transactionBrokerSK(broker.getBrokerSK())
                .brokerName(broker.getBrokerName())
                .brokerId(broker.getBrokerId())
                .agencyName(broker.getAgencyName())
                .agencyId(broker.getAgencyId())
                .accountNumber1(broker.getAccountNumber1())
                .accountNumber2(broker.getAccountNumber2())
                .receivedDate(broker.getReceivedDate())
                .createdDate(broker.getCreatedDate())
                .updatedDate(broker.getUpdatedDate())
                .build();
        return brokerDto;
    }

    /**
     * Convert broker dto to broker entity
     * @param brokerDto
     * @return
     */
    @Override
    public Broker brokerDtoToBroker(TransactionBrokerDto brokerDto) {
        if (brokerDto == null){
            return null;
        }
        Broker broker = Broker.builder()
                .brokerSK(brokerDto.getTransactionBrokerSK())
                .brokerName(brokerDto.getBrokerName())
                .brokerId(brokerDto.getBrokerId())
                .agencyName(brokerDto.getAgencyName())
                .agencyId(brokerDto.getAgencyId())
                .accountNumber1(brokerDto.getAccountNumber1())
                .accountNumber2(brokerDto.getAccountNumber2())
                .receivedDate(brokerDto.getReceivedDate())
                .createdDate(brokerDto.getCreatedDate())
                .updatedDate(brokerDto.getUpdatedDate())
                .build();
        return broker;
    }

    /**
     * Convert broker entities to broker dtos
     * @param brokers
     * @return
     */
    @Override
    public List<TransactionBrokerDto> brokersToBrokerDtos(List<Broker> brokers) {

        return brokers.stream().map(this::brokerToBrokerDto).collect(Collectors.toList());
    }

    /**
     * Convert broker dtos to broker entities
     * @param brokerDtos
     * @return
     */
    @Override
    public List<Broker> brokerDtosToBrokers(List<TransactionBrokerDto> brokerDtos) {

        return brokerDtos.stream().map(this::brokerDtoToBroker).collect(Collectors.toList());
    }
}
