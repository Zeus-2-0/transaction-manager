package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import com.brihaspathee.zeus.dto.transaction.TransactionStatusDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionStatusMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:51 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionStatusMapperImpl implements TransactionStatusMapper {

    /**
     * Convert status entity to status dto
     * @param status
     * @return
     */
    @Override
    public TransactionStatusDto statusToStatusDto(TransactionStatus status) {
        if(status == null){
            return null;
        }
        TransactionStatusDto statusDto = TransactionStatusDto.builder()
                .transactionStatusSK(status.getTransactionStatusSK())
                .statusSequence(status.getStatusSeq())
                .transactionStatusTypeCode(status.getTransactionStatusTypeCode())
                .processingStatusTypeCode(status.getProcessingStatusTypeCode())
                .createdDate(status.getCreatedDate())
                .updatedDate(status.getUpdatedDate())
                .build();
        return statusDto;
    }

    /**
     * Convert status dto to status entity
     * @param statusDto
     * @return
     */
    @Override
    public TransactionStatus statusDtoToStatus(TransactionStatusDto statusDto) {
        if(statusDto == null){
            return null;
        }
        TransactionStatus status = TransactionStatus.builder()
                .transactionStatusSK(statusDto.getTransactionStatusSK())
                .statusSeq(statusDto.getStatusSequence())
                .transactionStatusTypeCode(statusDto.getTransactionStatusTypeCode())
                .processingStatusTypeCode(statusDto.getProcessingStatusTypeCode())
                .createdDate(statusDto.getCreatedDate())
                .updatedDate(statusDto.getUpdatedDate())
                .build();
        return status;
    }

    /**
     * Convert status entities to status dtos
     * @param statuses
     * @return
     */
    @Override
    public List<TransactionStatusDto> statusesToStatusDtos(List<TransactionStatus> statuses) {

        return statuses.stream().map(this::statusToStatusDto).collect(Collectors.toList());
    }

    /**
     * Convert status dtos to status entities
     * @param statusDtos
     * @return
     */
    @Override
    public List<TransactionStatus> statusDtosToStatuses(List<TransactionStatusDto> statusDtos) {

        return statusDtos.stream().map(this::statusDtoToStatus).collect(Collectors.toList());
    }
}
