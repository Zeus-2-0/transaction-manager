package com.brihaspathee.zeus.service.interfaces;

import com.brihaspathee.zeus.web.model.DataTransformationDto;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, November 2022
 * Time: 3:40 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.service.interfaces
 * To change this template use File | Settings | File and Code Template
 */
public interface TransactionService {

    void createTransaction(DataTransformationDto dataTransformationDto);
}
