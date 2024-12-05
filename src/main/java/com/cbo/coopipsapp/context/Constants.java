/*
 * Copyright (c) 2020 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 */
package com.cbo.coopipsapp.context;

import javax.xml.namespace.QName;

public class Constants {

    private Constants(){}
    public static final String SECUREMENT_ACTION_TRANSFORMER_EXCLUSION = "AppHdr";
    public static final String SECUREMENT_ACTION_EXCLUSION = "Document";
    public static final String SECUREMENT_SINATURE_INFO_EXCLUSION = "SignedProperties";
    public static final String SECUREMENT_KEY_INFO_EXCLUSION = "KeyInfo";
    public static final QName BAH_NAME = new QName("urn:iso:std:iso:20022:tech:xsd:head.001.001.03", SECUREMENT_ACTION_TRANSFORMER_EXCLUSION);
    public static final QName DOCUMENT_NAME = new QName("urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10", SECUREMENT_ACTION_EXCLUSION);
    public static final QName WS_SECURITY_NAME = new QName("urn:iso:std:iso:20022:tech:xsd:head.001.001.03", "Sgntr","document");
    public static final QName  SIGNED_PROPERTIES_NAME = new QName("http://uri.etsi.org/01903/v1.3.2#", SECUREMENT_SINATURE_INFO_EXCLUSION);
    public static final QName  XADES_QUALIFYING_PROPERTIES_NAME = new QName("http://uri.etsi.org/01903/v1.3.2#", "QualifyingProperties","xades");
    public static final QName  XADES_SIGNED_PROPERTIES_NAME = new QName("http://uri.etsi.org/01903/v1.3.2#", "SignedProperties","xades");
    public static final QName  XADES_SIGNED_SIG_PROPERTIES_NAME = new QName("http://uri.etsi.org/01903/v1.3.2#", "SignedSignatureProperties","xades");
   public static final QName  XADES_SIGNED_SIGN_TIME_NAME = new QName("http://uri.etsi.org/01903/v1.3.2#", "SigningTime","xades");
    public static final String SECUREMENT_ACTION_SEPARATOR = " | ";
    public static final String DS_NS = "http://www.w3.org/2000/09/xmldsig#";
    public static final String SIGNATURE_LOCAL_NAME = "Signature";
}
