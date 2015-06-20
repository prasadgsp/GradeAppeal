package com.restbucks.gradeappealclient.model;
import com.restbucks.gradeappealclient.model.AppealStatus;
import java.util.ArrayList;
import java.util.List;

public class EmailBuilder {
    
    private String fromEmail="gmanjapa@asu.edu";
    private String toEmail="Frank.Calliss@asu.edu";
    private String subject="APPEAL - Exam";
    private ArrayList<Question> questions = null;
    private Grade grade=null;
    private AppealStatus status = AppealStatus.SUBMIT;
    
    public static EmailBuilder email() {
        return new EmailBuilder();
    }
    
     public Email build(List<Question> questions, Grade grade) {
           return new Email(fromEmail, toEmail, subject, questions,grade, status);
    }
    
    public EmailBuilder withStatus(AppealStatus status)
    {
        this.status=status;
        return this;
    }
    
    public EmailBuilder withQuestion(Question question)
    {
        if(questions == null) {
            questions = new ArrayList<Question>();
        }
        questions.add(question);
        return this;
    }
    
    public EmailBuilder withToEmail(String name) {
        this.toEmail = name;
        return this;
    }
    
    public EmailBuilder withFromEmail(String name) {
        this.fromEmail = name;
        return this;
    }
    
    public EmailBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
     public EmailBuilder withGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

}
