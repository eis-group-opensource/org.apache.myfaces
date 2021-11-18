/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import java.util.Comparator;
import java.util.List;

import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.config.RuntimeConfig;

/**
 * Comparator for ELResolvers that shifts the Resolvers from
 * the faces-config to the front.
 * 
 * @author Jakob Korherr (latest modification by $Author: lu4242 $)
 * @version $Revision: 1136229 $ $Date: 2011-06-16 01:38:34 +0300 (Thu, 16 Jun 2011) $
 *
 * @since 1.2.10, 2.0.2
 */
public class CustomFirstELResolverComparator implements Comparator<ELResolver>
{
    
    private List<ELResolver> _facesConfigResolvers;
    
    public int compare(ELResolver r1, ELResolver r2)
    {
        List<ELResolver> facesConfigResolvers = _getFacesConfigElResolvers();
        
        if (facesConfigResolvers == null)
        {
            // no el-resolvers in faces-config
            return 0; // keep order
        }
        
        boolean r1FromFacesConfig = facesConfigResolvers.contains(r1);
        boolean r2FromFacesConfig = facesConfigResolvers.contains(r2);
        
        if (r1FromFacesConfig)
        {
            if (r2FromFacesConfig)
            {
                // both are from faces-config
                return 0; // keep order
            }
            else
            {
                // only r1 is from faces-config
                return -1;
            }
        }
        else
        {
            if (r2FromFacesConfig)
            {
                // only r2 is from faces-config
                return 1;
            }
            else
            {
                // neither r1 nor r2 are from faces-config
                return 0; // keep order
            }
        }
    }
    
    /**
     * Returns a List of all ELResolvers from the faces-config.
     * @return
     */
    private List<ELResolver> _getFacesConfigElResolvers()
    {
        if (_facesConfigResolvers == null)
        {
            ExternalContext externalContext
                    = FacesContext.getCurrentInstance().getExternalContext();
            RuntimeConfig runtimeConfig
                    = RuntimeConfig.getCurrentInstance(externalContext);
            _facesConfigResolvers 
                    = runtimeConfig.getFacesConfigElResolvers();
        }
        
        return _facesConfigResolvers;
    }
    
}
