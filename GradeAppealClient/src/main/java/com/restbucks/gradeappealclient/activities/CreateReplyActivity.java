package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.activities.InvalidReplyException;
import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Reply;
import com.restbucks.gradeappealclient.model.Grade;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.repositories.ReplyRepository;
import com.restbucks.gradeappealclient.representations.Link;
import com.restbucks.gradeappealclient.representations.ReplyRepresentation;
import com.restbucks.gradeappealclient.representations.Representation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

public class CreateReplyActivity {
    public ReplyRepresentation create(Reply reply, RestbucksUri requestUri) throws InvalidReplyException {
        
        Identifier identifier = ReplyRepository.current().store(reply);
        
        RestbucksUri replyUri = new RestbucksUri(requestUri.getBaseUri() + "/reply/" + identifier.toString());
        return new ReplyRepresentation(reply, 
                new Link(Representation.RELATIONS_URI + "reply", replyUri), 
                new Link(Representation.SELF_REL_VALUE, replyUri));
    }
}
