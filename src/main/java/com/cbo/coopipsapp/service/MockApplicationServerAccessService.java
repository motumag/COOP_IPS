package com.cbo.coopipsapp.service;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Properties;

@Service
@Data
public class MockApplicationServerAccessService {
    Properties properties = new Properties();

    @Value("${ets.bank.mock.server.url}")
    private String serverUrl;
    @Value("${ets.bank.mock.server.api.path}")
    private String apiPath;

    private String port;
    HttpHeaders headers;
    private URI AUTH_SERVER_UPLOAD_URI;
    private URI AUTH_SERVER_TOKEN_URI;
    private RestTemplate restTemplate;
    @Autowired
    private DigestService digestService;
    @Autowired
    private XMLDigestVerifier digestVerifier;
    private static final Logger logger= LoggerFactory.getLogger(MockApplicationServerAccessService.class);
    public void create() {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(serverUrl)
                .append(":")
                .append(port)
                .append(apiPath);
        restTemplate = new RestTemplate();
        AUTH_SERVER_UPLOAD_URI = URI.create(urlBuilder.toString());
        headers = new HttpHeaders();

    }

    public ResponseEntity<String> invokeMockService(String xmlMessage) {
        String isValid =digestVerifier.verify(xmlMessage);
        logger.debug(" verification started:  ");
        System.out.println(" the message is: "+ xmlMessage);
        System.out.println(" from out verification started:");
        String validationMessage="true".equals(isValid)? "valid": " not valid";
        logger.debug(" verified message and the message is :  " +validationMessage);
        System.out.println(" from out verified message and the message is :  " +validationMessage);
        create();
        ResponseEntity<String> responseXml;
        ResponseEntity<String> wrappedResponseXml;
        headers.setContentType(MediaType.APPLICATION_XML);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();


        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
        responseXml = restTemplate.postForEntity(AUTH_SERVER_UPLOAD_URI, xmlMessage, String.class);
        String responseBody= digestService.signDocument(responseXml.getBody());
        wrappedResponseXml= new ResponseEntity<>(responseBody,responseXml.getStatusCode());
        return wrappedResponseXml;
    }

}
