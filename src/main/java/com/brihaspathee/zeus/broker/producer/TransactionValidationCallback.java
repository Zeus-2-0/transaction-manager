package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:30 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class TransactionValidationCallback implements
        ListenableFutureCallback<SendResult<String, ZeusMessagePayload<TransactionDto>>> {

    /**
     * The payload that was sent to the topic
     */
    private TransactionDto transactionDto;

    /**
     * Invoked when there is failure to post the message to the topic
     * @param ex
     */
    @Override
    public void onFailure(Throwable ex) {
        log.info("The message failed to publish");
    }

    /**
     * Invoked when there is success to post the message to the topic
     * @param result
     */
    @Override
    public void onSuccess(SendResult<String, ZeusMessagePayload<TransactionDto>> result) {
        log.info("The message successfully published");
    }
}
