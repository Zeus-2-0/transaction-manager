package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionRate;
import com.brihaspathee.zeus.dto.transaction.TransactionRateDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:50 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRateMapperImpl implements TransactionRateMapper {

    /**
     * Convert rate entity to rate dto
     * @param rate
     * @return
     */
    @Override
    public TransactionRateDto rateToRateDto(TransactionRate rate) {
        if(rate == null){
            return null;
        }
        TransactionRateDto rateDto = TransactionRateDto.builder()
                .transactionRateSK(rate.getTransactionRateSK())
                .rateTypeCode(rate.getRateTypeCode())
                .transactionRate(rate.getTransactionRate())
                .rateStartDate(rate.getRateStartDate())
                .rateEndDate(rate.getRateEndDate())
                .build();
        return rateDto;
    }

    /**
     * Convert rate dto to rate entity
     * @param rateDto
     * @return
     */
    @Override
    public TransactionRate rateDtoToRate(TransactionRateDto rateDto) {
        if(rateDto == null){
            return null;
        }
        TransactionRate rate = TransactionRate.builder()
                .transactionRateSK(rateDto.getTransactionRateSK())
                .rateTypeCode(rateDto.getRateTypeCode())
                .transactionRate(rateDto.getTransactionRate())
                .rateStartDate(rateDto.getRateStartDate())
                .rateEndDate(rateDto.getRateEndDate())
                .build();
        return rate;
    }

    /**
     * Convert Rate entities to rate dtos
     * @param rates
     * @return
     */
    @Override
    public List<TransactionRateDto> ratesToRateDtos(List<TransactionRate> rates) {

        return rates.stream().map(this::rateToRateDto).collect(Collectors.toList());
    }

    /**
     * Convert rate dtos to rate entities
     * @param rateDtos
     * @return
     */
    @Override
    public List<TransactionRate> rateDtosToRates(List<TransactionRateDto> rateDtos) {

        return rateDtos.stream().map(this::rateDtoToRate).collect(Collectors.toList());
    }
}
