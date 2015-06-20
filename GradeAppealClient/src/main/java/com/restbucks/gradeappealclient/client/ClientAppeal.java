package com.restbucks.gradeappealclient.client;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappealclient.model.Question;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Grade;
import com.restbucks.gradeappealclient.representations.Representation;


@XmlRootElement(name = "appeal", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ClientAppeal {
    
    
    @XmlElement(name = "question", namespace = Representation.RESTBUCKS_NAMESPACE)
    private List<Question> questions;
    @XmlElement(name = "grade", namespace = Representation.RESTBUCKS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
    private AppealStatus status;
    
    private ClientAppeal(){}
    
    public ClientAppeal(Appeal appeal) {

        this.grade=appeal.getGrade();
        this.questions = appeal.getQuestions();
    }
    
    public Appeal getAppeal() {
       
        return new Appeal(status, questions, grade);
    }
    
    
    public List<Question> getQuestions() {
 
        return questions;
    }

    public Grade getGrade() {
        return this.grade;
    }
   
    @Override
    public String toString() {
       
        try {
            JAXBContext context = JAXBContext.newInstance(ClientAppeal.class);
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