package com.cbo.coopipsapp.service;

import com.cbo.coopipsapp.models.TokenInfo;
import com.cbo.coopipsapp.utils.JwtSigningUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Slf4j
public class TokenManager {

    private RestTemplate restTemplate;


    HttpHeaders headers;
    private URI AUTH_SERVER_UPLOAD_URI;
    private URI AUTH_SERVER_TOKEN_URI;

    @Value("${ets.ips.token.url}")
    private String tokenUrl;
    private JwtSigningUtils signingUtils;

    @Value("${ets.ips.grantType}")
    private String grantType;
    @Value("${ets.ips.userName}")
    private String userName;
    @Value("${ets.ips.password}")
    private String password;

    @Autowired
    public TokenManager(JwtSigningUtils signingUtils) {
        this.signingUtils = signingUtils;
    }


    public void create() {
        restTemplate = new RestTemplate();
        AUTH_SERVER_TOKEN_URI = URI.create(tokenUrl);
        headers = new HttpHeaders();

    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }


    public ResponseEntity<TokenInfo> generateToken() {
        ResponseEntity<TokenInfo> token = null;
        create();
        // ContentType
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String signeJWT = signingUtils.getSigneJWT();
        headers.add("jwt-assertion", signeJWT);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
        requestBody.add("username", userName);
        requestBody.add("password", password);
        requestBody.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);

        token = restTemplate.postForEntity(AUTH_SERVER_TOKEN_URI, httpEntity,
                TokenInfo.class);


        return token;

    }
}
