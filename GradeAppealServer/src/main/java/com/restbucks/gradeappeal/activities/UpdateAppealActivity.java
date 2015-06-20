package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

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
