package com.restbucks.gradeappeal.model;

import com.restbucks.gradeappeal.representations.Representation;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Reply {
    
    private String fromEmail;
    private String toEmail;
    private String subject;
    private Eval eval;
    private Grade grade;
    @XmlTransient
    private AppealStatus status;
    
    public Reply(String fromEmail, String toEmail, String subject, Eval eval, Grade grade, AppealStatus status) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.eval=eval;
        this.grade=grade;
        this.status = status;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public AppealStatus getStatus() {
        return status;
    }
    
    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public Eval getEval() {
        return eval;
    }

    public void setEval(Eval eval) {
        this.eval = eval;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
     
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nREPLY\n");
        sb.append("From Email: " + fromEmail + "\n");
        sb.append("To Email: " + toEmail + "\n");
        sb.append("Subject: " + subject + "\n");
        sb.append("Evaluation Report: "+eval+"\n");
        sb.append("Grade:" + grade + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString();
    }
     
}