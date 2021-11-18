/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared.util.ClassUtils;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

/**
 * Holds all configuration init parameters (from web.xml) that are independent
 * from the core implementation. The parameters in this class are available to
 * all shared, component and implementation classes.
 * See RuntimeConfig for configuration infos that come from the faces-config
 * files and are needed by the core implementation.
 *
 * MyfacesConfig is meant for components that implement some of the extended features
 * of MyFaces. Anyhow, using the MyFaces JSF implementation is no precondition for using
 * MyfacesConfig in custom components. Upon using another JSF implementation
 * (or omitting the extended init parameters) all config properties will simply have
 * their default values.
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 775010 $ $Date: 2009-05-15 08:33:38 +0300 (Pn, 15 Geg 2009) $
 */
public class MyfacesConfig
{
    private static final Log log = LogFactory.getLog(MyfacesConfig.class);

    private static final String APPLICATION_MAP_PARAM_NAME = MyfacesConfig.class.getName();

    public static final String  INIT_PARAM_RESOURCE_VIRTUAL_PATH = "org.apache.myfaces.RESOURCE_VIRTUAL_PATH";
    public static final String  INIT_PARAM_RESOURCE_VIRTUAL_PATH_DEFAULT = "/faces/myFacesExtensionResource";

    private static final String  INIT_PARAM_PRETTY_HTML = "org.apache.myfaces.PRETTY_HTML";
    private static final boolean INIT_PARAM_PRETTY_HTML_DEFAULT = true;

    private static final String  INIT_PARAM_ALLOW_JAVASCRIPT = "org.apache.myfaces.ALLOW_JAVASCRIPT";
    private static final boolean INIT_PARAM_ALLOW_JAVASCRIPT_DEFAULT = true;

    private static final String  INIT_PARAM_DETECT_JAVASCRIPT = "org.apache.myfaces.DETECT_JAVASCRIPT";
    private static final boolean INIT_PARAM_DETECT_JAVASCRIPT_DEFAULT = false;

    private static final String  INIT_PARAM_AUTO_SCROLL = "org.apache.myfaces.AUTO_SCROLL";
    private static final boolean INIT_PARAM_AUTO_SCROLL_DEFAULT = false;

    private static final String INIT_PARAM_ADD_RESOURCE_CLASS = "org.apache.myfaces.ADD_RESOURCE_CLASS";
    private static final String INIT_PARAM_ADD_RESOURCE_CLASS_DEFAULT = "org.apache.myfaces.renderkit.html.util.DefaultAddResource";

    private static final String  INIT_CHECK_EXTENSIONS_FILTER = "org.apache.myfaces.CHECK_EXTENSIONS_FILTER";
    private static final boolean INIT_CHECK_EXTENSIONS_FILTER_DEFAULT = true;

    private static final String INIT_READONLY_AS_DISABLED_FOR_SELECT = "org.apache.myfaces.READONLY_AS_DISABLED_FOR_SELECTS";
    private static final boolean INIT_READONLY_AS_DISABLED_FOR_SELECT_DEFAULT = true;

    public static final String INIT_PARAM_CONFIG_REFRESH_PERIOD = "org.apache.myfaces.CONFIG_REFRESH_PERIOD";
    public static final long INIT_PARAM_CONFIG_REFRESH_PERIOD_DEFAULT = 2;

    private static final String  INIT_PARAM_VIEWSTATE_JAVASCRIPT = "org.apache.myfaces.VIEWSTATE_JAVASCRIPT";
    private static final boolean INIT_PARAM_VIEWSTATE_JAVASCRIPT_DEFAULT = false;

    private static final String  INIT_PARAM_RENDER_VIEWSTATE_ID = "org.apache.myfaces.RENDER_VIEWSTATE_ID";
    private static final boolean INIT_PARAM_RENDER_VIEWSTATE_ID_DEFAULT = true;

    private static final String  INIT_PARAM_STRICT_XHTML_LINKS = "org.apache.myfaces.STRICT_XHTML_LINKS";
    private static final boolean INIT_PARAM_STRICT_XHTML_LINKS_DEFAULT = true;
    
