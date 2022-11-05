package com.brihaspathee.zeus.helper.interfaces;

import com.brihaspathee.zeus.domain.entity.Transaction;
import com.brihaspathee.zeus.dto.transaction.TransactionSponsorDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 03, November 2022
 * Time: 6:57 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface SponsorHelper {

    /**
     * Create sponsor
     * @param sponsorDto
     * @param transaction
     */
    void createSponsor(TransactionSponsorDto sponsorDto,
                       Transaction transaction);
}
