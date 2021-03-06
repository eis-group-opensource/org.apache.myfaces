/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.webapp.webxml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.util.ClassUtils;
import org.apache.myfaces.shared.util.xml.MyFacesErrorHandler;
import org.apache.myfaces.shared.util.xml.XmlUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 777341 $ $Date: 2009-05-22 04:06:09 +0300 (Pn, 22 Geg 2009) $
 */
public class WebXmlParser
{
    private static final Log log = LogFactory.getLog(WebXmlParser.class);

    /*
    private static final String JAXP_SCHEMA_LANGUAGE =
        "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String W3C_XML_SCHEMA =
        "http://www.w3.org/2001/XMLSchema";
        */

    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";

    private static final String WEB_APP_2_2_J2EE_SYSTEM_ID = "http://java.sun.com/j2ee/dtds/web-app_2_2.dtd";
    private static final String WEB_APP_2_2_SYSTEM_ID = "http://java.sun.com/dtd/web-app_2_2.dtd";
    private static final String WEB_APP_2_2_RESOURCE  = "javax/servlet/resources/web-app_2_2.dtd";

    private static final String WEB_APP_2_3_SYSTEM_ID = "http://java.sun.com/dtd/web-app_2_3.dtd";
    private static final String WEB_APP_2_3_RESOURCE  = "javax/servlet/resources/web-app_2_3.dtd";

    private ExternalContext _context;
    private org.apache.myfaces.shared.webapp.webxml.WebXml _webXml;

    public WebXmlParser(ExternalContext context)
    {
        _context = context;
    }

