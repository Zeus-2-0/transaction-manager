package com.brihaspathee.zeus.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
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
@Table(name = "ALTERNATE_CONTACT")
public class AlternateContact {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @Type(type = "uuid-char")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "alternate_contact_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID alternateContactSK;

    /**
     * The member to whom the address belongs
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_sk")
    private Member member;

    /**
     * The type of alternate contact
     */
    @Column(name = "alternate_contact_type_code", length = 50, columnDefinition = "varchar", nullable = false)
    private String alternateContactTypeCode;

    /**
     * The first name of the alternate contact
     */
    @Column(name = "first_name", length = 100, columnDefinition = "varchar", nullable = true)
    private String firstName;

    /**
     * The middle name of the alternate contact
     */
    @Column(name = "middle_name", length = 50, columnDefinition = "varchar", nullable = true)
    private String middleName;

    /**
     * The last name of the alternate contact
     */
    @Column(name = "last_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String lastName;

    /**
     * The identifier type received for the alternate contact
     */
    @Column(name = "identifier_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String identifierTypeCode;

    /**
     * The identifier received for the alternate contact
     */
    @Column(name = "identifier_value", length = 50, columnDefinition = "varchar", nullable = true)
    private String identifierValue;

    /**
     * The phone type received for the alternate contact
     */
    @Column(name = "phone_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String phoneTypeCode;

    /**
     * The phone number received for the alternate contact
     */
    @Column(name = "phone_number", length = 50, columnDefinition = "varchar", nullable = true)
    private String phoneNumber;

    /**
     * The email received for the alternate contact
     */
    @Column(name = "email", length = 50, columnDefinition = "varchar", nullable = true)
    private String email;

    /**
     * The address line 1 of the address received for the alternate contact
     */
    @Column(name = "address_line_1", length = 100, columnDefinition = "varchar", nullable = true)
    private String addressLine1;

    /**
     * The address line 2 of the address received for the alternate contact
     */
    @Column(name = "address_line_2", length = 50, columnDefinition = "varchar", nullable = true)
    private String addressLine2;

    /**
     * The city of the address received for the alternate contact
     */
    @Column(name = "city", length = 50, columnDefinition = "varchar", nullable = true)
    private String city;

    /**
     * The state of the address received for the alternate contact
     */
    @Column(name = "state_type_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String stateTypeCode;

    /**
     * The zipcode of the address received for the alternate contact
     */
    @Column(name = "zip_code", length = 50, columnDefinition = "varchar", nullable = true)
    private String zipCode;

    /**
     * The date when the alternate contact was received on the transaction
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
     * @return returns the string value
     */
    @Override
    public String toString() {
        return "AlternateContact{" +
                "alternateContactSK=" + alternateContactSK +
                ", alternateContactTypeCode='" + alternateContactTypeCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", identifierTypeCode='" + identifierTypeCode + '\'' +
                ", identifierValue='" + identifierValue + '\'' +
                ", phoneTypeCode='" + phoneTypeCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", stateTypeCode='" + stateTypeCode + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", receivedDate=" + receivedDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
