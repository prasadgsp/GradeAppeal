package com.restbucks.gradeappealclient.activities;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(Exception ex) {
        super(ex);
    }

    public InvalidEmailException() {}
    
    private static final long serialVersionUID = -9208425088487285282L;

}
