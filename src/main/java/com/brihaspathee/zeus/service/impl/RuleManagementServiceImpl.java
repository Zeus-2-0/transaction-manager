package com.brihaspathee.zeus.service.impl;

import com.brihaspathee.zeus.constants.MemberIdentifierTypes;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionRule;
import com.brihaspathee.zeus.domain.entity.TransactionRuleMessage;
import com.brihaspathee.zeus.domain.repository.TransactionRuleMessageRepository;
import com.brihaspathee.zeus.domain.repository.TransactionRuleRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberDto;
import com.brihaspathee.zeus.dto.transaction.TransactionMemberIdentifierDto;
import com.brihaspathee.zeus.service.interfaces.RuleManagementService;
import com.brihaspathee.zeus.util.TransactionManagerUtil;
import com.brihaspathee.zeus.validator.TransactionValidationResult;
import com.brihaspathee.zeus.validator.rules.RuleResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 25, February 2024
 * Time: 6:22 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RuleManagementServiceImpl implements RuleManagementService {

    /**
     * Instance of transaction rule repository
     */
    private final TransactionRuleRepository transactionRuleRepository;

    /**
     * Instance of transaction rule message repository
     */
    private final TransactionRuleMessageRepository transactionRuleMessageRepository;

    /**
     * Instance of utility class
     */
    private final TransactionManagerUtil transactionManagerUtil;

    /**
     * Save the rules that were executed for the transaction
     * @param transactionDto
     * @param transactionValidationResult
     */
    @Override
    public boolean saveTransactionRules(TransactionDto transactionDto,
                                     TransactionValidationResult transactionValidationResult) {
        Transaction transaction = Transaction.builder()
                .transactionSK(transactionDto.getTransactionSK())
                .build();
        // Iterate through all the transaction level rules and store them at the transaction level.
        transactionValidationResult.getRuleResults().forEach(ruleResult -> {
            saveTransactionRule(transaction, ruleResult, null, null);

        });
        // Iterate through each member's rule results and store the rules executed for each member
        transactionValidationResult.getMemberValidationResults().forEach(memberValidationResult -> {
            String exchangeMemberId = getExchangeMemberId(transactionDto, memberValidationResult.getMemberCode());
            log.info("Member Code of the member for the rule:{}", memberValidationResult.getMemberCode());
            log.info("Exchange Member id of the member:{}", exchangeMemberId);
            memberValidationResult.getRuleResults().forEach(ruleResult -> {
                saveTransactionRule(transaction, ruleResult, memberValidationResult.getMemberCode(), exchangeMemberId);
            });
        });
        return transactionValidationResult.isValidationPassed();
    }

    /**
     * Save the transaction rule
     * @param transaction
     * @param ruleResult
     * @param memberCode
     */
    private void saveTransactionRule(Transaction transaction,
                                     RuleResult ruleResult,
                                     String memberCode,
                                     String exchangeMemberId){
        TransactionRule transactionRule = TransactionRule.builder()
                .transaction(transaction)
                .ruleId(ruleResult.getRuleId())
                .ruleName(ruleResult.getRuleName())
                .rulePassed(ruleResult.isRulePassed())
                .transactionMemberCode(memberCode)
                .exchangeMemberId(exchangeMemberId)
                .build();
        transactionRule = transactionRuleRepository.save(transactionRule);
        TransactionRule finalTransactionRule = transactionRule;
        ruleResult.getRuleMessages().forEach(ruleMessage -> {
            TransactionRuleMessage transactionRuleMessage = TransactionRuleMessage.builder()
                    .transactionRule(finalTransactionRule)
                    .messageCode(ruleMessage.getMessageCode())
                    .messageTypeCode(ruleMessage.getMessageTypeCode())
                    .messageDesc(ruleMessage.getMessageDescription())
                    .build();
            transactionRuleMessageRepository.save(transactionRuleMessage);
        });
    }

    /**
     * Get Exchange member id of the member
     * @param transactionDto
     * @param transactionMemberCode
     * @return
     */
    private String getExchangeMemberId(TransactionDto transactionDto, String transactionMemberCode){
        TransactionMemberDto memberDto = transactionDto.getMembers()
                .stream()
                .filter(transactionMemberDto ->
                        transactionMemberDto.getTransactionMemberCode().equals(transactionMemberCode))
                .findFirst()
                .orElseThrow();
        TransactionMemberIdentifierDto exchangeMemberIdentifierDto = transactionManagerUtil.getMemberId(memberDto,
                MemberIdentifierTypes.EXCHMEMID.toString());
        if (exchangeMemberIdentifierDto != null){
            return exchangeMemberIdentifierDto.getIdentifierValue();
        }
        return null;
    }
}
