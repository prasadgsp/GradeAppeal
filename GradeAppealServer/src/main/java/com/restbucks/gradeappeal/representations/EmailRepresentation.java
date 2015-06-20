package com.restbucks.gradeappeal.representations;

import com.restbucks.gradeappeal.activities.InvalidEmailException;
import com.restbucks.gradeappeal.model.AppealStatus;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappeal.model.Email;
import com.restbucks.gradeappeal.model.Grade;
import com.restbucks.gradeappeal.model.Question;
import static com.restbucks.gradeappeal.representations.Representation.RELATIONS_URI;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


@XmlRootElement(name = "email", namespace = Representation.RESTBUCKS_NAMESPACE)
public class EmailRepresentation extends Representation {

    @XmlElement(name = "fromemail", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String fromEmail;
    @XmlElement(name = "toemail", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String toEmail;
    @XmlElement(name = "subject", namespace = Representation.RESTBUCKS_NAMESPACE)
    private String subject;
    @XmlElement(name = "question", namespace = Representation.RESTBUCKS_NAMESPACE)
    private List<Question> questions;
    @XmlElement(name = "grade", namespace = Representation.RESTBUCKS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
    private AppealStatus status;
   
    
    EmailRepresentation() {
    }

   public static EmailRepresentation fromXmlString(String xmlRepresentation) {
        EmailRepresentation emailRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(EmailRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            emailRepresentation = (EmailRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidEmailException(e);
        }
        return emailRepresentation;
    }
    
    public static EmailRepresentation createResponseEmailRepresentation(Email email, RestbucksUri emailUri) {
        
        EmailRepresentation emailRepresentation;     
        
       RestbucksUri responseUri = new RestbucksUri(emailUri.getBaseUri() + "/reply/" + emailUri.getId().toString());
        emailRepresentation=null;
        if(email.getStatus() == AppealStatus.SUBMIT) {
            emailRepresentation = new EmailRepresentation(email, 
                    new Link(RELATIONS_URI + "reply", responseUri), 
                    new Link(Representation.SELF_REL_VALUE, emailUri));
        }  else {
            throw new RuntimeException("Unknown Email Status");
        }
        
        return emailRepresentation;
    }

    public EmailRepresentation(Email email, Link... links) {
        
        try {
            this.fromEmail = email.getFromEmail();
            this.toEmail = email.getToEmail();
            this.subject = email.getSubject();
            this.questions=email.getQuestions();
            this.grade=email.getGrade();
            this.status=email.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidEmailException(ex);
        }
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(EmailRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Email getEmail() {
   
        if (status == null) {
            System.out.println("INVALID EMAIL!!!");
            throw new InvalidEmailException();
        }
        Email email = new Email(fromEmail, toEmail, subject, questions, grade, status);
        return email;
    }

    public Link getReplyLink() {
        return getLinkByName(RELATIONS_URI + "reply");
    }
       
    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    
    public AppealStatus getStatus() {
        return status;
    }
}
