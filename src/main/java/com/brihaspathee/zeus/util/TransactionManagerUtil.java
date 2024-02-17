package com.brihaspathee.zeus.util;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberAddressDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, December 2023
 * Time: 5:58 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.util
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionManagerUtil {
    /**
     * The spring environment instance
     */
    private final Environment environment;

    /**
     * Generate a unique code
     * @param source
     * @param target
     */
    public void populateEntityCodes(TransactionDto source, TransactionDto target) {
        // Use the code from the test data if the profile is "test"
        if (Arrays.asList(environment.getActiveProfiles()).contains("test") ||
                Arrays.asList(environment.getActiveProfiles()).contains("int-test")) {
            target.setEntityCodes(source.getEntityCodes());
            source.getMembers().forEach(sourceMember -> {
                if(sourceMember.getEntityCodes()!=null && !sourceMember.getEntityCodes().isEmpty()){
                    String sourceExchangeMemberId = getMemberId(sourceMember, "EXCHMEMID").getIdentifierValue();
                    TransactionMemberDto targetMember = target.getMembers().stream().filter(targetMem -> {
                        String targetExchangeMemberId = getMemberId(targetMem, "EXCHMEMID").getIdentifierValue();
                        return targetExchangeMemberId.equals(sourceExchangeMemberId);
                    }).findFirst().orElseThrow();
                    targetMember.setEntityCodes(sourceMember.getEntityCodes());
                }
            });
        }
    }

    /**
     * Get the exchange member id of the member
     * @param transactionMemberDto
     * @param identifierType
     * @return
     */
    public TransactionMemberIdentifierDto getMemberId(TransactionMemberDto transactionMemberDto, String identifierType){
        return transactionMemberDto.getIdentifiers()
                .stream()
                .filter(
                        transactionMemberIdentifierDto ->
                                transactionMemberIdentifierDto.getIdentifierTypeCode().equals(identifierType))
                .findFirst()
                .orElse(null);

    }

    /**
     * Get the primary subscriber if present in the transaction
     * @return
     */
    public TransactionMemberDto getPrimarySubscriber(TransactionDto transactionDto){
        return transactionDto.getMembers()
                .stream()
                .filter(transactionMemberDto ->
                        transactionMemberDto.getRelationshipTypeCode().equals("HOH"))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a specific address type from the member
     * @param transactionMemberDto
     * @param addressType
     * @return
     */
    public TransactionMemberAddressDto getAddress(TransactionMemberDto transactionMemberDto, String addressType){
        return transactionMemberDto.getMemberAddresses()
                .stream()
                .filter(
                        transactionMemberAddressDto ->
                                transactionMemberAddressDto.getAddressTypeCode().equals(addressType))
                .findFirst()
                .orElse(null);
    }

}
