package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionRate;
import com.brihaspathee.zeus.dto.transaction.TransactionRateDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:46 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionRateMapper {

    /**
     * Convert rate entity to rate dto
     * @param rate
     * @return
     */
    TransactionRateDto rateToRateDto(TransactionRate rate);

    /**
     * Convert rate dto to rate entity
     * @param rateDto
     * @return
     */
    TransactionRate rateDtoToRate(TransactionRateDto rateDto);

    /**
     * Convert Rate entities to rate dtos
     * @param rates
     * @return
     */
    List<TransactionRateDto> ratesToRateDtos(List<TransactionRate> rates);

    /**
     * Convert rate dtos to rate entities
     * @param rateDtos
     * @return
     */
    List<TransactionRate> rateDtosToRates(List<TransactionRateDto> rateDtos);
}
