/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import net.sf.maventaglib.checker.Tag;
import net.sf.maventaglib.checker.TagAttribute;
import net.sf.maventaglib.checker.Tld;
import net.sf.maventaglib.checker.TldParser;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Insures the following ...
 * <ul>
 * <li> Tag handlers have setters for all tag attributes</li>
 * <li> All tag handlers are in the classpath</li>
 * <li> Tag handlers do not appear in the TLD more than once</li>
 * <li> Tag handler attributes do not occur mare than once</li>
 * </ul>
 * 
 * @author Dennis Byrne
 * @see http://maven-taglib.sourceforge.net/ for dependency download
 */

public abstract class AbstractTagLibTestCase extends TestCase {
    private static Log log = LogFactory.getLog(AbstractTagLibTestCase.class);

    protected Tld[] tlds;

    protected String[] tldPaths;
    
    private ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Unmarshall TLDs to an object model. TLDs are supplied by a subclass.
     */

    protected void setUp() throws Exception {

        if (tldPaths == null)
            throw new NullPointerException(
                    "tldPaths cannot point to null before setUp() is called");

        int length = tldPaths.length;

        if (length == 0)
            throw new IllegalStateException(
                    "tldPaths should have at least one resource path");

        tlds = new Tld[length];

        for (int t = 0; t < length; t++) {
            String name = tldPaths[t];

            InputStream stream = classLoader.getResourceAsStream(name);

            if (stream == null)
                throw new NullPointerException(
                        "couldn't get an input stream for " + name);

            tlds[t] = TldTestUtils.getTld(name, stream);
            stream.close();
        }
    }

    public void testUniqueTagTestCase() throws Exception {

        for (int lib = 0; lib < tlds.length; lib++) {
            Tld tld = tlds[lib];
            List tagNames = new ArrayList();
            Tag[] tags = tld.getTags();

            for (int t = 0; t < tags.length; t++) {
                Tag tag = tags[t];

                if (tag == null)
                    throw new NullPointerException("tag");

                String name = tag.getName();
                String msg = name + " found more than once in " + tldPaths[lib];
                assertFalse(msg, tagNames.contains(name));
                tagNames.add(name);
            } // end t
        } // end lib
    }

    public void testUniqueTagAttributes() throws Exception {

        for (int lib = 0; lib < tlds.length; lib++) {
            Tld tld = tlds[lib];
            Tag[] tags = tld.getTags();

            for (int t = 0; t < tags.length; t++) {

                Tag tag = tags[t];
                assertUniqueTagAttributes(tag, tldPaths[lib]);

            } // end t
        } // end lib
    }

    private void assertUniqueTagAttributes(final Tag tag, final String path) {

        List attributeNames = new ArrayList();
        TagAttribute[] atts = tag.getAttributes();
        String tagName = tag.getName();

        for (int a = 0; a < atts.length;) {

            TagAttribute att = atts[a++];
            String name = att.getAttributeName();
            String msg = " @" + name + " of " + path + ":" + tagName
                    + " is duplicated.";
            assertFalse(msg, attributeNames.contains(name));
            attributeNames.add(name);

        } // end a

    }

    /**
     * Make sure the class exists. Make sure there is a setter for each
     * attribute.
     */

    public void testSetters() throws Exception {

        for (int t = 0; t < tlds.length; t++) {
            Tld tld = tlds[t];
            Tag[] tags = tld.getTags();

            for (int s = 0; s < tags.length; s++) {
                Tag tag = tags[s];
                String filename = tld.getFilename();

                Object object = TldTestUtils.makeTagClassInstance(tag,
                        filename, classLoader);
                log.debug("filename = " + filename + " ; tag = " + tag.getName());
                assertSetters(tag, filename, object);

            } // end for tag
        } // end for lib

    }

    private void assertSetters(final Tag tag, final String path,
            final Object object) {

        TagAttribute[] attributes = tag.getAttributes();
        String tagName = tag.getName();

        for (int a = 0; a < attributes.length; a++) {
            TagAttribute attribute = attributes[a];
            String name = attribute.getAttributeName();

            if (name == null || "".equals(name.trim()))
                throw new IllegalStateException(path + ":" + tagName
                        + " has a nameless attribute ");

            String msg = path + ":" + tagName + "@" + name
                    + " exists, but " + object.getClass().getName()
                    + " has no setter.";
            
            assertTrue(msg, PropertyUtils.isWriteable(object, name));
        } // end for attributes

    }
    
    private static class DelegateEntityResolver implements EntityResolver
    {
        private EntityResolver _delegate;

        public DelegateEntityResolver(EntityResolver delegate)
        {
            this._delegate = delegate;
        }

        public InputSource resolveEntity(String publicId, String systemId)
                throws SAXException, IOException
        {
            if (publicId.equals("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN"))
            {
                return new InputSource(Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(
                                "META-INF/dtd/web-jsptaglibrary_1_2.dtd"));
            }
            else
            {
                return _delegate.resolveEntity(publicId, systemId);
            }
        }
    }    

    private static class TldTestUtils {
        private static Log log = LogFactory.getLog(TldTestUtils.class);

        private static DocumentBuilderFactory dbf = DocumentBuilderFactory
                .newInstance();
        static
        {
            dbf.setNamespaceAware(false);
            dbf.setValidating(false);
        }

        public static Tld getTld(String name, InputStream stream)
                throws Exception {
            if (stream == null)
                log.error(" input stream is null ");

            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver(new DelegateEntityResolver(null));
            Document doc = db.parse(stream);

            return TldParser.parse(doc, name);
        }

        public static Object makeTagClassInstance(Tag tag, String filename,
                ClassLoader classLoader) throws Exception {

            String clazzName = tag.getTagClass();

            if (clazzName == null || "".equals(clazzName.trim()))
                throw new NullPointerException(tag.getName()
                        + " is missing a tag class.");

            try {

                Class clazz = classLoader.loadClass(clazzName);
                return clazz.newInstance();

            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException(clazzName);
            } catch (IllegalAccessException ie) {
                throw new IllegalAccessException(clazzName);
            } catch (InstantiationException iste) {
                throw new InstantiationException(clazzName);
            }

        }

    }

}