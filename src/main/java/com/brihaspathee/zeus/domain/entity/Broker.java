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
@Table(name = "BROKER")
public class Broker {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "broker_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID brokerSK;

    /**
     * The detail of the transaction
     */
    @OneToOne
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The name of the broker
     */
    @Column(name = "broker_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String brokerName;

    /**
     * The id of the broker
     */
    @Column(name = "broker_id", length = 50, columnDefinition = "varchar", nullable = true)
    private String brokerId;

    /**
     * The name of the agency
     */
    @Column(name = "agency_name", length = 100, columnDefinition = "varchar", nullable = true)
    private String agencyName;

    /**
     * The id of the agency
     */
    @Column(name = "agency_id", length = 50, columnDefinition = "varchar", nullable = true)
    private String agencyId;

    /**
     * The account number of the broker
     */
    @Column(name = "account_number_1", length = 50, columnDefinition = "varchar", nullable = true)
    private String accountNumber1;

    /**
     * The account number of the broker
     */
    @Column(name = "account_number_2", length = 50, columnDefinition = "varchar", nullable = true)
    private String accountNumber2;

    /**
     * The date when the broker was received on the transaction
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
        return "Broker{" +
                "brokerSK=" + brokerSK +
                ", brokerName='" + brokerName + '\'' +
                ", brokerId='" + brokerId + '\'' +
                ", agencyName='" + agencyName + '\'' +
                ", agencyId='" + agencyId + '\'' +
                ", accountNumber1='" + accountNumber1 + '\'' +
                ", accountNumber2='" + accountNumber2 + '\'' +
                ", receivedDate=" + receivedDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
