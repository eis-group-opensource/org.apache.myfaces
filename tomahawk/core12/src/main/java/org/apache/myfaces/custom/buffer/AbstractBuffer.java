/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.buffer;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperties;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspProperty;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * A component that renders its child components into an in-memory buffer rather than
 * render them directly to the response stream.
 * <p>
 * Property "into" is an EL expression that specifies where to store a String holding
 * the results of rendering all the children of this component; this is assigned to
 * after rendering of this component (and its children) is complete.
 * </p>
 * <p>
 * Typically, an h:output tag is then used later in the same page to output the buffer
 * contents.
 * </p>
 * <p>
 * This can be useful with JSF1.1/JSP2.0 to work around the well-known problem where
 * on first render of a page, a component "A" cannot reference a component "B" which is
 * defined later in the page because it has not yet been created. A solution is to define
 * "B" before "A", but wrapped in a Buffer component. Component A can then be rendered
 * and successfully reference "B" because it now exists. And later in the page, the buffer
 * contents can then be output, preserving the original layout.
 * </p>
 * <p>
 * This can also be useful when rendering the same data block multiple times within a page.
 * For example, a datatable can be rendered with a datascroller both before and after it;
 * first render the table into a buffer B1, then render the datascroller into a buffer B2,
 * then output buffers B2,B1,B2.
 * </p>
 * 
 * @since 1.1.7
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 691871 $ $Date: 2008-09-04 07:32:08 +0300 (Thu, 04 Sep 2008) $
 */
@JSFComponent(
        name = "t:buffer",
        clazz = "org.apache.myfaces.custom.buffer.Buffer",
        tagClass = "org.apache.myfaces.custom.buffer.BufferTag")
@JSFJspProperties(properties={
        @JSFJspProperty(
                name = "rendered",
                returnType = "boolean", 
                tagExcluded = true),
        @JSFJspProperty(
                name = "binding",
                returnType = "java.lang.String",
                tagExcluded = true),
        @JSFJspProperty(
                name = "id",
                returnType = "java.lang.String",
                tagExcluded = true)
                })
public abstract class AbstractBuffer extends UIComponentBase{

    public static final String COMPONENT_TYPE = "org.apache.myfaces.Buffer";
    public static final String COMPONENT_FAMILY = "javax.faces.Data";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Buffer";

    protected abstract String getLocalInto();
    
    public abstract void setInto(String into);

    void fill(String content, FacesContext facesContext){
        ValueExpression intoVB;

        if (getLocalInto() == null) {
            intoVB = getValueExpression("into");
            setInto(intoVB.getExpressionString());
        } else {
            intoVB = facesContext.getApplication().
            getExpressionFactory().createValueExpression(
                    facesContext.getELContext(), getLocalInto(), Object.class );
        }

        intoVB.setValue(facesContext.getELContext(), content);
    }
    
    /**
     * An EL expression that specifies where to store a String holding 
     * the results of rendering all the children of this component; 
     * this is assigned to after rendering of this component (and its 
     * children) is complete.
     * 
     */
    @JSFProperty(
            required = true,
            localMethod = true)
    protected abstract String getInto(); 

}
