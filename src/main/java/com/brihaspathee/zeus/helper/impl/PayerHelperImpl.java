package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Payer;
import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.repository.PayerRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionPayerDto;
import com.brihaspathee.zeus.helper.interfaces.PayerHelper;
import com.brihaspathee.zeus.mapper.interfaces.PayerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 10:59 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PayerHelperImpl implements PayerHelper {

    /**
     * Payer Mapper instance
     */
    private final PayerMapper payerMapper;

    /**
     * Payer Repository instance for performing CRUD operations
     */
    private final PayerRepository payerRepository;

    /**
     * Create a payer
     * @param payerDto
     * @param transaction
     */
    @Override
    public Payer createPayer(TransactionPayerDto payerDto,
                            Transaction transaction) {
        if(payerDto !=null){
            Payer payer = payerMapper.payerDtoToPayer(payerDto);
            payer.setTransaction(transaction);
            return payerRepository.save(payer);
        }else {
            return null;
        }
    }
}
