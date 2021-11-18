/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.autoscroll;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.BehaviorHandler;
import javax.faces.view.facelets.FaceletContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

/**
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
@JSFFaceletTag(name="t:autoScroll", behaviorClass="org.apache.myfaces.custom.autoscroll.AutoscrollBehavior")
public class AutoscrollBehaviorTagHandler extends BehaviorHandler
{

    public static final String AUTOSCROLL_TAG_ON_PAGE = "oam.autoscroll.AUTOSCROLL_TAG_ON_PAGE";
    
    public AutoscrollBehaviorTagHandler(BehaviorConfig config)
    {
        super(config);
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException
    {
        super.apply(ctx, parent);
        
        UIViewRoot root = getViewRoot(ctx, parent);
        
        if (!root.getAttributes().containsKey(AUTOSCROLL_TAG_ON_PAGE))
        {
            root.getAttributes().put(AUTOSCROLL_TAG_ON_PAGE, Boolean.TRUE);
        }
    }
    
    private UIViewRoot getViewRoot(FaceletContext ctx, UIComponent parent)
    {
        UIComponent c = parent;
        do
        {
            if (c instanceof UIViewRoot)
            {
                return (UIViewRoot) c;
            }
            else
            {
                c = c.getParent();
            }
        } while (c != null);

        return ctx.getFacesContext().getViewRoot();
    }
}
