package com.allie.data.service;

import com.allie.data.dto.SkillDTO;
import com.allie.data.factory.SkillFactory;
import com.allie.data.jpa.model.AllieSkill;
import com.allie.data.repository.AllieSkillsRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
@Component
public class AllieSkillService {

    private AllieSkillsRepository repository;
    private SkillFactory skillFactory;

    public AllieSkillService(AllieSkillsRepository repository, SkillFactory skillFactory) {
        this.repository = repository;
        this.skillFactory = skillFactory;
    }

    /**
     * Method to get all allie skills for Jadex platform
     * will return 404 if no skills are found
     * @return
     */
    public List<SkillDTO> getSkills(){
        List<AllieSkill> skills = repository.findAll();
        //if no skills are found, throw exception for exception handler to handle appropriately
        if(skills.isEmpty()){
            throw new MissingResourceException("No skills found" , SkillDTO.class.getName(), "");
        }
        //create response object
        List<SkillDTO> skillDTOs = new ArrayList<>();
        for(AllieSkill skill : skills){
            skillDTOs.add(skillFactory.createSkill(skill));
        }
        return skillDTOs;
    }
}
