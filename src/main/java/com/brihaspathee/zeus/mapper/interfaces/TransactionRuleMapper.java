package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionRule;
import com.brihaspathee.zeus.dto.transaction.TransactionRuleDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, February 2024
 * Time: 4:00 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionRuleMapper {

    /**
     * Convert dto to entity
     * @param transactionRuleDto
     * @return
     */
    TransactionRule ruleDtoToRule(TransactionRuleDto transactionRuleDto);

    /**
     * Convert entity to dto
     * @param transactionRule
     * @return
     */
    TransactionRuleDto ruleToRuleDto(TransactionRule transactionRule);

    /**
     * Convert dtos to entities
     * @param transactionRuleDtos
     * @return
     */
    List<TransactionRule> ruleDtosToRules(List<TransactionRuleDto> transactionRuleDtos);

    /**
     * Convert entities to dtos
     * @param transactionRules
     * @return
     */
    List<TransactionRuleDto> rulesToRuleDtos(List<TransactionRule> transactionRules);
}
