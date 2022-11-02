package com.brihaspathee.zeus.mapper.interfaces;

import com.brihaspathee.zeus.domain.entity.Payer;
import com.brihaspathee.zeus.dto.transaction.TransactionPayerDto;

import java.util.List;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 4:48 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.mapper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface PayerMapper {

    /**
     * Convert payer to payer dto
     * @param payer
     * @return
     */
    TransactionPayerDto payerToPayerDto(Payer payer);

    /**
     * Convert payer dto to payer
     * @param payerDto
     * @return
     */
    Payer payerDtoToPayer(TransactionPayerDto payerDto);

    /**
     * Convert the list of payers to payer dtos
     * @param payers
     * @return
     */
    List<TransactionPayerDto> payersToPayerDtos(List<Payer> payers);

    /**
     * Convert the list of payer dtos to payer entities
     * @param payerDtos
     * @return
     */
    List<Payer> payerDtoToPayers(List<TransactionPayerDto> payerDtos);
}
