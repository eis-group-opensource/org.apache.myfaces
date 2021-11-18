/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.el.ValueBinding;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shale.test.mock.MockFacesContext12;
import static org.easymock.EasyMock.*;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class AbstractUIComponentPropertyTest<T extends Object>
{
    private final String _property;
    private final T _defaultValue;
    private final T[] _testValues;

    private IMocksControl _mocksControl;
    private MockFacesContext12 _facesContext;
    private ValueBinding _valueBinding;
    private ValueExpression _valueExpression;
    private ELContext _elContext;
    private UIComponent _component;

    public AbstractUIComponentPropertyTest(String property, T defaultValue, T... testValues)
    {
        _property = property;
        _defaultValue = defaultValue;
        _testValues = testValues;
    }

    @BeforeMethod
    protected void setUp() throws Exception
    {
        _mocksControl = EasyMock.createControl();
        _facesContext = new MockFacesContext12();
        _elContext = _mocksControl.createMock(ELContext.class);
        _facesContext.setELContext(_elContext);
        _valueBinding = _mocksControl.createMock(ValueBinding.class);
        _valueExpression = _mocksControl.createMock(ValueExpression.class);
        _component = createComponent();
    }
    
    protected IMocksControl getMocksControl()
    {
        return _mocksControl;
    }

    protected abstract UIComponent createComponent();

    @Test
    public void testDefaultValue() throws Exception
    {
        assertEquals(_defaultValue, PropertyUtils.getProperty(_component, _property));
    }

    @Test
    public void testExplicitValue() throws Exception
    {
        for (T testValue : _testValues)
        {
            PropertyUtils.setProperty(_component, _property, testValue);
            assertEquals(testValue, PropertyUtils.getProperty(_component, _property));
        }
    }

    @Test
    public void testExpressionWithLiteralTextValue() throws Exception
    {
        for (T testValue : _testValues)
        {
            expect(_valueExpression.isLiteralText()).andReturn(true);
            expect(_valueExpression.getValue(eq(_facesContext.getELContext()))).andReturn(testValue);
            _mocksControl.replay();
            _component.setValueExpression(_property, _valueExpression);
            assertEquals(testValue, PropertyUtils.getProperty(_component, _property));
            _mocksControl.reset();
        }
    }

    @Test
    public void testExpressionValue() throws Exception
    {
        for (T testValue : _testValues)
        {
            expect(_valueExpression.isLiteralText()).andReturn(false);
            _mocksControl.replay();
            _component.setValueExpression(_property, _valueExpression);
            _mocksControl.reset();
            expect(_valueExpression.getValue(eq(_facesContext.getELContext()))).andReturn(testValue);
            _mocksControl.replay();
            assertEquals(testValue, PropertyUtils.getProperty(_component, _property));
            _mocksControl.reset();
        }
    }
}
