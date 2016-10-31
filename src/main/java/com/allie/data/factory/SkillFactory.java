package com.allie.data.factory;

import com.allie.data.dto.SkillDTO;
import com.allie.data.jpa.model.AllieSkill;
import org.springframework.stereotype.Component;

/**
 * Created by andrew.larsen on 10/31/2016.
 */
@Component
public class SkillFactory {

    /**
     * Factory method to convert mongo AllieSkill object to
     * a SkillDTO object to be returned from controller
     * @param allieSkill
     * @return
     */
    public SkillDTO createSkill(AllieSkill allieSkill){
        return new SkillDTO(allieSkill.getSkillId(), allieSkill.getDescription());
    }
}
