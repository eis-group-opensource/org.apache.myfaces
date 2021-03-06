/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.test.utils;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.shale.test.mock.MockResponseWriter;

/**
 * This is a utility class used in unit test cases to check if
 * a component's attributes are rendered properly.
 */
public class HtmlCheckAttributesUtil 
{
    /**
     * This method adds all elements of attrs to the attributes map of component.
     * @param component The component to add the attributes to.
     * @param attrs     The attributes to be added to the component.
     */
    public static void addBaseAttributes(UIComponent component, HtmlRenderedAttr[] attrs) 
    {
        Map map = component.getAttributes();
        for(int i = 0; i < attrs.length; i++) 
        {
            HtmlRenderedAttr attr = attrs[i];
            map.put(attr.getName(), attr.getValue());
        }
    }
    
    /**
     * Iterates through all elements of attrs to check if they are only rendered once in output.
     * @param attrs      The attributes to be checked.
     * @param output     The html output of the component's renderer.
     * @throws Exception
     */
    public static void checkRenderedAttributes(HtmlRenderedAttr[] attrs, String output) throws Exception 
    {
        for(int i = 0; i < attrs.length; i++) 
        {
            //assertContainsOnlyOnce(attrs[i], output);
            checkAttributeOccurrences(attrs[i], output);
        }
    }
    
    /**
     * This method adds all attributes from attrs into the component.  After adding the attributes,
     * it calls the encodeAll() method of the component.  The html generated from the component's
     * renderer will be checked to see if the attributes have been rendered correctly.
     * @param component  The component whose attributes will be tested.
     * @param context    
     * @param writer     The ResponseWriter used by the renderer to output the html generated.
     * @param attrs      An array of attributes which will be tested.
     * @throws Exception
     */
    public static void checkRenderedAttributes(
            UIComponent component, 
            FacesContext context, 
            MockResponseWriter writer,
            HtmlRenderedAttr[] attrs) throws Exception 
    {
        
        addBaseAttributes(component, attrs);
        component.encodeBegin(context);
        component.encodeChildren(context);
        component.encodeEnd(context);
        context.renderResponse();
        checkRenderedAttributes(attrs, writer.getWriter().toString());
    }
    
    /**
     * Checks the attrs array if it has elements which were rendered incorrectly.
     * @param attrs The attributes to be checked.
     * @return True if there are attributes not rendered correctly.
     */
    public static boolean hasFailedAttrRender(HtmlRenderedAttr[] attrs) 
    {
        for(int i = 0; i < attrs.length; i++) 
        {
            if(!attrs[i].isRenderSuccessful()) return true;
        }
        return false;
    }
    
    /**
     * Constructs an error message string detailing which attributes were not rendered
     * and which attributes were rendered more than once.
     * @param attrs   The attributes to be tested.
     * @param actual  The html generated by the renderer.
     * @return The error message.
     */
    public static String constructErrorMessage(HtmlRenderedAttr[] attrs, String actual) 
    {
        StringBuffer messgBuffer = new StringBuffer();
        for(int i = 0; i < attrs.length; i++) 
        {
            if(attrs[i].getErrorCode() == HtmlRenderedAttr.RENDERED_MORE_TIMES_THAN_EXPECTED) 
            {
                messgBuffer.append(attrs[i].getName()).append(" (");
                messgBuffer.append(attrs[i].getExpectedHtml()).append(") was rendered more times (");
                messgBuffer.append(attrs[i].getActualOccurrences()).append(") than expected (");
                messgBuffer.append(attrs[i].getExpectedOccurrences()).append(").");
                messgBuffer.append(System.getProperty("line.separator"));
            } 
            else if(attrs[i].getErrorCode() == HtmlRenderedAttr.RENDERED_LESS_TIMES_THAN_EXPECTED)
            {
                messgBuffer.append(attrs[i].getName()).append(" (");
                messgBuffer.append(attrs[i].getExpectedHtml()).append(") was rendered less times (");
                messgBuffer.append(attrs[i].getActualOccurrences()).append(") than expected (");
                messgBuffer.append(attrs[i].getExpectedOccurrences()).append(").");
                messgBuffer.append(System.getProperty("line.separator"));
            }
        }
        messgBuffer.append("Actual HTML: ").append(actual);
        return messgBuffer.toString();
    }
    
