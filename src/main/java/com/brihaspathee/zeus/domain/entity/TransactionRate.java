package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 17, October 2022
 * Time: 5:44 PM
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
@Table(name = "TRANSACTION_RATE")
public class TransactionRate {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_rate_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionRateSK;

    /**
     * The rates of the transaction
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The type of the rate
     */
    @Column(name = "rate_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String rateTypeCode;

    /**
     * The dollar value of the rate
     */
    @Column(name = "transaction_rate", nullable = false)
    private BigDecimal transactionRate;

    /**
     * The start date of the rate
     */
    @Column(name = "rate_start_date")
    private LocalDate rateStartDate;

    /**
     * The end date of the rate
     */
    @Column(name = "rate_end_date")
    private LocalDateTime rateEndDate;

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
     * toString Method
     * @return
     */
    @Override
    public String toString() {
        return "TransactionRate{" +
                "transactionRateSK=" + transactionRateSK +
                ", rateTypeCode='" + rateTypeCode + '\'' +
                ", transactionRate=" + transactionRate +
                ", rateStartDate=" + rateStartDate +
                ", rateEndDate=" + rateEndDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
