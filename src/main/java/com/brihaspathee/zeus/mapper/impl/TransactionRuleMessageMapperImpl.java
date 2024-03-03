package com.brihaspathee.zeus.mapper.impl;

import com.brihaspathee.zeus.domain.entity.TransactionRuleMessage;
import com.brihaspathee.zeus.dto.transaction.TransactionRuleMessageDto;
import com.brihaspathee.zeus.mapper.interfaces.TransactionRuleMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 27, February 2024
 * Time: 4:04 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Component
@RequiredArgsConstructor
public class TransactionRuleMessageMapperImpl implements TransactionRuleMessageMapper {

    /**
     * Convert dto to entity
     * @param transactionRuleMessageDto
     * @return
     */
    @Override
    public TransactionRuleMessage ruleMessageDtoToRuleMessage(TransactionRuleMessageDto transactionRuleMessageDto) {
        if(transactionRuleMessageDto == null){
            return null;
        }
        return TransactionRuleMessage.builder()
                .transactionRuleMessageSK(transactionRuleMessageDto.getTransactionRuleMessageSK())
                .messageCode(transactionRuleMessageDto.getMessageCode())
                .messageTypeCode(transactionRuleMessageDto.getMessageTypeCode())
                .messageDesc(transactionRuleMessageDto.getMessageDesc())
                .build();
    }

    @Override
    public TransactionRuleMessageDto ruleMessageToRuleMessageDto(TransactionRuleMessage transactionRuleMessage) {
        if(transactionRuleMessage == null){
            return null;
        }
        return TransactionRuleMessageDto.builder()
                .transactionRuleMessageSK(transactionRuleMessage.getTransactionRuleMessageSK())
                .messageCode(transactionRuleMessage.getMessageCode())
                .messageTypeCode(transactionRuleMessage.getMessageTypeCode())
                .messageDesc(transactionRuleMessage.getMessageDesc())
                .build();
    }

    @Override
    public List<TransactionRuleMessage> ruleMessageDtosToRuleMessages(List<TransactionRuleMessageDto> transactionRuleMessageDtos) {
        return transactionRuleMessageDtos.stream().map(this::ruleMessageDtoToRuleMessage).toList();
    }

    /**
     * Convert dtos to entities
     * @param transactionRuleMessages
     * @return
     */
    @Override
    public List<TransactionRuleMessageDto> ruleMessagesToRuleMessageDtos(List<TransactionRuleMessage> transactionRuleMessages) {
        return transactionRuleMessages.stream().map(this::ruleMessageToRuleMessageDto).toList();
    }
}
