package com.brihaspathee.zeus.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 26, February 2024
 * Time: 10:35 AM
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
@Table(name = "TRANSACTION_RULE")
public class TransactionRule {

    /**
     * Primary key of the table
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(Types.LONGVARCHAR)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "transaction_rule_sk", length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID transactionRuleSK;

    /**
     * The id of the rule
     */
    @Column(name = "rule_id", length = 50, columnDefinition = "varchar", nullable = false)
    private String ruleId;

    /**
     * The name of the rule
     */
    @Column(name = "rule_name", length = 100, columnDefinition = "varchar", nullable = false)
    private String ruleName;

    /**
     * Identifies if the rule was passed
     */
    @Column(name = "rule_passed")
    private boolean rulePassed;

    /**
     * The rules of the transaction
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_sk")
    private Transaction transaction;

    /**
     * The member code of the member for which the rule was executed if the rule is a member level rule
     */
    @Column(name = "transaction_member_code", length = 15, columnDefinition = "varchar", nullable = false)
    private String transactionMemberCode;

    /**
     * The exchange member id of the member for which the rule was executed if the rule is a member level rule
     */
    @Column(name = "exchange_member_id", length = 50, columnDefinition = "varchar", nullable = false)
    private String exchangeMemberId;

    /**
     * The list of the messages if any present when the rule was executed
     */
    @OneToMany(mappedBy = "transactionRule", cascade = CascadeType.REMOVE)
    private List<TransactionRuleMessage> transactionRuleMessages;

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
}
