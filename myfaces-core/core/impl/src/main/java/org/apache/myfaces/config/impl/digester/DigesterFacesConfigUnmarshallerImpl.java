/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.context.ExternalContext;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.config.FacesConfigUnmarshaller;
import org.apache.myfaces.config.impl.FacesConfigEntityResolver;
import org.apache.myfaces.config.impl.digester.elements.Application;
import org.apache.myfaces.config.impl.digester.elements.Attribute;
import org.apache.myfaces.config.impl.digester.elements.Converter;
import org.apache.myfaces.config.impl.digester.elements.FacesConfig;
import org.apache.myfaces.config.impl.digester.elements.Factory;
import org.apache.myfaces.config.impl.digester.elements.ListEntries;
import org.apache.myfaces.config.impl.digester.elements.LocaleConfig;
import org.apache.myfaces.config.impl.digester.elements.ManagedBean;
import org.apache.myfaces.config.impl.digester.elements.ManagedProperty;
import org.apache.myfaces.config.impl.digester.elements.MapEntries;
import org.apache.myfaces.config.impl.digester.elements.NavigationCase;
import org.apache.myfaces.config.impl.digester.elements.NavigationRule;
import org.apache.myfaces.config.impl.digester.elements.Property;
import org.apache.myfaces.config.impl.digester.elements.RenderKit;
import org.apache.myfaces.config.impl.digester.elements.Renderer;
import org.apache.myfaces.config.impl.digester.elements.ResourceBundle;
import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 */
public class DigesterFacesConfigUnmarshallerImpl implements FacesConfigUnmarshaller<FacesConfig>
{

    private Digester digester;


