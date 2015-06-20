package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

public class RemoveAppealActivity {
    public AppealRepresentation delete(RestbucksUri appealUri) {
      
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new NoSuchAppealException();
        }

        Appeal appeal = appealRepository.get(identifier);

      
        if (appeal.getStatus() == AppealStatus.SUBMIT || appeal.getStatus() == AppealStatus.ACCEPT || appeal.getStatus() == AppealStatus.REJECT) {
            throw new AppealDeletionException();
        }

        if(appeal.getStatus() == AppealStatus.CREATED) {  
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }

}
