package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.TradingPartner;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.TradingPartnerRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionTradingPartnerDto;
import com.brihaspathee.zeus.helper.interfaces.TradingPartnerHelper;
import com.brihaspathee.zeus.mapper.interfaces.TradingPartnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, December 2022
 * Time: 11:18 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TradingPartnerHelperImpl implements TradingPartnerHelper {

    /**
     * Trading partner repository instance to perform CRUD operations
     */
    private final TradingPartnerRepository tradingPartnerRepository;

    /**
     * Trading partner mapper instance
     */
    private final TradingPartnerMapper tradingPartnerMapper;

    /**
     * Create the trading partner
     * @param tradingPartnerDto
     * @return
     */
    @Override
    public TradingPartner createTradingPartner(TransactionTradingPartnerDto tradingPartnerDto,
                                               Transaction transaction) {
        if(tradingPartnerDto != null){
            TradingPartner tradingPartner = tradingPartnerMapper.tpDtoToTp(tradingPartnerDto);
            tradingPartner.setTransaction(transaction);
            tradingPartner = tradingPartnerRepository.save(tradingPartner);
            return tradingPartner;
        }else{
            return null;
        }

    }
}