    public DigesterFacesConfigUnmarshallerImpl(ExternalContext externalContext)
    {
        digester = new Digester();
        // TODO: validation set to false during implementation of 1.2
        digester.setValidating(false);
        digester.setNamespaceAware(true);
        digester.setEntityResolver(new FacesConfigEntityResolver(externalContext));
        //digester.setUseContextClassLoader(true);
        digester.setClassLoader(ClassUtils.getContextClassLoader());

        digester.addObjectCreate("faces-config", FacesConfig.class);
        digester.addObjectCreate("faces-config/application", Application.class);
        digester.addSetNext("faces-config/application", "addApplication");
        digester.addCallMethod("faces-config/application/action-listener", "addActionListener", 0);
        digester.addCallMethod("faces-config/application/default-render-kit-id", "addDefaultRenderkitId", 0);
        digester.addCallMethod("faces-config/application/message-bundle", "addMessageBundle", 0);
        digester.addCallMethod("faces-config/application/navigation-handler", "addNavigationHandler", 0);
        digester.addCallMethod("faces-config/application/view-handler", "addViewHandler", 0);
        digester.addCallMethod("faces-config/application/state-manager", "addStateManager", 0);
        digester.addCallMethod("faces-config/application/property-resolver", "addPropertyResolver", 0);
        digester.addCallMethod("faces-config/application/variable-resolver", "addVariableResolver", 0);
        digester.addObjectCreate("faces-config/application/locale-config", LocaleConfig.class);
        digester.addSetNext("faces-config/application/locale-config", "addLocaleConfig");
        digester.addCallMethod("faces-config/application/locale-config/default-locale", "setDefaultLocale", 0);
        digester.addCallMethod("faces-config/application/locale-config/supported-locale", "addSupportedLocale", 0);
        
        // 1.2 specific start
        digester.addCallMethod("faces-config/application/el-resolver", "addElResolver", 0);
        digester.addObjectCreate("faces-config/application/resource-bundle", ResourceBundle.class);
        digester.addSetNext("faces-config/application/resource-bundle", "addResourceBundle");
        digester.addCallMethod("faces-config/application/resource-bundle/base-name", "setBaseName", 0);
        digester.addCallMethod("faces-config/application/resource-bundle/var", "setVar", 0);
        digester.addCallMethod("faces-config/application/resource-bundle/display-name", "setDisplayName", 0);
        // 1.2 specific end
        
        digester.addObjectCreate("faces-config/factory", Factory.class);
        digester.addSetNext("faces-config/factory", "addFactory");
        digester.addCallMethod("faces-config/factory/application-factory", "addApplicationFactory", 0);
        digester.addCallMethod("faces-config/factory/faces-context-factory", "addFacesContextFactory", 0);
        digester.addCallMethod("faces-config/factory/lifecycle-factory", "addLifecycleFactory", 0);
        digester.addCallMethod("faces-config/factory/render-kit-factory", "addRenderkitFactory", 0);

        digester.addCallMethod("faces-config/component", "addComponent", 2);
        digester.addCallParam("faces-config/component/component-type", 0);
        digester.addCallParam("faces-config/component/component-class", 1);

        digester.addObjectCreate("faces-config/converter", Converter.class);
        digester.addSetNext("faces-config/converter", "addConverter");
        digester.addCallMethod("faces-config/converter/converter-id", "setConverterId", 0);
        digester.addCallMethod("faces-config/converter/converter-for-class", "setForClass", 0);
        digester.addCallMethod("faces-config/converter/converter-class", "setConverterClass", 0);
        digester.addObjectCreate("faces-config/converter/attribute",Attribute.class);
        digester.addSetNext("faces-config/converter/attribute","addAttribute");
        digester.addCallMethod("faces-config/converter/attribute/description", "addDescription", 0);
        digester.addCallMethod("faces-config/converter/attribute/display-name", "addDisplayName", 0);
        digester.addCallMethod("faces-config/converter/attribute/icon", "addIcon", 0);
        digester.addCallMethod("faces-config/converter/attribute/attribute-name", "setAttributeName", 0);
        digester.addCallMethod("faces-config/converter/attribute/attribute-class", "setAttributeClass", 0);
        digester.addCallMethod("faces-config/converter/attribute/default-value", "setDefaultValue", 0);
        digester.addCallMethod("faces-config/converter/attribute/suggested-value", "setSuggestedValue", 0);
        digester.addCallMethod("faces-config/converter/attribute/attribute-extension", "addAttributeExtension", 0);
        digester.addObjectCreate("faces-config/converter/property",Property.class);
        digester.addSetNext("faces-config/converter/property","addProperty");
        digester.addCallMethod("faces-config/converter/property/description", "addDescription", 0);
        digester.addCallMethod("faces-config/converter/property/display-name", "addDisplayName", 0);
        digester.addCallMethod("faces-config/converter/property/icon", "addIcon", 0);
        digester.addCallMethod("faces-config/converter/property/property-name", "setPropertyName", 0);
        digester.addCallMethod("faces-config/converter/property/property-class", "setPropertyClass", 0);
        digester.addCallMethod("faces-config/converter/property/default-value", "setDefaultValue", 0);
        digester.addCallMethod("faces-config/converter/property/suggested-value", "setSuggestedValue", 0);
        digester.addCallMethod("faces-config/converter/property/property-extension", "addPropertyExtension", 0);

        digester.addObjectCreate("faces-config/managed-bean", ManagedBean.class);
        digester.addSetNext("faces-config/managed-bean", "addManagedBean");
        digester.addCallMethod("faces-config/managed-bean/description", "setDescription", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-bean-name", "setName", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-bean-class", "setBeanClass", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-bean-scope", "setScope", 0);
        digester.addObjectCreate("faces-config/managed-bean/managed-property", ManagedProperty.class);
        digester.addSetNext("faces-config/managed-bean/managed-property", "addProperty");
        digester.addCallMethod("faces-config/managed-bean/managed-property/property-name", "setPropertyName", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-property/property-class", "setPropertyClass", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-property/null-value", "setNullValue");
        digester.addCallMethod("faces-config/managed-bean/managed-property/value", "setValue", 0);
        digester.addObjectCreate("faces-config/managed-bean/managed-property/map-entries", MapEntries.class);
        digester.addSetNext("faces-config/managed-bean/managed-property/map-entries", "setMapEntries");
        digester.addCallMethod("faces-config/managed-bean/managed-property/map-entries/key-class", "setKeyClass", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-property/map-entries/value-class", "setValueClass", 0);
        digester.addObjectCreate("faces-config/managed-bean/managed-property/map-entries/map-entry", MapEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/managed-property/map-entries/map-entry", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/managed-property/map-entries/map-entry/key", "setKey", 0);
        digester.addCallMethod("faces-config/managed-bean/managed-property/map-entries/map-entry/null-value", "setNullValue");
        digester.addCallMethod("faces-config/managed-bean/managed-property/map-entries/map-entry/value", "setValue", 0);
        digester.addObjectCreate("faces-config/managed-bean/managed-property/list-entries", ListEntries.class);
        digester.addSetNext("faces-config/managed-bean/managed-property/list-entries", "setListEntries");
        digester.addCallMethod("faces-config/managed-bean/managed-property/list-entries/value-class", "setValueClass", 0);
        digester.addObjectCreate("faces-config/managed-bean/managed-property/list-entries/null-value", ListEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/managed-property/list-entries/null-value", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/managed-property/list-entries/null-value", "setNullValue");
        digester.addObjectCreate("faces-config/managed-bean/managed-property/list-entries/value", ListEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/managed-property/list-entries/value", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/managed-property/list-entries/value", "setValue", 0);
        digester.addObjectCreate("faces-config/managed-bean/map-entries", MapEntries.class);
        digester.addSetNext("faces-config/managed-bean/map-entries", "setMapEntries");
        digester.addCallMethod("faces-config/managed-bean/map-entries/key-class", "setKeyClass", 0);
        digester.addCallMethod("faces-config/managed-bean/map-entries/value-class", "setValueClass", 0);
        digester.addObjectCreate("faces-config/managed-bean/map-entries/map-entry", MapEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/map-entries/map-entry", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/map-entries/map-entry/key", "setKey", 0);
        digester.addCallMethod("faces-config/managed-bean/map-entries/map-entry/null-value", "setNullValue");
        digester.addCallMethod("faces-config/managed-bean/map-entries/map-entry/value", "setValue", 0);
        digester.addObjectCreate("faces-config/managed-bean/list-entries", ListEntries.class);
        digester.addSetNext("faces-config/managed-bean/list-entries", "setListEntries");
        digester.addCallMethod("faces-config/managed-bean/list-entries/value-class", "setValueClass", 0);
        digester.addObjectCreate("faces-config/managed-bean/list-entries/null-value", ListEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/list-entries/null-value", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/list-entries/null-value", "setNullValue");
        digester.addObjectCreate("faces-config/managed-bean/list-entries/value", ListEntries.Entry.class);
        digester.addSetNext("faces-config/managed-bean/list-entries/value", "addEntry");
        digester.addCallMethod("faces-config/managed-bean/list-entries/value", "setValue", 0);

        digester.addObjectCreate("faces-config/navigation-rule", NavigationRule.class);
        digester.addSetNext("faces-config/navigation-rule", "addNavigationRule");
        digester.addCallMethod("faces-config/navigation-rule/from-view-id", "setFromViewId", 0);
        digester.addObjectCreate("faces-config/navigation-rule/navigation-case", NavigationCase.class);
        digester.addSetNext("faces-config/navigation-rule/navigation-case", "addNavigationCase");
        digester.addCallMethod("faces-config/navigation-rule/navigation-case/from-action", "setFromAction", 0);
        digester.addCallMethod("faces-config/navigation-rule/navigation-case/from-outcome", "setFromOutcome", 0);
        digester.addCallMethod("faces-config/navigation-rule/navigation-case/to-view-id", "setToViewId", 0);
        digester.addCallMethod("faces-config/navigation-rule/navigation-case/redirect", "setRedirect");

        digester.addObjectCreate("faces-config/render-kit", RenderKit.class);
        digester.addSetNext("faces-config/render-kit", "addRenderKit");
        digester.addCallMethod("faces-config/render-kit/render-kit-id", "setId", 0);
        digester.addCallMethod("faces-config/render-kit/render-kit-class", "setRenderKitClass", 0);
        digester.addObjectCreate("faces-config/render-kit/renderer", Renderer.class);
        digester.addSetNext("faces-config/render-kit/renderer", "addRenderer");
        digester.addCallMethod("faces-config/render-kit/renderer/component-family", "setComponentFamily", 0);
        digester.addCallMethod("faces-config/render-kit/renderer/renderer-type", "setRendererType", 0);
        digester.addCallMethod("faces-config/render-kit/renderer/renderer-class", "setRendererClass", 0);

        digester.addCallMethod("faces-config/lifecycle/phase-listener", "addLifecyclePhaseListener", 0);

        digester.addCallMethod("faces-config/validator", "addValidator", 2);
        digester.addCallParam("faces-config/validator/validator-id", 0);
        digester.addCallParam("faces-config/validator/validator-class", 1);
    }


    public FacesConfig getFacesConfig(InputStream in, String systemId) throws IOException, SAXException
    {
        InputSource is = new InputSource(in);
        is.setSystemId(systemId);

        //Fix for http://issues.apache.org/jira/browse/MYFACES-236
        FacesConfig config = (FacesConfig) digester.parse(is);

        List li =config.getApplications();

        for (int i = 0; i < li.size(); i++)
        {
            Application application = (Application) li.get(i);
            List localeList = application.getLocaleConfig();

            for (int j = 0; j < localeList.size(); j++)
            {
                LocaleConfig localeConfig = (LocaleConfig) localeList.get(j);

                if(!localeConfig.getSupportedLocales().contains(localeConfig.getDefaultLocale()))
                    localeConfig.getSupportedLocales().add(localeConfig.getDefaultLocale());
            }
        }

        return config;
    }


}
