package com.restbucks.gradeappealclient.model;

import static com.restbucks.gradeappealclient.model.QuestionBuilder.question;

import java.util.ArrayList;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppealBuilder {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealBuilder.class);
    
    public static AppealBuilder appeal() {
        return new AppealBuilder();
    }
    
    private ArrayList<Question> questions = null;
    private AppealStatus status = AppealStatus.CREATED;
    private Grade grade=null;
    
    private void defaultItems() {
        LOG.debug("Executing OrderBuilder.defaultItems");
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(question().build());
        this.questions = questions;
    }
    
    private void corruptItems() {
        LOG.debug("Executing OrderBuilder.corruptItems");
        ArrayList<Question> questions = new ArrayList<Question>();
        questions.add(null);
        questions.add(null);
        questions.add(null);
        questions.add(null);
        this.questions = questions;
    }
   
    
    public Appeal build() {
        LOG.debug("Executing OrderBuilder.build");
        if(questions == null) {
            defaultItems();
        }
        return new Appeal(status, questions,grade);
    }

    public AppealBuilder withItem(Question question) {
        LOG.debug("Executing OrderBuilder.withItem");
        if(questions == null) {
            questions = new ArrayList<Question>();
        }
        questions.add(question);
        return this;
    }


    public AppealBuilder withCorruptedValues() {
        LOG.debug("Executing OrderBuilder.withCorruptedValues");
        corruptItems();
        return this;
    }
    
    public AppealBuilder withStatus(AppealStatus status) {
        LOG.debug("Executing OrderBuilder.withRandomItems");
        this.status = status;
        return this;
    }

    public AppealBuilder withRandom() {
        Random r = new Random();
        LOG.debug("Executing OrderBuilder.withRandomItems");
        int numberOfItems = (int) (System.currentTimeMillis() % 5 + 1);
        this.questions = new ArrayList<Question>();
        for(int i = 0; i < numberOfItems; i++) {
            questions.add(question().random().build());
        }
        do
        {
        grade = Grade.values()[r.nextInt(Grade.values().length)];
        }while(grade==Grade.A);
        
        return this;
    }
    
    public AppealBuilder withRandomItems(Grade grade) {
        LOG.debug("Executing OrderBuilder.withRandomItems");
        int numberOfItems = (int) (System.currentTimeMillis() % 5 + 1);
        this.questions = new ArrayList<Question>();
        for(int i = 0; i < numberOfItems; i++) {
            questions.add(question().random().build());
        }   
        this.grade=grade;
        return this;
    }

}
