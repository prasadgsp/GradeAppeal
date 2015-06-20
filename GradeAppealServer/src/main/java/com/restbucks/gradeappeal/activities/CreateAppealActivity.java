package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

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
