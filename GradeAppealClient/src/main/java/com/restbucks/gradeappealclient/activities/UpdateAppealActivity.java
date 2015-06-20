package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

public class UpdateAppealActivity {
    public AppealRepresentation update(Appeal appeal, RestbucksUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();
        AppealRepository repository = AppealRepository.current();
        if (AppealRepository.current().appealNotPlaced(appealIdentifier)) { 
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }

        Appeal storedAppeal = repository.get(appealIdentifier);
        
       
        storedAppeal.setStatus(storedAppeal.getStatus());
        storedAppeal.setQuestions(appeal.getQuestions());
        storedAppeal.setGrade(appeal.getGrade());
        return AppealRepresentation.createResponseAppealRepresentation(storedAppeal, appealUri); 
    }
    
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.CREATED;
    }
}
