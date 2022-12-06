package com.brihaspathee.zeus.broker.message;

import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 04, December 2022
 * Time: 6:40 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.web.model
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountProcessingResponse {

    /**
     * A unique id that is created as a response for processing the account information
     */
    private String responseId;

    /**
     * The request payload id for which the response is sent
     */
    private String requestPayloadId;

    /**
     * Account number for which the response is created.
     */
    private String accountNumber;

    /**
     * Transacton for which the response is created
     */
    private String ztcn;

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "AccountProcessingResponse{" +
                "responseId='" + responseId + '\'' +
                ", requestPayloadId='" + requestPayloadId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ztcn='" + ztcn + '\'' +
                '}';
    }
}
