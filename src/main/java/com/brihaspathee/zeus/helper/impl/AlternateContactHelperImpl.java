package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.AlternateContact;
import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.repository.AlternateContactRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionAlternateContactDto;
import com.brihaspathee.zeus.helper.interfaces.AlternateContactHelper;
import com.brihaspathee.zeus.mapper.interfaces.AlternateContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 3:24 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AlternateContactHelperImpl implements AlternateContactHelper {

    /**
     * Alternate contact mapper instance
     */
    private final AlternateContactMapper alternateContactMapper;

    /**
     * Alternate contact repository instance
     */
    private final AlternateContactRepository alternateContactRepository;

    /**
     * Create alternate contact
     * @param alternateContactDtos
     * @param member
     */
    @Override
    public List<AlternateContact> createAlternateContact(List<TransactionAlternateContactDto> alternateContactDtos, Member member) {
        List<AlternateContact> alternateContacts = new ArrayList<>();
        if(alternateContactDtos != null && alternateContactDtos.size() >0){
            alternateContactDtos.stream().forEach(alternateContactDto -> {
                AlternateContact alternateContact = alternateContactMapper.contactDtoToContact(alternateContactDto);
                alternateContact.setMember(member);
                alternateContacts.add(alternateContactRepository.save(alternateContact));
            });
        }
        if (alternateContacts.size() > 0){
            return alternateContacts;
        }else {
            return null;
        }

    }
}
