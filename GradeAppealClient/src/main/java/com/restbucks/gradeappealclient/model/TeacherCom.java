package com.restbucks.gradeappealclient.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum TeacherCom {
    @XmlEnumValue(value="No answer was given in the answer sheet")
    NOANSWER,
    @XmlEnumValue(value="Only part of the answer was provided")
    PARTIALANS,
    @XmlEnumValue(value="No examples are provided")
    NOEXAMPLE,
    @XmlEnumValue(value="The answer did not match the question asked")
    INCORRECT
}
