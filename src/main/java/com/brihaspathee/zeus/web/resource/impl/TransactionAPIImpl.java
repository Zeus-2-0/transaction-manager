package com.brihaspathee.zeus.web.resource.impl;

import com.brihaspathee.zeus.service.interfaces.TransactionService;
import com.brihaspathee.zeus.web.model.DataTransformationDto;
import com.brihaspathee.zeus.web.resource.interfaces.TransactionAPI;
import com.brihaspathee.zeus.web.response.ZeusApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, November 2022
 * Time: 3:38 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.resource.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionAPIImpl implements TransactionAPI {

    /**
     * Transaction service instance
     */
    private final TransactionService transactionService;

    /**
     * Create the transaction
     * @param dataTransformationDto
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public ResponseEntity<ZeusApiResponse<DataTransformationDto>> createTransaction(
            DataTransformationDto dataTransformationDto) throws JsonProcessingException {
        transactionService.createTransaction(dataTransformationDto);
        return null;
    }
}
