package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.model.Email;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.repositories.EmailRepository;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.EmailRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

public class EmailActivity {
    public EmailRepresentation send(Email email, RestbucksUri emailUri) {
        Identifier identifier = emailUri.getId();
        
       
        if(!AppealRepository.current().has(identifier)) {
            throw new NoSuchAppealException();
        }
        
        
        if(EmailRepository.current().has(identifier)) {
            throw new UpdateException();
        }
        
       
        AppealRepository.current().get(identifier).setStatus(AppealStatus.SUBMIT);
        email.setStatus(AppealStatus.SUBMIT);
        EmailRepository.current().store(identifier, email);
        
        return new EmailRepresentation(email, new Link(Representation.RELATIONS_URI + "email", UriExchange.appealForEmail(emailUri)),
                new Link(Representation.RELATIONS_URI + "response", UriExchange.responseForEmail(emailUri)));
    }
}
