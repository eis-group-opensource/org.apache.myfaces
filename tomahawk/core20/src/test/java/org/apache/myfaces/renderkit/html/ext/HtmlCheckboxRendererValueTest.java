/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UISelectItem;

import org.apache.myfaces.component.html.ext.HtmlSelectManyCheckbox;
import org.apache.myfaces.test.base.AbstractJsfTestCase;
import org.apache.myfaces.test.el.MockValueExpression;
import org.apache.myfaces.test.mock.MockResponseWriter;

/**
 * Test cases for HtmlCheckboxRenderer.
 * 
 * @author Jakob Korherr (latest modification by $Author: lu4242 $)
 * @version $Revision: 963899 $ $Date: 2010-07-14 01:57:38 +0300 (Wed, 14 Jul 2010) $
 */
public class HtmlCheckboxRendererValueTest extends AbstractJsfTestCase
{

    private HtmlCheckboxRenderer _renderer;
    private MockResponseWriter _writer;
    private StringWriter _stringWriter;
    
    public HtmlCheckboxRendererValueTest(String name)
    {
        super(name);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        _renderer = new HtmlCheckboxRenderer();
        _stringWriter = new StringWriter();
        _writer = new MockResponseWriter(_stringWriter, "text/html", "utf-8");
        
        facesContext.setResponseWriter(_writer);
    }

    @Override
    protected void tearDown() throws Exception
    {
        _renderer = null;
        _stringWriter = null;
        _writer = null;
        
        super.tearDown();
    }

    @SuppressWarnings("unchecked")
    public void testValueTypeRender() throws IOException
    {
        MockBean bean = new MockBean();
        externalContext.getApplicationMap().put("bean", bean);
        ValueExpression beanVE = new MockValueExpression("#{bean.values}", Object.class);
        
        // create UISelectMany component
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setValueExpression("value", beanVE);
        selectMany.setValueType(Integer.class.getName());
        
        // create the select item
        UISelectItem item = new UISelectItem();
        item.setItemValue("1");
        selectMany.getChildren().add(item);
        
        // register the converter
        application.addConverter(Integer.class, MockIntegerConverter.class.getName());
        
        // Render the component (only encodeEnd is used in this renderer)
        _renderer.encodeEnd(facesContext, selectMany);
        final String output = _stringWriter.toString();
        
        // we expect a rendered value of 11, because the Converter adds 10 to
        // the given value in getAsString(). Thus we verify that the converter was called.
        assertTrue(output.contains("value=\"11\""));
    }
    
    @SuppressWarnings({ "unchecked", "serial" })
    public void testValueTypeSubmit() throws IOException
    {
        MockBean bean = new MockBean();
        externalContext.getApplicationMap().put("bean", bean);
        ValueExpression beanVE = new MockValueExpression("#{bean.values}", Object.class)
        {

            @Override
            public Class getType(ELContext context)
            {
                // to simulate the behavior when a bean property has a null value,
                // but the getter has a return value of Collection
                return Collection.class;
            }
              
        };
        
        // create UISelectMany component
        HtmlSelectManyCheckbox selectMany = new HtmlSelectManyCheckbox();
        selectMany.setValueExpression("value", beanVE);
        selectMany.setValueType(Integer.class.getName());
        
        // create the select item
        UISelectItem item = new UISelectItem();
        item.setItemValue("1");
        selectMany.getChildren().add(item);
        
        // get the converted value
        Object convertedValue = _renderer.getConvertedValue(facesContext, selectMany, new String[] {"1"});
        
        // the value must be a Collection
        assertTrue(convertedValue instanceof Collection);
        
        // the first element of the Collection must be the _Integer_ 1
        // (without the valueType attribute it would be the String "1")
        assertEquals(1, ((Collection) convertedValue).iterator().next());
    }
    
}
