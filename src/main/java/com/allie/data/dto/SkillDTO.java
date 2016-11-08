package com.allie.data.dto;

/**
 * Created by andrew.larsen on 10/31/2016.
 */
public class SkillDTO {

    private String skillId;
    private String description;

    public SkillDTO(){}

    public SkillDTO(String skillId, String description) {
        this.skillId = skillId;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
                "skillId='" + skillId + '\'' +
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

}
