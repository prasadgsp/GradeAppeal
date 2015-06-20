package com.restbucks.gradeappealclient.client.activities;

public class InvalidEmailException extends Exception {

    private static final long serialVersionUID = 5911896330951274617L;

    public InvalidEmailException(Exception ex) {
        super(ex);
    }

    public InvalidEmailException() {}

}
