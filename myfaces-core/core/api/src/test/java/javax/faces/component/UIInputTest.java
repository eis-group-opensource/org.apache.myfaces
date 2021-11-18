/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import static org.easymock.EasyMock.*;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import junit.framework.Test;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.el.MockValueExpression;

public class UIInputTest extends AbstractJsfTestCase{
    
    private Converter mockConverter;
    private Validator mockValidator;
    private UIInput input;

    public UIInputTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        input = new UIInput();
        input.setId("testId");
        mockConverter = createMock(Converter.class);
        mockValidator = createMock(Validator.class);
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
        input = null;
        mockConverter = null;
        mockValidator = null;
    }

    public void testWhenSpecifiedConverterMessageIsUsedInCaseConverterExceptionOccurs() {
        input.setConverterMessage("Cannot convert");
        
        input.setConverter(mockConverter);
        expect(mockConverter.getAsObject(facesContext, input, "xxx")).andThrow(new ConverterException());
        replay(mockConverter);
        
        input.getConvertedValue(facesContext, "xxx");
        verify(mockConverter);
        
        assertFalse(input.isValid());
        assertNotNull(facesContext.getMessages("testId"));
        
        FacesMessage message = (FacesMessage) facesContext.getMessages("testId").next();
        assertEquals(message.getDetail(), "Cannot convert");
        assertEquals(message.getSummary(), "Cannot convert");
    }
    
    public void testWhenSpecifiedValidatorMessageIsUsedInCaseValidatorExceptionOccurs() {
        input.setValidatorMessage("Cannot validate");
        
        input.addValidator(mockValidator);
        mockValidator.validate(facesContext, input, "xxx");
        expectLastCall().andThrow(new ValidatorException(new FacesMessage()));
        replay(mockValidator);
        
        input.validateValue(facesContext, "xxx");
        verify(mockValidator);
        
        assertFalse(input.isValid());
        assertNotNull(facesContext.getMessages("testId"));
        
        FacesMessage message = (FacesMessage) facesContext.getMessages("testId").next();
        assertEquals(message.getDetail(), "Cannot validate");
        assertEquals(message.getSummary(), "Cannot validate");
    }
    
    public void testUpdateModelSetsTheLocalValueToModelValue() {
        input.setValue("testValue");
        
        ValueExpression expression = new MockValueExpression("#{requestScope.id}",String.class);
        input.setValueExpression("value", expression);
        
        input.updateModel(facesContext);
        
        String updatedValue = expression.getValue(facesContext.getELContext()).toString();
        assertEquals("testValue", updatedValue);
    }
}
