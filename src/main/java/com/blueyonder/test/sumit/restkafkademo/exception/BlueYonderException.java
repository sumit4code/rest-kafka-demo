package com.blueyonder.test.sumit.restkafkademo.exception;

public class BlueYonderException extends RuntimeException {

    public BlueYonderException(String message){
        super(message);
    }

    public BlueYonderException(String message, Exception e) {
        super(message, e);
    }
}
