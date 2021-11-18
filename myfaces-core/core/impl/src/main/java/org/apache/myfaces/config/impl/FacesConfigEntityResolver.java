/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl;

import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.ExternalContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarEntry;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 775310 $ $Date: 2009-05-15 23:20:30 +0300 (Fri, 15 May 2009) $
 */
public class FacesConfigEntityResolver
    implements EntityResolver
{
    private static final Log log = LogFactory.getLog(FacesConfigEntityResolver.class);

    private static final String FACES_CONFIG_1_0_DTD_SYSTEM_ID = "http://java.sun.com/dtd/web-facesconfig_1_0.dtd";
    private static final String FACES_CONFIG_1_0_DTD_RESOURCE
            = "org.apache.myfaces.resource".replace('.', '/') + "/web-facesconfig_1_0.dtd";
    private static final String FACES_CONFIG_1_1_DTD_SYSTEM_ID = "http://java.sun.com/dtd/web-facesconfig_1_1.dtd";
    private static final String FACES_CONFIG_1_1_DTD_RESOURCE
            = "org.apache.myfaces.resource".replace('.', '/') + "/web-facesconfig_1_1.dtd";

    private ExternalContext _externalContext = null;

    public FacesConfigEntityResolver(ExternalContext context)
    {
        _externalContext = context;
    }

    public FacesConfigEntityResolver()
    {
    }

    public InputSource resolveEntity(String publicId,
                                     String systemId)
        throws IOException
    {
        InputStream stream;
        if (systemId.equals(FACES_CONFIG_1_0_DTD_SYSTEM_ID))
        {
            stream = ClassUtils.getResourceAsStream(FACES_CONFIG_1_0_DTD_RESOURCE);
        }
        else if (systemId.equals(FACES_CONFIG_1_1_DTD_SYSTEM_ID))
        {
            stream = ClassUtils.getResourceAsStream(FACES_CONFIG_1_1_DTD_RESOURCE);
        }

        else if (systemId.startsWith("jar:"))
        {
            URL url = new URL(systemId);
            JarURLConnection conn = (JarURLConnection) url.openConnection();
            conn.setUseCaches(false);
            JarEntry jarEntry = conn.getJarEntry();
            if (jarEntry == null)
            {
                log.fatal("JAR entry '" + systemId + "' not found.");
            }
            //_jarFile.getInputStream(jarEntry);
            stream = conn.getJarFile().getInputStream(jarEntry);
        }
        else
        {
            if (_externalContext == null)
            {
                stream = ClassUtils.getResourceAsStream(systemId);
            }
            else
            {
                if (systemId.startsWith("file:")) {
                    systemId = systemId.substring(7); // remove file://
                }
                stream = _externalContext.getResourceAsStream(systemId);
            }
        }

        if (stream == null) {
            return null;
        }
        InputSource is = new InputSource(stream);
        is.setPublicId(publicId);
        is.setSystemId(systemId);
        is.setEncoding("ISO-8859-1");
        return is;
    }

}
