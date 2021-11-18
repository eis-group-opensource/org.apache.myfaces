/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import java.util.Comparator;

import javax.el.ELResolver;

/**
 * Comparator for ELResolvers that shifts the Resolvers from
 * the faces-config to the back.
 * 
 * @author Jakob Korherr (latest modification by $Author: jakobk $)
 * @version $Revision: 986159 $ $Date: 2010-08-17 02:18:37 +0300 (Tue, 17 Aug 2010) $
 *
 * @since 1.2.10, 2.0.2
 */
public class CustomLastELResolverComparator implements Comparator<ELResolver>
{

    private CustomFirstELResolverComparator _inverseResolver
            = new CustomFirstELResolverComparator();
    
    public int compare(ELResolver r1, ELResolver r2)
    {
        return _inverseResolver.compare(r2, r1);
    }

}