    private static final String INIT_PARAM_RENDER_CLEAR_JAVASCRIPT_FOR_BUTTON = "org.apache.myfaces.RENDER_CLEAR_JAVASCRIPT_FOR_BUTTON";
    private static final boolean INIT_PARAM_RENDER_CLEAR_JAVASCRIPT_FOR_BUTTON_DEFAULT= false;

    private static final String INIT_PARAM_SAVE_FORM_SUBMIT_LINK_IE = "org.apache.myfaces.SAVE_FORM_SUBMIT_LINK_IE";
    private static final boolean INIT_PARAM_SAVE_FORM_SUBMIT_LINK_IE_DEFAULT = false;
    
    private static final String INIT_PARAM_DELEGATE_FACES_SERVLET = "org.apache.myfaces.DELEGATE_FACES_SERVLET";

    private boolean _prettyHtml;
    private boolean _detectJavascript;
    private boolean _allowJavascript;
    private boolean _autoScroll;
    private String _addResourceClass;
    private String _resourceVirtualPath;
    private boolean _checkExtensionsFilter;
    private boolean _readonlyAsDisabledForSelect;
    private long _configRefreshPeriod;
    private boolean _viewStateJavascript;
    private boolean _renderViewStateId;
    private boolean _strictXhtmlLinks;
    private boolean _renderClearJavascriptOnButton;
    private boolean _saveFormSubmitLinkIE;
    private String _delegateFacesServlet;


    private static final boolean TOMAHAWK_AVAILABLE;
    private static final boolean MYFACES_IMPL_AVAILABLE;
    private static final boolean RI_IMPL_AVAILABLE;

    static
    {
        boolean tomahawkAvailable;
        try
        {
            ClassUtils.classForName("org.apache.myfaces.webapp.filter.ExtensionsFilter");
            tomahawkAvailable = true;
        }
        catch (ClassNotFoundException e)
        {
            tomahawkAvailable = false;
        }
        TOMAHAWK_AVAILABLE = tomahawkAvailable;
    }

    static
    {
        boolean myfacesImplAvailable;
        try
        {
            ClassUtils.classForName("org.apache.myfaces.application.ApplicationImpl");
            myfacesImplAvailable = true;
        }
        catch (ClassNotFoundException e)
        {
            myfacesImplAvailable = false;
        }
        MYFACES_IMPL_AVAILABLE = myfacesImplAvailable;
    }

    static
    {
        boolean riImplAvailable;
        try
        {
            ClassUtils.classForName("com.sun.faces.application.ApplicationImpl");
            riImplAvailable = true;
        }
        catch (ClassNotFoundException e)
        {
            riImplAvailable = false;
        }
        RI_IMPL_AVAILABLE = riImplAvailable;
    }

    public static MyfacesConfig getCurrentInstance(ExternalContext extCtx)
    {
        MyfacesConfig myfacesConfig = (MyfacesConfig) extCtx
                .getApplicationMap().get(APPLICATION_MAP_PARAM_NAME);
        if (myfacesConfig == null)
        {

            myfacesConfig = createAndInitializeMyFacesConfig(extCtx);

            extCtx.getApplicationMap().put(APPLICATION_MAP_PARAM_NAME, myfacesConfig);

        }

        return myfacesConfig;
    }

