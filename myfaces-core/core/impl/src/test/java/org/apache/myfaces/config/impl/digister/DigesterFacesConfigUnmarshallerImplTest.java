/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digister;

import org.apache.myfaces.config.impl.digester.DigesterFacesConfigUnmarshallerImpl;
import org.apache.myfaces.config.impl.digester.elements.Application;
import org.apache.myfaces.config.impl.digester.elements.FacesConfig;
import org.apache.myfaces.config.impl.digester.elements.LocaleConfig;

import java.util.List;

import junit.framework.TestCase;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511030 $ $Date: 2007-02-23 19:34:38 +0200 (Fri, 23 Feb 2007) $
 */
public class DigesterFacesConfigUnmarshallerImplTest extends TestCase
{
    private DigesterFacesConfigUnmarshallerImpl _impl;

    protected void setUp() throws Exception
    {
        _impl = new DigesterFacesConfigUnmarshallerImpl(null);
    }

    public void testEmptyConfig() throws Exception
    {
        FacesConfig cfg = _impl.getFacesConfig(getClass().getResourceAsStream(
                "empty-config.xml"), "empty-config.xml");
        assertNotNull(cfg);
        assertTrue(cfg.getApplications().isEmpty());
        assertTrue(cfg.getComponents().isEmpty());
        assertTrue(cfg.getConverters().isEmpty());
        assertTrue(cfg.getFactories().isEmpty());
        assertTrue(cfg.getLifecyclePhaseListener().isEmpty());
        assertTrue(cfg.getManagedBeans().isEmpty());
        assertTrue(cfg.getNavigationRules().isEmpty());
        assertTrue(cfg.getRenderKits().isEmpty());
        assertTrue(cfg.getValidators().isEmpty());
    }

    public void testApplicationConfig() throws Exception
    {
        FacesConfig cfg = _impl.getFacesConfig(getClass().getResourceAsStream(
                "application-config.xml"), "application-config.xml");
        assertNotNull(cfg);
        assertEquals(1, cfg.getApplications().size());
        Application app = cfg.getApplications().get(0);
        assertEquals(2, app.getActionListener().size());
        assertEquals("action-listener1", app.getActionListener().get(0));
        assertEquals("action-listener2", app.getActionListener().get(1));
        assertEquals(1, app.getDefaultRenderkitId().size());
        assertEquals("default-render-kit-id", app.getDefaultRenderkitId()
                .get(0));
        assertLocaleConfig(app.getLocaleConfig());
        assertEquals(1, app.getMessageBundle().size());
        assertEquals("message-bundle", app.getMessageBundle().get(0));
        assertEquals(1, app.getNavigationHandler().size());
        assertEquals("navigation-handler", app.getNavigationHandler().get(0));
        assertEquals(1, app.getPropertyResolver().size());
        assertEquals("property-resolver", app.getPropertyResolver().get(0));

        assertEquals(1, app.getStateManager().size());
        assertEquals("state-manager", app.getStateManager().get(0));

        assertEquals(1, app.getVariableResolver().size());
        assertEquals("variable-resolver", app.getVariableResolver().get(0));

        assertEquals(1, app.getViewHandler().size());
        assertEquals("view-handler", app.getViewHandler().get(0));

        assertEquals(1, app.getElResolver().size());
        assertEquals("el-resolver", app.getElResolver().get(0));

        assertEquals(1, app.getResourceBundle().size());
        assertEquals("base-name", app.getResourceBundle().get(0).getBaseName());
        assertEquals("var", app.getResourceBundle().get(0).getVar());
    }

    /**
     * @param localeConfig
     */
    private void assertLocaleConfig(List<LocaleConfig> localeConfig)
    {
        assertEquals(1, localeConfig.size());
        LocaleConfig cfg = localeConfig.get(0);
        assertEquals("aa", cfg.getDefaultLocale());
        assertEquals(2, cfg.getSupportedLocales().size());
        assertEquals("aa", cfg.getSupportedLocales().get(0));
        assertEquals("bb", cfg.getSupportedLocales().get(1));
    }
}
