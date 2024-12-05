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
 * A NamespaceContext implementation for digital signatures
 */

package com.cbo.coopipsapp.context;

import javax.xml.namespace.NamespaceContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class DSNamespaceContext implements NamespaceContext {

    private Map<String, String> namespaceMap = new HashMap<>();

    /**
     * Default constructor
     */
    public DSNamespaceContext() {
        namespaceMap.put("ds", "http://www.w3.org/2000/09/xmldsig#");
        namespaceMap.put("dsig", "http://www.w3.org/2000/09/xmldsig#");
        namespaceMap.put("xades", "http://uri.etsi.org/01903/v1.3.2#");
    }

    /**
     * Constructor with namespaces argument
     *
     * @param namespaces the namespaces
     */
    public DSNamespaceContext(Map<String, String> namespaces) {
        this();
        namespaceMap.putAll(namespaces);
    }

    /**
     * Getter for NamespaceURI
     *
     * @param arg0 get namespaceUri by this key
     * @return the namespaceUri
     */
    public String getNamespaceURI(String arg0) {
        return namespaceMap.get(arg0);
    }

    /**
     * Add a Prefix
     *
     * @param prefix    the key
     * @param namespace the value
     */
    public void putPrefix(String prefix, String namespace) {
        namespaceMap.put(prefix, namespace);
    }

    /**
     * Getter for Prefix
     *
     * @param arg0 get prefix by this key
     * @return the namespace
     */
    public String getPrefix(String arg0) {
        for (Map.Entry<String, String> entry : namespaceMap.entrySet()) {
            if (entry.getValue().equals(arg0)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Getter for prefixes
     *
     * @param arg0 get prefixes by this key
     * @return the prefix iterator
     */
    public Iterator<String> getPrefixes(String arg0) {
        return namespaceMap.keySet().iterator();
    }
}
