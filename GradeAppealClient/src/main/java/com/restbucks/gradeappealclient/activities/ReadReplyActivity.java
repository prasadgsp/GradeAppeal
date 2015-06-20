package com.restbucks.gradeappealclient.activities;

import com.restbucks.gradeappealclient.model.Identifier;
import com.restbucks.gradeappealclient.model.Appeal;
import com.restbucks.gradeappealclient.model.AppealStatus;
import com.restbucks.gradeappealclient.model.Email;
import com.restbucks.gradeappealclient.model.Eval;
import com.restbucks.gradeappealclient.model.Grade;
import com.restbucks.gradeappealclient.model.Reply;
import com.restbucks.gradeappealclient.repositories.AppealRepository;
import com.restbucks.gradeappealclient.repositories.EmailRepository;
import com.restbucks.gradeappealclient.representations.AppealRepresentation;
import com.restbucks.gradeappealclient.representations.EmailRepresentation;
import com.restbucks.gradeappealclient.representations.ReplyRepresentation;
import com.restbucks.gradeappealclient.representations.RestbucksUri;

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
