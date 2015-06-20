package com.restbucks.gradeappealclient.network;

import java.net.URI;

import com.restbucks.gradeappealclient.client.activities.CannotCancelException;
import com.restbucks.gradeappealclient.client.activities.CannotUpdateAppealException;
import com.restbucks.gradeappealclient.client.activities.MalformedAppealException;
import com.restbucks.gradeappealclient.client.activities.NotFoundException;
import com.restbucks.gradeappealclient.client.activities.ServiceFailureException;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

public class HttpBinding {

    private static final String RESTBUCKS_MEDIA_TYPE = "application/vnd.restbucks+xml";

    public AppealRepresentation createAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 201) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while creating order resource [%s]", status, appealUri.toString()));
    }
    
    public AppealRepresentation retrieveAppeal(URI appealUri) throws NotFoundException, ServiceFailureException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);

        int status = response.getStatus();

        if (status == 404) {
            throw new NotFoundException ();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response while retrieving order resource [%s]", appealUri.toString()));
    }

    public AppealRepresentation updateAppeal(Appeal appeal, URI appealUri) throws MalformedAppealException, ServiceFailureException, NotFoundException,
            CannotUpdateAppealException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, new AppealRepresentation(appeal));

        int status = response.getStatus();

        if (status == 400) {
            throw new MalformedAppealException();
        } else if (status == 404) {
            throw new NotFoundException();
        } else if (status == 409) {
            throw new CannotUpdateAppealException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while udpating order resource [%s]", status, appealUri.toString()));
    }

    public AppealRepresentation deleteAppeal(URI appealUri) throws ServiceFailureException, CannotCancelException, NotFoundException {
        Client client = Client.create();
        ClientResponse response = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).delete(ClientResponse.class);

        int status = response.getStatus();
        if (status == 404) {
            throw new NotFoundException();
        } else if (status == 405) {
            throw new CannotCancelException();
        } else if (status == 500) {
            throw new ServiceFailureException();
        } else if (status == 200) {
            return response.getEntity(AppealRepresentation.class);
        }

        throw new RuntimeException(String.format("Unexpected response [%d] while deleting order resource [%s]", status, appealUri.toString()));
    }

}