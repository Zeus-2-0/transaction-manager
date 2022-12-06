package com.brihaspathee.zeus.broker.message;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, December 2022
 * Time: 6:39 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountProcessingRequest {

    /**
     * The account for which the transaction is processed
     */
    private String accountNumber;

    /**
     * The transaction details
     */
    private TransactionDto transactionDto;
}
