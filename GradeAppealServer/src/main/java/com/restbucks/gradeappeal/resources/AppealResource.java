package com.restbucks.gradeappeal.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.restbucks.gradeappeal.activities.RemoveAppealActivity;
import com.restbucks.gradeappeal.activities.CreateAppealActivity;
import com.restbucks.gradeappeal.activities.InvalidAppealException;
import com.restbucks.gradeappeal.activities.NoSuchAppealException;
import com.restbucks.gradeappeal.activities.AppealDeletionException;
import com.restbucks.gradeappeal.activities.ReadAppealActivity;
import com.restbucks.gradeappeal.activities.UpdateException;
import com.restbucks.gradeappeal.activities.UpdateAppealActivity;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.RestbucksUri;



@Path("/appeal")
public class AppealResource {
    
    private @Context UriInfo uriInfo;

    public AppealResource() {
        
    }

    public AppealResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;  
    }
    
    @GET
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response getAppeal() {
        
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new ReadAppealActivity().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch(NoSuchAppealException nsoe) {     
            response = Response.status(Status.NOT_FOUND).build();
        } catch (Exception ex) {
            response = Response.serverError().build();
        }
        
        return response;
    }
    
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response createAppeal(String appealRepresentation) {
        
        System.out.println("\nReceived a Request to Create an Appeal using POST\n");
        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new CreateAppealActivity().create(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.created(responseRepresentation.getUpdateLink().getUri()).entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {    
            response = Response.status(Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            response = Response.serverError().build();
        }

        System.out.println("\n\nResponse object for create appeal request is "+ response.getEntity().toString() +"\n\n");
        return response;
    }

    @DELETE
    @Path("/{appealId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response removeAppeal() {

        System.out.println("\nReceived a Request to Delete an Appeal using DELETE\n");
        Response response;
        
        try {
            AppealRepresentation removedAppeal = new RemoveAppealActivity().delete(new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(removedAppeal).build();
        } catch (NoSuchAppealException nsoe) {       
            response = Response.status(Status.NOT_FOUND).build();
        } catch(AppealDeletionException ode) {
            response = Response.status(405).header("Allow", "GET").build();
        } catch (Exception ex) {
            response = Response.serverError().build();
        }
        
        System.out.println("\nAppeal was Created\n");
        return response;
    }

    @POST
    @Path("/{appealId}")
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response updateAppeal(String appealRepresentation) {
        
        System.out.println("\nReceived a Request to Update an Appeal using PUT\n");

        Response response;
        
        try {
            AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(appealRepresentation).getAppeal(), new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (InvalidAppealException ioe) {
            response = Response.status(Status.BAD_REQUEST).build();
        } catch (NoSuchAppealException nsoe) {
            response = Response.status(Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
            response = Response.status(Status.CONFLICT).build();
        } catch (Exception ex) {
            response = Response.serverError().build();
        } 
        
       System.out.println("\n\nResponse object for create appeal request is "+ response.getEntity().toString() +"\n\n");
        
        return response;
     }
}
