package com.restbucks.gradeappeal.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.ordering.representations.Representation;

@XmlRootElement
public class Question {

    @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)
    private TeacherCom tcom;
     @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)
    private StudentCom scom;

    Question(){}
    
    public Question(TeacherCom tcom, StudentCom scom) {
        this.tcom = tcom;
        this.scom = scom;       
    }
    
    public TeacherCom getTcom() {
        return tcom;
    }

       public StudentCom getSCom() {
        return scom;
    }
    
    public String toString() {
        return "\nTeacher Comment: " + tcom + "\nStudent Comment: " + scom;
    }
}