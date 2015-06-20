package com.restbucks.gradeappealclient.activities;

public class InvalidReplyException extends RuntimeException {
    public InvalidReplyException(Exception ex) {
        super(ex);
    }

    public InvalidReplyException() {}
    
    private static final long serialVersionUID = -9208425088487285282L;

}
