package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Sponsor;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.SponsorRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionSponsorDto;
import com.brihaspathee.zeus.helper.interfaces.SponsorHelper;
import com.brihaspathee.zeus.mapper.interfaces.SponsorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 11:04 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SponsorHelperImpl implements SponsorHelper {

    /**
     * Sponsor mapper instance
     */
    private final SponsorMapper sponsorMapper;

    /**
     * Sponsor Repository instance to perform CRUD operations
     */
    private final SponsorRepository sponsorRepository;

    /**
     * Create a sponsor
     * @param sponsorDto
     * @param transaction
     */
    @Override
    public Sponsor createSponsor(TransactionSponsorDto sponsorDto, Transaction transaction) {
        if(sponsorDto != null){
            Sponsor sponsor = sponsorMapper.sponsorDtoToSponsor(sponsorDto);
            sponsor.setTransaction(transaction);
            return sponsorRepository.save(sponsor);
        }else{
            return null;
        }
    }
}
