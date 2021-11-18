/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;

import junit.framework.Test;
import org.apache.myfaces.shared.util.MessageUtils;
import org.apache.shale.test.base.AbstractJsfTestCase;

/**
 * TestCase for MessageUtils
 *
 * @author Stephan Strittmatter
 */
public class MessageUtilsTest extends AbstractJsfTestCase
{

    private static final String DEFAULT_BUNDLE = "javax.faces.Messages";

    public MessageUtilsTest(String name)
    {
        super(name);
    }

    public static Test suite() {
        return null; // keep this method or maven won't run it
    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(Severity, String, Object)'
     */
    public void testGetMessageSeverityStringObject()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Conversion Error", msg.getSummary());

        facesContext.getViewRoot().setLocale(Locale.GERMAN);

        msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION",
                "blubb");
        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(Severity, String, Object[])'
     */
    public void testGetMessageSeverityStringObjectArray()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Conversion Error", msg.getSummary());

        facesContext.getViewRoot().setLocale(Locale.GERMAN);

        msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(Severity, String, Object[], FacesContext)'
     */
    public void testGetMessageSeverityStringObjectArrayFacesContext()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION", null, facesContext);
        assertEquals("Conversion Error", msg.getSummary());

        facesContext.getViewRoot().setLocale(Locale.GERMAN);

        msg = MessageUtils.getMessage(FacesMessage.SEVERITY_ERROR,
                "javax.faces.component.UIInput.CONVERSION", null,
                facesContext);
        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(Locale, String, Object[])'
     */
    public void testGetMessageLocaleStringObjectArray()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = org.apache.myfaces.shared.util.MessageUtils.getMessage(Locale.ENGLISH,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Conversion Error", msg.getSummary());

        msg = MessageUtils.getMessage(Locale.GERMAN,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Konvertierungsfehler", msg.getSummary());

    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(FacesContext, String)'
     */
    public void testGetMessageFacesContextString()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(facesContext,
                "javax.faces.component.UIInput.CONVERSION");
        assertEquals("Conversion Error", msg.getSummary());

        facesContext.getViewRoot().setLocale(Locale.GERMAN);

        msg = MessageUtils.getMessage(facesContext,
                "javax.faces.component.UIInput.CONVERSION");
        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * Test method for 'org.apache.myfaces.shared.util.MessageUtils.getMessage(FacesContext, String, Object[])'
     */
    public void testGetMessageFacesContextStringObjectArray()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(facesContext,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Conversion Error", msg.getSummary());

        facesContext.getViewRoot().setLocale(Locale.GERMAN);

        msg = MessageUtils.getMessage(facesContext,
                "javax.faces.component.UIInput.CONVERSION", null);
        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * testGetMessageWithBundle
     */
    public void testGetMessageWithBundle()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        ResourceBundle bundle = ResourceBundle.getBundle(DEFAULT_BUNDLE,
                Locale.ENGLISH);
        FacesMessage msg = MessageUtils.getMessage(bundle,
                "javax.faces.component.UIInput.CONVERSION", null);

        assertEquals("Conversion Error", msg.getSummary());
    }

    /**
     * testGetMessageWithBundleName
     */
    public void testGetMessageWithBundleName()
    {
        facesContext.getViewRoot().setLocale(Locale.ENGLISH);

        FacesMessage msg = MessageUtils.getMessage(DEFAULT_BUNDLE,
                "javax.faces.component.UIInput.CONVERSION", null);

        assertEquals("Conversion Error", msg.getSummary());
    }

    /**
     * testGetMessageWithBundleNameLocale
     */
    public void testGetMessageWithBundleNameLocale()
    {
        FacesMessage msg = MessageUtils.getMessage(DEFAULT_BUNDLE,
                Locale.GERMAN, "javax.faces.component.UIInput.CONVERSION", null);

        assertEquals("Konvertierungsfehler", msg.getSummary());
    }

    /**
     * testSubstituteParamsWithDELocale(
     */
    public void testSubstituteParamsWithDELocale() {
        String paramString = MessageUtils.substituteParams(Locale.GERMANY, "currency {0,number,currency}", new Object[]{100});

        assertEquals("currency 100,00 \u20ac",paramString);
    }

    /**
     * testSubstituteParamsWithGBLocale(
     */
    public void testSubstituteParamsWithGBLocale() {
        String paramString = MessageUtils.substituteParams(Locale.UK, "currency {0,number,currency}", new Object[]{100});

        System.out.println(paramString);
        assertEquals("currency \u00a3100.00",paramString);
    }

}
