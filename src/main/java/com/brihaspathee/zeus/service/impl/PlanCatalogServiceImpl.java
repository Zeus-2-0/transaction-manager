package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.dto.rate.MemberRateRequestDto;
import com.brihaspathee.zeus.dto.rate.MemberRateResponseDto;
import com.brihaspathee.zeus.dto.rate.RateRequestDto;
import com.brihaspathee.zeus.dto.rate.RateResponseDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
     * @param transactionMemberDtos
     * @param planId
     * @param effectiveDate
     */
    @Override
    public void getMemberRates(List<TransactionMemberDto> transactionMemberDtos,
                                          String planId,
                               LocalDate effectiveDate) {
        TransactionMemberDto primarySubscriber = getPrimarySubscriber(transactionMemberDtos);
        TransactionMemberAddressDto residentialAddress = getResidentialAddress(primarySubscriber);
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
        return MemberRateRequestDto.builder()
                .age(calculateAge(transactionMemberDto.getDateOfBirth(), effectiveDate))
                .memberRateCode(transactionManagerUtil.getExchangeMemberId(transactionMemberDto))
                .genderTypeCode(transactionMemberDto.getGenderTypeCode())
                .relationshipTypeCode(transactionMemberDto.getRelationshipTypeCode())
                .firstName(transactionMemberDto.getFirstName())
                .lastName(transactionMemberDto.getLastName())
                .tobaccoInd(transactionMemberDto.getTobaccoIndicator().equals("U"))
                .build();
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
     * Get member's primary residential address
     * @param transactionMemberDto
     * @return
     */
    private TransactionMemberAddressDto getResidentialAddress(TransactionMemberDto transactionMemberDto){
        if(transactionMemberDto != null){
            return transactionMemberDto.getMemberAddresses()
                    .stream()
                    .filter(transactionMemberAddressDto ->
                            transactionMemberAddressDto.getAddressTypeCode()
                                    .equals("RESIDENCE"))
                    .findFirst()
                    .orElse(null);
        }else{
            return null;
        }
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
                String exchangeMemberId = transactionManagerUtil.getExchangeMemberId(transactionMemberDto);
                Optional<MemberRateResponseDto> optionalMemberRate = memberRateResponseDtos.stream()
                        .filter(memberRateResponseDto -> memberRateResponseDto.getMemberRateCode()
                                .equals(exchangeMemberId)).findFirst();
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
        long daysBetween = ChronoUnit.DAYS.between(dateOfBirth, effectiveDate);
        long age = daysBetween / 365;
        return (int) age;
    }
}
