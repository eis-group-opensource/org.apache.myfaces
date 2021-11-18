/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.el.ValueExpression;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;
import org.apache.myfaces.shared_impl.util.ClassUtils;

/**
 * Loads a resource bundle and saves it as a variable in the request scope.
 * <p>
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * </p>
 * <p> 
 * TODO:
 * We should find a way to save loaded bundles in the state, because otherwise
 * on the next request the bundle map will not be present before the render phase
 * and value bindings that reference to the bundle will always log annoying
 * "Variable 'xxx' could not be resolved" error messages.
 * </p>
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 924403 $ $Date: 2010-03-17 20:28:20 +0200 (Wed, 17 Mar 2010) $
 */
@JSFJspTag(
        name="f:loadBundle",
        bodyContent="empty")
public class LoadBundleTag
        extends TagSupport
{
    private static final long serialVersionUID = -8892145684062838928L;

    private ValueExpression _basename;
    private String _var;

    /**
     * The base name of the resource bundle.
     */
    @JSFJspAttribute(
            required=true,
            rtexprvalue=true,
            className="java.lang.String")
    public void setBasename(ValueExpression basename)
    {
        _basename = basename;
    }

    /**
     * The name of the variable in request scope that the resources
     * are saved to.  This must be a static value.
     */
    @JSFJspAttribute(required=true)
    public void setVar(String var)
    {
        _var = var;
    }

    public int doStartTag() throws JspException
    {
        if (null == _var)
        {
            throw new NullPointerException("LoadBundle: 'var' must not be null");
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null)
        {
            throw new JspException("No faces context?!");
        }

        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null)
        {
            throw new JspException("No view root! LoadBundle must be nested inside <f:view> action.");
        }

        Locale locale = viewRoot.getLocale();
        if (locale == null)
        {
            locale = facesContext.getApplication().getDefaultLocale();
        }

        String basename = null;
        if (_basename!=null) {
            if (_basename.isLiteralText()) {
                basename = _basename.getExpressionString();
            } else {
                basename = (String) _basename.getValue(facesContext.getELContext());
            }
        }

        if (null == basename)
        {
            throw new NullPointerException("LoadBundle: 'basename' must not be null");
        }

        ResourceBundle bundle;
        try
        {
            bundle = ResourceBundle.getBundle(basename,
                                              locale,
                                              ClassUtils.getContextClassLoader());
        }
        catch (MissingResourceException e)
        {
            try
            {
                bundle = ResourceBundle.getBundle(basename,
                        locale,
                        this.getClass().getClassLoader());
            }
            catch (MissingResourceException e1)
            {
                throw new JspException("Resource bundle '" + basename + "' could not be found.", e1);
            }
        }

        facesContext.getExternalContext().getRequestMap().put(_var,
                                                              new BundleMap(bundle));
        return Tag.SKIP_BODY;
    }


    private static class BundleMap implements Map
    {
        private ResourceBundle _bundle;
        private List<String> _values;

        public BundleMap(ResourceBundle bundle)
        {
            _bundle = bundle;
        }

        //Optimized methods

        public Object get(Object key)
        {
            try {
                return _bundle.getObject(key.toString());
            } catch (Exception e) {
                return "???" + key + "???";
            }
        }

        public boolean isEmpty()
        {
            return !_bundle.getKeys().hasMoreElements();
        }

        public boolean containsKey(Object key)
        {
            try
            {
                return _bundle.getObject(key.toString()) != null;
            }
            catch (MissingResourceException e)
            {
                return false;
            }
        }


        //Unoptimized methods

        public Collection values()
        {
            if (_values == null)
            {
                _values = new ArrayList<String>();
                for (Enumeration<String> enumer = _bundle.getKeys(); enumer.hasMoreElements();)
                {
                    String v = _bundle.getString(enumer.nextElement());
                    _values.add(v);
                }
            }
            return _values;
        }

        public int size()
        {
            return values().size();
        }

        public boolean containsValue(Object value)
        {
            return values().contains(value);
        }

        public Set entrySet()
        {
            Set<Entry> set = new HashSet<Entry>();
            for (Enumeration<String> enumer = _bundle.getKeys(); enumer.hasMoreElements(); )
            {
                final String k = enumer.nextElement();
                set.add(new Map.Entry() {
                    public Object getKey()
                    {
                        return k;
                    }

                    public Object getValue()
                    {
                        return _bundle.getObject(k);
                    }

                    public Object setValue(Object value)
                    {
                        throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
                    }
                });
            }
            return set;
        }

        public Set keySet()
        {
            Set<String> set = new HashSet<String>();
            for (Enumeration<String> enumer = _bundle.getKeys(); enumer.hasMoreElements(); )
            {
                set.add(enumer.nextElement());
            }
            return set;
        }


        //Unsupported methods

        public Object remove(Object key)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public void putAll(Map t)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public Object put(Object key, Object value)
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

        public void clear()
        {
            throw new UnsupportedOperationException(this.getClass().getName() + " UnsupportedOperationException");
        }

    }

}
