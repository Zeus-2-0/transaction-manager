package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberPhoneDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 12:25 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface MemberPhoneHelper {

    void createMemberPhones(List<TransactionMemberPhoneDto> phoneDtos, Member member);
}
