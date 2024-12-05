package com.cbo.coopipsapp.service;

public interface XMLDigestVerifier {

    public String verify(String signedXml);

    void clearCache();
}
