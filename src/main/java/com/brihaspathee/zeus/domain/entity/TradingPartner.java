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
@Table(name = "TRADING_PARTNER")
public class TradingPartner {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "trading_partner_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID tradingPartnerSK;

    /**
     * The transaction to which the trading partner is associated
     */
    @OneToOne
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The trading partner id of the trading partner
     */
    @Column(name = "trading_partner_id", length = 50, columnDefinition = "varchar", nullable = false)
    private String tradingPartnerId;

    /**
     * The line of business type code of the trading partner
     */
    @Column(name = "line_of_business_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String lineOfBusinessTypeCode;

    /**
     * The business unit type code of the trading partner
     */
    @Column(name = "business_unit_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String businessUnitTypeCode;

    /**
     * The marketplace type code of the trading partner
     */
    @Column(name = "marketplace_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String marketplaceTypeCode;

    /**
     * The state of the trading partner
     */
    @Column(name = "state_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String stateTypeCode;

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
        return "TradingPartner{" +
                "tradingPartnerSK=" + tradingPartnerSK +
                ", tradingPartnerId='" + tradingPartnerId + '\'' +
                ", lineOfBusinessTypeCode='" + lineOfBusinessTypeCode + '\'' +
                ", businessUnitTypeCode='" + businessUnitTypeCode + '\'' +
                ", marketplaceTypeCode='" + marketplaceTypeCode + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