    /**
     * Checks if the occurrence of the rendered attribute in the html
     * generated by the renderer is equal to the number of times expected.
     * @param attr   The attribute to be tested.
     * @param actual The html generated by the renderer.
     */
    public static void checkAttributeOccurrences(HtmlRenderedAttr attr, String actual)
    {
        String expectedHtml = attr.getExpectedHtml();
        
        int index;
        int offset = 0;
        while((index=actual.indexOf(expectedHtml,offset)) != -1) 
        {
            attr.increaseActualOccurrences();
            if(attr.getActualOccurrences() > attr.getExpectedOccurrences()) 
            {
                attr.setErrorCode(HtmlRenderedAttr.RENDERED_MORE_TIMES_THAN_EXPECTED);
                return;
            } 

            offset += index + expectedHtml.length();
        }
        
        if(attr.getActualOccurrences() < attr.getExpectedOccurrences()) 
        {
            attr.setErrorCode(HtmlRenderedAttr.RENDERED_LESS_TIMES_THAN_EXPECTED);
        } 
        else 
        {
            attr.setRenderSuccessful(true);
        }
    }
    
    public static HtmlRenderedAttr[] generateBasicAttrs() {
        HtmlRenderedAttr[] attrs = {
            //_AccesskeyProperty
            new HtmlRenderedAttr("accesskey"),
            //_UniversalProperties
            new HtmlRenderedAttr("dir"), 
            new HtmlRenderedAttr("lang"), 
            new HtmlRenderedAttr("title"),
            //_FocusBlurProperties
            new HtmlRenderedAttr("onfocus"), 
            new HtmlRenderedAttr("onblur"),
            //_ChangeSelectProperties
            new HtmlRenderedAttr("onchange"), 
            new HtmlRenderedAttr("onselect"),
            //_EventProperties
            new HtmlRenderedAttr("onclick"), 
            new HtmlRenderedAttr("ondblclick"), 
            new HtmlRenderedAttr("onkeydown"), 
            new HtmlRenderedAttr("onkeypress"),
            new HtmlRenderedAttr("onkeyup"), 
            new HtmlRenderedAttr("onmousedown"), 
            new HtmlRenderedAttr("onmousemove"), 
            new HtmlRenderedAttr("onmouseout"),
            new HtmlRenderedAttr("onmouseover"), 
            new HtmlRenderedAttr("onmouseup"),
            //_StyleProperties
            new HtmlRenderedAttr("style"), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
            //_TabindexProperty
            new HtmlRenderedAttr("tabindex")
        };
        
        return attrs;
    }
    
    public static HtmlRenderedAttr[] generateAttrsNotRenderedForReadOnly() 
    {
        HtmlRenderedAttr[] attrs = {
            //_AccesskeyProperty
            new HtmlRenderedAttr("accesskey", 0),
            //_FocusBlurProperties
            new HtmlRenderedAttr("onfocus", 0), 
            new HtmlRenderedAttr("onblur", 0),
            //_ChangeSelectProperties
            new HtmlRenderedAttr("onchange", 0), 
            new HtmlRenderedAttr("onselect", 0),
            //_TabindexProperty
            new HtmlRenderedAttr("tabindex", 0)
        };
        return attrs;
    }
    
    public static HtmlRenderedAttr[] generateBasicReadOnlyAttrs() {
        HtmlRenderedAttr[] attrs = {
            //_UniversalProperties
            new HtmlRenderedAttr("dir"), 
            new HtmlRenderedAttr("lang"), 
            new HtmlRenderedAttr("title"),
            //_EventProperties
            new HtmlRenderedAttr("onclick"), 
            new HtmlRenderedAttr("ondblclick"), 
            new HtmlRenderedAttr("onkeydown"), 
            new HtmlRenderedAttr("onkeypress"),
            new HtmlRenderedAttr("onkeyup"), 
            new HtmlRenderedAttr("onmousedown"), 
            new HtmlRenderedAttr("onmousemove"), 
            new HtmlRenderedAttr("onmouseout"),
            new HtmlRenderedAttr("onmouseover"), 
            new HtmlRenderedAttr("onmouseup"),
            //_StyleProperties
            new HtmlRenderedAttr("style"), 
            new HtmlRenderedAttr("styleClass", "styleClass", "class=\"styleClass\""),
        };
        
        return attrs;
    }
}
