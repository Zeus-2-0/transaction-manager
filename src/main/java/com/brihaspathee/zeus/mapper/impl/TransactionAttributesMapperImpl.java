package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionAttributes;
import com.brihaspathee.zeus.dto.transaction.TransactionAttributeDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionAttributesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:35 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionAttributesMapperImpl implements TransactionAttributesMapper {

    /**
     * Convert attribute entity to attribute dto
     * @param attribute
     * @return
     */
    @Override
    public TransactionAttributeDto attributeToAttributeDto(TransactionAttributes attribute) {
        if(attribute == null){
            return null;
        }
        TransactionAttributeDto attributeDto = TransactionAttributeDto.builder()
                .transactionAttributeSK(attribute.getTransactionAttributeSK())
                .transactionAttributeTypeCode(attribute.getTransactionAttributeTypeCode())
                .transactionAttributeValue(attribute.getTransactionAttributeValue())
                .createdDate(attribute.getCreatedDate())
                .updatedDate(attribute.getUpdatedDate())
                .build();
        return attributeDto;
    }

    /**
     * Convert attribute dto to attribute entity
     * @param attributeDto
     * @return
     */
    @Override
    public TransactionAttributes attributeDtoToAttribute(TransactionAttributeDto attributeDto) {
        if(attributeDto == null){
            return null;
        }
        TransactionAttributes attribute = TransactionAttributes.builder()
                .transactionAttributeSK(attributeDto.getTransactionAttributeSK())
                .transactionAttributeTypeCode(attributeDto.getTransactionAttributeTypeCode())
                .transactionAttributeValue(attributeDto.getTransactionAttributeValue())
                .createdDate(attributeDto.getCreatedDate())
                .updatedDate(attributeDto.getUpdatedDate())
                .build();
        return attribute;
    }

    /**
     * Convert attribute entities to attribute dtos
     * @param attributes
     * @return
     */
    @Override
    public List<TransactionAttributeDto> attributesToAttributeDtos(List<TransactionAttributes> attributes) {
        return attributes.stream().map(this::attributeToAttributeDto).collect(Collectors.toList());
    }

    /**
     * Convert attribute dtos to attribute entities
     * @param attributeDtos
     * @return
     */
    @Override
    public List<TransactionAttributes> attributeDtosToAttribute(List<TransactionAttributeDto> attributeDtos) {
        return attributeDtos.stream().map(this::attributeDtoToAttribute).collect(Collectors.toList());
    }
}
