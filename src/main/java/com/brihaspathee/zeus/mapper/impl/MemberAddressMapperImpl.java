package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.MemberAddress;
import com.brihaspathee.zeus.dto.account.MemberAddressDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;
import com.brihaspathee.zeus.mapper.interfaces.MemberAddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 6:51 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAddressMapperImpl implements MemberAddressMapper {

    /**
     * Convert member address entity to member address dto
     * @param memberAddress
     * @return
     */
    @Override
    public TransactionMemberAddressDto addressToAddressDto(MemberAddress memberAddress) {
        if(memberAddress == null){
            return null;
        }
        TransactionMemberAddressDto addressDto = TransactionMemberAddressDto.builder()
                .memberAddressSK(memberAddress.getMemberAddressSK())
                .addressTypeCode(memberAddress.getAddressTypeCode())
                .addressLine1(memberAddress.getAddressLine1())
                .addressLine2(memberAddress.getAddressLine2())
                .city(memberAddress.getCity())
                .stateTypeCode(memberAddress.getStateTypeCode())
                .zipCode(memberAddress.getZipCode())
                .countyCode(memberAddress.getCountyCode())
                .receivedDate(memberAddress.getReceivedDate())
                .createdDate(memberAddress.getCreatedDate())
                .updatedDate(memberAddress.getUpdatedDate())
                .build();
        return addressDto;
    }

    /**
     * Convert address dto to address entity
     * @param addressDto
     * @return
     */
    @Override
    public MemberAddress addressDtoToAddress(TransactionMemberAddressDto addressDto) {
        if(addressDto == null){
            return null;
        }
        MemberAddress address = MemberAddress.builder()
                .memberAddressSK(addressDto.getMemberAddressSK())
                .addressTypeCode(addressDto.getAddressTypeCode())
                .addressLine1(addressDto.getAddressLine1())
                .addressLine2(addressDto.getAddressLine2())
                .city(addressDto.getCity())
                .stateTypeCode(addressDto.getStateTypeCode())
                .zipCode(addressDto.getZipCode())
                .countyCode(addressDto.getCountyCode())
                .receivedDate(addressDto.getReceivedDate())
                .createdDate(addressDto.getCreatedDate())
                .updatedDate(addressDto.getUpdatedDate())
                .build();
        return address;
    }

    /**
     * Convert address entities to address dtos
     * @param addresses
     * @return
     */
    @Override
    public List<TransactionMemberAddressDto> addressesToAddressDtos(List<MemberAddress> addresses) {

        return addresses.stream().map(this::addressToAddressDto).collect(Collectors.toList());
    }

    /**
     * Convert address dtos to address entities
     * @param addressDtos
     * @return
     */
    @Override
    public List<MemberAddress> addressDtosToAddresses(List<TransactionMemberAddressDto> addressDtos) {

        return addressDtos.stream().map(this::addressDtoToAddress).collect(Collectors.toList());
    }
}
