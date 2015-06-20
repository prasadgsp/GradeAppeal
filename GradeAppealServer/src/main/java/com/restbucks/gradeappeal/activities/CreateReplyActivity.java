package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.activities.InvalidReplyException;
import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Reply;
import com.restbucks.gradeappeal.model.Grade;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.repositories.ReplyRepository;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.ReplyRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

public class CreateReplyActivity {
    public ReplyRepresentation create(Reply reply, RestbucksUri requestUri) throws InvalidReplyException {
        
        Identifier identifier = ReplyRepository.current().store(reply);
        
        RestbucksUri replyUri = new RestbucksUri(requestUri.getBaseUri() + "/reply/" + identifier.toString());
        return new ReplyRepresentation(reply, 
                new Link(Representation.RELATIONS_URI + "reply", replyUri), 
                new Link(Representation.SELF_REL_VALUE, replyUri));
    }
}