    private static MyfacesConfig createAndInitializeMyFacesConfig(ExternalContext extCtx) {
        
        MyfacesConfig myfacesConfig = new MyfacesConfig();

        myfacesConfig.setPrettyHtml(getBooleanInitParameter(extCtx, INIT_PARAM_PRETTY_HTML,
                                                            INIT_PARAM_PRETTY_HTML_DEFAULT));
        myfacesConfig.setAllowJavascript(getBooleanInitParameter(extCtx, INIT_PARAM_ALLOW_JAVASCRIPT,
                                                                 INIT_PARAM_ALLOW_JAVASCRIPT_DEFAULT));

        myfacesConfig.setRenderClearJavascriptOnButton(getBooleanInitParameter(extCtx, INIT_PARAM_RENDER_CLEAR_JAVASCRIPT_FOR_BUTTON,
                                                            INIT_PARAM_RENDER_CLEAR_JAVASCRIPT_FOR_BUTTON_DEFAULT));

        myfacesConfig.setSaveFormSubmitLinkIE(getBooleanInitParameter(extCtx, INIT_PARAM_SAVE_FORM_SUBMIT_LINK_IE,
                                                            INIT_PARAM_SAVE_FORM_SUBMIT_LINK_IE_DEFAULT));
        
        myfacesConfig.setReadonlyAsDisabledForSelect(getBooleanInitParameter(extCtx, INIT_READONLY_AS_DISABLED_FOR_SELECT,
                                                                 INIT_READONLY_AS_DISABLED_FOR_SELECT_DEFAULT));
        myfacesConfig.setRenderViewStateId(getBooleanInitParameter(extCtx, INIT_PARAM_RENDER_VIEWSTATE_ID,
                                                                   INIT_PARAM_RENDER_VIEWSTATE_ID_DEFAULT));
        myfacesConfig.setStrictXhtmlLinks(getBooleanInitParameter(extCtx, INIT_PARAM_STRICT_XHTML_LINKS,
                                                                  INIT_PARAM_STRICT_XHTML_LINKS_DEFAULT));

        myfacesConfig.setConfigRefreshPeriod(getLongInitParameter(extCtx, INIT_PARAM_CONFIG_REFRESH_PERIOD,
                INIT_PARAM_CONFIG_REFRESH_PERIOD_DEFAULT));

        myfacesConfig.setViewStateJavascript(getBooleanInitParameter(extCtx, INIT_PARAM_VIEWSTATE_JAVASCRIPT,
                INIT_PARAM_VIEWSTATE_JAVASCRIPT_DEFAULT));

        myfacesConfig.setDelegateFacesServlet(extCtx.getInitParameter(INIT_PARAM_DELEGATE_FACES_SERVLET));

        if (TOMAHAWK_AVAILABLE)
        {
            myfacesConfig.setDetectJavascript(getBooleanInitParameter(extCtx, INIT_PARAM_DETECT_JAVASCRIPT,
                    INIT_PARAM_DETECT_JAVASCRIPT_DEFAULT));
            myfacesConfig.setAutoScroll(getBooleanInitParameter(extCtx, INIT_PARAM_AUTO_SCROLL,
                    INIT_PARAM_AUTO_SCROLL_DEFAULT));
                        
            myfacesConfig.setAddResourceClass(getStringInitParameter(extCtx, INIT_PARAM_ADD_RESOURCE_CLASS,
                    INIT_PARAM_ADD_RESOURCE_CLASS_DEFAULT));
            myfacesConfig.setResourceVirtualPath(getStringInitParameter(extCtx, INIT_PARAM_RESOURCE_VIRTUAL_PATH,
                    INIT_PARAM_RESOURCE_VIRTUAL_PATH_DEFAULT));

            myfacesConfig.setCheckExtensionsFilter(getBooleanInitParameter(extCtx, INIT_CHECK_EXTENSIONS_FILTER,
                    INIT_CHECK_EXTENSIONS_FILTER_DEFAULT));
        }
        else
        {
            if (log.isInfoEnabled())
            {
                log.info("Tomahawk jar not available. Autoscrolling, DetectJavascript, AddResourceClass and CheckExtensionsFilter are disabled now.");
            }
        }

        if(RI_IMPL_AVAILABLE)
        {
            if(log.isInfoEnabled())
            {
                log.info("Starting up Tomahawk on the RI-JSF-Implementation.");
            }
        }

        if(MYFACES_IMPL_AVAILABLE)
        {
            if(log.isInfoEnabled())
            {
                log.info("Starting up Tomahawk on the MyFaces-JSF-Implementation");
            }
        }

        if(RI_IMPL_AVAILABLE && MYFACES_IMPL_AVAILABLE)
        {
            log.error("Both MyFaces and the RI are on your classpath. Please make sure to use only one of the two JSF-implementations.");
        }
        return myfacesConfig;
    }

    private static boolean getBooleanInitParameter(ExternalContext externalContext,
                                                   String paramName,
                                                   boolean defaultValue)
    {
        String strValue = externalContext.getInitParameter(paramName);
        if (strValue == null)
        {
            if (log.isInfoEnabled()) log.info("No context init parameter '" + paramName + "' found, using default value " + defaultValue);
            return defaultValue;
        }
        else if (strValue.equalsIgnoreCase("true") || strValue.equalsIgnoreCase("on") || strValue.equalsIgnoreCase("yes"))
        {
            return true;
        }
        else if (strValue.equalsIgnoreCase("false") || strValue.equalsIgnoreCase("off") || strValue.equalsIgnoreCase("no"))
        {
            return false;
        }
        else
        {
            if (log.isWarnEnabled()) log.warn("Wrong context init parameter '" + paramName + "' (='" + strValue + "'), using default value " + defaultValue);
            return defaultValue;
        }
    }

