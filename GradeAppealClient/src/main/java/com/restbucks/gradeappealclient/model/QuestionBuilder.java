package com.restbucks.gradeappealclient.model;

import java.util.Random;


public class QuestionBuilder {
    public static QuestionBuilder question() {
        return new QuestionBuilder();
    }

   private TeacherCom tcom=TeacherCom.NOEXAMPLE;
   private StudentCom scom=StudentCom.EXAMPLEND;
    
    public Question build() {
        return new Question(tcom, scom);
    }
    

    public QuestionBuilder withTeacherCom(TeacherCom tcom) {
        this.tcom = tcom;
        return this;
    }
    
    public QuestionBuilder withStudentCom(StudentCom scom) {
        this.scom = scom;
        return this;
    }

    public QuestionBuilder random() {
        Random r = new Random();
        tcom = TeacherCom.values()[r.nextInt(TeacherCom.values().length)];
        scom = StudentCom.values()[r.nextInt(StudentCom.values().length)];
         // Obviously if Grade A, no need for a grade appeal     
        return this;
    }
    
}
