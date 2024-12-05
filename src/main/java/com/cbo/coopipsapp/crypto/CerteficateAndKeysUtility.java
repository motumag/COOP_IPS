package com.cbo.coopipsapp.crypto;

import com.cbo.coopipsapp.models.CerteficateInformation;
import com.cbo.coopipsapp.models.TokenInfo;
import com.cbo.coopipsapp.service.CerteficatClientService;
import com.cbo.coopipsapp.service.TokenManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


@Component
@Data
public class CerteficateAndKeysUtility {
    @Autowired
    CerteficatClientService certeficatClientService;
    @Autowired
    private TokenManager jwtManager;
    @Autowired
    private KeyUtils keyUtils;
    @Value("${security.pki.certificate.file.location}")
    private String certificateKeyPath;
    public RSAPublicKey loadCerteficateFromLdpa(CerteficateInformation certeficateInformation) throws Exception {
        ResponseEntity<TokenInfo> tokenInfo = this.jwtManager.generateToken();
        TokenInfo token = tokenInfo.getBody();
        CerteficateInformation certeficate = this.certeficatClientService.downloadCerteficate(certeficateInformation.getCertificateSerialNumber(),certeficateInformation, token.getAccess_token());
        X509Certificate x509Certificate = convertBase64StringToCerteficate(certeficate);
        RSAPublicKey publicKey = (RSAPublicKey) x509Certificate.getPublicKey();
        return publicKey;
    }
    public X509Certificate convertBase64StringToCerteficate(CerteficateInformation certeficate) throws CertificateException {
        String certificateString = certeficate.getCertificate();
        X509Certificate certificate = null;
        CertificateFactory cf = null;
        try {
            if (certificateString != null && !certificateString.trim().isEmpty()) {
                certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----", "")
                        .replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
                byte[] certificateData = Base64.getDecoder().decode(certificateString);
                cf = CertificateFactory.getInstance("X509");
                certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));
                
            }
        } catch (CertificateException e) {
            throw new CertificateException(e);
        }
        return certificate;
    }

    public void clearCache() {
        certeficatClientService.clearAllCache();
    }
}
