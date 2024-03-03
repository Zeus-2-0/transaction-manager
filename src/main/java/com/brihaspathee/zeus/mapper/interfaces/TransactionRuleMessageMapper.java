package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.TransactionRuleMessage;
import com.brihaspathee.zeus.dto.transaction.TransactionRuleMessageDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, February 2024
 * Time: 12:06 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionRuleMessageMapper {

    /**
     * Convert dto to entity
     * @param transactionRuleMessageDto
     * @return
     */
    TransactionRuleMessage ruleMessageDtoToRuleMessage(TransactionRuleMessageDto transactionRuleMessageDto);

    /**
     * Convert entity to dto
     * @param transactionRuleMessage
     * @return
     */
    TransactionRuleMessageDto ruleMessageToRuleMessageDto(TransactionRuleMessage transactionRuleMessage);

    /**
     * Convert dtos to entities
     * @param transactionRuleMessageDtos
     * @return
     */
    List<TransactionRuleMessage> ruleMessageDtosToRuleMessages(List<TransactionRuleMessageDto> transactionRuleMessageDtos);

    /**
     * Convert entities to dto
     * @param transactionRuleMessages
     * @return
     */
    List<TransactionRuleMessageDto> ruleMessagesToRuleMessageDtos(List<TransactionRuleMessage> transactionRuleMessages);
}
