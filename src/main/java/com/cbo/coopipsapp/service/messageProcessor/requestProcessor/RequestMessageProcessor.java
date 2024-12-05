package com.cbo.coopipsapp.service.messageProcessor.requestProcessor;//package org.ips.xml.signer.xmlSigner.service.messageProcessor.requestProcessor;
//
//
//import ch.qos.logback.core.joran.spi.XMLUtil;
//import org.ips.xml.signer.xmlSigner.models.MessageProcessor;
//import org.ips.xml.signer.xmlSigner.utils.XMLFileUtility;
//import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
//import org.springframework.beans.factory.config.BeanDefinition;
//import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
//import org.springframework.core.type.filter.AnnotationTypeFilter;
//import org.springframework.util.ClassUtils;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.print.Doc;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
//import static org.ips.xml.signer.xmlSigner.context.Constants.BAH_NAME;
//
//public class RequestMessageProcessor {
//
//    public static void processMessageType(Document xmlDocument) {
//        ClassPathScanningCandidateComponentProvider provider =
//                new ClassPathScanningCandidateComponentProvider(false);
//        provider.addIncludeFilter(new AnnotationTypeFilter(MessageProcessor.class));
//
//        Set<BeanDefinition> beanDefs = provider
//                .findCandidateComponents("org.ips.xml.signer.xmlSigner.service.messageProcessor.requestProcessor");
//        Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
//        beanDefs.stream().forEach(bd -> {
//
//            String name = ((AnnotatedBeanDefinition) bd).getMetadata().
//                    getAnnotationAttributes(MessageProcessor.class.getCanonicalName()).get("name").toString();
//            beanDefinitionMap.put(name, bd);
//
//        });
//    }
//
//    public static String getMessageType(Document xmlDocument) {
//
//        String value = xmlDocument.getDocumentElement().getAttribute("xmlns:document");
//        return value;
//
//    }
//
//    public static void main(String[] args) {
//        String xmlString = "<FPEnvelope xmlns=\"urn:iso:std:iso:20022:tech:xsd:verification_response\" \n" +
//                "xmlns:header=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\" \n" +
//                "xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:acmt.024.001.03\">\n" +
//                "\t<header:AppHdr>\n" +
//                "\t\t<header:Fr>\n" +
//                "\t\t\t<header:FIId>\n" +
//                "\t\t\t\t<header:FinInstnId>\n" +
//                "\t\t\t\t\t<header:Othr>\n" +
//                "\t\t\t\t\t\t<header:Id>AMHRETAAXXX</header:Id>\n" +
//                "\t\t\t\t\t</header:Othr>\n" +
//                "\t\t\t\t</header:FinInstnId>\n" +
//                "\t\t\t</header:FIId>\n" +
//                "\t\t</header:Fr>\n" +
//                "\t\t<header:To>\n" +
//                "\t\t\t<header:FIId>\n" +
//                "\t\t\t\t<header:FinInstnId>\n" +
//                "\t\t\t\t\t<header:Othr>\n" +
//                "\t\t\t\t\t\t<header:Id>AWINETAAXXX</header:Id>\n" +
//                "\t\t\t\t\t</header:Othr>\n" +
//                "\t\t\t\t</header:FinInstnId>\n" +
//                "\t\t\t</header:FIId>\n" +
//                "\t\t</header:To>\n" +
//                "\t\t<header:BizMsgIdr>AMHRETAAXXX2023122212170900568</header:BizMsgIdr>\n" +
//                "\t\t<header:MsgDefIdr>acmt.024.001.03</header:MsgDefIdr>\n" +
//                "\t\t<header:CreDt>2023-12-22T12:17:09.568Z</header:CreDt>\n" +
//                "\t\t<header:Rltd>\n" +
//                "\t\t\t<header:Fr>\n" +
//                "\t\t\t\t<header:FIId>\n" +
//                "\t\t\t\t\t<header:FinInstnId>\n" +
//                "\t\t\t\t\t\t<header:Othr>\n" +
//                "\t\t\t\t\t\t\t<header:Id>AWINETAAXXX</header:Id>\n" +
//                "\t\t\t\t\t\t</header:Othr>\n" +
//                "\t\t\t\t\t</header:FinInstnId>\n" +
//                "\t\t\t\t</header:FIId>\n" +
//                "\t\t\t</header:Fr>\n" +
//                "\t\t\t<header:To>\n" +
//                "\t\t\t\t<header:FIId>\n" +
//                "\t\t\t\t\t<header:FinInstnId>\n" +
//                "\t\t\t\t\t\t<header:Othr>\n" +
//                "\t\t\t\t\t\t\t<header:Id>AMHRETAAXXX</header:Id>\n" +
//                "\t\t\t\t\t\t</header:Othr>\n" +
//                "\t\t\t\t\t</header:FinInstnId>\n" +
//                "\t\t\t\t</header:FIId>\n" +
//                "\t\t\t</header:To>\n" +
//                "\t\t\t<header:BizMsgIdr>AWINETAAXXX1698755948</header:BizMsgIdr>\n" +
//                "\t\t\t<header:MsgDefIdr>acmt.023.001.03</header:MsgDefIdr>\n" +
//                "\t\t\t<header:CreDt>2023-08-24T00:00:00.000Z</header:CreDt>\n" +
//                "\t\t</header:Rltd>\n" +
//                "\t\t<document:Sgntr xmlns:document=\"urn:iso:std:iso:20022:tech:xsd:head.001.001.03\">\n" +
//                "\t\t\t<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
//                "\t\t\t\t<ds:SignedInfo>\n" +
//                "\t\t\t\t\t<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
//                "\t\t\t\t\t<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n" +
//                "\t\t\t\t\t<ds:Reference URI=\"#f9e6a82e-f875-48e8-8faa-a0396f56813c\">\n" +
//                "\t\t\t\t\t\t<ds:Transforms>\n" +
//                "\t\t\t\t\t\t\t<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
//                "\t\t\t\t\t\t</ds:Transforms>\n" +
//                "\t\t\t\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
//                "\t\t\t\t\t\t<ds:DigestValue>tyEzOEhNwmDPT8Cs+11mUahC77Ym4TsRm7xvuMB0nwI=</ds:DigestValue>\n" +
//                "\t\t\t\t\t</ds:Reference>\n" +
//                "\t\t\t\t\t<ds:Reference Type=\"http://uri.etsi.org/01903/v1.3.2#SignedProperties\" URI=\"#d632b510-288f-47a7-a55d-b770ecd227db\">\n" +
//                "\t\t\t\t\t\t<ds:Transforms>\n" +
//                "\t\t\t\t\t\t\t<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
//                "\t\t\t\t\t\t</ds:Transforms>\n" +
//                "\t\t\t\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
//                "\t\t\t\t\t\t<ds:DigestValue>k38sRGWGqHchUDOgrcabTNOcbhWnHeC+pHTxuNt/N9o=</ds:DigestValue>\n" +
//                "\t\t\t\t\t</ds:Reference>\n" +
//                "\t\t\t\t\t<ds:Reference>\n" +
//                "\t\t\t\t\t\t<ds:Transforms>\n" +
//                "\t\t\t\t\t\t\t<ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
//                "\t\t\t\t\t\t</ds:Transforms>\n" +
//                "\t\t\t\t\t\t<ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" +
//                "\t\t\t\t\t\t<ds:DigestValue>uO5R9rT3TvQA+uDTWU3EFHlDT0b637oDMmxRSJxs520=</ds:DigestValue>\n" +
//                "\t\t\t\t\t</ds:Reference>\n" +
//                "\t\t\t\t</ds:SignedInfo>\n" +
//                "\t\t\t\t<ds:SignatureValue>\n" +
//                "iNetE9Vw+PGy1kZ1D5K0nI5FK/8fnkvlaSaouYk/t5UCDQlOda+CD10+izoP2SsK+xDvgiqbJfZ6\n" +
//                "2LsjgT2zbygoAYSPwaMdFK8N3ATTTt8iOlHw289REyM+BLSsP2Oiz3zkCg+NVuKOmadtznwKFrJ2\n" +
//                "Jb7Ho21kkGT4S+YVptcvIjbAYdyXEZQ2xjxsXVOiant6UmrDtyTLqxSpVPdQAQRdHeQrMabbOoH0\n" +
//                "12t/tYuZIuXHPr6fIq4ANjh1VOsWVi3dV6STFiZ2UgvTXRQ4hjA0r6y1PmrRho7Xp90PJtIWSe9n\n" +
//                "0m1J5HtwWQMUpbcU039YNhG5YkGiEySvXZlZiQ==\n" +
//                "</ds:SignatureValue>\n" +
//                "\t\t\t\t<ds:KeyInfo Id=\"f9e6a82e-f875-48e8-8faa-a0396f56813c\">\n" +
//                "\t\t\t\t\t<ds:X509Data>\n" +
//                "\t\t\t\t\t\t<ds:X509IssuerSerial>\n" +
//                "\t\t\t\t\t\t\t<ds:X509IssuerName>CN=TEST ETS IPS Issuing CA,O=EthSwitch,C=ET</ds:X509IssuerName>\n" +
//                "\t\t\t\t\t\t\t<ds:X509SerialNumber>423714158962634707877257781427416909140197412</ds:X509SerialNumber>\n" +
//                "\t\t\t\t\t\t</ds:X509IssuerSerial>\n" +
//                "\t\t\t\t\t</ds:X509Data>\n" +
//                "\t\t\t\t</ds:KeyInfo>\n" +
//                "\t\t\t\t<ds:Object>\n" +
//                "\t\t\t\t\t<xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\">\n" +
//                "\t\t\t\t\t\t<xades:SignedProperties Id=\"d632b510-288f-47a7-a55d-b770ecd227db\">\n" +
//                "\t\t\t\t\t\t\t<xades:SignedSignatureProperties>\n" +
//                "\t\t\t\t\t\t\t\t<xades:SigningTime>2023-11-06T11:12:25Z</xades:SigningTime>\n" +
//                "\t\t\t\t\t\t\t</xades:SignedSignatureProperties>\n" +
//                "\t\t\t\t\t\t</xades:SignedProperties>\n" +
//                "\t\t\t\t\t</xades:QualifyingProperties>\n" +
//                "\t\t\t\t</ds:Object>\n" +
//                "\t\t\t</ds:Signature>\n" +
//                "\t\t</document:Sgntr>\n" +
//                "\t</header:AppHdr>\n" +
//                "\t<document:Document>\n" +
//                "\t\t<document:IdVrfctnRpt>\n" +
//                "\t\t\t<document:Assgnmt>\n" +
//                "\t\t\t\t<document:MsgId>AMHRETAAXXX2023122212170900568</document:MsgId>\n" +
//                "\t\t\t\t<document:CreDtTm>2023-12-22T12:17:09.568Z</document:CreDtTm>\n" +
//                "\t\t\t\t<document:Assgnr>\n" +
//                "\t\t\t\t\t<document:Agt>\n" +
//                "\t\t\t\t\t\t<document:FinInstnId>\n" +
//                "\t\t\t\t\t\t\t<document:Othr>\n" +
//                "\t\t\t\t\t\t\t\t<document:Id>AMHRETAAXXX</document:Id>\n" +
//                "\t\t\t\t\t\t\t</document:Othr>\n" +
//                "\t\t\t\t\t\t</document:FinInstnId>\n" +
//                "\t\t\t\t\t</document:Agt>\n" +
//                "\t\t\t\t</document:Assgnr>\n" +
//                "\t\t\t\t<document:Assgne>\n" +
//                "\t\t\t\t\t<document:Agt>\n" +
//                "\t\t\t\t\t\t<document:FinInstnId>\n" +
//                "\t\t\t\t\t\t\t<document:Othr>\n" +
//                "\t\t\t\t\t\t\t\t<document:Id>AWINETAAXXX</document:Id>\n" +
//                "\t\t\t\t\t\t\t</document:Othr>\n" +
//                "\t\t\t\t\t\t</document:FinInstnId>\n" +
//                "\t\t\t\t\t</document:Agt>\n" +
//                "\t\t\t\t</document:Assgne>\n" +
//                "\t\t\t</document:Assgnmt>\n" +
//                "\t\t\t<document:OrgnlAssgnmt>\n" +
//                "\t\t\t\t<document:MsgId>AWINETAAXXX1698755948</document:MsgId>\n" +
//                "\t\t\t\t<document:CreDtTm>2023-08-24T00:00:00.000+03:00</document:CreDtTm>\n" +
//                "\t\t\t</document:OrgnlAssgnmt>\n" +
//                "\t\t\t<document:Rpt>\n" +
//                "\t\t\t\t<document:OrgnlId>AWINETAAXXX423113</document:OrgnlId>\n" +
//                "\t\t\t\t<document:Vrfctn>false</document:Vrfctn>\n" +
//                "\t\t\t\t<document:Rsn>\n" +
//                "\t\t\t\t\t<document:Prtry>UNFN</document:Prtry>\n" +
//                "\t\t\t\t</document:Rsn>\n" +
//                "\t\t\t\t<document:OrgnlPtyAndAcctId>\n" +
//                "\t\t\t\t\t<document:Acct>\n" +
//                "\t\t\t\t\t\t<document:Id>\n" +
//                "\t\t\t\t\t\t\t<document:Othr>\n" +
//                "\t\t\t\t\t\t\t\t<document:Id>9900000001966</document:Id>\n" +
//                "\t\t\t\t\t\t\t\t<document:SchmeNm>\n" +
//                "\t\t\t\t\t\t\t\t\t<document:Prtry>ACCT</document:Prtry>\n" +
//                "\t\t\t\t\t\t\t\t</document:SchmeNm>\n" +
//                "\t\t\t\t\t\t\t</document:Othr>\n" +
//                "\t\t\t\t\t\t</document:Id>\n" +
//                "\t\t\t\t\t</document:Acct>\n" +
//                "\t\t\t\t</document:OrgnlPtyAndAcctId>\n" +
//                "\t\t\t</document:Rpt>\n" +
//                "\t\t</document:IdVrfctnRpt>\n" +
//                "\t</document:Document>\n" +
//                "</FPEnvelope>";
//        XMLFileUtility xmlFileUtility = new XMLFileUtility();
//
//        Document xmlDocument = xmlFileUtility.createDocumentFromString(xmlString);
//        processMessageType(xmlDocument);
//        getMessageType(xmlDocument);
//    }
//}
