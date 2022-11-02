package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import com.brihaspathee.zeus.dto.transaction.TransactionStatusDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionStatusMapper {

    /**
     * Convert status entity to status dto
     * @param status
     * @return
     */
    TransactionStatusDto statusToStatusDto(TransactionStatus status);

    /**
     * Convert status dto to status entity
     * @param statusDto
     * @return
     */
    TransactionStatus statusDtoToStatus(TransactionStatusDto statusDto);

    /**
     * Convert status entities to status dtos
     * @param statuses
     * @return
     */
    List<TransactionStatusDto> statusesToStatusDtos(List<TransactionStatus> statuses);

    /**
     * Convert status dtos to status entities
     * @param statusDtos
     * @return
     */
    List<TransactionStatus> statusDtosToStatuses(List<TransactionStatusDto> statusDtos);
}
