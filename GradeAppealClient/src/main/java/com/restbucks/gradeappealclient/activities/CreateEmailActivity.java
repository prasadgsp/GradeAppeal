package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Email;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.repositories.EmailRepository;
import com.restbucks.gradeappealclient.representations.Link;
import com.restbucks.gradeappealclient.representations.EmailRepresentation;
import com.restbucks.gradeappealclient.representations.Representation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

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
