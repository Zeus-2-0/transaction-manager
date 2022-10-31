package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.constants.ZeusServiceNames;
import com.brihaspathee.zeus.domain.entity.PayloadTracker;
import com.brihaspathee.zeus.dto.transaction.TransactionDto;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerHelper;
import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.util.ZeusRandomStringGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 09, October 2022
 * Time: 7:40 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class TransactionValidationProducer {

    /**
     * Kafka template to produce and send messages
     */
    private final KafkaTemplate<String, ZeusMessagePayload<TransactionDto>> kafkaTemplate;

    /**
     * ListenableFutureCallback class that is used after success or failure of publishing the message
     */
    private final TransactionValidationCallback transactionValidationCallback;

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
     * @param transactionDto
     * @throws JsonProcessingException
     */
    public void publishTransaction(TransactionDto transactionDto) throws JsonProcessingException {
        String[] messageDestinations = {ZeusServiceNames.VALIDATION_SERVICE};
        ZeusMessagePayload<TransactionDto> messagePayload = ZeusMessagePayload.<TransactionDto>builder()
                .messageMetadata(MessageMetadata.builder()
                        .messageSource(ZeusServiceNames.TRANSACTION_MANAGER)
                        .messageDestination(messageDestinations)
                        .messageCreationTimestamp(LocalDateTime.now())
                        .build())
                .payload(transactionDto)
                .payloadId(ZeusRandomStringGenerator.randomString(15))
                .build();
        transactionValidationCallback.setTransactionDto(transactionDto);
        log.info("About to send the transaction {} to validation service", transactionDto.getZtcn());
        PayloadTracker payloadTracker = createPayloadTracker(messagePayload);
        log.info("The payload tracker created for sending the transaction {} to validation service {}",
                transactionDto.getZtcn(),
                payloadTracker.getPayloadId());
        ProducerRecord<String, ZeusMessagePayload<TransactionDto>> producerRecord =
                buildProducerRecord(messagePayload);
        kafkaTemplate.send(producerRecord).addCallback(transactionValidationCallback);
        log.info("After send the transaction {} to validation", transactionDto.getZtcn());
    }

    /**
     * The method to build the producer record
     * @param messagePayload
     */
    private ProducerRecord<String, ZeusMessagePayload<TransactionDto>> buildProducerRecord(ZeusMessagePayload<TransactionDto> messagePayload){
        RecordHeader messageHeader = new RecordHeader("payload-id",
                "test payload id".getBytes());
        return new ProducerRecord<>("ZEUS.VALIDATOR.TRANSACTION.REQ",
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
    private PayloadTracker createPayloadTracker(ZeusMessagePayload<TransactionDto> messagePayload)
            throws JsonProcessingException {
        String payloadAsString = objectMapper.writeValueAsString(messagePayload);
        PayloadTracker payloadTracker = PayloadTracker.builder()
                .payloadDirectionTypeCode("OUTBOUND")
                .payload_key("TRANSACTION")
                .payload_key_type_code(messagePayload.getPayload().getZtcn())
                .payload(payloadAsString)
                .payloadId(messagePayload.getPayloadId())
                .sourceDestinations(StringUtils.join(
                        messagePayload.getMessageMetadata().getMessageDestination()))
                .build();
        return payloadTrackerHelper.createPayloadTracker(payloadTracker);
    }
}
