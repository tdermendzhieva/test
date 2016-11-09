package com.allie.data.factory;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.jpa.model.OAuthAccount;
import org.springframework.stereotype.Component;

@Component
public class OAuthAccountFactory {

    public OAuthAccount createOAuthAccount(OAuthAccountDTO dto) {
        OAuthAccount oAuthAccount = new OAuthAccount();
        oAuthAccount.setAllieId(dto.getAllieId());
        oAuthAccount.setAccountIssuer(dto.getAccountIssuer());
        oAuthAccount.setAccessToken(dto.getAccessToken());
        oAuthAccount.setRefreshToken(dto.getRefreshToken());

        return oAuthAccount;
    }

    public OAuthAccountDTO createOAuthAccountDTO(OAuthAccount oAuthAccount) {
        OAuthAccountDTO oAuthAccountDTO = new OAuthAccountDTO();
        oAuthAccountDTO.setAllieId(oAuthAccount.getAllieId());
        oAuthAccountDTO.setAccountIssuer(oAuthAccount.getAccountIssuer());
        oAuthAccountDTO.setAccessToken(oAuthAccount.getAccessToken());
        oAuthAccountDTO.setRefreshToken(oAuthAccount.getRefreshToken());

        return oAuthAccountDTO;
    }
}
