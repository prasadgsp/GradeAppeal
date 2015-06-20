package com.restbucks.gradeappealclient.model;

import com.restbucks.gradeappealclient.representations.Representation;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlTransient;

public class Appeal {
    
    private List<Question> questions;
    @XmlTransient
    private Grade grade;
    @XmlTransient
    private AppealStatus status = AppealStatus.CREATED;
    public Appeal(List<Question> questions, Grade grade) {
      this(AppealStatus.CREATED, questions, grade);
    }
    

    public Appeal(AppealStatus status, List<Question> questions, Grade grade) {
        this.questions = questions;
        this.status = status;
        this.grade=grade;
    }

    
    public List<Question> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<Question> questions)
    {
        this.questions=questions;
    }

    public void setStatus(AppealStatus status) {
        this.status = status;
    }

    public AppealStatus getStatus() {
        return status;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nAPPEAL\n");
        sb.append("Status: " + status + "\n");
        for(Question q : questions) {
            sb.append("Question: " + q.toString()+ "\n");
        }
        sb.append("Grade: " + grade + "\n");
        return sb.toString();
    }
}