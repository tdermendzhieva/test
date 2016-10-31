package com.allie.data.service;

import com.allie.data.dto.SkillDTO;
import com.allie.data.factory.SkillFactory;
import com.allie.data.jpa.model.AllieSkill;
import com.allie.data.repository.AllieSkillsRepository;
import com.allie.data.util.AllieSkillsTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
public class AllieSkillServiceTest {

    private AllieSkillService skillService;
    private AllieSkillsRepository repository;
    private SkillFactory  factory;
    private AllieSkill allieSkill;
    private List<AllieSkill> skills;
    private String skillId = "TESTSKILL";
    private String description = "TESTDESCRIPTION";
    private SkillDTO skillDTO;
    @Before
    public void setup(){
        repository = mock(AllieSkillsRepository.class);
        factory = mock(SkillFactory.class);
        skillService = new AllieSkillService(repository, factory);
        allieSkill = AllieSkillsTestUtil.createSkill(skillId, description);
        skills = new ArrayList<>();
        skills.add(allieSkill);
        skillDTO = AllieSkillsTestUtil.createSkillDTO(skillId, description);
    }
    @Test
    public void testGetSkillsThrowsExceptionWhenNoSkillsFound() throws Exception {
        given(repository.findAll()).willReturn(new ArrayList<>());
        try{
            skillService.getSkills();
        }
        catch (MissingResourceException e){
            assertThat("exception was thrown correctly", true, equalTo(true));

        }
        catch (Exception e){
            assertThat("incorrect exception was thrown", true, equalTo(false));

        }
    }

    @Test
    public void testSkillsSizeIsCorrect() throws Exception {

        given(repository.findAll()).willReturn(skills);
        given(factory.createSkill(allieSkill)).willReturn(skillDTO);

        assertThat(skillService.getSkills().size(), equalTo(1));
    }
    @Test
    public void testSkillsresponseIsCorrect() throws Exception {

        given(repository.findAll()).willReturn(skills);
        given(factory.createSkill(allieSkill)).willReturn(skillDTO);

        assertThat(skillService.getSkills().get(0), equalTo(skillDTO));
    }
}