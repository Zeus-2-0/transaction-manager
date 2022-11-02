package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.MemberLanguage;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberLanguageDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:49 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface MemberLanguageMapper {

    /**
     * Convert language entity to language dto
     * @param language
     * @return
     */
    TransactionMemberLanguageDto languageToLanguageDto(MemberLanguage language);

    /**
     * Convert language dto to language entity
     * @param languageDto
     * @return
     */
    MemberLanguage languageDtoToLanguage(TransactionMemberLanguageDto languageDto);

    /**
     * Convert language entities to language dtos
     * @param languages
     * @return
     */
    List<TransactionMemberLanguageDto> languagesToLanguageDtos(List<MemberLanguage> languages);

    /**
     * Convert language dtos to language entities
     * @param languageDtos
     * @return
     */
    List<MemberLanguage> languageDtosToLanguages(List<TransactionMemberLanguageDto> languageDtos);
}
