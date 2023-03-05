package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import jakarta.persistence.*;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2022
 * Time: 5:43 PM
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
@Table(name = "TRANSACTION_ATTRIBUTES")
public class TransactionAttributes {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_attribute_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionAttributeSK;

    /**
     * The transaction to which the attribute belongs
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The type of attribute
     */
    @Column(name = "transaction_attribute_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String transactionAttributeTypeCode;

    /**
     * The transaction attribute value
     */
    @Column(name = "transaction_attribute_value", length = 100, columnDefinition = "varchar", nullable = false)
    private String transactionAttributeValue;

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

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "TransactionAttributes{" +
                "transactionAttributeSK=" + transactionAttributeSK +
                ", transactionAttributeTypeCode='" + transactionAttributeTypeCode + '\'' +
                ", transactionAttributeValue='" + transactionAttributeValue + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
