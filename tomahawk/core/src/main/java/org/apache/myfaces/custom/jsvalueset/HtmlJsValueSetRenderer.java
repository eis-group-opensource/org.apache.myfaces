/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.jsvalueset;

import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;

import org.apache.commons.lang.StringEscapeUtils;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.JsValueSet"
 * 
 * @author Martin Marinschek (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlJsValueSetRenderer
extends HtmlRenderer
{
   private void writeValue(String varName, StringBuffer valueSb, StringBuffer prependSb, Object value) throws IOException 
   {
      if(value instanceof String || value instanceof Number || value instanceof Boolean || value == null) 
      {
         valueSb.append(getValueString(value));
      }
      else if(value instanceof Object[])
      {
         Object[] objs = (Object[]) value;
         
         valueSb.append("[");
         
         String comma = "";
         for (int i = 0; i < objs.length; i++) 
         {
            valueSb.append(comma);
            writeValue(varName + "_h" + i, valueSb, prependSb, objs[i]);
            comma = ",";
         }
         
         valueSb.append("]");
      }
      else if(value instanceof Collection)
      {
         Collection coll = (Collection) value;
         
         valueSb.append("[");
         
         String comma = "";
         int i = 0;
         for (Iterator iterator = coll.iterator(); iterator.hasNext();) 
         {
            valueSb.append(comma);
            writeValue(varName + "_h" + i, valueSb, prependSb, iterator.next());
            comma = ",";
            i++;
         }
         
         valueSb.append("]");
      }
      else if(value instanceof Map)
      {
         Map map = (Map) value;
         
         varName = varName + "_hm";
          prependSb.append("var ").append(varName).append("=new Object();\n");
         
         Iterator it = map.entrySet().iterator();
         int i = 0;
         while (it.hasNext()) 
         {
            Map.Entry entry = (Map.Entry) it.next();
            StringBuffer helpValueSb = new StringBuffer();
            StringBuffer helpHelpSb = new StringBuffer();
            writeValue(varName + "_h" + i, helpValueSb, helpHelpSb, entry.getValue());
            
            prependSb.append(helpHelpSb);
             prependSb.append(varName).append("[").append(getValueString(entry.getKey())).append("]=");
             prependSb.append(helpValueSb).append(";\n");
            
            i++;
         }
         
         valueSb.append(varName);
      }
      else
         throw new IOException("Type of value not handled. Allowed are String, Number, Boolean, Object[], Collection, Map. Type of value : "+value.getClass());
      
   }
   
   public void encodeEnd(FacesContext facesContext, UIComponent component)
   throws IOException
   {
      RendererUtils.checkParamValidity(facesContext, component, HtmlJsValueSet.class);
      
      HtmlJsValueSet jsValue = (HtmlJsValueSet) component;
      
      Object value = jsValue.getValue();
      String varName = jsValue.getName();
      
      
      ResponseWriter writer = getFacesContext().getResponseWriter();
      
      writer.startElement(HTML.SCRIPT_ELEM, null);
      writer.writeAttribute(HTML.SCRIPT_TYPE_ATTR, HTML.SCRIPT_TYPE_TEXT_JAVASCRIPT, null);
      
      StringBuffer valueSb = new StringBuffer("var " + varName + "=");
      StringBuffer prependSb = new StringBuffer("");
      
      writeValue(varName, valueSb, prependSb, value);
      
      writer.write(prependSb.toString());
      writer.write(valueSb.toString() + ";\n");
      
      writer.endElement(HTML.SCRIPT_ELEM);
   }
   
   
   private String getValueString(Object value) throws IOException
   {
      if(value instanceof String)
      {
         return "'"+StringEscapeUtils.escapeJavaScript(value.toString())+"'";
      }
      else if(value instanceof Number)
      {
         return value.toString();
      }
      else if(value instanceof Boolean)
      {
         return value.toString();
      }
      else if(value == null)
      {
         return "null";
      }
      else
      {
         throw new IOException("value : "+value+" is of type : "+value.getClass()+
         ", provide a method to convert this.");
      }
   }
   
   protected Application getApplication()
   {
      return getFacesContext().getApplication();
   }
   
   protected FacesContext getFacesContext()
   {
      return FacesContext.getCurrentInstance();
   }
   
}
