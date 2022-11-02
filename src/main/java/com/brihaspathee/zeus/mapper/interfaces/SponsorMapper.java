package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.Sponsor;
import com.brihaspathee.zeus.dto.transaction.TransactionSponsorDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface SponsorMapper {

    /**
     * Convert sponsor entity to sponsor dto
     * @param sponsor
     * @return
     */
    TransactionSponsorDto sponsorToSponsorDto(Sponsor sponsor);

    /**
     * Convert sponsor dto to sponsor entity
     * @param sponsorDto
     * @return
     */
    Sponsor sponsorDtoToSponsor(TransactionSponsorDto sponsorDto);

    /**
     * Convert sponsor entities to sponsor dtos
     * @param sponsors
     * @return
     */
    List<TransactionSponsorDto> sponsorsToSponsorDtos(List<Sponsor> sponsors);

    /**
     * Convert list of sponsor dto to sponsor entities
     * @param sponsorDtos
     * @return
     */
    List<Sponsor> sponsorDtosToSponsors(List<TransactionSponsorDto> sponsorDtos);
}
