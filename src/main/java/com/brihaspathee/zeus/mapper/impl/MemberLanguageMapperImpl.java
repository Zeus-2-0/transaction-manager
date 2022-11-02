package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.MemberLanguage;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberLanguageDto;
import com.brihaspathee.zeus.mapper.interfaces.MemberLanguageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 1:27 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberLanguageMapperImpl implements MemberLanguageMapper {

    /**
     * Convert language entity to language dto
     * @param language
     * @return
     */
    @Override
    public TransactionMemberLanguageDto languageToLanguageDto(MemberLanguage language) {
        if(language == null){
            return null;
        }
        TransactionMemberLanguageDto languageDto = TransactionMemberLanguageDto.builder()
                .memberLanguageSK(language.getMemberLanguageSK())
                .languageTypeCode(language.getLanguageTypeCode())
                .languageCode(language.getLanguageCode())
                .receivedDate(language.getReceivedDate())
                .createdDate(language.getCreatedDate())
                .updatedDate(language.getUpdatedDate())
                .build();
        return languageDto;
    }

    /**
     * Convert language dto to language entity
     * @param languageDto
     * @return
     */
    @Override
    public MemberLanguage languageDtoToLanguage(TransactionMemberLanguageDto languageDto) {
        if(languageDto == null){
            return null;
        }
        MemberLanguage language = MemberLanguage.builder()
                .memberLanguageSK(languageDto.getMemberLanguageSK())
                .languageTypeCode(languageDto.getLanguageTypeCode())
                .languageCode(languageDto.getLanguageCode())
                .receivedDate(languageDto.getReceivedDate())
                .createdDate(languageDto.getCreatedDate())
                .updatedDate(languageDto.getUpdatedDate())
                .build();
        return language;
    }

    /**
     * Convert language entities to language dtos
     * @param languages
     * @return
     */
    @Override
    public List<TransactionMemberLanguageDto> languagesToLanguageDtos(List<MemberLanguage> languages) {

        return languages.stream().map(this::languageToLanguageDto).collect(Collectors.toList());
    }

    /**
     * Convert language dtos to language entities
     * @param languageDtos
     * @return
     */
    @Override
    public List<MemberLanguage> languageDtosToLanguages(List<TransactionMemberLanguageDto> languageDtos) {
        return languageDtos.stream().map(this::languageDtoToLanguage).collect(Collectors.toList());
    }
}
