package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.dto.account.AccountDto;
import com.brihaspathee.zeus.dto.rate.RateResponseDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;

import java.time.LocalDate;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, January 2024
 * Time: 6:15 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface PlanCatalogService {

    /**
     * Get Rates for all the members in the transaction
     * @param matchedAccount - the account that was matched for the transaction
     * @param transactionMemberDtos
     * @param planId
     * @param effectiveDate
     */
    void getMemberRates(AccountDto matchedAccount, List<TransactionMemberDto> transactionMemberDtos,
                        String planId,
                        LocalDate effectiveDate);
}
