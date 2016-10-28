package com.allie.data.jpa.model;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
public class Skill {
    private String skillId;
    private String description;

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Skill(String skillId, String description) {
        this.skillId = skillId;
        this.description = description;
    }

    public Skill() {
    }
}
