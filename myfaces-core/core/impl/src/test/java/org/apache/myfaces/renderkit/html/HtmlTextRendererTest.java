/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html;

import java.io.IOException;
import java.io.StringWriter;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.el.MockValueExpression;
import org.apache.shale.test.mock.MockRenderKitFactory;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * @author Bruno Aranda (latest modification by $Author: grantsmith $)
 * @version $Revision: 472618 $ $Date: 2006-11-09 04:06:54 +0800 (Thu, 09 Nov 2006) $
 */
public class HtmlTextRendererTest extends AbstractJsfTestCase
{

    public static Test suite()
    {
        return new TestSuite(HtmlTextRendererTest.class); // needed in maven
    }

    private MockResponseWriter writer ;
    private HtmlOutputText outputText;
    private HtmlInputText inputText;

    public HtmlTextRendererTest(String name)
    {
        super(name);
    }

    public void setUp() throws Exception
    {
        super.setUp();

        outputText = new HtmlOutputText();
        inputText = new HtmlInputText();

        writer = new MockResponseWriter(new StringWriter(), null, null);
        facesContext.setResponseWriter(writer);
        // TODO remove these two lines once shale-test goes alpha, see MYFACES-1155
        facesContext.getViewRoot().setRenderKitId(MockRenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.getRenderKit().addRenderer(
                outputText.getFamily(),
                outputText.getRendererType(),
                new HtmlTextRenderer());
        facesContext.getRenderKit().addRenderer(
                inputText.getFamily(),
                inputText.getRendererType(),
                new HtmlTextRenderer());
    }

    public void tearDown() throws Exception
    {
        super.tearDown();
        outputText = null;
        inputText = null;
        writer = null;
    }

    public void testStyleClassAttr() throws IOException
    {
        outputText.setValue("Output");
        outputText.setStyleClass("myStyleClass");

        outputText.encodeEnd(facesContext);
        facesContext.renderResponse();

        String output = writer.getWriter().toString();

        assertEquals("<span class=\"myStyleClass\">Output</span>", output);
        assertNotSame("Output", output);
    }

    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        

        HtmlCheckAttributesUtil.checkRenderedAttributes(
                inputText, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testWhenSubmittedValueIsNullDefaultShouldDissapearFromRendering() {
        //See MYFACES-2161 and MYFACES-1549 for details
        UIViewRoot root = new UIViewRoot();
        UIForm form = new UIForm();
        form.setId("formId");
        
        form.getChildren().add(inputText);
        root.getChildren().add(form);
        
        Converter converter = new Converter()
        {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException
            {
                if (value == null || "".equals(value))
                    return null;
                else
                    return value;
            }

            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException
            {
                if (value == null)
                    return "";
                else
                    return value.toString();
            }
        };
        
        inputText.setConverter(converter);
        
        ValueExpression expression = new MockValueExpression("#{requestScope.someDefaultValueOnBean}",String.class);
        expression.setValue(facesContext.getELContext(), "defaultValue");
        inputText.setValueExpression("value", expression);
        
        // 1) user enters an empty string in an input-component: ""
        //Call to setSubmittedValue on HtmlRendererUtils.decodeUIInput(facesContext, component), 
        //that is called from renderer decode()
        facesContext.getExternalContext().getRequestParameterMap().put(inputText.getClientId(facesContext), "");
        
        inputText.decode(facesContext);
        
        // 2) conversion and validation phase: "" --> setValue(null);
        // isLocalValueSet = true; setSubmittedValue(null);
        inputText.validate(facesContext);
        
        // 3) validation fails in some component on the page --> update model
        // phase is skipped
        // No OP
        
        // 4) renderer calls getValue(); --> getValue() evaluates the
        // value-binding, as the local-value is 'null', and I get the
        // default-value of the bean shown again
        assertNotSame(expression.getValue(facesContext.getELContext()), inputText.getValue());
        assertNull(inputText.getValue());
    }

}
