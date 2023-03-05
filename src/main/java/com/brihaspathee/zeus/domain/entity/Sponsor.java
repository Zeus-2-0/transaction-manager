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
@Table(name = "SPONSOR")
public class Sponsor {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "sponsor_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID sponsorSK;

    /**
     * The detail of the transaction
     */
    @OneToOne
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The name of the sponsor
     */
    @Column(name = "sponsor_name", length = 200, columnDefinition = "varchar", nullable = false)
    private String sponsorName;

    /**
     * The id of the sponsor
     */
    @Column(name = "sponsor_id", length = 100, columnDefinition = "varchar", nullable = false)
    private String sponsorId;

    /**
     * The date when the sponsor was received on the transaction
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
        return "Sponsor{" +
                "sponsorSK=" + sponsorSK +
                ", sponsorName='" + sponsorName + '\'' +
                ", sponsorId='" + sponsorId + '\'' +
                ", receivedDate=" + receivedDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
