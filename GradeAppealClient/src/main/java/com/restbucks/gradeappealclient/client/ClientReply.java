package com.restbucks.gradeappealclient.client;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappealclient.model.Eval;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Reply;
import com.restbucks.gradeappealclient.model.Grade;
import com.restbucks.gradeappealclient.representations.Representation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "email", namespace = Representation.RESTBUCKS_NAMESPACE)
public class ClientReply {
    
    private static final Logger LOG = LoggerFactory.getLogger(ClientReply.class);
    
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
    
    private ClientReply(){}
    
    public ClientReply(Reply reply) {
        this.fromEmail=reply.getFromEmail();
        this.toEmail=reply.getToEmail();
        this.subject=reply.getSubject();
        this.eval=reply.getEval();
        this.grade=reply.getGrade();
        this.status=reply.getStatus();
    }
    
    public Reply getReply() {
        LOG.debug("Executing ClientOrder.getOrder");
        return new Reply(fromEmail, toEmail, subject, eval, grade, status);
    }
    
    
    @Override
    public String toString() {
        LOG.debug("Executing ClientOrder.toString");
        try {
            JAXBContext context = JAXBContext.newInstance(ClientReply.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AppealStatus getStatus() {
        LOG.debug("Executing ClientOrder.getStatus");
        return status;
    }

}