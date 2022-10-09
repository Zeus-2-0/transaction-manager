package com.brihaspathee.zeus.broker.producer;

import com.brihaspathee.zeus.message.MessageMetadata;
import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.web.model.AccountDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * Time: 7:41 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.broker.producer
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class AccountProducer {

    /**
     * Kafka template to produce and send messages
     */
    private final KafkaTemplate<String, ZeusMessagePayload<AccountDto>> kafkaTemplate;

    /**
     * ListenableFutureCallback class that is used after success or failure of publishing the message
     */
    private final AccountCreationCallback accountCreationCallback;

    /**
     * Object mapper to convert the payload to string
     */
    private final ObjectMapper objectMapper;

    /**
     * The method that publishes the messages to the kafka topic
     * @param accountDto
     */
    public void publishAccount(AccountDto accountDto){
        String[] messageDestinations = {"MEMBER-MGMT-SERVICE"};
        ZeusMessagePayload<AccountDto> messagePayload = ZeusMessagePayload.<AccountDto>builder()
                .messageMetadata(MessageMetadata.builder()
                        .messageSource("TRANSACTION-MANAGER")
                        .messageDestination(messageDestinations)
                        .messageCreationTimestamp(LocalDateTime.now())
                        .build())
                .payload(accountDto)
                .build();
        accountCreationCallback.setAccountDto(accountDto);
        // createPayloadTracker(messagePayload);
        ProducerRecord<String, ZeusMessagePayload<AccountDto>> producerRecord =
                buildProducerRecord(messagePayload);
        kafkaTemplate.send(producerRecord).addCallback(accountCreationCallback);
        log.info("After the send method is called");
    }

    /**
     * The method to build the producer record
     * @param messagePayload
     */
    private ProducerRecord<String, ZeusMessagePayload<AccountDto>> buildProducerRecord(ZeusMessagePayload<AccountDto> messagePayload){
        RecordHeader messageHeader = new RecordHeader("payload-id",
                "test payload id".getBytes());
        return new ProducerRecord<>("ZEUS.ACCOUNT.PROCESSOR",
                null,
                "test payload id 2",
                messagePayload,
                Arrays.asList(messageHeader));
    }
}
