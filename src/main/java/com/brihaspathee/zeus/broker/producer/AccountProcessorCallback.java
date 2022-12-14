package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.broker.message.AccountProcessingRequest;
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
 * Time: 7:37 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class AccountProcessorCallback implements
        ListenableFutureCallback<SendResult<String, ZeusMessagePayload<AccountProcessingRequest>>> {

    /**
     * The message that was sent in the Kafka topic
     */
    private AccountProcessingRequest accountProcessingRequest;

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
    public void onSuccess(SendResult<String, ZeusMessagePayload<AccountProcessingRequest>> result) {
        log.info("The message successfully published");
    }
}
