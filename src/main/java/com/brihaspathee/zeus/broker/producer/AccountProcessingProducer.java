package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.constants.ZeusServiceNames;
import com.brihaspathee.zeus.domain.entity.PayloadTracker;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerHelper;
import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.brihaspathee.zeus.broker.message.AccountProcessingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:41 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountProcessingProducer {

    /**
     * Kafka template to produce and send messages
     */
    private final KafkaTemplate<String, ZeusMessagePayload<AccountProcessingRequest>> kafkaTemplate;

    /**
     * ListenableFutureCallback class that is used after success or failure of publishing the message
     */
    private final AccountProcessorCallback accountProcessorCallback;

    /**
     * Object mapper to convert the payload to string
     */
    private final ObjectMapper objectMapper;

    /**
     * Payload tracker helper instance to create the payload tracker record
     */
    private final PayloadTrackerHelper payloadTrackerHelper;

    /**
     * The method that publishes the messages to the kafka topic
     * @param accountProcessingRequest
     */
    public void publishAccount(AccountProcessingRequest accountProcessingRequest) throws JsonProcessingException {
        log.info("About to publish the account to account processor service;{}", accountProcessingRequest.getAccountNumber());
        String[] messageDestinations = {ZeusServiceNames.ACCOUNT_PROCESSOR_SERVICE};
        ZeusMessagePayload<AccountProcessingRequest> messagePayload = ZeusMessagePayload.<AccountProcessingRequest>builder()
                .messageMetadata(MessageMetadata.builder()
                        .messageSource(ZeusServiceNames.TRANSACTION_MANAGER)
                        .messageDestination(messageDestinations)
                        .messageCreationTimestamp(LocalDateTime.now())
                        .build())
                .payload(accountProcessingRequest)
                .payloadId(ZeusRandomStringGenerator.randomString(15))
                .build();
        accountProcessorCallback.setAccountProcessingRequest(accountProcessingRequest);
        PayloadTracker payloadTracker = createPayloadTracker(messagePayload);
        log.info("Payload tracker created to send the account {} to account processor service is {}",
                accountProcessingRequest.getAccountNumber(),
                payloadTracker.getPayloadId());
        ProducerRecord<String, ZeusMessagePayload<AccountProcessingRequest>> producerRecord =
                buildProducerRecord(messagePayload);
        kafkaTemplate.send(producerRecord);//.addCallback(accountProcessorCallback);
        log.info("After the publishing the account {} to account processor service",
                accountProcessingRequest.getAccountNumber());
    }

    /**
     * The method to build the producer record
     * @param messagePayload
     */
    private ProducerRecord<String, ZeusMessagePayload<AccountProcessingRequest>> buildProducerRecord(ZeusMessagePayload<AccountProcessingRequest> messagePayload){
        RecordHeader messageHeader = new RecordHeader("payload-id",
                "test payload id".getBytes());
        return new ProducerRecord<>("ZEUS.ACCOUNT.PROCESSING.REQ",
                null,
                "test payload id 2",
                messagePayload,
                Arrays.asList(messageHeader));
    }

    /**
     * Create the payload tracker record
     * @param messagePayload
     * @throws JsonProcessingException
     */
    private PayloadTracker createPayloadTracker(ZeusMessagePayload<AccountProcessingRequest> messagePayload)
            throws JsonProcessingException {
        String payloadAsString = objectMapper.writeValueAsString(messagePayload);
        PayloadTracker payloadTracker = PayloadTracker.builder()
                .payloadDirectionTypeCode("OUTBOUND")
                .payload_key(messagePayload.getPayload().getTransactionDto().getZtcn())
                .payload_key_type_code("TRANSACTION")
                .payload(payloadAsString)
                .payloadId(messagePayload.getPayloadId())
                .sourceDestinations(StringUtils.join(
                        messagePayload.getMessageMetadata().getMessageDestination()))
                .build();
        return payloadTrackerHelper.createPayloadTracker(payloadTracker);
    }
}
