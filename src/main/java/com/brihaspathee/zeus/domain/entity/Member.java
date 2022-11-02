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
import java.util.List;
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
@Table(name = "MEMBER")
public class Member {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "member_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID memberSK;

    /**
     * The transaction that the member belongs
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * A unique member code assigned to the member for the transaction
     */
    @Column(name = "transaction_member_code", length = 20, columnDefinition = "varchar", nullable = false)
    private String transactionMemberCode;

    /**
     * The relationship code received for the member in the transaction
     */
    @Column(name = "relationship_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String relationshipTypeCode;

    /**
     * The transaction type received for the member in the transaction
     */
    @Column(name = "transaction_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String transactionTypeCode;

    /**
     * The effective date received for the member in the transaction
     */
    @Column(name = "effective_date", nullable = true)
    private LocalDate effectiveDate;

    /**
     * The end date received for the member in the transaction
     */
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    /**
     * The maintenance reason code received for the member in the transaction
     */
    @Column(name = "reason_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String reasonTypeCode;

    /**
     * The first name of the member
     */
    @Column(name = "first_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String firstName;

    /**
     * The middle name of the member
     */
    @Column(name = "middle_name", length = 100, columnDefinition = "varchar", nullable = true)
    private String middleName;

    /**
     * The last name of the member
     */
    @Column(name = "last_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String lastName;

    /**
     * The premium rate received for the member in the transaction
     */
    @Column(name = "member_rate", nullable = true)
    private BigDecimal memberRate;

    /**
     * The tobacco indicator value received for the member in the transaction
     */
    @Column(name = "tobacco_ind", length = 50, columnDefinition = "varchar", nullable = false)
    private String tobaccoIndicator;

    /**
     * The product catalog rate received for the member in the transaction
     */
    @Column(name = "product_catalog_rate", nullable = true)
    private BigDecimal productCatalogRate;

    /**
     * The date of birth of the member
     */
    @Column(name = "date_of_birth", nullable = true)
    private LocalDate dateOfBirth;

    /**
     * The gender type code of the member
     */
    @Column(name = "gender_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String genderTypeCode;

    /**
     * The list of the emails received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<MemberEmail> memberEmails;

    /**
     * The list of the emails received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<MemberPhone> memberPhones;

    /**
     * The list of the identifiers received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<MemberIdentifier> memberIdentifiers;

    /**
     * The list of the languages received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<MemberLanguage> memberLanguages;

    /**
     * The list of the addresses received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<MemberAddress> memberAddresses;

    /**
     * The list of the alternate contacts received for the member in the transaction
     */
    @OneToMany(mappedBy = "member")
    private List<AlternateContact> alternateContacts;

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
        return "Member{" +
                "memberSK=" + memberSK +
                ", transaction_member_code='" + transactionMemberCode + '\'' +
                ", relationshipTypeCode='" + relationshipTypeCode + '\'' +
                ", transactionTypeCode='" + transactionTypeCode + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", endDate=" + endDate +
                ", reasonTypeCode='" + reasonTypeCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", memberRate=" + memberRate +
                ", tobaccoIndicator='" + tobaccoIndicator + '\'' +
                ", productCatalogRate=" + productCatalogRate +
                ", dateOfBirth=" + dateOfBirth +
                ", genderTypeCode='" + genderTypeCode + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
