package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface MemberMapper {

    /**
     * Convert member entity to member dto
     * @param member
     * @return
     */
    TransactionMemberDto memberToMemberDto(Member member);

    /**
     * Convert member dto to member entity
     * @param memberDto
     * @return
     */
    Member memberDtoMember(TransactionMemberDto memberDto);

    /**
     * Convert member entities to member dtos
     * @param members
     * @return
     */
    List<TransactionMemberDto> membersToMemberDtos(List<Member> members);

    /**
     * Convert member dtos to member entities
     * @param memberDtos
     * @return
     */
    List<Member> memberDtosToMembers(List<TransactionMemberDto> memberDtos);
}
