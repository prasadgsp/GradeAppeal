package com.restbucks.gradeappeal.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum StudentCom {
    @XmlEnumValue(value="Answer given on backside of sheet")
    ANSBACKSIDE,
    @XmlEnumValue(value="Remaining part of answer is present and highlighted")
    PARTANS,
    @XmlEnumValue(value="Examples given at the end")
    EXAMPLEND,
    @XmlEnumValue(value="The answer given is correct for the question. References are provided at the end")
    ANSWERCORRECT,
}
