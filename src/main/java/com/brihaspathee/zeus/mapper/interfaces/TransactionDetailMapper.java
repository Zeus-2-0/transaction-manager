package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.dto.transaction.TransactionDetailDto;

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
public interface TransactionDetailMapper {

    /**
     * Convert detail entity to detail dto
     * @param detail
     * @return
     */
    TransactionDetailDto detailToDetailDto(TransactionDetail detail);

    /**
     * Convert detail dto to detail entity
     * @param detailDto
     * @return
     */
    TransactionDetail detailDtoToDetail(TransactionDetailDto detailDto);

    /**
     * Convert detail entities to detail dtos
     * @param details
     * @return
     */
    List<TransactionDetailDto> detailsToDetailDtos(List<TransactionDetail> details);

    /**
     * Convert detail dtos to detail entities
     * @param detailDtos
     * @return
     */
    List<TransactionDetail> detailDtosToDetails(List<TransactionDetailDto> detailDtos);
}
