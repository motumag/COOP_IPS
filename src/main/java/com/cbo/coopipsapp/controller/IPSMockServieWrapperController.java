package com.cbo.coopipsapp.controller;


import com.cbo.coopipsapp.service.DigestService;
import com.cbo.coopipsapp.service.MockApplicationServerAccessService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IPSMockServieWrapperController {
    @Autowired
    private MockApplicationServerAccessService accessService;

    @Autowired
    private DigestService digestService;

    @PostMapping(value = "/bank1/v1/iso20022/callback", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> wrapMockServiceBank1CallBack(@RequestBody String message, HttpServletRequest request) {
        String apiKey = "abysiniaIps12345566676";
        ResponseEntity<String> wrappedMessage;
        String headers = request.getHeader("Api-key");
        accessService.setPort("8084");
        wrappedMessage = accessService.invokeMockService(message);
        if (headers.equalsIgnoreCase(apiKey)) {
            System.out.println(" the api key is right");
        } else {
            System.out.println(" the api key is Wrong");
        }

        return wrappedMessage;
    }

    @PostMapping(value = "/bank2/v1/iso20022/callback", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> wrapMockServiceBank2CallBack(@RequestBody String message) {
        ResponseEntity<String> wrappedMessage;
        accessService.setPort("8085");
        wrappedMessage = accessService.invokeMockService(message);
        return wrappedMessage;
    }

    @PostMapping(value = "/bank3/v1/iso20022/callback", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> wrapMockServiceBank3CallBack(@RequestBody String message) {
        ResponseEntity<String> wrappedMessage;
        accessService.setPort("8086");
        wrappedMessage = accessService.invokeMockService(message);
        return wrappedMessage;
    }

}
