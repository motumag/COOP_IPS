package com.cbo.coopipsapp.service;

import com.cbo.coopipsapp.info.SignatureInfo;
import com.cbo.coopipsapp.info.SignatureKeyInfo;
import com.cbo.coopipsapp.utils.XMLFileUtility;
import com.cbo.coopipsapp.utils.XmlSignUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

@Log4j2
@Service
public class DigestServiceImpl implements DigestService {
    public static String xadesNS = "http://uri.etsi.org/01903/v1.3.2#";
    public static String signatureID = "Sig1";
    public static String signedPropID = "SignP";

    @Autowired
    private XMLFileUtility xmlFileUtility;
    @Autowired
    private XmlSignUtil signUtil;

    @Override
    public String signDocument(String xmlString) {

        String signedXml = null;
        Document document = xmlFileUtility.createDocumentFromString(xmlString);
        Document signedDocument=null;
        SignatureInfo signatureInfo = xmlFileUtility.buildKeySignatureInfo();
        SignatureKeyInfo signatureKeyInfo = xmlFileUtility.buildSignaturePrivateKeyInfo();
        try {
            signedDocument= signUtil.sign(document, signatureInfo, signatureKeyInfo);
            signedXml = convertDocumentToString(signedDocument);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return signedXml;
    }

    @Override
    public String signIncomingDocument(String xmlString) {
        String signedXml = null;
        Document document = xmlFileUtility.createDocumentFromString(xmlString);
        Document signedDocument=null;
        SignatureInfo signatureInfo = xmlFileUtility.buildKeySignatureInfo();
        SignatureKeyInfo signatureKeyInfo = xmlFileUtility.buildSignaturePrivateKeyInfo();
        try {
            signedDocument= signUtil.signIncoming(document, signatureInfo, signatureKeyInfo);
            signedXml = convertDocumentToString(signedDocument);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return signedXml;
    }

    private String convertDocumentToString(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private Document createDocumentFromString(String xmlString) {
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
}
