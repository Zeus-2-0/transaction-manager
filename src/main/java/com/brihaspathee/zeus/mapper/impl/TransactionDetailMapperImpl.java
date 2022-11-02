package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.dto.transaction.TransactionDetailDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:36 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionDetailMapperImpl implements TransactionDetailMapper {

    /**
     * Convert detail entity to detail dto
     * @param detail
     * @return
     */
    @Override
    public TransactionDetailDto detailToDetailDto(TransactionDetail detail) {
        if(detail == null){
            return null;
        }
        TransactionDetailDto detailDto = TransactionDetailDto.builder()
                .transactionDetailSK(detail.getTransactionDetailSK())
                .transactionTypeCode(detail.getTransactionTypeCode())
                .planId(detail.getPlanId())
                .csrVariant(detail.getCsrVariant())
                .groupPolicyId(detail.getGroupPolicyId())
                .effectiveDate(detail.getEffectiveDate())
                .endDate(detail.getEndDate())
                .maintenanceEffectiveDate(detail.getMaintenanceEffectiveDate())
                .createdDate(detail.getCreatedDate())
                .updatedDate(detail.getUpdatedDate())
                .build();
        return detailDto;
    }

    /**
     * Convert detail dto to detail entity
     * @param detailDto
     * @return
     */
    @Override
    public TransactionDetail detailDtoToDetail(TransactionDetailDto detailDto) {
        if(detailDto == null){
            return null;
        }
        TransactionDetail detail = TransactionDetail.builder()
                .transactionDetailSK(detailDto.getTransactionDetailSK())
                .transactionTypeCode(detailDto.getTransactionTypeCode())
                .planId(detailDto.getPlanId())
                .csrVariant(detailDto.getCsrVariant())
                .groupPolicyId(detailDto.getGroupPolicyId())
                .effectiveDate(detailDto.getEffectiveDate())
                .endDate(detailDto.getEndDate())
                .maintenanceEffectiveDate(detailDto.getMaintenanceEffectiveDate())
                .createdDate(detailDto.getCreatedDate())
                .updatedDate(detailDto.getUpdatedDate())
                .build();
        return detail;
    }

    /**
     * Convert detail entities to detail dtos
     * @param details
     * @return
     */
    @Override
    public List<TransactionDetailDto> detailsToDetailDtos(List<TransactionDetail> details) {

        return details.stream().map(this::detailToDetailDto).collect(Collectors.toList());
    }

    /**
     * Convert detail dtos to detail entities
     * @param detailDtos
     * @return
     */
    @Override
    public List<TransactionDetail> detailDtosToDetails(List<TransactionDetailDto> detailDtos) {

        return detailDtos.stream().map(this::detailDtoToDetail).collect(Collectors.toList());
    }
}
