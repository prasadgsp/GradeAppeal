package com.restbucks.gradeappeal.resources;

import com.restbucks.gradeappeal.activities.CreateEmailActivity;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.restbucks.gradeappeal.activities.InvalidEmailException;
import com.restbucks.gradeappeal.activities.NoSuchEmailException;
import com.restbucks.gradeappeal.activities.EmailActivity;
import com.restbucks.gradeappeal.activities.UpdateException;
import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.representations.Link;
import com.restbucks.gradeappeal.representations.EmailRepresentation;
import com.restbucks.gradeappeal.representations.Representation;
import com.restbucks.gradeappeal.representations.RestbucksUri;
import javax.ws.rs.POST;


@Path("/email")
public class EmailResource {
    
  
    
    private @Context UriInfo uriInfo;
    
    public EmailResource(){
       
    }
   
    public EmailResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
    
      
    @POST
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response email(String emailRepresentation) {
        
        System.out.println("\nReceived a Request to Create an Email using POST\n");
        Response response;
       try {
            EmailRepresentation responseRepresentation = new CreateEmailActivity().create(EmailRepresentation.fromXmlString(emailRepresentation).getEmail(), new RestbucksUri(uriInfo.getRequestUri()));
            response = Response.created(responseRepresentation.getReplyLink().getUri()).entity(responseRepresentation).build();
        } catch(NoSuchEmailException nsoe) {
            response = Response.status(Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
           Identifier identifier = new RestbucksUri(uriInfo.getRequestUri()).getId();
            Link link = new Link(Representation.SELF_REL_VALUE, new RestbucksUri(uriInfo.getBaseUri().toString() + "email/" + identifier));
            response = Response.status(Status.FORBIDDEN).entity(link).build();
        } catch(InvalidEmailException ipe) {
            response = Response.status(Status.BAD_REQUEST).build();
        } catch(Exception e) {
            response = Response.serverError().build();
        }
        
        System.out.println("\n\nResponse object for create email request is " + response.getEntity().toString() +"\n\n");
        
        return response;
    }
}
