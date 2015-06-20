package com.restbucks.gradeappealclient.client.activities;

import java.net.URI;

import com.restbucks.gradeappealclient.client.ClientAppeal;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;

public class PlaceAppealActivity extends Activity {

    private Appeal appeal;

    public void place(Appeal appeal, URI appealingUri) {        
        try {
            AppealRepresentation createdAppealRepresentation = binding.createAppeal(appeal, appealingUri);
            this.actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(createdAppealRepresentation);
            this.appeal = createdAppealRepresentation.getAppeal();
        } catch (MalformedAppealException e) {
            this.actions = retryCurrentActivity();
        } catch (ServiceFailureException e) {
            this.actions = retryCurrentActivity();
        }
    }
    
    public ClientAppeal getAppeal() {
        return new ClientAppeal(appeal);
    }
}
