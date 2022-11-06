package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
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
@Table(name = "TRANSACTION")
public class Transaction {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionSK;

    /**
     * Unique assigned to the transaction by the transaction origination service
     */
    @Column(name = "ztcn", length = 50, columnDefinition = "varchar", nullable = false)
    private String ztcn;

    /**
     * The file control number of the file that the transaction was received
     */
    @Column(name = "zfcn", length = 50, columnDefinition = "varchar", nullable = false)
    private String zfcn;

    /**
     * The date the transaction was received
     */
    @Column(name ="transaction_received_date")
    private LocalDateTime transactionReceivedDate;

    /**
     * The source of the transaction
     */
    @Column(name = "transaction_source_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String transactionSourceTypeCode;

    /**
     * Contains the details of the transaction
     */
    @OneToOne(mappedBy = "transaction", fetch = FetchType.EAGER)
    private TransactionDetail transactionDetail;

    /**
     * Contains the details of the trading partner
     */
    @OneToOne(mappedBy = "transaction")
    private TradingPartner tradingPartner;

    /**
     * The list of rates received for the transaction
     */
    @OneToMany(mappedBy = "transaction")
    private List<TransactionRate> transactionRates;

    /**
     * The list of rates received for the transaction
     */
    @OneToMany(mappedBy = "transaction")
    private List<TransactionAttributes> transactionAttributes;

    /**
     * Contains the payer of the transaction
     */
    @OneToOne(mappedBy = "transaction")
    private Payer payer;

    /**
     * Contains the sponsor of the transaction
     */
    @OneToOne(mappedBy = "transaction")
    private Sponsor sponsor;

    /**
     * Contains the broker of the transaction
     */
    @OneToOne(mappedBy = "transaction")
    private Broker broker;

    /**
     * Contains the broker of the transaction
     */
    @OneToMany(mappedBy = "transaction")
    private List<TransactionStatus> transactionStatus;

    /**
     * Contains the members of the transaction
     */
    @OneToMany(mappedBy = "transaction", fetch = FetchType.EAGER)
    private List<Member> members;

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
        return "Transaction{" +
                "transactionSK=" + transactionSK +
                ", ztcn='" + ztcn + '\'' +
                ", zfcn='" + zfcn + '\'' +
                ", transactionReceivedDate=" + transactionReceivedDate +
                ", transactionSourceTypeCode='" + transactionSourceTypeCode + '\'' +
                ", transactionDetail=" + transactionDetail +
                ", tradingPartner=" + tradingPartner +
                ", transactionRates=" + transactionRates +
                ", transactionAttributes=" + transactionAttributes +
                ", payer=" + payer +
                ", sponsor=" + sponsor +
                ", broker=" + broker +
                ", transactionStatus=" + transactionStatus +
                ", members=" + members +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
