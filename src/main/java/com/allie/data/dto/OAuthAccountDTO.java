package com.allie.data.dto;

public class OAuthAccountDTO {
    private String allieId;
    private String accountIssuer;
    private String accessToken;
    private String refreshToken;

    public void setAllieId(String allieId) {
        this.allieId = allieId;
    }

    public String getAllieId() {
        return allieId;
    }

    public void setAccountIssuer(String accountIssuer) {
        this.accountIssuer = accountIssuer;
    }

    public String getAccountIssuer() {
        return accountIssuer;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String toString() {
        return "OAuthAccountDTO { " +
                "allieId: '" + allieId + "\', " +
                "accountIssuer: '" + accountIssuer + "\' " +
                '}';
    }
}
