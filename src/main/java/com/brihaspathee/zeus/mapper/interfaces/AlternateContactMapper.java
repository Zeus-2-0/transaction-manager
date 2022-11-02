package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.AlternateContact;
import com.brihaspathee.zeus.dto.transaction.TransactionAlternateContactDto;

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
public interface AlternateContactMapper {

    /**
     * Alternate Contact entity to alternate contact dto
     * @param alternateContact
     * @return
     */
    TransactionAlternateContactDto contactToContactDto(AlternateContact alternateContact);

    /**
     * Alternate Contact Dto to alternate Contact entity
     * @param alternateContactDto
     * @return
     */
    AlternateContact contactDtoToContact(TransactionAlternateContactDto alternateContactDto);

    /**
     * Alternate contact entities to alternate contact dtos
     * @param alternateContacts
     * @return
     */
    List<TransactionAlternateContactDto> contactsToContactDtos(List<AlternateContact> alternateContacts);

    /**
     * Alternate contact dtos to alternate contact entities
     * @param contactDtos
     * @return
     */
    List<AlternateContact> contactDtosToContacts(List<TransactionAlternateContactDto> contactDtos);
}
