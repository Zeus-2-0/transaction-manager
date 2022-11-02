package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.MemberPhone;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberPhoneDto;
import com.brihaspathee.zeus.mapper.interfaces.MemberPhoneMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:30 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberPhoneMapperImpl implements MemberPhoneMapper {

    /**
     * Convert phone entity to phone dto
     * @param memberPhone
     * @return
     */
    @Override
    public TransactionMemberPhoneDto phoneToPhoneDto(MemberPhone memberPhone) {
        if(memberPhone == null){
            return null;
        }
        TransactionMemberPhoneDto phoneDto = TransactionMemberPhoneDto.builder()
                .memberPhoneSK(memberPhone.getMemberPhoneSK())
                .phoneTypeCode(memberPhone.getPhoneTypeCode())
                .phoneNumber(memberPhone.getPhoneNumber())
                .receivedDate(memberPhone.getReceivedDate())
                .createdDate(memberPhone.getCreatedDate())
                .updatedDate(memberPhone.getUpdatedDate())
                .build();
        return phoneDto;
    }

    /**
     * Convert phone dto to phone entity
     * @param memberPhoneDto
     * @return
     */
    @Override
    public MemberPhone phoneDtoToPhone(TransactionMemberPhoneDto memberPhoneDto) {
        if(memberPhoneDto == null){
            return null;
        }
        MemberPhone phone = MemberPhone.builder()
                .memberPhoneSK(memberPhoneDto.getMemberPhoneSK())
                .phoneTypeCode(memberPhoneDto.getPhoneTypeCode())
                .phoneNumber(memberPhoneDto.getPhoneNumber())
                .receivedDate(memberPhoneDto.getReceivedDate())
                .createdDate(memberPhoneDto.getCreatedDate())
                .updatedDate(memberPhoneDto.getUpdatedDate())
                .build();
        return phone;
    }

    /**
     * Convert phone entities to phone dtos
     * @param memberPhones
     * @return
     */
    @Override
    public List<TransactionMemberPhoneDto> phonesToPhoneDtos(List<MemberPhone> memberPhones) {

        return memberPhones.stream().map(this::phoneToPhoneDto).collect(Collectors.toList());
    }

    /**
     * Convert phone dtos to phone entities
     * @param memberPhoneDtos
     * @return
     */
    @Override
    public List<MemberPhone> phoneDtosToPhones(List<TransactionMemberPhoneDto> memberPhoneDtos) {

        return memberPhoneDtos.stream().map(this::phoneDtoToPhone).collect(Collectors.toList());
    }
}
