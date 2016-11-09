package com.allie.data.repository;

import com.allie.data.jpa.model.OAuthAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OAuthAccountRepository extends MongoRepository<OAuthAccount, String> {
    List<OAuthAccount> findByAllieId(String allieId);
}
