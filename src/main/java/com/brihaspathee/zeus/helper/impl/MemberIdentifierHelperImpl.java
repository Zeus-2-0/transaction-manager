package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberIdentifier;
import com.brihaspathee.zeus.domain.repository.MemberIdentifierRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import com.brihaspathee.zeus.helper.interfaces.MemberIdentifierHelper;
import com.brihaspathee.zeus.mapper.interfaces.MemberIdentifierMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 2:31 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberIdentifierHelperImpl implements MemberIdentifierHelper {

    /**
     * Member identifier mapper instance
     */
    private final MemberIdentifierMapper identifierMapper;

    /**
     * Member identifier repository instance to perform CRUD operations
     */
    private final MemberIdentifierRepository identifierRepository;

    /**
     * Create member identifier
     * @param identifierDtos
     * @param member
     */
    @Override
    public List<MemberIdentifier> createMemberIdentifier(List<TransactionMemberIdentifierDto> identifierDtos, Member member) {
        List<MemberIdentifier> identifiers = new ArrayList<>();
        if(identifierDtos != null && identifierDtos.size() > 0){
            identifierDtos.stream().forEach(identifierDto -> {
                MemberIdentifier identifier = identifierMapper.identifierDtoToIdentifier(identifierDto);
                identifier.setMember(member);
                identifiers.add(identifierRepository.save(identifier));
            });
        }
        if(identifiers.size() > 0){
            return identifiers;
        }else {
            return null;
        }
    }
}
