package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TradingPartner;
import com.brihaspathee.zeus.dto.transaction.TransactionTradingPartnerDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:47 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TradingPartnerMapper {

    /**
     * Convert TP entity to TP DTO
     * @param tradingPartner
     * @return
     */
    TransactionTradingPartnerDto tpToTpDto(TradingPartner tradingPartner);

    /**
     * Convert TP DTO to TP entity
     * @param tradingPartnerDto
     * @return
     */
    TradingPartner tpDtoToTp(TransactionTradingPartnerDto tradingPartnerDto);

    /**
     * Convert TP entities to TP DTOs
     * @param tradingPartners
     * @return
     */
    List<TransactionTradingPartnerDto> tpsToTpDtos(List<TradingPartner> tradingPartners);

    /**
     * Convert TP DTOs to TP entities
     * @param tradingPartnerDtos
     * @return
     */
    List<TradingPartner> tpDtosToTps(List<TransactionTradingPartnerDto> tradingPartnerDtos);
}
