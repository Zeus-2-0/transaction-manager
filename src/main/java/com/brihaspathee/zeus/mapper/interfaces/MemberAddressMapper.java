package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.MemberAddress;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;

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
public interface MemberAddressMapper {

    /**
     * Convert member address entity to member address dto
     * @param memberAddress
     * @return
     */
    TransactionMemberAddressDto addressToAddressDto(MemberAddress memberAddress);

    /**
     * Convert address dto to address entity
     * @param addressDto
     * @return
     */
    MemberAddress addressDtoToAddress(TransactionMemberAddressDto addressDto);

    /**
     * Convert address entities to address dtos
     * @param addresses
     * @return
     */
    List<TransactionMemberAddressDto> addressesToAddressDtos(List<MemberAddress> addresses);

    /**
     * Convert address dtos to address entities
     * @param addressDtos
     * @return
     */
    List<MemberAddress> addressDtosToAddresses(List<TransactionMemberAddressDto> addressDtos);
}
