package com.cbo.coopipsapp.service;

public interface DigestService {

    String signDocument(String xmlString);
    String signIncomingDocument(String xmlString);
}
