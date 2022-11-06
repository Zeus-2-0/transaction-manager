package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Member;
import com.brihaspathee.zeus.domain.entity.MemberAddress;
import com.brihaspathee.zeus.domain.repository.MemberAddressRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;
import com.brihaspathee.zeus.helper.interfaces.MemberAddressHelper;
import com.brihaspathee.zeus.mapper.interfaces.MemberAddressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 2:24 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MemberAddressHelperImpl implements MemberAddressHelper {

    /**
     * Address mapper instance
     */
    private final MemberAddressMapper addressMapper;

    /**
     * Repository instance to perform CRUD operations
     */
    private final MemberAddressRepository addressRepository;

    /**
     * Create Member Address
     * @param addressDtos
     * @param member
     */
    @Override
    public List<MemberAddress> createMemberAddress(List<TransactionMemberAddressDto> addressDtos, Member member) {
        List<MemberAddress> addresses = new ArrayList<>();
        if(addressDtos != null && addressDtos.size() >0){
            addressDtos.stream().forEach(addressDto -> {
                MemberAddress address = addressMapper.addressDtoToAddress(addressDto);
                address.setMember(member);
                addresses.add(addressRepository.save(address));
            });
        }
        if(addresses.size() > 0){
            return addresses;
        }else {
            return null;
        }
    }
}
