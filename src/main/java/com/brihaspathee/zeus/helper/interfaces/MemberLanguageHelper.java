package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberLanguage;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberLanguageDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 12:28 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface MemberLanguageHelper {

    List<MemberLanguage> createMemberLanguage(List<TransactionMemberLanguageDto> languageDtos, Member member);
}
