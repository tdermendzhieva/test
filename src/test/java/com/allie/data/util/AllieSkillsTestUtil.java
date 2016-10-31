package com.allie.data.util;

import com.allie.data.dto.SkillDTO;
import com.allie.data.jpa.model.AllieSkill;

/**
 * Created by andrew.larsen on 10/31/2016.
 */
public class AllieSkillsTestUtil {

    /**
     * Helper method to create AllieSkill object
     * @param skill
     * @param description
     * @return
     */
    public static AllieSkill createSkill(String skill, String description){
        AllieSkill allieSkill = new AllieSkill();
        allieSkill.setSkillId(skill);
        allieSkill.setDescription(description);
        return allieSkill;
    }

    /**
     * Helper method to create SkillDTO object
     * @param skill
     * @param description
     * @return
     */
    public static SkillDTO createSkillDTO(String skill, String description){
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setSkillId(skill);
        skillDTO.setDescription(description);
        return skillDTO;
    }

}
