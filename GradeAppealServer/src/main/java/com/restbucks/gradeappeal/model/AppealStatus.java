package com.restbucks.gradeappeal.model;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="Created")
    CREATED,
    @XmlEnumValue(value="Submitted")
    SUBMIT, 
    @XmlEnumValue(value="Accepted")
    ACCEPT, 
    @XmlEnumValue(value="Rejected")
    REJECT, 
    @XmlEnumValue(value="Cancelled")
    DELETE
}
