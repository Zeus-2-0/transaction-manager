package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.Payer;
import com.brihaspathee.zeus.dto.transaction.TransactionPayerDto;
import com.brihaspathee.zeus.mapper.interfaces.PayerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 5:21 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PayerMapperImpl implements PayerMapper {

    /**
     * Convert payer to payer dto
     * @param payer
     * @return
     */
    @Override
    public TransactionPayerDto payerToPayerDto(Payer payer) {
        if(payer == null){
            return null;
        }
        TransactionPayerDto payerDto = TransactionPayerDto.builder()
                .payerSK(payer.getPayerSK())
                .payerName(payer.getPayerName())
                .payerId(payer.getPayerId())
                .receivedDate(payer.getReceivedDate())
                .createdDate(payer.getCreatedDate())
                .updatedDate(payer.getUpdatedDate())
                .build();
        return payerDto;
    }

    /**
     * Convert payer dto to payer
     * @param payerDto
     * @return
     */
    @Override
    public Payer payerDtoToPayer(TransactionPayerDto payerDto) {
        if(payerDto == null){
            return null;
        }
        Payer payer = Payer.builder()
                .payerSK(payerDto.getPayerSK())
                .payerName(payerDto.getPayerName())
                .payerId(payerDto.getPayerId())
                .receivedDate(payerDto.getReceivedDate())
                .createdDate(payerDto.getCreatedDate())
                .updatedDate(payerDto.getUpdatedDate())
                .build();
        return payer;
    }

    /**
     * Convert the list of payers to payer dtos
     * @param payers
     * @return
     */
    @Override
    public List<TransactionPayerDto> payersToPayerDtos(List<Payer> payers) {

        return payers.stream().map(this::payerToPayerDto).collect(Collectors.toList());
    }

    /**
     * Convert the list of payer dtos to payer entities
     * @param payerDtos
     * @return
     */
    @Override
    public List<Payer> payerDtoToPayers(List<TransactionPayerDto> payerDtos) {

        return payerDtos.stream().map(this::payerDtoToPayer).collect(Collectors.toList());
    }
}
