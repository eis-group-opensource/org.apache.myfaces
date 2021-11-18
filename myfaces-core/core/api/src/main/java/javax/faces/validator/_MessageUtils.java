/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.validator;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 940134 $ $Date: 2010-05-02 04:31:07 +0300 (Sun, 02 May 2010) $
 */
class _MessageUtils
{
    private static final String DETAIL_SUFFIX = "_detail";

    static FacesMessage getErrorMessage(FacesContext facesContext,
                                        String messageId,
                                        Object args[])
    {
        return getMessage(facesContext,
                          facesContext.getViewRoot().getLocale(),
                          FacesMessage.SEVERITY_ERROR,
                          messageId,
                          args);
    }

    static FacesMessage getMessage(FacesContext facesContext,
                                   Locale locale,
                                   FacesMessage.Severity severity,
                                   String messageId,
                                   Object args[])
    {
        ResourceBundle appBundle;
        ResourceBundle defBundle;
        String summary;
        String detail;

        if(locale == null)
        {
            locale = Locale.getDefault();
        }

        appBundle = getApplicationBundle(facesContext, locale);
        summary = getBundleString(appBundle, messageId);
        if (summary != null)
        {
            detail = getBundleString(appBundle, messageId + DETAIL_SUFFIX);
        }
        else
        {
            defBundle = getDefaultBundle(facesContext, locale);
            summary = getBundleString(defBundle, messageId);
            if (summary != null)
            {
                detail = getBundleString(defBundle, messageId + DETAIL_SUFFIX);
            }
            else
            {
                //Try to find detail alone
                detail = getBundleString(appBundle, messageId + DETAIL_SUFFIX);
                if (detail != null)
                {
                    summary = null;
                }
                else
                {
                    detail = getBundleString(defBundle, messageId + DETAIL_SUFFIX);
                    if (detail != null)
                    {
                        summary = null;
                    }
                    else
                    {
                        //Neither detail nor summary found
                        facesContext.getExternalContext().log("No message with id " + messageId + " found in any bundle");
                        return new FacesMessage(severity, messageId, null);
                    }
                }
            }
        }

        if (args != null && args.length > 0)
        {
            return new _ParametrizableFacesMessage(severity, summary, detail, args, locale);
        }
        else
        {
            return new FacesMessage(severity, summary, detail);
        }
    }

    private static String getBundleString(ResourceBundle bundle, String key)
    {
        try
        {
            return bundle == null ? null : bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            return null;
        }
    }


    private static ResourceBundle getApplicationBundle(FacesContext facesContext, Locale locale)
    {
        String bundleName = facesContext.getApplication().getMessageBundle();
        return bundleName != null ? getBundle(facesContext, locale, bundleName) : null;
    }

    private static ResourceBundle getDefaultBundle(FacesContext facesContext,
                                                   Locale locale)
    {
        return getBundle(facesContext, locale, FacesMessage.FACES_MESSAGES);
    }

    private static ResourceBundle getBundle(FacesContext facesContext,
                                            Locale locale,
                                            String bundleName)
    {
        try
        {
            //First we try the JSF implementation class loader
            return ResourceBundle.getBundle(bundleName,
                                            locale,
                                            facesContext.getClass().getClassLoader());
        }
        catch (MissingResourceException ignore1)
        {
            try
            {
                //Next we try the JSF API class loader
                return ResourceBundle.getBundle(bundleName,
                                                locale,
                                                _MessageUtils.class.getClassLoader());
            }
            catch (MissingResourceException ignore2)
            {
                try
                {
                    //Last resort is the context class loader
                    return ResourceBundle.getBundle(bundleName,
                                                    locale,
                                                    Thread.currentThread().getContextClassLoader());
                }
                catch (MissingResourceException damned)
                {
                    facesContext.getExternalContext().log("resource bundle " + bundleName + " could not be found");
                    return null;
                }
            }
        }
    }
    
    static Object getLabel(FacesContext facesContext, UIComponent component) {
        Object label = component.getAttributes().get("label");
        if(label != null)
            return label;
        
        ValueExpression expression = component.getValueExpression("label");
        if(expression != null)
            return expression;
        
        //If no label is not specified, use clientId
        return component.getClientId( facesContext );
    }
}