    public WebXml parse()
    {
        _webXml = new WebXml();

        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setIgnoringComments(true);
            dbf.setNamespaceAware(true);
            dbf.setValidating(false);
//            dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new _EntityResolver());
            db.setErrorHandler(new MyFacesErrorHandler(log));

            InputSource is = createContextInputSource(null, WEB_XML_PATH);

            if(is==null)
            {
                URL url = _context.getResource(WEB_XML_PATH);
                log.debug("No web-xml found at : "+(url==null?" null ":url.toString()));
                return _webXml;
            }

            Document document = db.parse(is);

            Element webAppElem = document.getDocumentElement();
            if (webAppElem == null ||
                !webAppElem.getNodeName().equals("web-app"))
            {
                throw new FacesException("No valid web-app root element found!");
            }

            readWebApp(webAppElem);

            return _webXml;
        }
        catch (Exception e)
        {
            log.fatal("Unable to parse web.xml", e);
            throw new FacesException(e);
        }
    }

    public static long getWebXmlLastModified(ExternalContext context)
    {
        try {
            URL url = context.getResource(WEB_XML_PATH);
            if (url != null)
                return url.openConnection().getLastModified();
        } catch (IOException e) {
            log.error("Could not find web.xml in path " + WEB_XML_PATH);
        }
        return 0L;
    }


    private InputSource createContextInputSource(String publicId, String systemId)
    {
        InputStream inStream = _context.getResourceAsStream(systemId);
        if (inStream == null)
        {
            // there is no such entity
            return null;
        }
        InputSource is = new InputSource(inStream);
        is.setPublicId(publicId);
        is.setSystemId(systemId);
        //the next line was removed - encoding should be determined automatically out of the inputStream
        //DEFAULT_ENCODING was ISO-8859-1
        //is.setEncoding(DEFAULT_ENCODING);
        return is;
    }

    private InputSource createClassloaderInputSource(String publicId, String systemId)
    {
        InputStream inStream = ClassUtils.getContextClassLoader().getResourceAsStream(systemId);
        if (inStream == null)
        {
            // there is no such entity
            return null;
        }
        InputSource is = new InputSource(inStream);
        is.setPublicId(publicId);
        is.setSystemId(systemId);
        //the next line was removed - encoding should be determined automatically out of the inputStream
        //encoding should be determined automatically out of the inputStream
        //DEFAULT_ENCODING was ISO-8859-1
        //is.setEncoding(DEFAULT_ENCODING);
        return is;
    }

    private class _EntityResolver implements EntityResolver
    {
        public InputSource resolveEntity(String publicId, String systemId) throws IOException
        {
            if (systemId == null)
            {
                throw new UnsupportedOperationException("systemId must not be null");
            }

            if (systemId.equals(WebXmlParser.WEB_APP_2_2_SYSTEM_ID) ||
                systemId.equals(WebXmlParser.WEB_APP_2_2_J2EE_SYSTEM_ID))
            {
                //Load DTD from servlet.jar
                return createClassloaderInputSource(publicId, WebXmlParser.WEB_APP_2_2_RESOURCE);
            }
            else if (systemId.equals(WebXmlParser.WEB_APP_2_3_SYSTEM_ID))
            {
                //Load DTD from servlet.jar
                return createClassloaderInputSource(publicId, WebXmlParser.WEB_APP_2_3_RESOURCE);
            }
            else
            {
                //Load additional entities from web context
                return createContextInputSource(publicId, systemId);
            }
        }

    }


    private void readWebApp(Element webAppElem)
    {
        NodeList nodeList = webAppElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet"))
                {
                    readServlet((Element)n);
                }
                if (n.getNodeName().equals("servlet-mapping"))
                {
                    readServletMapping((Element)n);
                }
                if (n.getNodeName().equals("filter"))
                {
                    readFilter((Element)n);
                }
                if (n.getNodeName().equals("filter-mapping"))
                {
                    readFilterMapping((Element)n);
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
    }

    private void readServlet(Element servletElem)
    {
        String servletName = null;
        String servletClass = null;
        NodeList nodeList = servletElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet-name"))
                {
                    servletName = XmlUtils.getElementText((Element)n);
                }
                else if (n.getNodeName().equals("servlet-class"))
                {
                    servletClass = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("description") || n.getNodeName().equals("load-on-startup") || n.getNodeName().equals("init-param"))
                {
                    //ignore
                }
                else
                {
                    if (log.isDebugEnabled()) log.debug("Ignored element '" + n.getNodeName() + "' as child of '" + servletElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        _webXml.addServlet(servletName, servletClass);
    }


    private void readServletMapping(Element servletMappingElem)
    {
        String servletName = null;
        String urlPattern = null;
        NodeList nodeList = servletMappingElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("servlet-name"))
                {
                    servletName = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n);
                }
                else if (n.getNodeName().equals("url-pattern"))
                {
                    urlPattern = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else
                {
                    if (log.isDebugEnabled()) log.debug("Ignored element '" + n.getNodeName() + "' as child of '" + servletMappingElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        urlPattern = urlPattern.trim();
        _webXml.addServletMapping(servletName, urlPattern);
    }

    private void readFilter(Element filterElem)
    {
        String filterName = null;
        String filterClass = null;
        NodeList nodeList = filterElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("filter-name"))
                {
                    filterName = XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("filter-class"))
                {
                    filterClass = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("description") || n.getNodeName().equals("init-param"))
                {
                    //ignore
                }
                else
                {
                    if (log.isDebugEnabled()) log.debug("Ignored element '" + n.getNodeName() + "' as child of '" + filterElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        _webXml.addFilter(filterName, filterClass);
    }


    private void readFilterMapping(Element filterMappingElem)
    {
        String filterName = null;
        String urlPattern = null;
        NodeList nodeList = filterMappingElem.getChildNodes();
        for (int i = 0, len = nodeList.getLength(); i < len; i++)
        {
            Node n = nodeList.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE)
            {
                if (n.getNodeName().equals("filter-name"))
                {
                    filterName = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("url-pattern"))
                {
                    urlPattern = org.apache.myfaces.shared.util.xml.XmlUtils.getElementText((Element)n).trim();
                }
                else if (n.getNodeName().equals("servlet-name"))
                {
                    // we are not interested in servlet-name based mapping - for now
                }
                else
                {
                    if (log.isDebugEnabled()) log.debug("Ignored element '" + n.getNodeName() + "' as child of '" + filterMappingElem.getNodeName() + "'.");
                }
            }
            else
            {
                if (log.isDebugEnabled()) log.debug("Ignored node '" + n.getNodeName() + "' of type " + n.getNodeType());
            }
        }
        _webXml.addFilterMapping(filterName, urlPattern);
    }
}
