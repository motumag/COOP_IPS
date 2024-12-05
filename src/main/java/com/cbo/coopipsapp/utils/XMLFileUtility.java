package com.cbo.coopipsapp.utils;

import com.cbo.coopipsapp.crypto.KeyUtils;
import com.cbo.coopipsapp.info.ReferenceSignInfo;
import com.cbo.coopipsapp.info.SignatureInfo;
import com.cbo.coopipsapp.info.SignatureKeyInfo;
import com.cbo.coopipsapp.models.CerteficateInformation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.security.PrivateKey;

@Component
@Log4j2
public class XMLFileUtility {
    @Autowired
    private KeyUtils usageUtil;

    public ReferenceSignInfo buildDocumentReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public ReferenceSignInfo buildAppHdrReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public ReferenceSignInfo buildKeyReferenceSignInfo() {
        return ReferenceSignInfo.builder().
                transformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                digestMethodAlgorithm("http://www.w3.org/2001/04/xmlenc#sha256").
                build();
    }

    public SignatureInfo buildKeySignatureInfo() {
        SignatureInfo signatureInfo;
        signatureInfo = SignatureInfo.builder().
                signatureMethodAlgorithm("http://www.w3.org/2000/09/xmldsig#rsa-sha1").
                signatureCanonicalizationMethodAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#").
                signatureExclusionTransformer("http://www.w3.org/2001/10/xml-exc-c14n#").
                appHdrReferenceSignInfo(buildAppHdrReferenceSignInfo()).
                documentReferenceSignInfo(buildDocumentReferenceSignInfo()).
                keyReferenceSignInfo(buildKeyReferenceSignInfo()).
                build();
        return signatureInfo;

    }

    public SignatureKeyInfo buildSignaturePrivateKeyInfo() {
        PrivateKey privateKey = this.usageUtil.loadPrivateKey();
        SignatureKeyInfo signatureKeyInfo = SignatureKeyInfo.builder().privateKey(privateKey).build();
        return signatureKeyInfo;
    }


    public Document createDocumentFromString(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public CerteficateInformation parseCerteficateFromDocument(Document doc) {
        Node serianNameNode = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509IssuerName").item(0);
        Node serianNumberNode = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber").item(0);
        String serialName = serianNameNode.getTextContent();
        String serialNumber = serianNumberNode.getTextContent();
        CerteficateInformation certeficateInformation = new CerteficateInformation();
        certeficateInformation.setCertificateIssuer(serialName);
        certeficateInformation.setCertificateSerialNumber(serialNumber);
        return certeficateInformation;
    }

}
