/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectMany;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * The util methods in this class are shared between the javax.faces.component package and
 * the org.apache.myfaces.renderkit package.
 * Please note: Any changes here must also apply to the class in the other package!
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673827 $ $Date: 2008-07-04 00:46:23 +0300 (Pn, 04 Lie 2008) $
 */
class _SharedRendererUtils
{
    static Converter findUIOutputConverter(FacesContext facesContext, UIOutput component)
    {
        // Attention!
        // This code is duplicated in jsfapi component package.
        // If you change something here please do the same in the other class!

        Converter converter = component.getConverter();
        if (converter != null) return converter;

        //Try to find out by value expression
        ValueExpression expression = component.getValueExpression("value");
        if (expression == null) return null;

        Class valueType = expression.getType(facesContext.getELContext());
        if (valueType == null) return null;

        if (Object.class.equals(valueType)) return null;    //There is no converter for Object class

        try
        {
            return facesContext.getApplication().createConverter(valueType);
        }
        catch (FacesException e)
        {
            log(facesContext, "No Converter for type " + valueType.getName() + " found", e);
            return null;
        }
    }

    static Object getConvertedUISelectManyValue(FacesContext facesContext,
                                                UISelectMany component,
                                                String[] submittedValue)
            throws ConverterException
    {
        // Attention!
        // This code is duplicated in jsfapi component package.
        // If you change something here please do the same in the other class!

        if (submittedValue == null) throw new NullPointerException("submittedValue");

        ValueExpression expression = component.getValueExpression("value");
        Class valueType = null;
        Class arrayComponentType = null;
        if (expression != null)
        {
            //By some strange reason vb.getType(facesContext.getELContext());
            //does not return the same as vb.getValue(facesContext.getELContext()).getClass(),
            //so we need to use this instead.
            Object value = expression.getValue(facesContext.getELContext()); 
            valueType = (value != null) ? value.getClass() :
                expression.getType(facesContext.getELContext()) ;
            
            if (valueType != null && valueType.isArray())
            {
                arrayComponentType = valueType.getComponentType();
            }
        }
        
        Converter converter = component.getConverter();
        if (converter == null)
        {
            if (valueType == null)
            {
                // No converter, and no idea of expected type
                // --> return the submitted String array
                return submittedValue;
            }

            if (List.class.isAssignableFrom(valueType))
            {
                // expected type is a List
                // --> according to javadoc of UISelectMany we assume that the element type
                //     is java.lang.String, and copy the String array to a new List
                int len = submittedValue.length;
                List lst = new ArrayList(len);
                for (int i = 0; i < len; i++)
                {
                    lst.add(submittedValue[i]);
                }
                return lst;
            }

            if (arrayComponentType == null)
            {
                throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
            }

            if (Object.class.equals(arrayComponentType)) return submittedValue; //No conversion for Object class

            converter = facesContext.getApplication().createConverter(arrayComponentType);

            if (converter == null)
            {
                return submittedValue;
            }
        }

        // Now, we have a converter...
        // We determine the type of the component array after converting one of it's elements
        if (expression != null)
        {
            valueType = expression.getType(facesContext.getELContext());
            if (valueType != null && valueType.isArray())
            {
                if (submittedValue.length > 0) 
                {
                    arrayComponentType = converter.getAsObject(facesContext, component, submittedValue[0]).getClass();
                }
            }
        }
        
        if (valueType == null)
        {
            // ...but have no idea of expected type
            // --> so let's convert it to an Object array
            int len = submittedValue.length;
            Object [] convertedValues = (Object []) Array.newInstance(
                    arrayComponentType==null?Object.class:arrayComponentType,len);
            for (int i = 0; i < len; i++)
            {
                convertedValues[i]
                    = converter.getAsObject(facesContext, component, submittedValue[i]);
            }
            return convertedValues;
        }

        if (List.class.isAssignableFrom(valueType))
        {
            // Curious case: According to specs we should assume, that the element type
            // of this List is java.lang.String. But there is a Converter set for this
            // component. Because the user must know what he is doing, we will convert the values.
            int len = submittedValue.length;
            List lst = new ArrayList(len);
            for (int i = 0; i < len; i++)
            {
                lst.add(converter.getAsObject(facesContext, component, submittedValue[i]));
            }
            return lst;
        }

        if (arrayComponentType == null)
        {
            throw new IllegalArgumentException("ValueBinding for UISelectMany must be of type List or Array");
        }

        if (arrayComponentType.isPrimitive())
        {
            //primitive array
            int len = submittedValue.length;
            Object convertedValues = Array.newInstance(arrayComponentType, len);
            for (int i = 0; i < len; i++)
            {
                Array.set(convertedValues, i,
                          converter.getAsObject(facesContext, component, submittedValue[i]));
            }
            return convertedValues;
        }

        //Object array
        int len = submittedValue.length;
        ArrayList convertedValues = new ArrayList(len); 
        for (int i = 0; i < len; i++)
        {
            convertedValues.add(i, converter.getAsObject(facesContext, component, submittedValue[i])); 
        }
        return convertedValues.toArray((Object[]) Array.newInstance(arrayComponentType, len));
        
    }



    private static final Log log = LogFactory.getLog(_SharedRendererUtils.class);

    /**
     * This method is different in the two versions of _SharedRendererUtils.
     */
    private static void log(FacesContext context, String msg, Exception e)
    {
        log.error(msg, e);
    }
}
