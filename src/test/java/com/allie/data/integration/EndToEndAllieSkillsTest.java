package com.allie.data.integration;

import com.allie.data.dto.SkillDTO;
import com.allie.data.jpa.model.AllieSkill;
import com.allie.data.repository.AllieSkillsRepository;
import com.allie.data.util.AllieSkillsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndAllieSkillsTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private AllieSkillsRepository repository;

    private AllieSkill allieSkill;
    private String url = "/allie-data/v1/skills";
    private String skillId = "TESTSKILL";
    private String description = "TESTDESCRIPTION";
    @Before
    public void setup(){
        allieSkill = AllieSkillsTestUtil.createSkill(skillId, description);
    }
    @After
    public void dropDB() {

        //Make sure we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Then drop it
        template.getDb().dropDatabase();
    }

    @Test
    public void testGetAllSkills200(){
        //insert skill for testing
        repository.insert(allieSkill);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> resp = this.testRestTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        assertThat("200 is response code", resp.getStatusCode() == HttpStatus.OK);
    }
    @Test
    public void testGetAllSkills200AndResponseBodySizeIsCorrect(){
        //insert skill for testing
        repository.insert(allieSkill);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<List<SkillDTO>> resp = this.testRestTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<SkillDTO>>() {
        });
        assertThat("One object is returned", resp.getBody().size() == 1);
    }
    @Test
    public void testGetAllSkills200AndResponseBodySkillDTOisCorrect(){
        //insert skill for testing
        repository.insert(allieSkill);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<List<SkillDTO>> resp = this.testRestTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<SkillDTO>>() {
        });
        assertThat("AllieSkill is correct", resp.getBody().get(0).getDescription().equals(allieSkill.getDescription()));
        assertThat("AllieSkill is correct", resp.getBody().get(0).getSkillId().equals(allieSkill.getSkillId()));

    }
    @Test
    public void testGetAllSkillsSkillsNotFound404(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");

        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> resp = this.testRestTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        assertThat("404 is response code", resp.getStatusCode() == HttpStatus.NOT_FOUND);


    }

}
