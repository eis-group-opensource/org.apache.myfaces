/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.lifecycle;

import static org.easymock.EasyMock.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.myfaces.Assert;
import org.apache.myfaces.FacesTestCase;
import org.apache.myfaces.TestRunner;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 520068 $ $Date: 2007-03-19 21:45:57 +0200 (Mon, 19 Mar 2007) $
 */
public class DefaultRestoreViewSupportTest extends FacesTestCase
{

    private DefaultRestoreViewSupport _testimpl;

    protected void setUp() throws Exception
    {
        super.setUp();
        _testimpl = new DefaultRestoreViewSupport();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#processComponentBinding(javax.faces.context.FacesContext, javax.faces.component.UIComponent)}.
     */
    public void testProcessComponentBinding()
    {
        UIComponent root = _mocksControl.createMock(UIComponent.class);
        UIComponent testcomponent = _mocksControl.createMock(UIComponent.class);
        ValueExpression rootExpression = _mocksControl.createMock(ValueExpression.class);
        ValueExpression testExpression = _mocksControl.createMock(ValueExpression.class);
        
        _mocksControl.checkOrder(true);
        expect(root.getValueExpression(eq("binding"))).andReturn(rootExpression);
        expect(_facesContext.getELContext()).andReturn(_elContext);
        rootExpression.setValue(same(_elContext), same(root));
        expect(root.getFacetsAndChildren()).andReturn(Arrays.asList(new UIComponent[] { testcomponent }).iterator());
        expect(testcomponent.getValueExpression(eq("binding"))).andReturn(testExpression);
        expect(_facesContext.getELContext()).andReturn(_elContext);
        testExpression.setValue(same(_elContext), same(testcomponent));
        expect(testcomponent.getFacetsAndChildren()).andReturn(Collections.EMPTY_LIST.iterator());

        _mocksControl.replay();
        _testimpl.processComponentBinding(_facesContext, root);
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#calculateViewId(javax.faces.context.FacesContext)}.
     */
    public void testCalculateViewIdFromRequestAttributeIncludePathInfo()
    {
        _mocksControl.checkOrder(true);
        expect(_facesContext.getExternalContext()).andReturn(_externalContext);
        Map map = new HashMap();
        String expectedValue = "javax.servlet.include.path_info_VIEWID";
        map.put("javax.servlet.include.path_info", expectedValue);
        expect(_externalContext.getRequestMap()).andReturn(map);

        _mocksControl.replay();
        assertEquals(expectedValue, _testimpl.calculateViewId(_facesContext));
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#calculateViewId(javax.faces.context.FacesContext)}.
     */
    public void testCalculateViewIdFromRequestPathInfo()
    {
        _mocksControl.checkOrder(true);
        expect(_facesContext.getExternalContext()).andReturn(_externalContext);
        expect(_externalContext.getRequestMap()).andReturn(Collections.EMPTY_MAP);
        String expectedValue = "requestPathInfo_VIEWID";
        expect(_externalContext.getRequestPathInfo()).andReturn(expectedValue);

        _mocksControl.replay();
        assertEquals(expectedValue, _testimpl.calculateViewId(_facesContext));
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#calculateViewId(javax.faces.context.FacesContext)}.
     */
    public void testCalculateViewIdFromRequestAttributeIncludeServletPath()
    {
        _mocksControl.checkOrder(true);
        expect(_facesContext.getExternalContext()).andReturn(_externalContext);
        Map map = new HashMap();
        String expectedValue = "javax.servlet.include.servlet_path_VIEWID";
        map.put("javax.servlet.include.servlet_path", expectedValue);
        expect(_externalContext.getRequestMap()).andReturn(map);
        expect(_externalContext.getRequestPathInfo()).andReturn(null);

        _mocksControl.replay();
        assertEquals(expectedValue, _testimpl.calculateViewId(_facesContext));
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#calculateViewId(javax.faces.context.FacesContext)}.
     */
    public void testCalculateViewIdFromRequestServletPath()
    {
        _mocksControl.checkOrder(true);
        expect(_facesContext.getExternalContext()).andReturn(_externalContext);
        expect(_externalContext.getRequestMap()).andReturn(Collections.EMPTY_MAP);
        expect(_externalContext.getRequestPathInfo()).andReturn(null);
        String expectedValue = "RequestServletPath_VIEWID";
        expect(_externalContext.getRequestServletPath()).andReturn(expectedValue);

        _mocksControl.replay();
        assertEquals(expectedValue, _testimpl.calculateViewId(_facesContext));
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#calculateViewId(javax.faces.context.FacesContext)}.
     */
    public void testCalculateViewIdFacesException()
    {
        _mocksControl.checkOrder(true);
        expect(_facesContext.getExternalContext()).andReturn(_externalContext);
        expect(_externalContext.getRequestMap()).andReturn(Collections.EMPTY_MAP);
        expect(_externalContext.getRequestPathInfo()).andReturn(null);
        expect(_externalContext.getRequestServletPath()).andReturn(null);

        _mocksControl.replay();
        Assert.assertException(FacesException.class, new TestRunner()
        {
            public void run() throws Throwable
            {
                _testimpl.calculateViewId(_facesContext);
            }
        });
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.lifecycle.DefaultRestoreViewSupport#isPostback(javax.faces.context.FacesContext)}.
     */
    public void testIsPostback()
    {
        // TODO: not testable unless static call to RendererUtils is removed
    }

}
