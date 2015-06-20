package com.restbucks.gradeappealclient.model;
import com.restbucks.gradeappealclient.model.AppealStatus;
import java.util.ArrayList;
import java.util.List;

public class ReplyBuilder {
    
    private String fromEmail="Frank.Calliss@asu.edu";
    private String toEmail="gmanjapa@asu.edu";
    private String subject="Reply : APPEAL - Exam";
    private Eval eval=null;
    private Grade grade=null;
    private AppealStatus status = null;
    
    public static ReplyBuilder email() {
        return new ReplyBuilder();
    }
    
     public Reply build(Eval eval, Grade grade) {
           return new Reply(fromEmail, toEmail, subject, eval, grade, status);
    }
    
    public ReplyBuilder withStatus(AppealStatus status)
    {
        this.status=status;
        return this;
    }
    
    public ReplyBuilder withEval(Eval eval)
    {
       this.eval=eval;
        return this;
    }
    
    public ReplyBuilder withToEmail(String name) {
        this.toEmail = name;
        return this;
    }
    
    public ReplyBuilder withFromEmail(String name) {
        this.fromEmail = name;
        return this;
    }
    
    public ReplyBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
     public ReplyBuilder withGrade(Grade grade) {
        this.grade = grade;
        return this;
    }

}
