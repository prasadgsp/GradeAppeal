package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

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
