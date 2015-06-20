package com.restbucks.gradeappeal.representations;

import com.restbucks.gradeappeal.activities.InvalidEmailException;
import com.restbucks.gradeappeal.model.AppealStatus;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappeal.model.Eval;
import com.restbucks.gradeappeal.model.Grade;
import com.restbucks.gradeappeal.model.Reply;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


@XmlRootElement(name = "reply", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ReplyRepresentation extends Representation {

    @XmlElement(name = "fromemail", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String fromEmail;
    @XmlElement(name = "toemail", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String toEmail;
    @XmlElement(name = "subject", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String subject;
    @XmlElement(name = "eval", namespace = Representation.RESTBUCKS_NAMESPACE)
    private Eval eval;
    @XmlElement(name = "grade", namespace = Representation.RESTBUCKS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
    private AppealStatus status;
   
    ReplyRepresentation() {
    }

   public static ReplyRepresentation fromXmlString(String xmlRepresentation) {       
        ReplyRepresentation replyRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            replyRepresentation = (ReplyRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidEmailException(e);
        }
        return replyRepresentation;
    }
    
    public static ReplyRepresentation createResponseReplyRepresentation(Reply reply, RestbucksUri replyUri) {
       
       ReplyRepresentation replyRepresentation;     
        
       RestbucksUri responseUri = new RestbucksUri(replyUri.getBaseUri() + "/reply/" + replyUri.getId().toString());
        replyRepresentation=null;
        if((reply.getStatus() == AppealStatus.ACCEPT) || (reply.getStatus() == AppealStatus.REJECT)) {
            replyRepresentation = new ReplyRepresentation(reply,
                    new Link(Representation.SELF_REL_VALUE, replyUri));
        }  else {
            throw new RuntimeException("Unknown Email Status");
        }
        
        return replyRepresentation;
    }

    public ReplyRepresentation(Reply reply, Link... links) {
        
        
        try {
            this.fromEmail = reply.getFromEmail();
            this.toEmail = reply.getToEmail();
            this.subject = reply.getSubject();
            this.eval=reply.getEval();
            this.grade=reply.getGrade();
            this.status=reply.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidEmailException(ex);
        }
        
       
    }

    @Override
    public String toString() {
     
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Reply getReply() {
     
   
        if (status == null) {
            System.out.println("INVALID EMAIL!!!");
            throw new InvalidEmailException();
        }
        
        Reply reply = new Reply(fromEmail, toEmail, subject, eval, grade, status);
        
      

        return reply;
    }

 
       
    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    
    public AppealStatus getStatus() {
        return status;
    }
}