    private static String getStringInitParameter(ExternalContext externalContext,
                                                 String paramName,
                                                 String defaultValue)
    {
        String strValue = externalContext.getInitParameter(paramName);
        if (strValue == null)
        {
            if (log.isInfoEnabled()) log.info("No context init parameter '" + paramName + "' found, using default value " + defaultValue); //defaultValue==null should not be a problem here
            return defaultValue;
        }
        
        return strValue;
    }

    private static long getLongInitParameter(ExternalContext externalContext,
                                                  String paramName,
                                                  long defaultValue) {
       String strValue = externalContext.getInitParameter(paramName);
       if (strValue == null)
       {
           if (log.isInfoEnabled()) log.info("No context init parameter '" +paramName +"' found, using default value " +defaultValue);
           return defaultValue;
       }
       else
       {
           try {
               return Long.parseLong(strValue);
           } catch (NumberFormatException e) {
               if (log.isWarnEnabled()) log.warn("Wrong context init parameter '" + paramName + "' (='" + strValue + "'), using default value " + defaultValue);
           }
           return defaultValue;
       }
    }
        
     private void setResourceVirtualPath( String resourceVirtualPath )
     {
         this._resourceVirtualPath = resourceVirtualPath;
    }

     public String getResourceVirtualPath()
     {
         return this._resourceVirtualPath;
     }

    public boolean isPrettyHtml()
    {
        return _prettyHtml;
    }

    private void setPrettyHtml(boolean prettyHtml)
    {
        _prettyHtml = prettyHtml;
    }

    public boolean isDetectJavascript()
    {
        return _detectJavascript;
    }

    private void setDetectJavascript(boolean detectJavascript)
    {
        _detectJavascript = detectJavascript;
    }

    private void setReadonlyAsDisabledForSelect(boolean readonlyAsDisabledForSelect)
    {
        _readonlyAsDisabledForSelect = readonlyAsDisabledForSelect;
    }

    public boolean isReadonlyAsDisabledForSelect()
    {
        return _readonlyAsDisabledForSelect;
    }


   public long getConfigRefreshPeriod()
   {
       return _configRefreshPeriod;
   }

   public void setConfigRefreshPeriod(long configRefreshPeriod)
   {
       _configRefreshPeriod = configRefreshPeriod;
   }

    /**
     * JSF API 1.2 defines a "javax.faces.ViewState" client parameter, that must be rendered as both the "name"
     * and the "id" attribute of the hidden input that is rendered for the purpose of state saving
     * (see <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/javax/faces/render/ResponseStateManager.html#VIEW_STATE_PARAM">API</a>).
     * Actually this causes duplicate id attributes and thus invalid XHTML pages when multiple forms are rendered on
     * one page. With the {@link #INIT_PARAM_RENDER_VIEWSTATE_ID} context parameter you can tune this behaviour.
     * <br/>Set it to
     * <ul><li>true - to render JSF 1.2 compliant id attributes (that might cause invalid XHTML), or</li>
     * <li>false - to omit rendering of the id attribute (which is only needed for very special AJAX/Javascript components)</li></ul>
     * Default value is: true (for backwards compatibility and JSF 1.2 compliancy) 
     * @return true, if the client state hidden input "javax.faces.ViewState" id attribute should be rendered
     */
    public boolean isRenderViewStateId() {
        return _renderViewStateId;
    }

    public void setRenderViewStateId(boolean renderViewStateId) {
        _renderViewStateId = renderViewStateId;
    }

