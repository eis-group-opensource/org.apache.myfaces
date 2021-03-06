/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import static org.apache.myfaces.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.Date;

import javax.el.ELContext;
import javax.el.ELException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.ValueBinding;

import junit.framework.TestCase;

import org.apache.myfaces.TestRunner;
import org.apache.myfaces.mock.ExceptionMockRunner;
import org.apache.shale.test.mock.MockFacesContext12;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 518520 $ $Date: 2007-03-15 10:48:51 +0200 (Thu, 15 Mar 2007) $
 */
@SuppressWarnings("deprecation")
public class _ValueBindingToValueExpressionTest extends TestCase
{

    private _ValueBindingToValueExpression testimpl;
    private ValueBinding binding;
    private IMocksControl mockControl;
    private FacesContext facesContext;
    private ELContext elContext;

    @Override
    protected void setUp() throws Exception
    {
        mockControl = EasyMock.createControl();
        binding = mockControl.createMock(ValueBinding.class);
        facesContext = mockControl.createMock(FacesContext.class);
        elContext = mockControl.createMock(ELContext.class);
        testimpl = new _ValueBindingToValueExpression(binding);
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#hashCode()}.
     */
    public void testHashCode()
    {
        assertEquals(testimpl.hashCode(), testimpl.hashCode());
        _ValueBindingToValueExpression other = new _ValueBindingToValueExpression(binding);
        assertEquals(testimpl.hashCode(), other.hashCode());
        other.setTransient(true);
        assertFalse(testimpl.hashCode() == other.hashCode());
        assertFalse(testimpl.hashCode() == mockControl.createMock(ValueBinding.class).hashCode());
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#equals(java.lang.Object)}.
     */
    public void testEqualsObject()
    {
        assertEquals(testimpl, testimpl);
        _ValueBindingToValueExpression other = new _ValueBindingToValueExpression(binding);
        assertEquals(testimpl, other);
        other.setTransient(true);
        assertFalse(testimpl.equals(other));
        assertFalse(testimpl.equals(mockControl.createMock(ValueBinding.class)));
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#isLiteralText()}.
     */
    public void testIsLiteralText()
    {
        mockControl.replay();
        assertFalse(testimpl.isLiteralText());
        mockControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#ValueBindingToValueExpression()}.
     */
    public void testValueBindingToValueExpression()
    {
        testimpl = new _ValueBindingToValueExpression();
        assertNull(testimpl.getValueBinding());
        assertNull(testimpl.getExpectedType());
        assertException(IllegalStateException.class, new TestRunner()
        {
            public void run() throws Throwable
            {
                testimpl.getExpressionString();
                testimpl.getType(elContext);
                testimpl.getValue(elContext);
            }
        });
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#ValueBindingToValueExpression(javax.faces.el.ValueBinding)}.
     */
    public void testValueBindingToValueExpressionValueBinding()
    {
        assertEquals(binding, testimpl.getValueBinding());
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#isReadOnly(javax.el.ELContext)}.
     */
    public void testIsReadOnlyELContext()
    {
        expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
        expect(binding.isReadOnly(eq(facesContext))).andReturn(false);
        mockControl.replay();
        assertEquals(false, testimpl.isReadOnly(elContext));
        mockControl.verify();
        mockControl.reset();
        expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
        expect(binding.isReadOnly(eq(facesContext))).andReturn(true);
        mockControl.replay();
        assertEquals(true, testimpl.isReadOnly(elContext));
        mockControl.verify();
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#getValue(javax.el.ELContext)}.
     */
    public void testGetValueELContext()
    {
        expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
        Object expectedValue = new Object();
        expect(binding.getValue(eq(facesContext))).andReturn(expectedValue);
        mockControl.replay();
        assertEquals(expectedValue, testimpl.getValue(elContext));
        mockControl.verify();
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#getType(javax.el.ELContext)}.
     */
    public void testGetTypeELContext()
    {
        expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
        Class expectedType = Date.class;
        expect(binding.getType(eq(facesContext))).andReturn(expectedType);
        mockControl.replay();
        assertEquals(expectedType, testimpl.getType(elContext));
        mockControl.verify();
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#getType(javax.el.ELContext)}.
     */
    public void testGetTypeELContextExceptions() throws Exception
    {
        class GetTypeExceptionMockRunner extends ExceptionMockRunner
        {
            GetTypeExceptionMockRunner(Throwable exception)
            {
                super(mockControl, exception);
            }

            @Override
            protected void setUp(IMocksControl mockControl, Throwable exceptionToThrow)
            {
                expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
                expect(binding.getType(eq(facesContext))).andThrow(exceptionToThrow);
            }

            @Override
            protected void run(IMocksControl mockControl) throws Exception
            {
                testimpl.getType(elContext);
            }
        }
        assertException(ELException.class, new GetTypeExceptionMockRunner(new EvaluationException()));
        mockControl.reset();
        assertException(javax.el.PropertyNotFoundException.class, new GetTypeExceptionMockRunner(
                new javax.faces.el.PropertyNotFoundException()));
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#setValue(javax.el.ELContext, java.lang.Object)}.
     */
    public void testSetValueELContextObject()
    {
        expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
        Object expectedValue = new Object();
        binding.setValue(eq(facesContext), eq(expectedValue));
        mockControl.replay();
        testimpl.setValue(elContext, expectedValue);
        mockControl.verify();
    }

    /**
     * Test method for
     * {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#setValue(javax.el.ELContext, java.lang.Object)}.
     */
    public void testSetValueELContextObjectExceptions() throws Exception
    {
        final Object expectedValue = new Object();
        class SetValueExceptionMockRunner extends ExceptionMockRunner
        {
            SetValueExceptionMockRunner(Throwable exception)
            {
                super(mockControl, exception);
            }

            @Override
            protected void setUp(IMocksControl mockControl, Throwable exceptionToThrow)
            {
                expect(elContext.getContext(eq(FacesContext.class))).andReturn(facesContext);
                binding.setValue(eq(facesContext), eq(expectedValue));
                expectLastCall().andThrow(exceptionToThrow);
            }

            @Override
            protected void run(IMocksControl mockControl) throws Exception
            {
                testimpl.setValue(elContext, expectedValue);
            }
        }
        assertException(ELException.class, new SetValueExceptionMockRunner(new EvaluationException()));
        mockControl.reset();
        assertException(javax.el.PropertyNotFoundException.class, new SetValueExceptionMockRunner(
                new javax.faces.el.PropertyNotFoundException()));
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#getExpressionString()}.
     */
    public void testGetExpressionString()
    {
        String expectedValue = "xxx";
        expect(binding.getExpressionString()).andReturn(expectedValue);
        mockControl.replay();
        assertEquals(expectedValue, testimpl.getExpressionString());
        mockControl.verify();
    }

    /**
     * Test method for {@link org.apache.myfaces.el.convert.ValueBindingToValueExpression#getExpectedType()}.
     */
    public void testGetExpectedType()
    {
        Object expectedValue = new Date();
        facesContext = new MockFacesContext12();
        expect(binding.getValue(eq(facesContext))).andReturn(expectedValue);
        mockControl.replay();
        assertEquals(Date.class, testimpl.getExpectedType());
        mockControl.verify();
    }

}
