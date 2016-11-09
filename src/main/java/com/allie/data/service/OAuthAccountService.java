package com.allie.data.service;

import com.allie.data.dto.OAuthAccountDTO;
import com.allie.data.factory.OAuthAccountFactory;
import com.allie.data.jpa.model.OAuthAccount;
import com.allie.data.repository.OAuthAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OAuthAccountService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OAuthAccountRepository oAuthAccountRepository;
    private final OAuthAccountFactory oAuthAccountFactory;

    public OAuthAccountService(OAuthAccountRepository oAuthAccountRepository, OAuthAccountFactory oAuthAccountFactory) {

        this.oAuthAccountRepository = oAuthAccountRepository;
        this.oAuthAccountFactory = oAuthAccountFactory;
    }

    public void insert(OAuthAccountDTO newAccount) {
        if (StringUtils.isEmpty(newAccount.getAllieId())) {
            logger.error("Account missing required field, received: " + newAccount);
            throw new IllegalArgumentException("Account must have an allieId");
        }

        OAuthAccount model = oAuthAccountFactory.createOAuthAccount(newAccount);

        oAuthAccountRepository.insert(model);
        logger.debug("Inserted Account");
    }

    public List<OAuthAccountDTO> getByAllieId(String allieId) {
        if (StringUtils.isEmpty(allieId)) {
            logger.error("OAuthAccountService getByAllieId must not be null or empty");
            throw new IllegalArgumentException("AllieId must not be null or empty");
        }

        List<OAuthAccount> accounts = oAuthAccountRepository.findByAllieId(allieId);

        return accounts.stream().map(oAuthAccountFactory::createOAuthAccountDTO).collect(Collectors.toList());
    }
}
