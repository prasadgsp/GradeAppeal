package com.restbucks.gradeappealclient.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.restbucks.gradeappealclient.representations.Representation;

@XmlRootElement
public class Question {

    @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)
    private TeacherCom tcom;
     @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)
    private StudentCom scom;
    
    /**
     * For JAXB :-(
     */
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