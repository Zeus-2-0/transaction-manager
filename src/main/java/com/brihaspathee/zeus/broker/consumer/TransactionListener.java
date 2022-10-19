package com.brihaspathee.zeus.broker.consumer;

import com.brihaspathee.zeus.domain.entity.PayloadTracker;
import com.brihaspathee.zeus.domain.entity.PayloadTrackerDetail;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerDetailHelper;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerHelper;
import com.brihaspathee.zeus.message.Acknowledgement;
import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.service.interfaces.TransactionProcessor;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:21 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.consumer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionListener {

    /**
     * Object mapper instance to convert the JSON to object
     */
    private final ObjectMapper objectMapper;

    /**
     * Transaction processor instance to process the transaction
     */
    private final TransactionProcessor transactionProcessor;

    /**
     * Payload tracker helper instance to create the payload tracker record
     */
    private final PayloadTrackerHelper payloadTrackerHelper;

    /**
     * Payload tracker detail helper instance to create the payload tracker detail record
     */
    private final PayloadTrackerDetailHelper payloadTrackerDetailHelper;

    /**
     * Listen to kafka topic to receive the raw transaction from transaction origination service
     * @param consumerRecord
     * @return
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "ZEUS.TRANSACTION.PROCESSOR")
    @SendTo("ZEUS.TRANSACTION.PROCESSOR.ACK")
    public ZeusMessagePayload<Acknowledgement> listen(
            ConsumerRecord<String, ZeusMessagePayload<TransactionDto>> consumerRecord)
            throws JsonProcessingException {
        Headers headers = consumerRecord.headers();
        log.info("Headers are:");
        headers.forEach(header -> {
            log.info("key: {}, value: {}", header.key(), new String(header.value()));
        });
        // Convert the payload as String
        String valueAsString = objectMapper.writeValueAsString(consumerRecord.value());
        // Convert it to Zeus Message Payload
        ZeusMessagePayload<TransactionDto> messagePayload = objectMapper.readValue(
                valueAsString,
                new TypeReference<ZeusMessagePayload<TransactionDto>>(){});
        log.info("Transaction received from the Kafka topic:{}", messagePayload.getPayload().getZtcn());
        PayloadTracker payloadTracker = createPayloadTracker(messagePayload);
        log.info("Payload inbound from data transform service created for transaction {} is {}",
                messagePayload.getPayload().getZtcn(),
                payloadTracker.getPayloadId() );
        transactionProcessor.processTransaction(messagePayload.getPayload()).subscribe();
        return createAcknowledgment(messagePayload, payloadTracker);

    }

    /**
     * Create payload tracker record
     * @param messagePayload
     * @throws JsonProcessingException
     */
    private PayloadTracker createPayloadTracker(ZeusMessagePayload<TransactionDto> messagePayload)
            throws JsonProcessingException {
        String payloadAsString = objectMapper.writeValueAsString(messagePayload);
        PayloadTracker payloadTracker = PayloadTracker.builder()
                .payloadDirectionTypeCode("INBOUND")
                .payload_key(messagePayload.getPayload().getZtcn())
                .payload_key_type_code("TRANSACTION")
                .payload(payloadAsString)
                .payloadId(messagePayload.getPayloadId())
                .sourceDestinations(StringUtils.join(
                        messagePayload.getMessageMetadata().getMessageSource()))
                .build();
        return payloadTrackerHelper.createPayloadTracker(payloadTracker);
    }

    /**
     * Create the acknowledgement to send back to transaction orig service
     * @param messagePayload
     * @param payloadTracker
     * @return
     * @throws JsonProcessingException
     */
    private ZeusMessagePayload<Acknowledgement> createAcknowledgment(
            ZeusMessagePayload<TransactionDto> messagePayload,
            PayloadTracker payloadTracker) throws JsonProcessingException {
        String[] messageDestinations = {"DATA-TRANSFORM-SERVICE"};
        String ackId = ZeusRandomStringGenerator.randomString(15);
        ZeusMessagePayload<Acknowledgement> ack = ZeusMessagePayload.<Acknowledgement>builder()
                .messageMetadata(MessageMetadata.builder()
                        .messageDestination(messageDestinations)
                        .messageSource("TRANSACTION-MANAGER")
                        .messageCreationTimestamp(LocalDateTime.now())
                        .build())
                .payload(Acknowledgement.builder()
                        .ackId(ackId)
                        .requestPayloadId(payloadTracker.getPayloadId())
                        .build())
                .build();
        String ackAsString = objectMapper.writeValueAsString(ack);

        // Store the acknowledgement in the detail table
        PayloadTrackerDetail payloadTrackerDetail = PayloadTrackerDetail.builder()
                .payloadTracker(payloadTracker)
                .responseTypeCode("ACKNOWLEDGEMENT")
                .responsePayload(ackAsString)
                .responsePayloadId(ackId)
                .payloadDirectionTypeCode("OUTBOUND")
                .sourceDestinations(StringUtils.join(messageDestinations))
                .build();
        payloadTrackerDetailHelper.createPayloadTrackerDetail(payloadTrackerDetail);
        return ack;
    }
}
