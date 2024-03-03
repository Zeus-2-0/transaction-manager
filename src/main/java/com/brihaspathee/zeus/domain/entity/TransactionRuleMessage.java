package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, February 2024
 * Time: 10:36 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.entity
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION_RULE_MESSAGE")
public class TransactionRuleMessage {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_rule_message_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionRuleMessageSK;

    /**
     * The rates of the transaction
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_rule_sk")
    private TransactionRule transactionRule;

    /**
     * Message Code of the message
     */
    @Column(name = "message_code", length = 100, columnDefinition = "varchar", nullable = false)
    private String messageCode;

    /**
     * Type code of the message
     */
    @Column(name = "message_type_code", length = 100, columnDefinition = "varchar", nullable = false)
    private String messageTypeCode;

    /**
     * Description of the message
     */
    @Column(name = "message_desc", length = 100, columnDefinition = "varchar", nullable = false)
    private String messageDesc;

    /**
     * The date when the record was created
     */
    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    /**
     * The date when the record was updated
     */
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


}
