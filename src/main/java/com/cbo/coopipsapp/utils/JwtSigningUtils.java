package com.cbo.coopipsapp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cbo.coopipsapp.crypto.KeyUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAKey;
import java.util.Date;

@Service
@Slf4j
public class JwtSigningUtils {

    @Value("${ips.participant.bic}")
    private String participantBic;
    @Autowired
    KeyUtils keyUtils;
    int accessExpirationMs = 9600000;
    private String signeJWT;


    @PostConstruct
    public  void _init() {
        try {
            this.signeJWT = this.generateJWT("cbo");
        } catch (Exception e) {
            log.error(e.getMessage());

        }
    }

    public String getSigneJWT() {
        if(!StringUtils.hasText(signeJWT)){
            _init();
        }
        return signeJWT;
    }

    public String generateJWT(String userName) throws NoSuchAlgorithmException, Exception {
        PrivateKey privateKey = keyUtils.loadPrivateKey();
        Algorithm algorithm = Algorithm.RSA256((RSAKey) privateKey);
        X509Certificate key = keyUtils.getStoredCerteficate();
        BigInteger serialNumber = key.getSerialNumber();
        String issuer = key.getIssuerX500Principal().getName();
        String jwtToken = JWT.create()
                .withIssuer(participantBic)
                .withClaim("cert_iss", issuer)
                .withClaim("cert_sn", String.valueOf(serialNumber))
                .withExpiresAt(new Date(System.currentTimeMillis() + 5000L))
                .withJWTId("11223312412321")
                .sign(algorithm);
        return jwtToken;
    }

}