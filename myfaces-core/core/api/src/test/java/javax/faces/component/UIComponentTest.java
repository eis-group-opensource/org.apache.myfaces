/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import static org.apache.myfaces.Assert.*;
import org.apache.myfaces.TestRunner;
import static org.easymock.EasyMock.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class UIComponentTest extends UIComponentTestBase
{
    /**
     * Test method for {@link javax.faces.component.UIComponent#getFacetCount()}.
     */
    @Test
    public void testGetFacetCount() throws Exception
    {
        UIComponent component = _mocksControl.createMock(UIComponent.class, new Method[] { UIComponent.class
                .getDeclaredMethod("getFacets", null) });
        Map<String, UIComponent> map = new HashMap<String, UIComponent>();
        map.put("xxx1", new UIInput());
        map.put("xxx2", new UIInput());
        map.put("xxx3", new UIInput());
        expect(component.getFacets()).andReturn(map);
        _mocksControl.replay();
        assertEquals(3, component.getFacetCount());
        _mocksControl.verify();

        _mocksControl.reset();
        expect(component.getFacets()).andReturn(null);
        _mocksControl.replay();
        assertEquals(0, component.getFacetCount());
        _mocksControl.verify();
    }

    /**
     * Test method for
     * {@link javax.faces.component.UIComponent#getContainerClientId(javax.faces.context.FacesContext)}.
     * 
     * @throws Exception
     */
    @Test
    public void testGetContainerClientId() throws Exception
    {
        Collection<Method> mockedMethods = new ArrayList<Method>();
        Class<UIComponent> clazz = UIComponent.class;
        mockedMethods.add(clazz.getDeclaredMethod("getClientId", new Class[] { FacesContext.class }));
        final UIComponent testimpl = _mocksControl.createMock(clazz, mockedMethods.toArray(new Method[mockedMethods
                .size()]));
        _mocksControl.checkOrder(true);

        assertException(NullPointerException.class, new TestRunner()
        {
            public void run() throws Throwable
            {
                testimpl.getContainerClientId(null);
            }
        });

        expect(testimpl.getClientId(same(_facesContext))).andReturn("xyz");
        _mocksControl.replay();
        assertEquals("xyz", testimpl.getContainerClientId(_facesContext));
        _mocksControl.verify();
    }
}
