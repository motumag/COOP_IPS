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

package com.cbo.coopipsapp.info;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SignatureInfo {

    private String signatureMethodAlgorithm;//"http://www.w3.org/2000/09/xmldsig#rsa-sha1"
    private String signatureCanonicalizationMethodAlgorithm;//"http://www.w3.org/2001/10/xml-exc-c14n#"
    private String signatureExclusionTransformer;//"http://www.w3.org/2001/10/xml-exc-c14n#"
    private ReferenceSignInfo appHdrReferenceSignInfo;
    private ReferenceSignInfo documentReferenceSignInfo;
    private ReferenceSignInfo keyReferenceSignInfo;

}
