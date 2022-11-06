package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.web.model.DataTransformationDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, November 2022
 * Time: 3:40 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionService {

    /**
     * Create transaction
     * @param dataTransformationDto
     * @return
     */
    TransactionDto createTransaction(DataTransformationDto dataTransformationDto);

    /**
     * Get transaction by ztcn
     * @param ztcn
     * @return
     */
    TransactionDto getTransactionByZtcn(String ztcn);
}
