package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.MemberPhone;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberPhoneDto;

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
public interface MemberPhoneMapper {

    /**
     * Convert phone entity to phone dto
     * @param memberPhone
     * @return
     */
    TransactionMemberPhoneDto phoneToPhoneDto(MemberPhone memberPhone);

    /**
     * Convert phone dto to phone entity
     * @param memberPhoneDto
     * @return
     */
    MemberPhone phoneDtoToPhone(TransactionMemberPhoneDto memberPhoneDto);

    /**
     * Convert phone entities to phone dtos
     * @param memberPhones
     * @return
     */
    List<TransactionMemberPhoneDto> phonesToPhoneDtos(List<MemberPhone> memberPhones);

    /**
     * Convert phone dtos to phone entities
     * @param memberPhoneDtos
     * @return
     */
    List<MemberPhone> phoneDtosToPhones(List<TransactionMemberPhoneDto> memberPhoneDtos);
}
