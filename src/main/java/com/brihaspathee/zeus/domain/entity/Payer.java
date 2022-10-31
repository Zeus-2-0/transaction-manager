package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
@Table(name = "PAYER")
public class Payer {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "payer_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID payerSK;

    /**
     * The detail of the transaction
     */
    @OneToOne
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The name of the payer received in the transaction
     */
    @Column(name = "payer_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String payerName;

    /**
     * The id of the payer received in the transaction
     */
    @Column(name = "payer_id", length = 100, columnDefinition = "varchar", nullable = false)
    private String payerId;

    /**
     * The date when the payer was received on the transaction
     */
    @Column(name = "received_date")
    private LocalDateTime receivedDate;

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
        return "Payer{" +
                "payerSK=" + payerSK +
                ", payerName='" + payerName + '\'' +
                ", payerId='" + payerId + '\'' +
                ", receivedDate=" + receivedDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
