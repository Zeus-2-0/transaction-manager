package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.domain.repository.TransactionDetailRepository;
import com.brihaspathee.zeus.dto.transaction.TransactionDetailDto;
import com.brihaspathee.zeus.helper.interfaces.TransactionDetailHelper;
import com.brihaspathee.zeus.mapper.interfaces.TransactionDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 7:01 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionDetailHelperImpl implements TransactionDetailHelper {

    /**
     * Transaction detail mapper instance
     */
    private final TransactionDetailMapper detailMapper;

    /**
     * Transaction detail repository instance
     */
    private final TransactionDetailRepository detailRepository;


    /**
     * Create transaction detail
     * @param detailDto
     * @param transaction
     */
    @Override
    public TransactionDetail createTransactionDetail(TransactionDetailDto detailDto,
                                        Transaction transaction) {
        if(detailDto != null){
            TransactionDetail detail = detailMapper.detailDtoToDetail(detailDto);
            detail.setTransaction(transaction);
            return detailRepository.save(detail);
        }else{
            return null;
        }

    }
}
