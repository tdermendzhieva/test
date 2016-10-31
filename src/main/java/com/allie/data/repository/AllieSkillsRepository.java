package com.allie.data.repository;

import com.allie.data.jpa.model.AllieSkill;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
public interface AllieSkillsRepository extends MongoRepository<AllieSkill, String> {
}
