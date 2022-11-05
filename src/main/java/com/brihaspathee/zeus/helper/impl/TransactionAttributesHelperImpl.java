package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionAttributes;
import com.brihaspathee.zeus.domain.repository.TransactionAttributesRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionAttributeDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionAttributesHelper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionAttributesMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 11:09 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionAttributesHelperImpl implements TransactionAttributesHelper {

    /**
     * Attributes mapper instance
     */
    private final TransactionAttributesMapper attributesMapper;

    /**
     * Attributes repository instance
     */
    private final TransactionAttributesRepository attributesRepository;

    /**
     * Create transaction attributes
     * @param attributeDtos
     * @param transaction
     */
    @Override
    public void createTransactionAttributes(List<TransactionAttributeDto> attributeDtos,
                                            Transaction transaction) {
        if(attributeDtos != null && attributeDtos.size() > 0){
            attributeDtos.stream().forEach(attributeDto -> {
                TransactionAttributes attribute = attributesMapper.attributeDtoToAttribute(attributeDto);
                attribute.setTransaction(transaction);
                attributesRepository.save(attribute);
            });
        }
    }
}
