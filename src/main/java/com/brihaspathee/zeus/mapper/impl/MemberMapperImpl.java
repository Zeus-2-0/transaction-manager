package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberIdentifier;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.mapper.interfaces.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:28 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberMapperImpl implements MemberMapper {

    /**
     * Member Address Mapper Instance to convert dto to entity and vice versa
     */
    private final MemberAddressMapper addressMapper;

    /**
     * Member Email Mapper Instance to convert dto to entity and vice versa
     */
    private final MemberEmailMapper emailMapper;

    /**
     * Member Identifier Mapper Instance to convert dto to entity and vice versa
     */
    private final MemberIdentifierMapper identifierMapper;

    /**
     * Member Language Mapper Instance to convert dto to entity and vice versa
     */
    private final MemberLanguageMapper languageMapper;

    /**
     * Member Phone Mapper Instance to convert dto to entity and vice versa
     */
    private final MemberPhoneMapper phoneMapper;

    /**
     * Alternate Contact Mapper Instance to convert dto to entity and vice versa
     */
    private final AlternateContactMapper alternateContactMapper;

    /**
     * Convert member entity to member dto
     * @param member
     * @return
     */
    @Override
    public TransactionMemberDto memberToMemberDto(Member member) {
        if(member == null){
            return null;
        }
        TransactionMemberDto memberDto = TransactionMemberDto.builder()
                .memberSK(member.getMemberSK())
                .transactionMemberCode(member.getTransactionMemberCode())
                .relationshipTypeCode(member.getRelationshipTypeCode())
                .transactionTypeCode(member.getTransactionTypeCode())
                .effectiveDate(member.getEffectiveDate())
                .endDate(member.getEndDate())
                .reasonTypeCode(member.getReasonTypeCode())
                .firstName(member.getFirstName())
                .middleName(member.getMiddleName())
                .lastName(member.getLastName())
                .memberRate(member.getMemberRate())
                .tobaccoIndicator(member.getTobaccoIndicator())
                .productCatalogRate(member.getProductCatalogRate())
                .dateOfBirth(member.getDateOfBirth())
                .genderTypeCode(member.getGenderTypeCode())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .build();
        // Check and map alternate contacts if available for the member
        if(member.getAlternateContacts() != null && member.getAlternateContacts().size() > 0){
            memberDto.setAlternateContacts(
                    member.getAlternateContacts()
                            .stream()
                            .map(alternateContactMapper :: contactToContactDto)
                            .collect(Collectors.toList()));
        }
        // Check and map addresses if available for the member
        if(member.getMemberAddresses() != null && member.getMemberAddresses().size() > 0){
            memberDto.setMemberAddresses(
                    member.getMemberAddresses()
                            .stream()
                            .map(addressMapper :: addressToAddressDto)
                            .collect(Collectors.toList()));
        }
        // Check and map emails if available for the member
        if(member.getMemberEmails() != null && member.getMemberEmails().size() > 0){
            memberDto.setEmails(
                    member.getMemberEmails()
                            .stream()
                            .map(emailMapper :: emailToEmailDto)
                            .collect(Collectors.toList()));
        }
        // Check and map identifiers if available for the member
        if(member.getMemberIdentifiers() != null && member.getMemberIdentifiers().size() > 0){
            memberDto.setIdentifiers(
                    member.getMemberIdentifiers()
                            .stream()
                            .map(identifierMapper :: identifierToIdentifierDto)
                            .collect(Collectors.toList()));
        }
        // Check and map languages if available for the member
        if(member.getMemberLanguages() != null && member.getMemberLanguages().size() > 0){
            memberDto.setLanguages(
                    member.getMemberLanguages()
                            .stream()
                            .map(languageMapper :: languageToLanguageDto)
                            .collect(Collectors.toList()));
        }
        // Check and map phones if available for the member
        if(member.getMemberPhones() != null && member.getMemberPhones().size() > 0){
            memberDto.setMemberPhones(
                    member.getMemberPhones()
                            .stream()
                            .map(phoneMapper :: phoneToPhoneDto)
                            .collect(Collectors.toList()));
        }
        return memberDto;
    }

    /**
     * Convert member dto to member entity
     * @param memberDto
     * @return
     */
    @Override
    public Member memberDtoMember(TransactionMemberDto memberDto) {
        if(memberDto == null){
            return null;
        }
        Member member = Member.builder()
                .memberSK(memberDto.getMemberSK())
                .transactionMemberCode(memberDto.getTransactionMemberCode())
                .relationshipTypeCode(memberDto.getRelationshipTypeCode())
                .transactionTypeCode(memberDto.getTransactionTypeCode())
                .effectiveDate(memberDto.getEffectiveDate())
                .endDate(memberDto.getEndDate())
                .reasonTypeCode(memberDto.getReasonTypeCode())
                .firstName(memberDto.getFirstName())
                .middleName(memberDto.getMiddleName())
                .lastName(memberDto.getLastName())
                .memberRate(memberDto.getMemberRate())
                .tobaccoIndicator(memberDto.getTobaccoIndicator())
                .productCatalogRate(memberDto.getProductCatalogRate())
                .dateOfBirth(memberDto.getDateOfBirth())
                .genderTypeCode(memberDto.getGenderTypeCode())
                .createdDate(memberDto.getCreatedDate())
                .updatedDate(memberDto.getUpdatedDate())
                .build();
        // Check and map alternate contacts if available for the member
        if(memberDto.getAlternateContacts() != null && memberDto.getAlternateContacts().size() > 0){
            member.setAlternateContacts(
                    memberDto.getAlternateContacts()
                            .stream()
                            .map(alternateContactMapper :: contactDtoToContact)
                            .collect(Collectors.toList()));
        }
        // Check and map addresses if available for the member
        if(memberDto.getMemberAddresses() != null && memberDto.getMemberAddresses().size() > 0){
            member.setMemberAddresses(
                    memberDto.getMemberAddresses()
                            .stream()
                            .map(addressMapper :: addressDtoToAddress)
                            .collect(Collectors.toList()));
        }
        // Check and map emails if available for the member
        if(memberDto.getEmails() != null && memberDto.getEmails().size() > 0){
            member.setMemberEmails(
                    memberDto.getEmails()
                            .stream()
                            .map(emailMapper :: emailDtoToEmail)
                            .collect(Collectors.toList()));
        }
        // Check and map identifiers if available for the member
        if(memberDto.getIdentifiers() != null && memberDto.getIdentifiers().size() > 0){
            member.setMemberIdentifiers(
                    memberDto.getIdentifiers()
                            .stream()
                            .map(identifierMapper :: identifierDtoToIdentifier)
                            .collect(Collectors.toList()));
        }
        // Check and map languages if available for the member
        if(memberDto.getLanguages() != null && memberDto.getLanguages().size() > 0){
            member.setMemberLanguages(
                    memberDto.getLanguages()
                            .stream()
                            .map(languageMapper :: languageDtoToLanguage)
                            .collect(Collectors.toList()));
        }
        // Check and map phones if available for the member
        if(memberDto.getMemberPhones() != null && memberDto.getMemberPhones().size() > 0){
            member.setMemberPhones(
                    memberDto.getMemberPhones()
                            .stream()
                            .map(phoneMapper :: phoneDtoToPhone)
                            .collect(Collectors.toList()));
        }
        return member;
    }

    /**
     * Convert member entities to member dtos
     * @param members
     * @return
     */
    @Override
    public List<TransactionMemberDto> membersToMemberDtos(List<Member> members) {

        return members.stream().map(this::memberToMemberDto).collect(Collectors.toList());
    }

    /**
     * Convert member dtos to member entities
     * @param memberDtos
     * @return
     */
    @Override
    public List<Member> memberDtosToMembers(List<TransactionMemberDto> memberDtos) {

        return memberDtos.stream().map(this::memberDtoMember).collect(Collectors.toList());
    }
}
