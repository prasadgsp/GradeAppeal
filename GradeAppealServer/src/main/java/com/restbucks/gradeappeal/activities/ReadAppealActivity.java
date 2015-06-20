package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

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
