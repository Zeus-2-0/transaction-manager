package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.MemberEmail;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberEmailDto;

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
public interface MemberEmailMapper {

    /**
     * Convert member email entity to member email dto
     * @param memberEmail
     * @return
     */
    TransactionMemberEmailDto emailToEmailDto(MemberEmail memberEmail);

    /**
     * Convert member email dto to member email
     * @param emailDto
     * @return
     */
    MemberEmail emailDtoToEmail(TransactionMemberEmailDto emailDto);

    /**
     * Convert member email entities to member email dtos
     * @param memberEmails
     * @return
     */
    List<TransactionMemberEmailDto> emailsToEmailDtos(List<MemberEmail> memberEmails);

    /**
     * Convert email dtos to email entities
     * @param emailDtos
     * @return
     */
    List<MemberEmail> emailDtosToEmails(List<TransactionMemberEmailDto> emailDtos);
}
