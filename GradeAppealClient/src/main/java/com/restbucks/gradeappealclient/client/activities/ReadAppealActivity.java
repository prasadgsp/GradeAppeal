package com.restbucks.gradeappealclient.client.activities;

import java.net.URI;

import com.restbucks.gradeappealclient.client.ClientAppeal;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;

public class ReadAppealActivity extends Activity {

    private final URI appealUri;
    private AppealRepresentation currentAppealRepresentation;

    public ReadAppealActivity(URI appealUri) {
        this.appealUri = appealUri;
    }

    public void readAppeal() {
        try {
            currentAppealRepresentation = binding.retrieveAppeal(appealUri);
            actions = new RepresentationHypermediaProcessor().extractNextActionsFromAppealRepresentation(currentAppealRepresentation);
        } catch (NotFoundException e) {
            actions = new Actions();
            actions.add(new PlaceAppealActivity());
        } catch (ServiceFailureException e) {
            actions = new Actions();
            actions.add(this);
        }
    }

    public ClientAppeal getAppeal() {
        return new ClientAppeal(currentAppealRepresentation.getAppeal());
    }
}
