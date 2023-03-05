package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import jakarta.persistence.*;

import java.sql.Types;
import java.time.LocalDate;
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
@Table(name = "TRANSACTION_DETAIL")
public class TransactionDetail {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_detail_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionDetailSK;

    /**
     * The detail of the transaction
     */
    @OneToOne
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The type of transaction
     */
    @Column(name = "transaction_type_code", length = 45, columnDefinition = "varchar", nullable = false)
    private String transactionTypeCode;

    /**
     * The plan id of the transaction
     */
    @Column(name = "plan_id", length = 100, columnDefinition = "varchar", nullable = true)
    private String planId;

    /**
     * The CSR Variant of the transaction
     */
    @Column(name = "csr_variant", length = 10, columnDefinition = "varchar", nullable = true)
    private String csrVariant;

    /**
     * The group policy id of the transaction
     */
    @Column(name = "group_policy_id", length = 50, columnDefinition = "varchar", nullable = false)
    private String groupPolicyId;

    /**
     * The effective date of the transaction
     */
    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    /**
     * The end date of the transaction
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * The maintenance effective date of the transaction
     */
    @Column(name = "maintenance_effective_date")
    private LocalDate maintenanceEffectiveDate;

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
        return "TransactionDetail{" +
                "transactionDetailSK=" + transactionDetailSK +
                ", transactionTypeCode='" + transactionTypeCode + '\'' +
                ", planId='" + planId + '\'' +
                ", csrVariant='" + csrVariant + '\'' +
                ", groupPolicyId='" + groupPolicyId + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", endDate=" + endDate +
                ", maintenanceEffectiveDate=" + maintenanceEffectiveDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
