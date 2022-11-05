package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.MemberRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.helper.interfaces.*;
import com.brihaspathee.zeus.mapper.interfaces.MemberMapper;
import com.brihaspathee.zeus.service.interfaces.TransactionMemberService;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:49 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionMemberServiceImpl implements TransactionMemberService {

    /**
     * Member mapper instance
     */
    private final MemberMapper memberMapper;

    /**
     * Member phone helper instance
     */
    private final MemberPhoneHelper phoneHelper;

    /**
     * Member address helper instance
     */
    private final MemberAddressHelper addressHelper;

    /**
     * Member identifier helper instance
     */
    private final MemberIdentifierHelper identifierHelper;

    /**
     * Member email helper instance
     */
    private final MemberEmailHelper emailHelper;

    /**
     * Member language helper instance
     */
    private final MemberLanguageHelper languageHelper;

    /**
     * Alternate contact helper instance
     */
    private final AlternateContactHelper alternateContactHelper;

    /**
     * Member Repository instance for CRUD operations
     */
    private final MemberRepository memberRepository;

    /**
     * Create Member
     * @param memberDtos
     */
    @Override
    public void createMember(List<TransactionMemberDto> memberDtos,
                             Transaction transaction) {
        memberDtos.stream().forEach(memberDto -> {
            Member member = memberMapper.memberDtoMember(memberDto);
            member.setTransaction(transaction);
            member.setTransactionMemberCode(ZeusRandomStringGenerator.randomString(15));
            member = memberRepository.save(member);
            phoneHelper.createMemberPhones(memberDto.getMemberPhones(), member);
            addressHelper.createMemberAddress(memberDto.getMemberAddresses(), member);
            identifierHelper.createMemberIdentifier(memberDto.getIdentifiers(), member);
            emailHelper.createMemberEmail(memberDto.getEmails(), member);
            languageHelper.createMemberLanguage(memberDto.getLanguages(), member);
            alternateContactHelper.createAlternateContact(memberDto.getAlternateContacts(), member);
        });

    }
}
