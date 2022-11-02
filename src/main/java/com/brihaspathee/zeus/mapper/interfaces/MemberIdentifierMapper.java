package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.MemberIdentifier;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:49 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface MemberIdentifierMapper {

    /**
     * Convert identifier entity to identifier dto
     * @param identifier
     * @return
     */
    TransactionMemberIdentifierDto identifierToIdentifierDto(MemberIdentifier identifier);

    /**
     * Convert identifier dto to identifier entity
     * @param identifierDto
     * @return
     */
    MemberIdentifier identifierDtoToIdentifier(TransactionMemberIdentifierDto identifierDto);

    /**
     * Convert identifier entities to identifier dtos
     * @param identifiers
     * @return
     */
    List<TransactionMemberIdentifierDto> identifiersToIdentifierDtos(List<MemberIdentifier> identifiers);

    /**
     * Convert identifier dtos to identifier entities
     * @param identifierDtos
     * @return
     */
    List<MemberIdentifier> identifierDtosToIdentifiers(List<TransactionMemberIdentifierDto> identifierDtos);
}
