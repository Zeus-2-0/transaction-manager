package com.brihaspathee.zeus.helper.impl;

import com.brihaspathee.zeus.domain.entity.PayloadTracker;
import com.brihaspathee.zeus.domain.repository.PayloadTrackerRepository;
import com.brihaspathee.zeus.helper.interfaces.PayloadTrackerHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 11, October 2022
 * Time: 7:34 AM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.helper.impl
 * To change this template use File | Settings | File and Code Template
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayloadTrackerHelperImpl implements PayloadTrackerHelper {

    /**
     * The repository instance to perform crud operations
     */
    private final PayloadTrackerRepository payloadTrackerRepository;

    /**
     * Create the payload tracker record
     * @param payloadTracker
     * @return
     */
    @Override
    public PayloadTracker createPayloadTracker(PayloadTracker payloadTracker) {
        log.info("Payload tracker about to be inserted: {}", payloadTracker);
        return payloadTrackerRepository.save(payloadTracker);
    }

    /**
     * Get payload tracker by payload id
     * @param payloadId
     * @return
     */
    @Override
    public PayloadTracker getPayloadTracker(String payloadId) {
        return payloadTrackerRepository.findPayloadTrackerByPayloadId(payloadId).orElseThrow();
    }

    /**
     * Clean up the payload tracker
     */
    @Override
    public void deleteAll() {
        payloadTrackerRepository.deleteAll();
    }
}
