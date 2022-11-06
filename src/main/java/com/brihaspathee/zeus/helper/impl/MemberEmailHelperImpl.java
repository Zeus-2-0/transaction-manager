package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberEmail;
import com.brihaspathee.zeus.domain.repository.MemberEmailRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberEmailDto;
import com.brihaspathee.zeus.helper.interfaces.MemberEmailHelper;
import com.brihaspathee.zeus.mapper.interfaces.MemberEmailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 2:28 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEmailHelperImpl implements MemberEmailHelper {

    /**
     * Member email mapper instance
     */
    private final MemberEmailMapper emailMapper;

    /**
     * Member email repository for performing CRUD operations
     */
    private final MemberEmailRepository emailRepository;

    /**
     * Create member email
     * @param emailDtos
     * @param member
     */
    @Override
    public List<MemberEmail> createMemberEmail(List<TransactionMemberEmailDto> emailDtos,
                                  Member member) {
        List<MemberEmail> emails = new ArrayList<>();
        if(emailDtos != null && emailDtos.size() > 0){
            emailDtos.stream().forEach(emailDto -> {
                MemberEmail email = emailMapper.emailDtoToEmail(emailDto);
                email.setMember(member);
                emails.add(emailRepository.save(email));
            });
        }
        if(emails.size() > 0){
            return emails;
        }else {
            return null;
        }
    }
}
