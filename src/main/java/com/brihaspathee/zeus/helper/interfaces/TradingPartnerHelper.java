package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.TradingPartner;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionTradingPartnerDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 05, December 2022
 * Time: 11:17 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TradingPartnerHelper {

    /**
     * Create the trading partner
     * @param tradingPartnerDto
     * @return
     */
    TradingPartner createTradingPartner(TransactionTradingPartnerDto tradingPartnerDto,
                                        Transaction transaction);
}
