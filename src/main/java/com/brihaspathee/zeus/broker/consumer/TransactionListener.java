package com.brihaspathee.zeus.broker.consumer;

import com.brihaspathee.zeus.message.ZeusMessagePayload;
import com.brihaspathee.zeus.service.interfaces.TransactionProcessor;
import com.brihaspathee.zeus.web.model.RawTransactionDto;
import com.brihaspathee.zeus.web.model.TransactionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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
     * Listen to kafka topic to receive the raw transaction from transaction origination service
     * @param consumerRecord
     * @return
     * @throws JsonProcessingException
     */
    @KafkaListener(topics = "ZEUS.TRANSACTION.PROCESSOR")
    // @SendTo("ZEUS.VALIDATOR.ACCOUNT.ACK")
    public ZeusMessagePayload<TransactionDto> listen(
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
        log.info("Transaction received from the Kafka topic:{}", messagePayload.getPayload());
        transactionProcessor.processTransaction(messagePayload.getPayload());
        return null;

    }
}
