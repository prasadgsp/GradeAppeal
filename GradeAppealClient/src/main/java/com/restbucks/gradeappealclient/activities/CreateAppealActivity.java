package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.representations.Link;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.Representation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, RestbucksUri requestUri) {
        appeal.setStatus(AppealStatus.CREATED);
        Identifier identifier = AppealRepository.current().store(appeal);
      
        RestbucksUri appealUri = new RestbucksUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        RestbucksUri emailUri = new RestbucksUri(requestUri.getBaseUri() + "/email/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representation.RELATIONS_URI + "cancel", appealUri), 
                new Link(Representation.RELATIONS_URI + "email", emailUri), 
                new Link(Representation.RELATIONS_URI + "update", appealUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
    }
}
