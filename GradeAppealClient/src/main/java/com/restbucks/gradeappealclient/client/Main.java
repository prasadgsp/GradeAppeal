package com.restbucks.gradeappealclient.client;

import static com.restbucks.gradeappealclient.model.AppealBuilder.appeal;
import static com.restbucks.gradeappealclient.model.EmailBuilder.email;
import java.net.URI;
import java.net.URISyntaxException;

import com.restbucks.gradeappealclient.client.activities.Actions;
import com.restbucks.gradeappealclient.client.activities.PlaceAppealActivity;
import com.restbucks.gradeappealclient.client.activities.ReadAppealActivity;
import com.restbucks.gradeappealclient.client.activities.UpdateAppealActivity;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Email;
import com.restbucks.gradeappealclient.representations.Link;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.EmailRepresentation;
import com.restbucks.gradeappealclient.representations.ReplyRepresentation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final String RESTBUCKS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    private static final long ONE_MINUTE = 60000; 
    
    private static final String ENTRY_POINT_URI = "http://localhost:8080/GradeAppealServer/webresources/appeal";
    private static final String EMAIL_POINT_URI = "http://localhost:8080/GradeAppealServer/webresources/email";
    
    public static void main(String[] args) throws Exception {
        URI appealUri = new URI(ENTRY_POINT_URI);
        Scanner sc=new Scanner(System.in);
        
        System.out.println("--------------------------------------------------------------------------------------------------------------\n\nExecuting HAPPY Case....Please Enter to continue\n\n");
        sc.nextLine();
        happyCase(appealUri);
        
        System.out.println("--------------------------------------------------------------------------------------------------------------\n\nExecuting ABANDONED Case....Please Enter to continue\n\n");
        sc.nextLine();
        abandonCase(appealUri);
        
        System.out.println("--------------------------------------------------------------------------------------------------------------\n\nExecuting FORGOTTEN Case....Please Enter to continue\n\n");
        sc.nextLine();
        forgotCase(appealUri);
        
        System.out.println("--------------------------------------------------------------------------------------------------------------\n\nExecuting BAD START Case....Please Enter to continue\n\n");
        sc.nextLine();
        badStartCase(appealUri);
        
        System.out.println("--------------------------------------------------------------------------------------------------------------\n\nExecuting BAD ID Case....Please Enter to continue\n\n");
        sc.nextLine();
        badIdCase(appealUri);
    }
    
     private static void badStartCase(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING BAD START CASE WITH APPEAL URI "+ appealUri +"--------\n\n");
        
        // Compose the appeal
        System.out.println(String.format("\n\n-------Composing appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("\nComposing base appeal " + appeal);
        Client client = Client.create();
        System.out.println("\nCreated appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("\nResponse appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println("\nAppeal composed at " + appealRepresentation.getSelfLink().getUri().toString());
         
         //Updating the appeal with a bad URI
        System.out.println("\n-------Trying a BAD UPDATE-------\n");
        System.out.println(String.format("About to update an order with bad URI [%s] via POST", appealRepresentation.getUpdateLink().getUri().toString() + "/bad-uri"));
        appeal = appeal().withRandom().build();
        System.out.println("\nComposing base appeal " + appeal);
        Link badUriLink = new Link("bad", new RestbucksUri(appealRepresentation.getSelfLink().getUri().toString() + "/bad-uri"), RESTBUCKS_MEDIA_TYPE);
        System.out.println("\nCreated bad URI Link "+ badUriLink);
        ClientResponse badResponse = client.resource(badUriLink.getUri()).accept(badUriLink.getMediaType()).type(badUriLink.getMediaType()).post(ClientResponse.class, new AppealRepresentation(appeal));
        System.out.println("\nBad Response Received from Server is" + badResponse);
        System.out.println("\nTried to update order with bad URI at "+ badUriLink.getUri().toString() +" via POST ");
        System.out.println("\n The status of the bad response was " +badResponse.getStatus());
     }
     
     private static void forgotCase(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING FORGOT CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Compose the appeal
        System.out.println(String.format("\n\n-------Composing appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("Composing base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Response appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal composed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
    
        // Send the appeal as email
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\n-------Sending the appeal composd as an email at [%s] via POST-------\n\n", emailUri.toString()));
        Email email = email().build(appeal.getQuestions(),appeal.getGrade());
        System.out.println("Email to be sent" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(EmailRepresentation.class, new ClientEmail(email));
        System.out.println("Response email received from server is " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
      
        System.out.println("\n--- Email Appeal was sent but Professor did not reply");
        System.out.println("\n--- Hence, Following up\n\n");
        
        
        // Following up with professor with same appeal
        System.out.println("\n\n-------Following up with earlier appeal via POST-------\n");
        email.setSubject("Follow Up on APPEAL - Exam");
        System.out.println("Email to be sent" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation2 = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(EmailRepresentation.class, new ClientEmail(email));
        System.out.println("Response email received from server is:\n " + emailRepresentation2.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        // Get the reply email from professor
        System.out.println("\n\n------Get the reply email from professor------");
        System.out.println(String.format("\nAbout to request the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Reply link  for the email is " + replyLink.getUri().toString());
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Reply Email received from server is:\n " + replyRepresentation.getReply());
        
    }
    
    private static void badIdCase(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING BAD ID CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Compose the appeal
        System.out.println(String.format("\n\n-------Composing appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("Composing base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Response appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal composed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        
        // Update the appeal
        System.out.println("\n\n-------About to update appeal via POST-------");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Response Updated Appeal received from server is:\n " + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
    
        // Send the appeal as email
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\n-------Sending the appeal composd as an email at [%s] via POST-------\n\n", emailUri.toString()));
        Email email = email().build(appeal.getQuestions(),appeal.getGrade());
        System.out.println("Email to be sent" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(EmailRepresentation.class, new ClientEmail(email));
        System.out.println("Response email received from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        
        System.out.println("\n\n--- Email Appeal was sent but URI was forgotten to follow up \n");
        System.out.println("\n\n--- Hence, Creating New Appeal and Sending it\n\n");
        
        
       // Compose the appeal again
        System.out.println(String.format("\n\n-------Composing the same appeal as earlier at [%s] via POST------\n\n", appealUri.toString()));
        System.out.println("Composing base appeal " + appeal);
        System.out.println("Created appeal link -" + appealUri);
        appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Response appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal composed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
            
        // Send the appeal as email
        System.out.println(String.format("\n\n-------Sending the appeal email again at [%s] via POST-------\n\n", emailUri.toString()));
        email = email().build(appeal.getQuestions(),appeal.getGrade());
        System.out.println("Email to be sent" + email);
        System.out.println("Sending email with URI - " + emailUri);
        emailRepresentation = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(EmailRepresentation.class, new ClientEmail(email));
        System.out.println("Response email received from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        // Get the reply email from professor
        System.out.println("\n\n------Get the reply email from professor------");
        System.out.println(String.format("\nAbout to request the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Reply link  for the email is " + replyLink.getUri().toString());
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Reply Email received from server is:\n " + replyRepresentation.getReply());
        
    }
    
    private static void abandonCase(URI appealUri) throws Exception {
        
        System.out.println("\n\n--------STARTING ABANDON CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Compose the appeal
        System.out.println(String.format("\n\n-------Composing appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("Composing base appeal " + appeal);
        Client client = Client.create();
        System.out.println("Created appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("Composed appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal composed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
        
        // Update the appeal
        System.out.println("\n\n-------About to update appeal via POST-------");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Received Updated Appeal from server is:\n" + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
    
        //Cancelling the Appeal
        System.out.println(String.format("\n\n-------Abandoning the Appeal created earlier by cancelling it------\n\n", appealUri.toString()));
        Link cancelLink = appealRepresentation.getCancelLink();
        System.out.println("Created appeal update link -" + cancelLink.getUri());
        ClientResponse finalResponse = client.resource(cancelLink.getUri()).delete(ClientResponse.class);
        System.out.println(String.format("Delete Response Status is [%d]", finalResponse.getStatus()));
        if(finalResponse.getStatus() == 200) {
        System.out.println(String.format("Appeal Created has been Deleted", finalResponse.getEntity(AppealRepresentation.class).getStatus()));
        }
    
    }

    private static void happyCase(URI appealUri) throws Exception {
        
       System.out.println("\n\n--------STARTING HAPPY CASE WITH APPEAL URI "+appealUri+" --------\n\n");
        
        // Compose the appeal
        System.out.println(String.format("\n\n-------Composing appeal at [%s] via POST------\n\n", appealUri.toString()));
        Appeal appeal = appeal().withRandom().build();
        System.out.println("\nComposing base appeal " + appeal);
        Client client = Client.create();
        System.out.println("\nCreated appeal link -" + appealUri);
        AppealRepresentation appealRepresentation = client.resource(appealUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new ClientAppeal(appeal));
        System.out.println("\nComposed appeal received from server is:\n" + appealRepresentation.getAppeal());
        System.out.println(String.format("Appeal composed at [%s]", appealRepresentation.getSelfLink().getUri().toString()));
            
      //  System.out.println("\n\n\n --------" + appealRepresentation.getEmailLink().getUri().toString()+"\n\n\n\n----");
        // Update the appeal
        System.out.println("\n\n-------About to update appeal via PUT-------");
        appeal = appeal().withRandomItems(appeal.getGrade()).build();
        System.out.println("Updated appeal " + appeal);
        Link updateLink = appealRepresentation.getUpdateLink();
        System.out.println("Created appeal update link -" + updateLink.getUri());
        AppealRepresentation updatedRepresentation = client.resource(updateLink.getUri()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(AppealRepresentation.class, new AppealRepresentation(appeal));
        System.out.println("Received Updated Appeal from server is:\n" + updatedRepresentation.getAppeal());
        System.out.println(String.format("Appeal updated at [%s]", updatedRepresentation.getSelfLink().getUri().toString()));
        
      
        // Send the appeal as email
        URI emailUri=new URI(EMAIL_POINT_URI);
        System.out.println(String.format("\n\n-------Sending an email at [%s] via POST-------\n\n", emailUri.toString()));
        Email email = email().build(appeal.getQuestions(),appeal.getGrade());
        System.out.println("Email to be sent" + email);
        System.out.println("Sending email with URI - " + emailUri);
        EmailRepresentation emailRepresentation = client.resource(emailUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(EmailRepresentation.class, new ClientEmail(email));
        System.out.println("Received Email from server is:\n " + emailRepresentation.getEmail());
        System.out.println(String.format("Email sent at [%s]", emailRepresentation.getSelfLink().getUri().toString()));
        
        // Get the reply email from professor
        System.out.println("\n\n------Get the reply email from professor------");
        System.out.println(String.format("\nAbout to request the reply email from Professor via GET at [%s]", emailRepresentation.getReplyLink().getUri().toString()));
        Link replyLink = emailRepresentation.getReplyLink();
        System.out.println("Retrieved the reply link " + replyLink.getUri().toString() + " for the email " );
        ReplyRepresentation replyRepresentation = client.resource(replyLink.getUri()).get(ReplyRepresentation.class);
        System.out.println("Reply Email received is:\n " + replyRepresentation.getReply());
    }
    
}
