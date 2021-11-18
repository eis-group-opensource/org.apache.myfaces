/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.render;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class Renderer
{
    public void decode(FacesContext context,
                       UIComponent component)
    {
        if (context == null) throw new NullPointerException("context");
        if (component == null) throw new NullPointerException("component");
    }

    public void encodeBegin(FacesContext context,
                            UIComponent component)
            throws IOException
    {
        if (context == null) throw new NullPointerException("context");
        if (component == null) throw new NullPointerException("component");
    }

    /**Render all children if there are any.
     * 
     * Note: this will only be called if getRendersChildren()
     * returns true. A component which has a renderer with
     * getRendersChildren() set to true will typically contain
     * the rendering logic for its children in this method.
     * 
     * @param context
     * @param component
     * @throws IOException
     */
    public void encodeChildren(FacesContext context,
                               UIComponent component)
            throws IOException {
        if (context == null) throw new NullPointerException("context");
        if (component == null) throw new NullPointerException("component");
        
        if(component.getChildCount()>0) {
          for (UIComponent child : component.getChildren()) {
              if (!child.isRendered()) {
                  continue;
              }
  
              child.encodeAll(context);
          }
        }
    }

    public void encodeEnd(FacesContext context,
                          UIComponent component)
            throws IOException 
    {
        if (context == null) throw new NullPointerException("context");
        if (component == null) throw new NullPointerException("component");
    }

    public String convertClientId(FacesContext context,
                                  String clientId) 
    {
        if (context == null) throw new NullPointerException("context");
        if (clientId == null) throw new NullPointerException("clientId");
        return clientId;
    }

    /**Switch for deciding who renders the children.
     * 
     * @return <b>true</b> - if the component takes care of rendering its
     * children. In this case, encodeChildren() ought to be called
     * by the rendering controller (e.g., the rendering controller
     * could be the method encodeAll() in UIComponent). 
     * In the method encodeChildren(), the component
     * should therefore provide all children encode logic.
     * <br/>
     * <b>false</b> - if the component does not take care of rendering its
     * children. In this case, encodeChildren() should not be called
     * by the rendering controller. Instead, the children-list should
     * be retrieved and the children should directly be rendered by
     * the rendering controller one by one.
     */
    public boolean getRendersChildren()
    {
        return false;
    }

    public Object getConvertedValue(FacesContext context,
                                    UIComponent component,
                                    Object submittedValue)
            throws ConverterException
    {
        if (context == null) throw new NullPointerException("context");
        if (component == null) throw new NullPointerException("component");
        return submittedValue;
    }

}
