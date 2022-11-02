package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TradingPartner;
import com.brihaspathee.zeus.dto.transaction.TransactionTradingPartnerDto;
import com.brihaspathee.zeus.mapper.interfaces.TradingPartnerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:34 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TradingPartnerMapperImpl implements TradingPartnerMapper {

    /**
     * Convert TP entity to TP DTO
     * @param tradingPartner
     * @return
     */
    @Override
    public TransactionTradingPartnerDto tpToTpDto(TradingPartner tradingPartner) {
        if(tradingPartner == null){
            return null;
        }
        TransactionTradingPartnerDto tradingPartnerDto = TransactionTradingPartnerDto.builder()
                .tradingPartnerSK(tradingPartner.getTradingPartnerSK())
                .tradingPartnerId(tradingPartner.getTradingPartnerId())
                .lineOfBusinessTypeCode(tradingPartner.getLineOfBusinessTypeCode())
                .businessTypeCode(tradingPartner.getBusinessUnitTypeCode())
                .marketplaceTypeCode(tradingPartner.getMarketplaceTypeCode())
                .createdDate(tradingPartner.getCreatedDate())
                .updatedDate(tradingPartner.getUpdatedDate())
                .build();
        return tradingPartnerDto;
    }

    /**
     * Convert TP DTO to TP entity
     * @param tradingPartnerDto
     * @return
     */
    @Override
    public TradingPartner tpDtoToTp(TransactionTradingPartnerDto tradingPartnerDto) {
        if(tradingPartnerDto == null){
            return null;
        }
        TradingPartner tradingPartner = TradingPartner.builder()
                .tradingPartnerSK(tradingPartnerDto.getTradingPartnerSK())
                .tradingPartnerId(tradingPartnerDto.getTradingPartnerId())
                .lineOfBusinessTypeCode(tradingPartnerDto.getLineOfBusinessTypeCode())
                .businessUnitTypeCode(tradingPartnerDto.getBusinessTypeCode())
                .marketplaceTypeCode(tradingPartnerDto.getMarketplaceTypeCode())
                .createdDate(tradingPartnerDto.getCreatedDate())
                .updatedDate(tradingPartnerDto.getUpdatedDate())
                .build();
        return tradingPartner;
    }

    /**
     * Convert TP entities to TP DTOs
     * @param tradingPartners
     * @return
     */
    @Override
    public List<TransactionTradingPartnerDto> tpsToTpDtos(List<TradingPartner> tradingPartners) {

        return tradingPartners.stream().map(this::tpToTpDto).collect(Collectors.toList());
    }

    /**
     * Convert TP DTOs to TP entities
     * @param tradingPartnerDtos
     * @return
     */
    @Override
    public List<TradingPartner> tpDtosToTps(List<TransactionTradingPartnerDto> tradingPartnerDtos) {

        return tradingPartnerDtos.stream().map(this::tpDtoToTp).collect(Collectors.toList());
    }
}
