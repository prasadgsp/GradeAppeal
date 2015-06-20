package com.restbucks.gradeappeal.activities;

import com.restbucks.gradeappeal.model.Identifier;
import com.restbucks.gradeappeal.model.Appeal;
import com.restbucks.gradeappeal.model.AppealStatus;
import com.restbucks.gradeappeal.model.Email;
import com.restbucks.gradeappeal.model.Eval;
import com.restbucks.gradeappeal.model.Grade;
import com.restbucks.gradeappeal.model.Reply;
import com.restbucks.gradeappeal.repositories.AppealRepository;
import com.restbucks.gradeappeal.repositories.EmailRepository;
import com.restbucks.gradeappeal.representations.AppealRepresentation;
import com.restbucks.gradeappeal.representations.EmailRepresentation;
import com.restbucks.gradeappeal.representations.ReplyRepresentation;
import com.restbucks.gradeappeal.representations.RestbucksUri;

public class ReadReplyActivity {
    public ReplyRepresentation retrieveByUri(RestbucksUri replyUri) {
    
        Identifier identifier  = replyUri.getId();
        
        Email email = EmailRepository.current().get(identifier);
        
        Eval eval=null;
        AppealStatus status=null;
        do
        {
        status = AppealStatus.values()[(int)(Math.random()*AppealStatus.values().length)];
        }while(status!=AppealStatus.ACCEPT && status!=AppealStatus.REJECT);
        
        
        Grade grade=null;
        if(status==AppealStatus.ACCEPT)
        {
          do
           {
           grade = Grade.values()[(int)(Math.random()*Grade.values().length)];
           }while(grade.toString().compareTo(email.getGrade().toString())>-1);
           eval=Eval.CORRECT;
        }
        else
        {
            grade=email.getGrade();
            eval=Eval.WRONG;
        }
        Reply reply=new Reply(email.getToEmail(),email.getFromEmail(),email.getSubject(),eval,grade,status);
                    
        return ReplyRepresentation.createResponseReplyRepresentation(reply, replyUri);
    }
}
