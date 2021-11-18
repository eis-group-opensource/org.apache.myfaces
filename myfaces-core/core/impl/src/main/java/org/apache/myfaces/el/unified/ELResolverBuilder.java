/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import javax.el.CompositeELResolver;
import javax.faces.application.Application;

/**
 * The ELResolverBuilder is responsible to build the el resolver which is used by the application through
 * {@link Application#getELResolver()} according to 1.2 spec section 5.6.2 or to be used as the el resolver for jsp
 * according to 1.2 spec section 5.6.1
 * 
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511514 $ $Date: 2007-02-25 15:47:48 +0200 (Sun, 25 Feb 2007) $
 */
public interface ELResolverBuilder
{
    void build(CompositeELResolver elResolver);
}
