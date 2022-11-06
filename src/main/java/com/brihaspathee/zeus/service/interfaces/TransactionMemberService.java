package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:48 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionMemberService {

    /**
     * Create a member for the transaction
     * @param memberDtos
     * @param transaction
     */
    List<Member> createMember(List<TransactionMemberDto> memberDtos,
                              Transaction transaction);
}
