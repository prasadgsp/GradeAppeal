package com.restbucks.gradeappeal.model;

import com.restbucks.gradeappeal.representations.Representation;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Email {
    
    private String fromEmail;
    private String toEmail;
    private String subject;
    private List<Question> questions;
    private Grade grade;
    @XmlTransient
    private AppealStatus status = AppealStatus.SUBMIT;
    
    public Email(String fromEmail, String toEmail, String subject, List<Question> questions, Grade grade, AppealStatus status) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.subject = subject;
        this.questions=questions;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nEMAIL\n");
        sb.append("From Email: " + fromEmail + "\n");
        sb.append("To Email: " + toEmail + "\n");
        sb.append("Subject: " + subject + "\n");
        for(Question q : questions) {
            sb.append("Question: " + q.toString()+ "\n");
        }
        sb.append("Grade:" + grade + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString();
    }
     
}