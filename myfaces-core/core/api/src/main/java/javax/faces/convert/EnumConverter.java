/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFConverter;

/**
 * see Javadoc of <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
@JSFConverter
public class EnumConverter implements Converter, StateHolder {
    
    public static final String CONVERTER_ID = "javax.faces.Enum";
    public static final String ENUM_ID = "javax.faces.converter.EnumConverter.ENUM";
    public static final String ENUM_NO_CLASS_ID = "javax.faces.converter.EnumConverter.ENUM_NO_CLASS";
    
    private Class targetClass;
    
    private boolean isTransient = false;
    
    /** Creates a new instance of EnumConverter */
    public EnumConverter() {
    }
    
    public EnumConverter(Class targetClass) {
        if (!targetClass.isEnum()) throw new IllegalArgumentException("targetClass for EnumConverter must be an Enum");
        this.targetClass = targetClass;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) throws ConverterException {
        if (facesContext == null) throw new NullPointerException("facesContext can not be null");
        if (uiComponent == null) throw new NullPointerException("uiComponent can not be null");
        if (value == null) return "";
        checkTargetClass(facesContext, uiComponent, value);
        
        for (Object enumConstant : targetClass.getEnumConstants()) {
            if (enumConstant == value) return enumConstant.toString();
        }

        return value.toString();
    }

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) throws ConverterException {
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (uiComponent == null) throw new NullPointerException("uiComponent");
        if (value == null)  return null;
        value = value.trim();
        if (value.length() == 0)  return null;
        checkTargetClass(facesContext, uiComponent, value);
        
        // we know targetClass and value can't be null, so we can use Enum.valueOf
        // instead of the hokey looping called for in the javadoc
        try {
           return Enum.valueOf(targetClass, value);    
        } catch (IllegalArgumentException e) {
            Object[] params = new Object[]{value, 
                                           firstConstantOfEnum(), 
                                           _MessageUtils.getLabel(facesContext, uiComponent)};
            
            throw new ConverterException(_MessageUtils.getErrorMessage(facesContext,
                                                                       ENUM_ID,
                                                                       params));
        }
    }

    private void checkTargetClass(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (targetClass == null) {
            Object[] params = new Object[]{value, _MessageUtils.getLabel(facesContext, uiComponent)};
            throw new ConverterException(_MessageUtils.getErrorMessage(facesContext, 
                                                                       ENUM_NO_CLASS_ID, 
                                                                       params));
        }
    }

    // find the first constant value of the targetClass and return as a String
    private String firstConstantOfEnum() {
        Object[] enumConstants= targetClass.getEnumConstants();

        if (enumConstants.length != 0 ) return enumConstants[0].toString();
        
        return ""; // if empty Enum
    }

    public void restoreState(FacesContext context, Object state) {
        targetClass = (Class)state;
    }

    public Object saveState(FacesContext context) {
        return targetClass;
    }

    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    public boolean isTransient() {
        return isTransient;
    }
    
}
