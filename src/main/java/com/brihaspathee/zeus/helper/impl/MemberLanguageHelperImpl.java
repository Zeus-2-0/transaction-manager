package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberLanguage;
import com.brihaspathee.zeus.domain.repository.MemberLanguageRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberLanguageDto;
import com.brihaspathee.zeus.helper.interfaces.MemberLanguageHelper;
import com.brihaspathee.zeus.mapper.interfaces.MemberLanguageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 2:34 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberLanguageHelperImpl implements MemberLanguageHelper {

    /**
     * Member language mapper instance
     */
    private final MemberLanguageMapper languageMapper;

    /**
     * Member language repository instance
     */
    private final MemberLanguageRepository languageRepository;

    /**
     * Create member language
     * @param languageDtos
     * @param member
     */
    @Override
    public void createMemberLanguage(List<TransactionMemberLanguageDto> languageDtos, Member member) {
        if(languageDtos != null && languageDtos.size() > 0){
            languageDtos.stream().forEach(languageDto -> {
                MemberLanguage language = languageMapper.languageDtoToLanguage(languageDto);
                language.setMember(member);
                languageRepository.save(language);
            });
        }

    }
}
