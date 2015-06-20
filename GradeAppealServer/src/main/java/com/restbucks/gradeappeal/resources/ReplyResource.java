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
import com.restbucks.gradeappeal.activities.CreateReplyActivity;
import com.restbucks.gradeappeal.activities.InvalidReplyException;
import com.restbucks.gradeappeal.activities.NoSuchReplyException;
import com.restbucks.gradeappeal.activities.ReadAppealActivity;
import com.restbucks.gradeappeal.activities.ReadReplyActivity;
import com.restbucks.gradeappeal.activities.UpdateException;
import com.restbucks.gradeappeal.activities.UpdateAppealActivity;
import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.ReplyRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/reply")
public class ReplyResource {
    
    private @Context UriInfo uriInfo;

    public ReplyResource() {
        
    }

    public ReplyResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;  
    }
    
    
    
    @GET
    @Path("/{replyId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response getReply() {
        
      System.out.println("\nReceived a Request to Create a new Reply using GET\n");
        Response response;
        try {
            ReplyRepresentation responseRepresentation = new ReadReplyActivity().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
            System.out.println("\nOK!!!!\n");
            response = Response.ok().entity(responseRepresentation).build();           
            System.out.println("Retrieved the Reply Email" + responseRepresentation.getReply().toString());
        } catch(NoSuchAppealException nsoe) {        
            response = Response.status(Status.NOT_FOUND).build();
        } catch (Exception ex) {
            response = Response.serverError().build();
        }
        System.out.println("\n\nResponse object for get reply request is " + response.getEntity().toString() +"\n\n");
       
        return response;
    }
    
}
