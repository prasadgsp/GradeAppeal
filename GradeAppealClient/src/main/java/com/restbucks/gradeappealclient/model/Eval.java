package com.restbucks.gradeappealclient.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Eval {
    @XmlEnumValue(value="On further inspection, it was seen that the answers provided were indeed correct")
    CORRECT,
    @XmlEnumValue(value="The grade appeal was reviewed but rejected as the reasons given were not convincing enough")
    WRONG,
}
