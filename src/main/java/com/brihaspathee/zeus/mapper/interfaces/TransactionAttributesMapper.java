package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionAttributes;
import com.brihaspathee.zeus.dto.transaction.TransactionAttributeDto;

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
public interface TransactionAttributesMapper {

    /**
     * Convert attribute entity to attribute dto
     * @param attribute
     * @return
     */
    TransactionAttributeDto attributeToAttributeDto(TransactionAttributes attribute);

    /**
     * Convert attribute dto to attribute entity
     * @param attributeDto
     * @return
     */
    TransactionAttributes attributeDtoToAttribute(TransactionAttributeDto attributeDto);

    /**
     * Convert attribute entities to attribute dtos
     * @param attributes
     * @return
     */
    List<TransactionAttributeDto> attributesToAttributeDtos(List<TransactionAttributes> attributes);

    /**
     * Convert attribute dtos to attribute entities
     * @param attributeDtos
     * @return
     */
    List<TransactionAttributes> attributeDtosToAttribute(List<TransactionAttributeDto> attributeDtos);
}
