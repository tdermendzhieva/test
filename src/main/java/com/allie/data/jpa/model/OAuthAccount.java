package com.allie.data.jpa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "OAuthAccounts")
public class OAuthAccount {
    @Id
    public String dbId;

    @Indexed(unique = true)
    private String allieId;

    private String accountIssuer;
    private String accessToken;
    private String refreshToken;

    public String getDbId() {
        return this.dbId;
    }
    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
    public String getAllieId() {
        return allieId;
    }
    public void setAllieId(String allieId) {
        this.allieId = allieId;
    }
    public String getAccountIssuer() {
        return accountIssuer;
    }
    public void setAccountIssuer(String accountIssuer) {
        this.accountIssuer = accountIssuer;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
