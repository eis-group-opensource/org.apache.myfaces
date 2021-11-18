/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

/**
 * Tag handler used in facelets
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 *
 */
public class AliasBeansScopeTagHandler extends ComponentHandler
{

    public AliasBeansScopeTagHandler(ComponentConfig tagConfig)
    {
        super(tagConfig);
    }
    
    protected void applyNextHandler(FaceletContext ctx, UIComponent c)
            throws IOException, FacesException, ELException
    {
        AliasBeansScope aliasBean = (AliasBeansScope) c;              
        aliasBean.makeAliases(ctx.getFacesContext());
        super.applyNextHandler(ctx, c);
        aliasBean.removeAliases(ctx.getFacesContext());
    }
}
