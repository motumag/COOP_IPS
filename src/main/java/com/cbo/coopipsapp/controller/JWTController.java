package com.cbo.coopipsapp.controller;

import com.cbo.coopipsapp.utils.JwtSigningUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RequestMapping("/api")
@RestController()
public class JWTController {

    @Autowired
    private JwtSigningUtils jwtSigningUtils;

    @PostMapping(value = "/jwt")
    public String getJwt(@RequestBody String request) {

        String xmlResponse = null;
        try {
            xmlResponse = jwtSigningUtils.generateJWT(request);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        xmlResponse = xmlResponse.replace("&#xD;", "");
        return xmlResponse;
    }
}
