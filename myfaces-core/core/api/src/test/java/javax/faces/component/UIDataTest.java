/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.myfaces.Assert;
import org.apache.myfaces.TestRunner;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 823343 $ $Date: 2009-10-09 00:59:45 +0300 (Fri, 09 Oct 2009) $
 */
public class UIDataTest extends AbstractJsfTestCase
{
    public UIDataTest(String name)
    {
        super(name);
    }

    private IMocksControl _mocksControl;
    private UIData _testImpl;

    protected void setUp() throws Exception
    {
        super.setUp();
        _mocksControl = EasyMock.createControl();
        _testImpl = new UIData();
    }

    /**
     * Test method for
     * {@link javax.faces.component.UIData#setValueExpression(java.lang.String, javax.el.ValueExpression)}.
     */
    public void testValueExpression()
    {
        assertSetValueExpressionException(IllegalArgumentException.class, "rowIndex");
        assertSetValueExpressionException(NullPointerException.class, null);
    }

    private void assertSetValueExpressionException(Class<? extends Throwable> expected, final String name)
    {
        Assert.assertException(expected, new TestRunner()
        {
            public void run() throws Throwable
            {
                _testImpl.setValueExpression(name, null);
            }
        });
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getClientId(javax.faces.context.FacesContext)}.
     */
    public void testGetClientId()
    {
        _testImpl.setId("xxx");
        Renderer renderer = _mocksControl.createMock(Renderer.class);
        renderKit.addRenderer(UIData.COMPONENT_FAMILY, UIData.COMPONENT_TYPE, renderer );
        assertEquals("xxx", _testImpl.getClientId(facesContext));
        _testImpl.setRowIndex(99);
        assertEquals("xxx:99", _testImpl.getClientId(facesContext));
    }

    /**
     * Test method for
     * {@link javax.faces.component.UIData#invokeOnComponent(javax.faces.context.FacesContext, java.lang.String, javax.faces.component.ContextCallback)}.
     * Tests, if invokeOnComponent also checks the facets of the h:column children (MYFACES-2370)
     */
    public void testInvokeOnComponentFacesContextStringContextCallback()
    {
        /**
         * Concrete class of ContextCallback needed to test invokeOnComponent. 
         * @author Jakob Korherr
         */
        final class MyContextCallback implements ContextCallback
        {
            
            private boolean invoked = false;

            public void invokeContextCallback(FacesContext context,
                    UIComponent target)
            {
                this.invoked = true;
            }
            
        }
        
        UIComponent facet = new UIOutput();
        facet.setId("facet");
        UIColumn column = new UIColumn();
        column.setId("column");
        column.getFacets().put("header", facet);
        _testImpl.setId("uidata");
        _testImpl.getChildren().add(column);
        
        MyContextCallback callback = new MyContextCallback();
        _testImpl.invokeOnComponent(facesContext, facet.getClientId(facesContext), callback);
        // the callback should have been invoked
        assertTrue(callback.invoked);
    }

    /**
     * Test method for {@link javax.faces.component.UIData#broadcast(javax.faces.event.FacesEvent)}.
     */
    public void testBroadcastFacesEvent()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#encodeBegin(javax.faces.context.FacesContext)}.
     */
    public void testEncodeBeginFacesContext()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#encodeEnd(javax.faces.context.FacesContext)}.
     */
    public void testEncodeEndFacesContext()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#queueEvent(javax.faces.event.FacesEvent)}.
     */
    public void testQueueEventFacesEvent()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#processDecodes(javax.faces.context.FacesContext)}.
     */
    public void testProcessDecodesFacesContext()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#processValidators(javax.faces.context.FacesContext)}.
     */
    public void testProcessValidatorsFacesContext()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#processUpdates(javax.faces.context.FacesContext)}.
     */
    public void testProcessUpdatesFacesContext()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#saveState(javax.faces.context.FacesContext)}.
     */
    public void testSaveState()
    {
        // TODO
    }

    /**
     * Test method for
     * {@link javax.faces.component.UIData#restoreState(javax.faces.context.FacesContext, java.lang.Object)}.
     */
    public void testRestoreState()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#UIData()}.
     */
    public void testUIData()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setFooter(javax.faces.component.UIComponent)}.
     */
    public void testSetFooter()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getFooter()}.
     */
    public void testGetFooter()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setHeader(javax.faces.component.UIComponent)}.
     */
    public void testSetHeader()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getHeader()}.
     */
    public void testGetHeader()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#isRowAvailable()}.
     */
    public void testIsRowAvailable()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getRowCount()}.
     */
    public void testGetRowCount()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getRowData()}.
     */
    public void testGetRowData()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getRowIndex()}.
     */
    public void testGetRowIndex()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setRowIndex(int)}.
     */
    public void testSetRowIndex()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getDataModel()}.
     */
    public void testGetDataModel()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setDataModel(javax.faces.model.DataModel)}.
     */
    public void testSetDataModel()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setValue(java.lang.Object)}.
     */
    public void testSetValue()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setRows(int)}.
     */
    public void testSetRows()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setFirst(int)}.
     */
    public void testSetFirst()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getValue()}.
     */
    public void testGetValue()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getVar()}.
     */
    public void testGetVar()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#setVar(java.lang.String)}.
     */
    public void testSetVar()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getRows()}.
     */
    public void testGetRows()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getFirst()}.
     */
    public void testGetFirst()
    {
        // TODO
    }

    /**
     * Test method for {@link javax.faces.component.UIData#getFamily()}.
     */
    public void testGetFamily()
    {
        // TODO
    }

}
