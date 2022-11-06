package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.domain.entity.TransactionDetail;
import com.brihaspathee.zeus.dto.transaction.TransactionDetailDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:56 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionDetailHelper {

    /**
     * Create the transaction detail
     * @param detailDto
     * @param transaction
     */
    TransactionDetail createTransactionDetail(TransactionDetailDto detailDto,
                                              Transaction transaction);
}
