package com.allie.data.jpa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
@Document(collection = "AvailableSkills")
public class AllieSkill {

    @Id
    private String dbId;
    private String skillId;
    private String description;

    @Override
    public String toString() {
        return "AllieSkill{" +
                "dbId='" + dbId + '\'' +
                ", skillId='" + skillId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

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

    public String getDbId() {
        return dbId;
    }
    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

}
