package com.cbo.coopipsapp.service;

import com.cbo.coopipsapp.crypto.CerteficateAndKeysUtility;
import com.cbo.coopipsapp.models.CerteficateInformation;
import com.cbo.coopipsapp.utils.XMLFileUtility;
import com.cbo.coopipsapp.utils.XmlSignUtil;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Setter
@Log4j2
@Service
public class XMLDigestVerifierImpl implements XMLDigestVerifier {
    @Autowired
    XMLFileUtility xmlFileUtility;
    @Autowired
    CerteficateAndKeysUtility certeficateAndKeysUtility;
    @Autowired
    private XmlSignUtil signUtil;
    @Override
    public String verify(String signedXml) {
        Document document = xmlFileUtility.createDocumentFromString(signedXml);
      boolean validDocuemnt=false;

        try {
            CerteficateInformation certeficateInformation= xmlFileUtility.parseCerteficateFromDocument(document);
            validDocuemnt= signUtil.verify(document,certeficateAndKeysUtility.loadCerteficateFromLdpa(certeficateInformation));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return String.valueOf(validDocuemnt);
    }

    @Override
    public void clearCache() {
        certeficateAndKeysUtility.clearCache();
    }
}
