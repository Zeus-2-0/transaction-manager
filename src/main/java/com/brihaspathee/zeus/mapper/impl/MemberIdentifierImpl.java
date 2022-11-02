package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.MemberIdentifier;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import com.brihaspathee.zeus.mapper.interfaces.MemberIdentifierMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 7:07 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberIdentifierImpl implements MemberIdentifierMapper {

    /**
     * Convert identifier entity to identifier dto
     * @param identifier
     * @return
     */
    @Override
    public TransactionMemberIdentifierDto identifierToIdentifierDto(MemberIdentifier identifier) {
        if(identifier == null){
            return null;
        }
        TransactionMemberIdentifierDto identifierDto = TransactionMemberIdentifierDto.builder()
                .memberIdentifierSK(identifier.getMemberIdentifierSK())
                .identifierTypeCode(identifier.getIdentifierTypeCode())
                .identifierValue(identifier.getIdentifierValue())
                .receivedDate(identifier.getReceivedDate())
                .createdDate(identifier.getCreatedDate())
                .updatedDate(identifier.getUpdatedDate())
                .build();
        return identifierDto;
    }

    /**
     * Convert identifier dto to identifier entity
     * @param identifierDto
     * @return
     */
    @Override
    public MemberIdentifier identifierDtoToIdentifier(TransactionMemberIdentifierDto identifierDto) {
        if(identifierDto == null){
            return null;
        }
        MemberIdentifier identifier = MemberIdentifier.builder()
                .memberIdentifierSK(identifierDto.getMemberIdentifierSK())
                .identifierTypeCode(identifierDto.getIdentifierTypeCode())
                .identifierValue(identifierDto.getIdentifierValue())
                .receivedDate(identifierDto.getReceivedDate())
                .createdDate(identifierDto.getCreatedDate())
                .updatedDate(identifierDto.getUpdatedDate())
                .build();
        return identifier;
    }

    /**
     * Convert identifier entities to identifier dtos
     * @param identifiers
     * @return
     */
    @Override
    public List<TransactionMemberIdentifierDto> identifiersToIdentifierDtos(List<MemberIdentifier> identifiers) {
        return identifiers.stream().map(this::identifierToIdentifierDto).collect(Collectors.toList());
    }

    /**
     * Convert identifier dtos to identifier entities
     * @param identifierDtos
     * @return
     */
    @Override
    public List<MemberIdentifier> identifierDtosToIdentifiers(List<TransactionMemberIdentifierDto> identifierDtos) {
        return identifierDtos.stream().map(this::identifierDtoToIdentifier).collect(Collectors.toList());
    }
}
