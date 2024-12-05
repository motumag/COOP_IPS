package com.cbo.coopipsapp.crypto;

import com.cbo.coopipsapp.models.CerteficateInformation;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyUtils {

    @Value("${security.pki.privatekey.file.location}")
    private String privateKeyPath;

    @Value("${security.pki.certificate.file.location}")
    private String certificateKeyPath;

    public PrivateKey loadPrivateKey() {
        PrivateKey privateKey = null;

        File file = new File(privateKeyPath);
        try {
            privateKey = readPKCS8PrivateKey(file);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return privateKey;

    }


    public PrivateKey readPKCS8PrivateKey(File file) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        FileReader keyReader = new FileReader(file);
        PemReader pemReader = new PemReader(keyReader);
        PemObject pemObject = pemReader.readPemObject();
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(privKeySpec);

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

    public X509Certificate getStoredCerteficate() {
        X509Certificate cer = null;
        CertificateFactory keyFactory = null;
        try {
            keyFactory = CertificateFactory.getInstance("x.509");
            FileInputStream is = new FileInputStream(certificateKeyPath);
            cer = (X509Certificate) keyFactory.generateCertificate(is);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cer;
    }
}
