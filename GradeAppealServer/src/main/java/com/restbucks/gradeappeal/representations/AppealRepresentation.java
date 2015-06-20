package com.restbucks.gradeappeal.representations;


import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappeal.activities.InvalidAppealException;
import com.restbucks.gradeappeal.model.Grade;
import com.restbucks.gradeappeal.model.Question;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.model.AppealStatus;


@XmlRootElement(name = "appeal", namespace = Representation.RESTBUCKS_NAMESPACE)
public class AppealRepresentation extends Representation {

    @XmlElement(name = "question", namespace = Representation.RESTBUCKS_NAMESPACE)
    private List<Question> questions;
    @XmlElement(name = "grade", namespace = Representation.RESTBUCKS_NAMESPACE)
    private Grade grade;
    @XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
    private AppealStatus status;
    AppealRepresentation() {
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        

        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, RestbucksUri appealUri) {
        
        AppealRepresentation appealRepresentation;     
        RestbucksUri emailUri = new RestbucksUri(appealUri.getBaseUri() + "/email");
        appealRepresentation=null;
        if(appeal.getStatus() == AppealStatus.CREATED) {
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(RELATIONS_URI + "cancel", appealUri), 
                    new Link(RELATIONS_URI + "email", emailUri), 
                    new Link(RELATIONS_URI + "update", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        }  else {
            
            throw new RuntimeException("Unknown Appeal Status");
        }
        
        return appealRepresentation;
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        
        try {
            this.questions = appeal.getQuestions();
            this.grade=appeal.getGrade();
            this.status = appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
    }

    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getAppeal() {

        if (questions == null) {
            throw new InvalidAppealException();
        }
        for (Question q : questions) {
            if (q == null) {
                throw new InvalidAppealException();
            }
        }
        
        Appeal appeal = new Appeal(status, questions, grade);
        return appeal;
    }

    public Link getCancelLink() {
        return getLinkByName(RELATIONS_URI + "cancel");
    }
    
     public Link getEmailLink() {
        return getLinkByName(RELATIONS_URI + "email");
    }
    
    
    public Link getUpdateLink() {
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
        return status;
    }
}
