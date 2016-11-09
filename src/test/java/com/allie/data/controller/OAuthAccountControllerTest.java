package com.allie.data.controller;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.service.OAuthAccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthAccountControllerTest {

    @Mock
    private OAuthAccountService oAuthAccountService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPostMethodShouldCallInsertService() throws Exception {
        OAuthAccountDTO account = new OAuthAccountDTO();

        OAuthAccountController oAuthAccountController = new OAuthAccountController(oAuthAccountService);

        OAuthAccountDTO response = oAuthAccountController.postAccount(account, "requestId", "correlationId");

        assertThat(response, is(account));
        verify(oAuthAccountService).insert(account);
    }

    @Test
    public void testGetMethodShouldCallGetService() throws Exception {
        String allieId = "1";
        List<OAuthAccountDTO> accounts = new ArrayList<>();
        accounts.add(new OAuthAccountDTO());

        when(oAuthAccountService.getByAllieId(allieId)).thenReturn(accounts);

        OAuthAccountController oAuthAccountController = new OAuthAccountController(oAuthAccountService);
        List<OAuthAccountDTO> response = oAuthAccountController.getAccount(allieId, "requestId", "correlationId");

        assertThat(response, is(accounts));
    }
}
