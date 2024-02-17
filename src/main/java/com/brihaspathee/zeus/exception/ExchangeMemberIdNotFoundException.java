package com.brihaspathee.zeus.exception;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 08, February 2024
 * Time: 12:04 PM
 * Project: Zeus
 * Package Name: com.brihaspathee.zeus.exception
 * To change this template use File | Settings | File and Code Template
 */
public class ExchangeMemberIdNotFoundException extends RuntimeException{

    public ExchangeMemberIdNotFoundException(String message){
        super(message);
    }

    public ExchangeMemberIdNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
