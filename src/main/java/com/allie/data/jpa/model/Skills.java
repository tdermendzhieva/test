package com.allie.data.jpa.model;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
public enum Skills {
    ATTEND_MEETING("ATTEND_MEETING"),
    PROTECT_BY_ALLIE("PROTECT_BY_ALLIE"),
    CHILD_SAFETY("CHILD_SAFETY");

    private String value;

    private Skills(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