    /**
     * <p>W3C recommends to use the "&amp;amp;" entity instead of a plain "&amp;" character within HTML.
     * This also applies to attribute values and thus to the "href" attribute of &lt;a&gt; elements as well.
     * Even more, when XHTML is used as output the usage of plain "&amp;" characters is forbidden and would lead to
     * invalid XML code.
     * Therefore, since version 1.1.6 MyFaces renders the correct "&amp;amp;" entity for links.</p>
     * <p>The init parameter
     * {@link #INIT_PARAM_STRICT_XHTML_LINKS} makes it possible to restore the old behaviour and to make MyFaces
     * "bug compatible" to the Sun RI which renders plain "&amp;" chars in links as well.</p>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3.2">HTML 4.01 Specification</a>
     * @see <a href="http://issues.apache.org/jira/browse/MYFACES-1774">Jira: MYFACES-1774</a>
     * @return true if ampersand characters ("&amp;") should be correctly rendered as "&amp;amp;" entities within link urls (=default),
     *         false for old (XHTML incompatible) behaviour
     */
    public boolean isStrictXhtmlLinks() {
        return _strictXhtmlLinks;
    }

    public void setStrictXhtmlLinks(boolean strictXhtmlLinks) {
        _strictXhtmlLinks = strictXhtmlLinks;
    }

    public boolean isTomahawkAvailable()
    {
        return TOMAHAWK_AVAILABLE;
    }

    public boolean isMyfacesImplAvailable()
    {
        return MYFACES_IMPL_AVAILABLE;
    }

    public boolean isRiImplAvailable()
    {
        return RI_IMPL_AVAILABLE;
    }

    /**
     * Do not use this in renderers directly!
     * You should use {@link org.apache.myfaces.shared.renderkit.html.util.JavascriptUtils#isJavascriptAllowed}
     * to determine if javascript is allowed or not.
     */
    public boolean isAllowJavascript()
    {
        return _allowJavascript;
    }

    private void setAllowJavascript(boolean allowJavascript)
    {
        _allowJavascript = allowJavascript;
    }

    public boolean isAutoScroll()
    {
        return _autoScroll;
    }

    private void setAutoScroll(boolean autoScroll)
    {
        _autoScroll = autoScroll;
    }

    private void setAddResourceClass(String addResourceClass)
    {
        _addResourceClass = addResourceClass;
    }

    public String getAddResourceClass()
    {
        return _addResourceClass;
    }

    /**
     * ExtensionFilter needs access to AddResourceClass init param without having
     * an ExternalContext at hand.
     */
    public static String getAddResourceClassFromServletContext(ServletContext servletContext)
    {
        String addResourceClass = servletContext.getInitParameter(INIT_PARAM_ADD_RESOURCE_CLASS);

        return addResourceClass == null ? INIT_PARAM_ADD_RESOURCE_CLASS_DEFAULT : addResourceClass;
    }

    /**
     * Should the environment be checked so that the ExtensionsFilter will work properly. 
     */
    public boolean isCheckExtensionsFilter()
    {
        return _checkExtensionsFilter;
    }

    public void setCheckExtensionsFilter(boolean extensionsFilter)
    {
        _checkExtensionsFilter = extensionsFilter;
    }

    /**
     * 
     */
    public boolean isViewStateJavascript()
    {
        return _viewStateJavascript;
    }

    private void setViewStateJavascript(boolean viewStateJavascript)
    {
        _viewStateJavascript = viewStateJavascript;
    }

    public void setRenderClearJavascriptOnButton(
            boolean renderClearJavascriptOnButton)
    {
        _renderClearJavascriptOnButton = renderClearJavascriptOnButton;
    }

    /**
     * This param renders the clear javascript on button necessary only for
     * compatibility with hidden fields feature of myfaces. This is done 
     * because jsf ri does not render javascript on onclick method for button,
     * so myfaces should do this.
     * 
     * @return
     */
    public boolean isRenderClearJavascriptOnButton()
    {
        return _renderClearJavascriptOnButton;
    }

    public void setSaveFormSubmitLinkIE(boolean saveFormSubmitLinkIE)
    {
        _saveFormSubmitLinkIE = saveFormSubmitLinkIE;
    }

    /**
     * Add a code that save the form when submit a form using a
     * link. It's a bug on IE.
     * 
     * @return
     */
    public boolean isSaveFormSubmitLinkIE()
    {
        return _saveFormSubmitLinkIE;
    }
    
    public String getDelegateFacesServlet()
    {
        return _delegateFacesServlet;
    }
    
    public void setDelegateFacesServlet(String delegateFacesServlet)
    {
        _delegateFacesServlet = delegateFacesServlet;
    }
}
