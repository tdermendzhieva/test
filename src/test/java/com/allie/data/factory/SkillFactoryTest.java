package com.allie.data.factory;

import com.allie.data.jpa.model.AllieSkill;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by andrew.larsen on 10/31/2016.
 */
public class SkillFactoryTest {

    private String skillId = "TESTSKILL";
    private String description = "TESTDESCRIPTION";
    AllieSkill allieSkill;
    SkillFactory skillFactory = new SkillFactory();
    @Before
    public void setup(){
        allieSkill = new AllieSkill();
    }
    @Test
    public void testCreateSkillSkillId() throws Exception {
        allieSkill.setSkillId(skillId);
        String id = skillFactory.createSkill(allieSkill).getSkillId();
        assertThat(id).isEqualTo(skillId);

    }
    @Test
    public void testCreateSkillDescription() throws Exception {
        allieSkill.setDescription(description);
        assertThat(skillFactory.createSkill(allieSkill).getDescription()).isEqualTo(description);

    }
}