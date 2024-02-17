package com.brihaspathee.zeus.exception;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, February 2024
 * Time: 12:05 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.exception
 * To change this template use File | Settings | File and Code Template
 */
public class ExchangeSubscriberIdNotFoundException extends RuntimeException{

    public ExchangeSubscriberIdNotFoundException(String message){
        super(message);
    }

    public ExchangeSubscriberIdNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
