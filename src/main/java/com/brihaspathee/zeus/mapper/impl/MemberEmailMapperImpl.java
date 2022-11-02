package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.MemberEmail;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberEmailDto;
import com.brihaspathee.zeus.mapper.interfaces.MemberEmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 7:00 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEmailMapperImpl implements MemberEmailMapper {

    /**
     * Convert member email entity to member email dto
     * @param memberEmail
     * @return
     */
    @Override
    public TransactionMemberEmailDto emailToEmailDto(MemberEmail memberEmail) {
        if(memberEmail == null){
            return null;
        }
        TransactionMemberEmailDto emailDto = TransactionMemberEmailDto.builder()
                .memberEmailSK(memberEmail.getMemberEmailSK())
                .email(memberEmail.getEmail())
                .receivedDate(memberEmail.getReceivedDate())
                .createdDate(memberEmail.getCreatedDate())
                .updatedDate(memberEmail.getUpdatedDate())
                .build();
        return emailDto;
    }

    /**
     * Convert member email dto to member email
     * @param emailDto
     * @return
     */
    @Override
    public MemberEmail emailDtoToEmail(TransactionMemberEmailDto emailDto) {
        if(emailDto == null){
            return null;
        }
        MemberEmail email = MemberEmail.builder()
                .memberEmailSK(emailDto.getMemberEmailSK())
                .email(emailDto.getEmail())
                .receivedDate(emailDto.getReceivedDate())
                .createdDate(emailDto.getCreatedDate())
                .updatedDate(emailDto.getUpdatedDate())
                .build();
        return email;
    }

    /**
     * Convert member email entities to member email dtos
     * @param memberEmails
     * @return
     */
    @Override
    public List<TransactionMemberEmailDto> emailsToEmailDtos(List<MemberEmail> memberEmails) {

        return memberEmails.stream().map(this::emailToEmailDto).collect(Collectors.toList());
    }

    /**
     * Convert email dtos to email entities
     * @param emailDtos
     * @return
     */
    @Override
    public List<MemberEmail> emailDtosToEmails(List<TransactionMemberEmailDto> emailDtos) {

        return emailDtos.stream().map(this::emailDtoToEmail).collect(Collectors.toList());
    }
}
