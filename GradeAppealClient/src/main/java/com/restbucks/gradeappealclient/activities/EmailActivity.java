package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Email;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.repositories.EmailRepository;
import com.restbucks.gradeappealclient.representations.Link;
import com.restbucks.gradeappealclient.representations.EmailRepresentation;
import com.restbucks.gradeappealclient.representations.Representation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

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
