package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(RestbucksUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new NoSuchAppealException();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
