package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.representations.RestbucksUri;

public class UriExchange {

    public static RestbucksUri appealForEmail(RestbucksUri appealUri) {
        checkForValidAppealUri(appealUri);
        return new RestbucksUri(appealUri.getBaseUri() + "/email/" + appealUri.getId().toString());
    }
    
    public static RestbucksUri orderForPayment(RestbucksUri paymentUri) {
        checkForValidEmailUri(paymentUri);
        return new RestbucksUri(paymentUri.getBaseUri() + "/order/" + paymentUri.getId().toString());
    }

    public static RestbucksUri responseForEmail(RestbucksUri emailUri) {
        checkForValidEmailUri(emailUri);
        return new RestbucksUri(emailUri.getBaseUri() + "/response/" + emailUri.getId().toString());
    }
    
    public static RestbucksUri appealForResponse(RestbucksUri responseUri) {
        checkForValidResponseUri(responseUri);
        return new RestbucksUri(responseUri.getBaseUri() + "/appeal/" + responseUri.getId().toString());
    }

    private static void checkForValidAppealUri(RestbucksUri appealUri) {
        if(!appealUri.toString().contains("/appeal/")) {
            throw new RuntimeException("Invalid Appeal URI");
        }
    }
    
    private static void checkForValidEmailUri(RestbucksUri email) {
        if(!email.toString().contains("/email/")) {
            throw new RuntimeException("Invalid Email URI");
        }
    }
    
    private static void checkForValidResponseUri(RestbucksUri response) {
        if(!response.toString().contains("/response/")) {
            throw new RuntimeException("Invalid Response URI");
        }
    }
}
