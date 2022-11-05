package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionRate;
import com.brihaspathee.zeus.domain.repository.TransactionRateRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionRateDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionRateHelper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionRateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 11:16 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionRateHelperImpl implements TransactionRateHelper {

    /**
     * Transaction Rate mapper instance
     */
    private final TransactionRateMapper rateMapper;

    /**
     * Transaction Rate repository instance
     */
    private final TransactionRateRepository rateRepository;

    /**
     * Create transaction rates
     * @param rateDtos
     * @param transaction
     */
    @Override
    public void createTransactionRates(List<TransactionRateDto> rateDtos, Transaction transaction) {
        if(rateDtos != null && rateDtos.size() >0){
            rateDtos.stream().forEach(rateDto -> {
                TransactionRate rate = rateMapper.rateDtoToRate(rateDto);
                rate.setTransaction(transaction);
                rateRepository.save(rate);
            });
        }
    }
}
