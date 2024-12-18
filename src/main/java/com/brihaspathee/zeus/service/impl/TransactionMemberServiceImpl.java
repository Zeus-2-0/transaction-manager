package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.MemberRepository;
import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.rate.RateResponseDto;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.helper.interfaces.*;
import com.brihaspathee.zeus.mapper.interfaces.MemberMapper;
import com.brihaspathee.zeus.service.interfaces.PlanCatalogService;
import com.brihaspathee.zeus.service.interfaces.TransactionMemberService;
import com.brihaspathee.zeus.util.TransactionManagerUtil;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
     * Plan Catalog Service instance to get the rates for the members in the transaction
     */
    private final PlanCatalogService planCatalogService;

    /**
     * Transaction manage utility class
     */
    private final TransactionManagerUtil transactionManagerUtil;

    /**
     * Create Member
     * @param memberDtos
     */
    @Override
    public List<Member> createMember(List<TransactionMemberDto> memberDtos,
                             Transaction transaction) {
        List<Member> members = new ArrayList<>();
//        getMemberRates(memberDtos,
//                transaction.getTransactionDetail().getPlanId(),
//                transaction.getTransactionDetail().getEffectiveDate());
        memberDtos.forEach(memberDto -> {
            Member member = memberMapper.memberDtoMember(memberDto);
            member.setTransaction(transaction);
            member.setTransactionMemberCode(ZeusRandomStringGenerator.randomString(15));
            member = memberRepository.save(member);
            member.setMemberPhones(
                    phoneHelper.createMemberPhones(
                            memberDto.getMemberPhones(),
                            member));
            member.setMemberAddresses(
                    addressHelper.createMemberAddress(
                            memberDto.getMemberAddresses(),
                            member));
            member.setMemberIdentifiers(
                    identifierHelper.createMemberIdentifier(
                            memberDto.getIdentifiers(),
                            member));
            member.setMemberEmails(
                    emailHelper.createMemberEmail(
                            memberDto.getEmails(),
                            member));
            member.setMemberLanguages(
                    languageHelper.createMemberLanguage(
                            memberDto.getLanguages(),
                            member));
            member.setAlternateContacts(
                    alternateContactHelper.createAlternateContact(
                            memberDto.getAlternateContacts(),
                            member));
            members.add(member);
        });
        return members;
    }

    /**
     * Get member rates
     * @param matchedAccount - the account that was matched for the transaction
     * @param transactionMemberDtos - the members in the transaction
     * @param planId - the plan id of the transaction
     * @param effectiveDate - the effective date of the transaction
     */
    private void getMemberRates(AccountDto matchedAccount, List<TransactionMemberDto> transactionMemberDtos,
                                String planId,
                                LocalDate effectiveDate){
        planCatalogService.getMemberRates(matchedAccount, transactionMemberDtos, planId, effectiveDate);
    }

    /**
     * Populate the product catalog and member rates
     * @param matchedAccount - the account that was matched
     * @param transactionDto - the transaction dto
     */
    public void populateMemberRates(AccountDto matchedAccount, TransactionDto transactionDto){
        List<TransactionMemberDto> memberDtos = transactionDto.getMembers();
        getMemberRates(matchedAccount, memberDtos,
                transactionDto.getTransactionDetail().getPlanId(),
                transactionDto.getTransactionDetail().getEffectiveDate());
        memberDtos.forEach(memberDto -> {
            log.info("Member Code:{}", memberDto.getTransactionMemberCode());
            log.info("Member Rate:{}", memberDto.getMemberRate());
            log.info("Product Catalog rate:{}", memberDto.getProductCatalogRate());
            Member member = memberMapper.memberDtoMember(memberDto);
            member.setTransaction(Transaction.builder()
                    .transactionSK(transactionDto.getTransactionSK())
                    .build());
            memberRepository.save(member);
        });
    }
}
