/**
 * Copyright 2005-2010 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.ext.sip.internal;

import java.io.IOException;
import java.util.Collection;

import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.engine.http.header.HeaderReader;
import org.restlet.ext.sip.SipRecipientInfo;

/**
 * Via header reader.
 * 
 * @author Thierry Boileau
 */
public class SipRecipientInfoReader extends HeaderReader<SipRecipientInfo> {

    /**
     * Adds values to the given collection.
     * 
     * @param header
     *            The header to read.
     * @param collection
     *            The collection to update.
     */
    public static void addValues(Parameter header,
            Collection<SipRecipientInfo> collection) {
        new SipRecipientInfoReader(header.getValue()).addValues(collection);
    }

    public static void main(String[] args) throws Exception {
        // 

        String str = "SIP/2.0/UDP 192.0.2.1:5060 ;received=192.0.2.207;branch=z9hG4bK77asjd";
        SipRecipientInfoReader r = new SipRecipientInfoReader(str);
        SipRecipientInfo s = r.readValue();
        System.out.println(s.getProtocol());
        System.out.println(s.getTransport());

        System.out.println(s.getName());

        for (Parameter parameter : s.getParameters()) {
            System.out
                    .println(parameter.getName() + "/" + parameter.getValue());
        }
        System.out.println(s.getComment());

        str = "SIP/2.0/UDP 192.0.2.1:5060 ;received=192.0.2.207;branch=z9hG4bK77asjd (this is a comment)";
        r = new SipRecipientInfoReader(str);
        s = r.readValue();
        System.out.println(s.getProtocol());
        System.out.println(s.getTransport());

        System.out.println(s.getName());

        for (Parameter parameter : s.getParameters()) {
            System.out
                    .println(parameter.getName() + "/" + parameter.getValue());
        }
        System.out.println(s.getComment());

        str = "SIP/2.0/TCP 127.0.0.1:5061;branch=z9hG4bK-6503-1-0";
        r = new SipRecipientInfoReader(str);
        s = r.readValue();
        System.out.println(s.getProtocol());
        System.out.println(s.getTransport());

        System.out.println(s.getName());

        for (Parameter parameter : s.getParameters()) {
            System.out
                    .println(parameter.getName() + "/" + parameter.getValue());
        }
        System.out.println(s.getComment());

    }

    /**
     * Constructor.
     * 
     * @param header
     *            The header to read.
     */
    public SipRecipientInfoReader(String header) {
        super(header);
    }

    @Override
    public SipRecipientInfo readValue() throws IOException {
        SipRecipientInfo result = new SipRecipientInfo();

        skipSpaces();

        String protocolToken = readToken();

        if (peek() == '/') {
            read();
            result.setProtocol(new Protocol(protocolToken, protocolToken, null,
                    -1, readToken()));
            if (peek() == '/') {
                read();
                result.setTransport(readToken());
            }
        } else {
            result.setProtocol(new Protocol("HTTP", "HTTP", null, -1,
                    protocolToken));
        }

        // Move to the next text
        if (skipSpaces()) {
            StringBuilder sb = new StringBuilder(readToken());
            if (read() == ':') {
                sb.append(":");
                sb.append(readToken());
            }
            result.setName(sb.toString());

            // Read address parameters.
            if (skipParameterSeparator()) {
                Parameter param = readParameter();

                while (param != null) {
                    result.getParameters().add(param);

                    if (skipParameterSeparator()) {
                        param = readParameter();
                    } else {
                        param = null;
                    }
                }
            }

            // Move to the next text
            skipSpaces();
            if (peek() == '(') {
                result.setComment(readComment());
            }
        }

        return result;
    }
}