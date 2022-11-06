package com.brihaspathee.zeus.domain.repository;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 31, October 2022
 * Time: 2:53 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.domain.repository
 * To change this template use File | Settings | File and Code Template
 */
@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, UUID> {

    /**
     * Get the maximum transaction status for the transaction
     * @param transaction
     * @return
     */
    Optional<TransactionStatus> findFirstByTransactionOrderByStatusSeqDesc(Transaction transaction);

    /**
     * Find all the statuses of the transaction
     * @param transaction
     * @return
     */
    List<TransactionStatus> findAllByTransaction(Transaction transaction);
}
