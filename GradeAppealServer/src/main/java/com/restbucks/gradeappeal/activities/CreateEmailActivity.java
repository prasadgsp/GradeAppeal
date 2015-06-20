package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Email;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.repositories.EmailRepository;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.EmailRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

public class CreateEmailActivity {
    public EmailRepresentation create(Email email, RestbucksUri requestUri) {
        email.setStatus(AppealStatus.SUBMIT);
        
        Identifier identifier = EmailRepository.current().store(email);

        
        RestbucksUri emailUri = new RestbucksUri(requestUri.getBaseUri() + "/email/" + identifier.toString());
        RestbucksUri replyUri = new RestbucksUri(requestUri.getBaseUri() + "/reply/" + identifier.toString());
        return new EmailRepresentation(email, 
                new Link(Representation.RELATIONS_URI + "reply", replyUri), 
                new Link(Representation.SELF_REL_VALUE, emailUri));
    }
}
