package com.allie.data.factory;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.jpa.model.OAuthAccount;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OAuthAccountFactoryTest {
    private OAuthAccountDTO dto;
    private OAuthAccount model;

    private OAuthAccountFactory oAuthAccountFactory;

    @Before
    public void setUp() throws Exception {
        oAuthAccountFactory = new OAuthAccountFactory();

        dto = new OAuthAccountDTO();
        dto.setAllieId("1");
        dto.setAccountIssuer("google");
        dto.setAccessToken("accessToken");
        dto.setRefreshToken("refreshToken");

        model = new OAuthAccount();
        model.setAllieId("2");
        model.setAccountIssuer("not-google");
        model.setAccessToken("differentAccessToken");
        model.setRefreshToken("differentRefreshToken");
    }

    @Test
    public void testCreateOAuthAccountModelFromDTO() throws Exception {
        OAuthAccount account = oAuthAccountFactory.createOAuthAccount(dto);

        assertThat(account.getAllieId(), is(dto.getAllieId()));
        assertThat(account.getAccountIssuer(), is(dto.getAccountIssuer()));
        assertThat(account.getAccessToken(), is(dto.getAccessToken()));
        assertThat(account.getRefreshToken(), is(dto.getRefreshToken()));
    }

    @Test
    public void testCreateOAuthAccountDTOFromModel() throws Exception {
        OAuthAccountDTO dto = oAuthAccountFactory.createOAuthAccountDTO(model);

        assertThat(dto.getAllieId(), is(model.getAllieId()));
        assertThat(dto.getAccountIssuer(), is(model.getAccountIssuer()));
        assertThat(dto.getAccessToken(), is(model.getAccessToken()));
        assertThat(dto.getRefreshToken(), is(model.getRefreshToken()));
    }
}
