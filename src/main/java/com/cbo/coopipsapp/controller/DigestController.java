package com.cbo.coopipsapp.controller;

import com.cbo.coopipsapp.service.DigestService;
import com.cbo.coopipsapp.service.XMLDigestVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController()
public class DigestController {

    @Autowired
    private DigestService digestService;
    @Autowired
    private XMLDigestVerifier digestVerifier;

    @PostMapping(value = "/digest", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String handleXmlRequest(@RequestBody String request) {
        String xmlResponse = digestService.signDocument(request);
        xmlResponse = xmlResponse.replace("&#xD;", "");
        return xmlResponse;
    }
    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_XML_VALUE)
    public String verifyXml(@RequestBody String request) {
        String xmlResponse = digestVerifier.verify(request);
        xmlResponse = xmlResponse.replace("&#xD;", "");
        return xmlResponse;
    }
//    TODO: add cbs payment

    @PostMapping(value = "/evictCache")
    public String evictCach() {

        digestVerifier.clearCache();
        return "evictede succ";
    }
}
