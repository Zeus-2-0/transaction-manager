package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.Sponsor;
import com.brihaspathee.zeus.dto.transaction.TransactionSponsorDto;
import com.brihaspathee.zeus.mapper.interfaces.SponsorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 01, November 2022
 * Time: 5:35 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SponsorMapperImpl implements SponsorMapper {

    /**
     * Convert sponsor entity to sponsor dto
     * @param sponsor
     * @return
     */
    @Override
    public TransactionSponsorDto sponsorToSponsorDto(Sponsor sponsor) {
        if(sponsor == null){
            return null;
        }
        TransactionSponsorDto sponsorDto = TransactionSponsorDto.builder()
                .sponsorSK(sponsor.getSponsorSK())
                .sponsorName(sponsor.getSponsorName())
                .sponsorId(sponsor.getSponsorId())
                .receivedDate(sponsor.getReceivedDate())
                .createdDate(sponsor.getCreatedDate())
                .updatedDate(sponsor.getUpdatedDate())
                .build();
        return sponsorDto;
    }

    /**
     * Convert sponsor dto to sponsor entity
     * @param sponsorDto
     * @return
     */
    @Override
    public Sponsor sponsorDtoToSponsor(TransactionSponsorDto sponsorDto) {
        if(sponsorDto == null){
            return null;
        }
        Sponsor sponsor = Sponsor.builder()
                .sponsorSK(sponsorDto.getSponsorSK())
                .sponsorName(sponsorDto.getSponsorName())
                .sponsorId(sponsorDto.getSponsorId())
                .receivedDate(sponsorDto.getReceivedDate())
                .createdDate(sponsorDto.getCreatedDate())
                .updatedDate(sponsorDto.getUpdatedDate())
                .build();
        return sponsor;
    }

    /**
     * Convert sponsor entities to sponsor dtos
     * @param sponsors
     * @return
     */
    @Override
    public List<TransactionSponsorDto> sponsorsToSponsorDtos(List<Sponsor> sponsors) {

        return sponsors.stream().map(this::sponsorToSponsorDto).collect(Collectors.toList());
    }

    /**
     * Convert list of sponsor dto to sponsor entities
     * @param sponsorDtos
     * @return
     */
    @Override
    public List<Sponsor> sponsorDtosToSponsors(List<TransactionSponsorDto> sponsorDtos) {

        return sponsorDtos.stream().map(this::sponsorDtoToSponsor).collect(Collectors.toList());
    }
}
