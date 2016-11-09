package com.allie.data.integration;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.jpa.model.OAuthAccount;
import com.allie.data.repository.OAuthAccountRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndOAuthAccountTest {

    private HttpEntity<OAuthAccountDTO> nullPostRequest;
    private HttpEntity<OAuthAccountDTO> validPostRequest;
    private HttpEntity validGetRequest;
    private String url = "/allie-data/v1/oAuthAccounts";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private OAuthAccountRepository repository;

    @Before
    public void setUp() throws Exception {

        OAuthAccountDTO nullOAuthAccountDTO = new OAuthAccountDTO();

        OAuthAccountDTO oAuthAccountDTO = new OAuthAccountDTO();
        oAuthAccountDTO.setAllieId("allieId");
        oAuthAccountDTO.setAccountIssuer("Google");
        oAuthAccountDTO.setAccessToken("accessToken");
        oAuthAccountDTO.setRefreshToken("refreshToken");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        validPostRequest = new HttpEntity<>(oAuthAccountDTO, headers);

        nullPostRequest = new HttpEntity<>(nullOAuthAccountDTO, headers);

        validGetRequest = new HttpEntity<>(null, headers);

        Index index = new Index().on("allieId", Sort.Direction.ASC).unique();
        template.indexOps(OAuthAccount.class).ensureIndex(index);
        assertThat(template.getCollection("OAuthAccounts").getIndexInfo().size(), equalTo(2));
    }

    @After
    public void dropDB() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    @Test
    public void testPostOAuthAccount() throws Exception {
        ResponseEntity<OAuthAccountDTO> response = testRestTemplate.postForEntity(url, validPostRequest, OAuthAccountDTO.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        List<OAuthAccount> accounts = repository.findAll();
        assertThat(accounts.size(), is(1));
        assertThat(accounts.get(0).getAllieId(), is("allieId"));
        assertThat(accounts.get(0).getAccountIssuer(), is("Google"));
        assertThat(accounts.get(0).getAccessToken(), is("accessToken"));
        assertThat(accounts.get(0).getRefreshToken(), is("refreshToken"));
    }

    @Test
    public void testPostOAuthAccountNoDuplicateAllieId() throws Exception {
        testRestTemplate.postForEntity(url, validPostRequest, OAuthAccountDTO.class);
        ResponseEntity<OAuthAccountDTO> secondResponse = testRestTemplate.postForEntity(url, validPostRequest, OAuthAccountDTO.class);

        assertThat(secondResponse.getStatusCode(), is(HttpStatus.CONFLICT));
    }

    @Test
    public void testThatBadRequestIsReturedWhenAllieIdIsNullOnPost() throws Exception {
        ResponseEntity<OAuthAccountDTO> response = testRestTemplate.postForEntity(url, nullPostRequest, OAuthAccountDTO.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void testGetOAuthAccountDTOByAllieId() throws Exception {
        OAuthAccount oAuthAccount = new OAuthAccount();
        oAuthAccount.setAllieId("allieId2");
        oAuthAccount.setAccountIssuer("Google2");
        oAuthAccount.setAccessToken("accessToken2");
        oAuthAccount.setRefreshToken("refreshToken2");

        repository.save(oAuthAccount);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        builder.queryParam("allieId", "allieId2");

        ResponseEntity<OAuthAccountDTO[]> response =
                testRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, validGetRequest, OAuthAccountDTO[].class);

        assertThat(response.getBody().length, is(1));
        assertThat(response.getBody()[0].getAllieId(), is(oAuthAccount.getAllieId()));
        assertThat(response.getBody()[0].getAccountIssuer(), is(oAuthAccount.getAccountIssuer()));
        assertThat(response.getBody()[0].getAccessToken(), is(oAuthAccount.getAccessToken()));
        assertThat(response.getBody()[0].getRefreshToken(), is(oAuthAccount.getRefreshToken()));
    }
}
