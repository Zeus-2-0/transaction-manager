package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.constants.TobaccoUse;
import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.account.MemberAddressDto;
import com.brihaspathee.zeus.dto.account.MemberDto;
import com.brihaspathee.zeus.dto.rate.MemberRateRequestDto;
import com.brihaspathee.zeus.dto.rate.MemberRateResponseDto;
import com.brihaspathee.zeus.dto.rate.RateRequestDto;
import com.brihaspathee.zeus.dto.rate.RateResponseDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import com.brihaspathee.zeus.exception.ExchangeMemberIdNotFoundException;
import com.brihaspathee.zeus.service.interfaces.PlanCatalogService;
import com.brihaspathee.zeus.util.TransactionManagerUtil;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, January 2024
 * Time: 6:17 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlanCatalogServiceImpl implements PlanCatalogService {

    @Value("${url.host.plan-catalog}")
    private String planCatalogServiceHost;

    /**
     * Rest template instance to get the member rates from plan catalog
     */
    private final RestTemplate restTemplate;

    /**
     * Transaction Manager Util instance
     */
    private final TransactionManagerUtil transactionManagerUtil;

    /**
     * Web client instance to get the member rates from plan catalog
     */
    private final WebClient webClient;

    /**
     * Get rates for the members in the transaction
     * @param matchedAccount - the account that was matched for the transaction
     * @param transactionMemberDtos - the member's in the transaction
     * @param planId - the plan id received in the transaction
     * @param effectiveDate - the effective date of the transaction
     */
    @Override
    public void getMemberRates(AccountDto matchedAccount, List<TransactionMemberDto> transactionMemberDtos,
                               String planId,
                               LocalDate effectiveDate) {
        TransactionMemberDto primarySubscriber = getPrimarySubscriber(transactionMemberDtos);
        TransactionMemberAddressDto residentialAddress = getResidentialAddress(matchedAccount, primarySubscriber);
        if(primarySubscriber == null || residentialAddress == null){
            return;
        }
        List<MemberRateRequestDto> memberRateRequestDtos = new ArrayList<>();
        transactionMemberDtos.forEach(transactionMemberDto -> {
            MemberRateRequestDto memberRateRequestDto = constructMemberRateRequest(transactionMemberDto, effectiveDate);
            memberRateRequestDtos.add(memberRateRequestDto);
        });
        RateRequestDto rateRequestDto = RateRequestDto.builder()
                .planId(planId)
                .fipsCode(residentialAddress.getCountyCode())
                .zipCode(residentialAddress.getZipCode())
                .stateTypeCode(residentialAddress.getStateTypeCode())
                .memberRateRequestDtos(memberRateRequestDtos)
                .build();
        RateResponseDto rateResponseDto = useWebClient(planCatalogServiceHost+"/zeus/plan-catalog/member-rate", rateRequestDto);
        populateMemberRates(transactionMemberDtos, rateResponseDto);
    }

    /**
     * Uses web client to make the REST API Call
     * @param host
     * @param rateRequestDto
     */
    private RateResponseDto useWebClient(String host, RateRequestDto rateRequestDto){
        log.info("Rate Request Dto:{}", rateRequestDto);
        ZeusApiResponse<RateResponseDto> apiResponse = webClient.post()
                .uri(host)
                .body(Mono.just(rateRequestDto), RateRequestDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ZeusApiResponse<RateResponseDto>>() {}).block();
        assert apiResponse != null;
        return apiResponse.getResponse();
    }

    /**
     * This method uses rest template to make the call to service
     * @param host
     * @param rateRequestDto
     */
    private void useRestTemplate(String host, RateRequestDto rateRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RateRequestDto> entity = new HttpEntity<>(rateRequestDto, headers);
        ResponseEntity<ZeusApiResponse<RateResponseDto>> apiResponse = restTemplate.exchange(
                host,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<ZeusApiResponse<RateResponseDto>>() {
                });
        log.info("Rest Template API Response:{}", Objects.requireNonNull(apiResponse.getBody()).getResponse());
    }

    /**
     * Construction Member rate request
     * @param transactionMemberDto
     * @param effectiveDate
     * @return
     */
    private MemberRateRequestDto constructMemberRateRequest(TransactionMemberDto transactionMemberDto,
                                                            LocalDate effectiveDate){
        MemberRateRequestDto memberRateRequestDto =  MemberRateRequestDto.builder()
                .age(calculateAge(transactionMemberDto.getDateOfBirth(), effectiveDate))
                .genderTypeCode(transactionMemberDto.getGenderTypeCode())
                .relationshipTypeCode(transactionMemberDto.getRelationshipTypeCode())
                .firstName(transactionMemberDto.getFirstName())
                .lastName(transactionMemberDto.getLastName())
                .tobaccoInd(isTobaccoUser(transactionMemberDto.getTobaccoIndicator()))
                .build();
        TransactionMemberIdentifierDto transactionMemberIdentifierDto =
                transactionManagerUtil.getMemberId(transactionMemberDto, "EXCHMEMID");
        if(transactionMemberIdentifierDto == null){
            throw new ExchangeMemberIdNotFoundException("Exchange Member Id not found for member: "+
                    transactionMemberDto.getTransactionMemberCode());
        }
        memberRateRequestDto.setMemberRateCode(transactionMemberIdentifierDto.getIdentifierValue());
        return memberRateRequestDto;
    }

    /**
     * Get the primary subscriber in the transaction
     * @param transactionMemberDtos
     * @return
     */
    private TransactionMemberDto getPrimarySubscriber(List<TransactionMemberDto> transactionMemberDtos){
        return transactionMemberDtos.stream()
                .filter(transactionMemberDto -> transactionMemberDto.getRelationshipTypeCode().equals("HOH"))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get member's primary residential address from the transaction or the account
     * @param matchAccount - the account that was matched
     * @param transactionMemberDto - the member in the transaction
     * @return the residential address of the member
     */
    private TransactionMemberAddressDto getResidentialAddress(AccountDto matchAccount, TransactionMemberDto transactionMemberDto){
        if(transactionMemberDto != null){
            if(transactionMemberDto.getMemberAddresses() != null){
                return transactionMemberDto.getMemberAddresses()
                        .stream()
                        .filter(transactionMemberAddressDto ->
                                transactionMemberAddressDto.getAddressTypeCode()
                                        .equals("RESIDENCE"))
                        .findFirst()
                        .orElse(null);
            }else{
                // get the residential address from the account
                MemberAddressDto memberAddressDto = getResidentialAddress(matchAccount,
                        transactionMemberDto.getEffectiveDate());
                return TransactionMemberAddressDto.builder()
                        .countyCode(memberAddressDto.getFipsCode())
                        .zipCode(memberAddressDto.getZipCode())
                        .stateTypeCode(memberAddressDto.getStateTypeCode())
                        .build();
            }

        }else{
            return null;
        }
    }

    /**
     * Get residential address from the account
     * @param accountDto
     * @param effectiveDate
     * @return
     */
    private MemberAddressDto getResidentialAddress(AccountDto accountDto, LocalDate effectiveDate){
        if(accountDto != null && accountDto.getMembers()!=null){
            log.info("About to get the address from the account:{}", accountDto.getAccountNumber());
            Optional<MemberDto> optionalMemberDto = accountDto.getMembers()
                    .stream()
                    .filter(
                            memberDto ->
                                    memberDto.getRelationshipTypeCode()
                                            .equals("HOH")).findFirst();
            if(optionalMemberDto.isPresent()){
                MemberDto memberDto = optionalMemberDto.get();
                log.info("Primary subscriber present in the account:{}", memberDto.getMemberCode());
                if(memberDto.getMemberAddresses() != null && !memberDto.getMemberAddresses().isEmpty()){
                    List<MemberAddressDto> residentialAddresses = memberDto.getMemberAddresses().stream().filter(memberAddressDto ->
                        memberAddressDto.getAddressTypeCode().equals("RESIDENCE")).toList();
                    if (!residentialAddresses.isEmpty()){
                        log.info("Residential addresses present in the account:{}", residentialAddresses.size());
                        Optional<MemberAddressDto> optionalMemberAddressDto = residentialAddresses.stream().filter(memberAddressDto -> {
                            LocalDate addressStartDate = memberAddressDto.getStartDate();
                            LocalDate addressEndDate = memberAddressDto.getEndDate();
                            if(addressEndDate != null){
                                if(addressStartDate.isEqual(addressEndDate)){
                                    return false;
                                }else if((effectiveDate.isEqual(addressStartDate))){
                                    return true;
                                }else return effectiveDate.isAfter(addressStartDate) &&
                                        (effectiveDate.isEqual(addressEndDate) ||
                                                effectiveDate.isBefore(addressEndDate));
                            }else{
                                return (effectiveDate.isEqual(addressStartDate)) ||
                                        (effectiveDate.isAfter(addressStartDate));
                            }
                        }).findFirst();
                        log.info("Is residential address present:{}", optionalMemberAddressDto.isPresent());
                        if(optionalMemberAddressDto.isPresent()){
                            return optionalMemberAddressDto.get();
                        }
                    }
                }
            }
        }
        return null;

    }

    /**
     * Populate the member rates from the response received from the
     * @param transactionMemberDtos
     * @param rateResponseDto
     */
    private void populateMemberRates(List<TransactionMemberDto> transactionMemberDtos,
                                                RateResponseDto rateResponseDto){
        log.info("Rate response DTO:{}",rateResponseDto);
        if(rateResponseDto == null ||
                rateResponseDto.isException() ||
                rateResponseDto.getMemberRateResponseDtos().isEmpty()){
            return;
        }
        List<MemberRateResponseDto> memberRateResponseDtos = rateResponseDto.getMemberRateResponseDtos();
        if(memberRateResponseDtos != null && !memberRateResponseDtos.isEmpty()){
            transactionMemberDtos.forEach(transactionMemberDto -> {
                TransactionMemberIdentifierDto transactionMemberIdentifierDto =
                        transactionManagerUtil.getMemberId(transactionMemberDto, "EXCHMEMID");
                if(transactionMemberIdentifierDto == null){
                    throw new ExchangeMemberIdNotFoundException("Exchange Member Id not found for member: "+
                            transactionMemberDto.getTransactionMemberCode());
                }
                Optional<MemberRateResponseDto> optionalMemberRate = memberRateResponseDtos.stream()
                        .filter(memberRateResponseDto -> memberRateResponseDto.getMemberRateCode()
                                .equals(transactionMemberIdentifierDto.getIdentifierValue())).findFirst();
                if(optionalMemberRate.isPresent()){
                    MemberRateResponseDto memberRateResponseDto = optionalMemberRate.get();
                    BigDecimal memberRate = memberRateResponseDto.getMemberRate();
                    transactionMemberDto.setProductCatalogRate(memberRate);
                    if(transactionMemberDto.getMemberRate() == null){
                        transactionMemberDto.setMemberRate(memberRate);
                    }
                }

            });
        }
    }

    /**
     * Calculate the age
     * @param dateOfBirth
     * @param effectiveDate
     * @return
     */
    private int calculateAge(LocalDate dateOfBirth, LocalDate effectiveDate){
        Temporal temporalDOB = dateOfBirth.atStartOfDay();
        Temporal temporalEffectiveDate = effectiveDate.atStartOfDay();
        Duration duration = Duration.between(temporalDOB, temporalEffectiveDate);
        long daysBetween = Math.abs(duration.toDays());
        long age = daysBetween / 365;
        return (int) age;
    }

    /**
     * Check if the tobacco indicator indicates if the user as consumes tobacco
     * @param tobaccoInd
     * @return
     */
    private boolean isTobaccoUser(String tobaccoInd){
        if(tobaccoInd.equals(TobaccoUse.SUBABS.toString()) ||
                tobaccoInd.equals(TobaccoUse.TOBANDSUB.toString()) ||
                tobaccoInd.equals(TobaccoUse.TOBUSE.toString())){
            return true;
        }else{
            return false;
        }
    }
}
