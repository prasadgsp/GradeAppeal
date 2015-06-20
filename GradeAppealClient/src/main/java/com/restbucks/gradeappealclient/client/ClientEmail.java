package com.restbucks.gradeappealclient.client;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappealclient.model.Question;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Email;
import com.restbucks.gradeappealclient.model.Grade;
import com.restbucks.gradeappealclient.representations.Representation;


@XmlRootElement(name = "email", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ClientEmail {
   
    
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
    
    private ClientEmail(){}
    
    public ClientEmail(Email email) {
        this.fromEmail=email.getFromEmail();
        this.toEmail=email.getToEmail();
        this.subject=email.getSubject();
        this.questions=email.getQuestions();
        this.grade=email.getGrade();
        this.status=email.getStatus();
    }
    
    public Email getEmail() {

        return new Email(fromEmail, toEmail, subject, questions, grade, status);
    }
    
    
    @Override
    public String toString() {
   
        try {
            JAXBContext context = JAXBContext.newInstance(ClientEmail.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {

        return status;
    }

}