package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberPhone;
import com.brihaspathee.zeus.domain.repository.MemberPhoneRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberPhoneDto;
import com.brihaspathee.zeus.helper.interfaces.MemberPhoneHelper;
import com.brihaspathee.zeus.mapper.interfaces.MemberPhoneMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 3:21 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberPhoneHelperImpl implements MemberPhoneHelper {

    /**
     * Member Phone mapper instance
     */
    private final MemberPhoneMapper phoneMapper;

    /**
     * Member Phone repository to perform CRUD operations
     */
    private final MemberPhoneRepository phoneRepository;

    /**
     * Create member phone record
     * @param phoneDtos
     * @param member
     */
    @Override
    public List<MemberPhone> createMemberPhones(List<TransactionMemberPhoneDto> phoneDtos, Member member) {
        List<MemberPhone> phones = new ArrayList<>();
        if(phoneDtos != null && phoneDtos.size() > 0){
            phoneDtos.stream().forEach(phoneDto -> {
                MemberPhone phone = phoneMapper.phoneDtoToPhone(phoneDto);
                phone.setMember(member);
                phones.add(phoneRepository.save(phone));
            });
        }
        if(phones.size() > 0){
            return phones;
        }else{
            return null;
        }
    }
}
