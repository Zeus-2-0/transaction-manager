package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionRule;
import com.brihaspathee.zeus.domain.entity.TransactionRuleMessage;
import com.brihaspathee.zeus.dto.transaction.TransactionRuleDto;
import com.brihaspathee.zeus.dto.transaction.TransactionRuleMessageDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionRuleMapper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionRuleMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, February 2024
 * Time: 4:14 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Component
@RequiredArgsConstructor
public class TransactionRuleMapperImpl implements TransactionRuleMapper {

    /**
     * Transaction rule message mapper instance
     */
    private final TransactionRuleMessageMapper messageMapper;

    /**
     * Convert dto to entity
     * @param transactionRuleDto
     * @return
     */
    @Override
    public TransactionRule ruleDtoToRule(TransactionRuleDto transactionRuleDto) {
        if(transactionRuleDto == null){
            return null;
        }
        TransactionRule rule = TransactionRule.builder()
                .transactionRuleSK(transactionRuleDto.getTransactionRuleSK())
                .ruleId(transactionRuleDto.getRuleId())
                .ruleName(transactionRuleDto.getRuleName())
                .rulePassed(transactionRuleDto.isRulePassed())
                .transactionMemberCode(transactionRuleDto.getTransactionMemberCode())
                .exchangeMemberId(transactionRuleDto.getExchangeMemberId())
                .build();
        if(transactionRuleDto.getTransactionRuleMessages() != null &&
                !transactionRuleDto.getTransactionRuleMessages().isEmpty()){
            List<TransactionRuleMessage> ruleMessages = new ArrayList<>();
            transactionRuleDto.getTransactionRuleMessages().forEach(transactionRuleMessage -> {
                ruleMessages.add(messageMapper.ruleMessageDtoToRuleMessage(transactionRuleMessage));
            });
            rule.setTransactionRuleMessages(ruleMessages);
        }
        return rule;
    }

    /**
     * Convert entity to dto
     * @param transactionRule
     * @return
     */
    @Override
    public TransactionRuleDto ruleToRuleDto(TransactionRule transactionRule) {
        if(transactionRule == null){
            return null;
        }
        TransactionRuleDto ruleDto = TransactionRuleDto.builder()
                .transactionRuleSK(transactionRule.getTransactionRuleSK())
                .ruleId(transactionRule.getRuleId())
                .ruleName(transactionRule.getRuleName())
                .rulePassed(transactionRule.isRulePassed())
                .transactionMemberCode(transactionRule.getTransactionMemberCode())
                .exchangeMemberId(transactionRule.getExchangeMemberId())
                .build();
        if(transactionRule.getTransactionRuleMessages() != null &&
        !transactionRule.getTransactionRuleMessages().isEmpty()){
            List<TransactionRuleMessageDto> ruleMessages = new ArrayList<>();
            transactionRule.getTransactionRuleMessages().forEach(transactionRuleMessage -> {
                ruleMessages.add(messageMapper.ruleMessageToRuleMessageDto(transactionRuleMessage));
            });
            ruleDto.setTransactionRuleMessages(ruleMessages);
        }
        return ruleDto;
    }

    /**
     * Convert dtos to entities
     * @param transactionRuleDtos
     * @return
     */
    @Override
    public List<TransactionRule> ruleDtosToRules(List<TransactionRuleDto> transactionRuleDtos) {
        return transactionRuleDtos.stream().map(this::ruleDtoToRule).toList();
    }

    /**
     * Convert entities to dtos
     * @param transactionRules
     * @return
     */
    @Override
    public List<TransactionRuleDto> rulesToRuleDtos(List<TransactionRule> transactionRules) {
        return transactionRules.stream().map(this::ruleToRuleDto).toList();
    }
}
