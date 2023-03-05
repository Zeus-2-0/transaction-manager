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
 * Time: 5:45 PM
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
@Table(name = "MEMBER_ADDRESS")
public class MemberAddress {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "member_address_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID memberAddressSK;

    /**
     * The member to whom the address belongs
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_sk")
    private Member member;

    /**
     * The type of address
     */
    @Column(name = "address_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String addressTypeCode;

    /**
     * The address line 1 of the address
     */
    @Column(name = "address_line_1", length = 100, columnDefinition = "varchar", nullable = false)
    private String addressLine1;

    /**
     * The address line 2 of the address
     */
    @Column(name = "address_line_2", length = 50, columnDefinition = "varchar", nullable = true)
    private String addressLine2;

    /**
     * The city of the address
     */
    @Column(name = "city", length = 50, columnDefinition = "varchar", nullable = true)
    private String city;

    /**
     * The state of the address
     */
    @Column(name = "state_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String stateTypeCode;

    /**
     * The zipcode of the address
     */
    @Column(name = "zip_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String zipCode;

    /**
     * The county code of the address
     */
    @Column(name = "county_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String countyCode;

    /**
     * The date when the email was received on the transaction
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
        return "MemberAddress{" +
                "memberAddressSK=" + memberAddressSK +
                ", addressTypeCode='" + addressTypeCode + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", countyCode='" + countyCode + '\'' +
                ", receivedDate=" + receivedDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
