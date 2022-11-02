package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.AlternateContact;
import com.brihaspathee.zeus.dto.transaction.TransactionAlternateContactDto;
import com.brihaspathee.zeus.mapper.interfaces.AlternateContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 6:23 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlternateContactMapperImpl implements AlternateContactMapper {

    /**
     * Alternate Contact entity to alternate contact dto
     * @param alternateContact
     * @return
     */
    @Override
    public TransactionAlternateContactDto contactToContactDto(AlternateContact alternateContact) {
        if(alternateContact == null){
            return null;
        }
        TransactionAlternateContactDto alternateContactDto = TransactionAlternateContactDto.builder()
                .alternateContactSK(alternateContact.getAlternateContactSK())
                .alternateContactTypeCode(alternateContact.getAlternateContactTypeCode())
                .firstName(alternateContact.getFirstName())
                .middleName(alternateContact.getMiddleName())
                .lastName(alternateContact.getLastName())
                .identifierTypeCode(alternateContact.getIdentifierTypeCode())
                .identifierValue(alternateContact.getIdentifierValue())
                .phoneTypeCode(alternateContact.getPhoneTypeCode())
                .phoneNumber(alternateContact.getPhoneNumber())
                .email(alternateContact.getEmail())
                .addressLine1(alternateContact.getAddressLine1())
                .addressLine2(alternateContact.getAddressLine2())
                .city(alternateContact.getCity())
                .stateTypeCode(alternateContact.getStateTypeCode())
                .zipCode(alternateContact.getZipCode())
                .receivedDate(alternateContact.getReceivedDate())
                .createdDate(alternateContact.getCreatedDate())
                .updatedDate(alternateContact.getUpdatedDate())
                .build();
        return alternateContactDto;
    }

    /**
     * Alternate Contact Dto to alternate Contact entity
     * @param alternateContactDto
     * @return
     */
    @Override
    public AlternateContact contactDtoToContact(TransactionAlternateContactDto alternateContactDto) {
        if(alternateContactDto == null){
            return null;
        }
        AlternateContact alternateContact = AlternateContact.builder()
                .alternateContactSK(alternateContactDto.getAlternateContactSK())
                .alternateContactTypeCode(alternateContactDto.getAlternateContactTypeCode())
                .firstName(alternateContactDto.getFirstName())
                .middleName(alternateContactDto.getMiddleName())
                .lastName(alternateContactDto.getLastName())
                .identifierTypeCode(alternateContactDto.getIdentifierTypeCode())
                .identifierValue(alternateContactDto.getIdentifierValue())
                .phoneTypeCode(alternateContactDto.getPhoneTypeCode())
                .phoneNumber(alternateContactDto.getPhoneNumber())
                .email(alternateContactDto.getEmail())
                .addressLine1(alternateContactDto.getAddressLine1())
                .addressLine2(alternateContactDto.getAddressLine2())
                .city(alternateContactDto.getCity())
                .stateTypeCode(alternateContactDto.getStateTypeCode())
                .zipCode(alternateContactDto.getZipCode())
                .receivedDate(alternateContactDto.getReceivedDate())
                .createdDate(alternateContactDto.getCreatedDate())
                .updatedDate(alternateContactDto.getUpdatedDate())
                .build();
        return alternateContact;
    }

    /**
     * Alternate contact entities to alternate contact dtos
     * @param alternateContacts
     * @return
     */
    @Override
    public List<TransactionAlternateContactDto> contactsToContactDtos(List<AlternateContact> alternateContacts) {
        return alternateContacts.stream().map(this::contactToContactDto).collect(Collectors.toList());
    }

    /**
     * Alternate contact dtos to alternate contact entities
     * @param contactDtos
     * @return
     */
    @Override
    public List<AlternateContact> contactDtosToContacts(List<TransactionAlternateContactDto> contactDtos) {
        return contactDtos.stream().map(this::contactDtoToContact).collect(Collectors.toList());
    }
}
