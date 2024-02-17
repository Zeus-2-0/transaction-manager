package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.account.AccountList;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import com.brihaspathee.zeus.exception.ExchangeMemberIdNotFoundException;
import com.brihaspathee.zeus.exception.ExchangeSubscriberIdNotFoundException;
import com.brihaspathee.zeus.service.interfaces.AccountMatchService;
import com.brihaspathee.zeus.util.TransactionManagerUtil;
import com.brihaspathee.zeus.web.model.AccountMatchParam;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, February 2024
 * Time: 6:54 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountMatchServiceImpl implements AccountMatchService {

    @Value("${url.host.mms}")
    private String mmsHost;

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
     * Match the account and return the account number
     * @param transactionDto
     * @return
     */
    @Override
    public String matchAccount(TransactionDto transactionDto) {
        TransactionMemberDto memberDto = transactionManagerUtil.getPrimarySubscriber(transactionDto);
        // check if the primary subscriber is present
        if(memberDto == null){
            // if not present then take the first dependent in the transaction
            memberDto = transactionDto.getMembers().get(0);
        }
        TransactionMemberIdentifierDto transactionMemberIdentifierDto =
                transactionManagerUtil.getMemberId(memberDto, "EXCHSUBID");
        if(transactionMemberIdentifierDto == null){
            throw new ExchangeSubscriberIdNotFoundException("Exchange Subscriber Id Id not found for member: "+
                    memberDto.getTransactionMemberCode());
        }
        AccountMatchParam accountMatchParam = AccountMatchParam.builder()
                .exchangeSubscriberId(transactionMemberIdentifierDto.getIdentifierValue())
                .stateTypeCode(transactionDto.getTradingPartnerDto().getStateTypeCode())
                .build();
        if(memberDto.getRelationshipTypeCode().equals("HOH")){
            accountMatchParam.setFirstName(memberDto.getFirstName());
            accountMatchParam.setLastName(memberDto.getLastName());
            accountMatchParam.setGenderTypeCode(memberDto.getGenderTypeCode());
            accountMatchParam.setDateOfBirth(memberDto.getDateOfBirth());
            TransactionMemberIdentifierDto ssnDto =
                    transactionManagerUtil.getMemberId(memberDto, "SSN");
            if(ssnDto != null){
                accountMatchParam.setSocialSecurityNumber(ssnDto.getIdentifierValue());
            }
        }
        AccountList accountList = useWebClient(mmsHost, accountMatchParam);
        if(accountList != null && accountList.getAccountDtos() != null && !accountList.getAccountDtos().isEmpty()){
            Optional<AccountDto> optionalAccount = accountList.getAccountDtos().stream().findFirst();
            return optionalAccount.map(AccountDto::getAccountNumber).orElse(null);
        }
        return null;
    }

    /**
     * Uses web client to make the REST API Call
     * @param host
     * @param accountMatchParam
     * @return
     */
    private AccountList useWebClient(String host, AccountMatchParam accountMatchParam){
        log.info("AccountMatchParam:{}", accountMatchParam);
        ZeusApiResponse<AccountList> apiResponse = webClient.get()
                .uri(host, uriBuilder -> uriBuilder
                        .path("/zeus/account/search")
                        .queryParam("exchangeSubscriberId", accountMatchParam.getExchangeSubscriberId())
                        .queryParam("stateTypeCode", accountMatchParam.getStateTypeCode())
                        .queryParam("socialSecurityNumber", accountMatchParam.getSocialSecurityNumber())
                        .queryParam("firstName", accountMatchParam.getFirstName())
                        .queryParam("lastName", accountMatchParam.getLastName())
                        .queryParam("genderTypeCode", accountMatchParam.getGenderTypeCode())
                        .queryParam("dateOfBirth", accountMatchParam.getDateOfBirth())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ZeusApiResponse<AccountList>>() {}).block();
        assert apiResponse != null;
        return apiResponse.getResponse();
    }
}
