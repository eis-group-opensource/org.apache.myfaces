/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * RenderKitFactory implementation as defined in Spec. JSF.7.3
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class RenderKitFactoryImpl
    extends RenderKitFactory
{
    private static final Log log = LogFactory.getLog(RenderKitFactoryImpl.class);

    private Map<String, RenderKit> _renderkits = new HashMap<String, RenderKit>();

    public RenderKitFactoryImpl()
    {
    }

    public void purgeRenderKit()
    {
        _renderkits.clear();
    }
    
    public void addRenderKit(String renderKitId, RenderKit renderKit)
    {
        if (renderKitId == null) throw new NullPointerException("renderKitId");
        if (renderKit == null) throw new NullPointerException("renderKit");
        if (log.isInfoEnabled())
        {
            if (_renderkits.containsKey(renderKitId))
            {
                log.info("RenderKit with renderKitId '" + renderKitId + "' was replaced.");
            }
        }
        _renderkits.put(renderKitId, renderKit);
    }


    public RenderKit getRenderKit(FacesContext context, String renderKitId)
            throws FacesException
    {
        if (renderKitId == null) throw new NullPointerException("renderKitId");
        RenderKit renderkit = _renderkits.get(renderKitId);
        if (renderkit == null)
        {
            //throw new IllegalArgumentException("Unknown RenderKit '" + renderKitId + "'.");
            //JSF Spec API Doc says:
            // "If there is no registered RenderKit for the specified identifier, return null"
            // vs "IllegalArgumentException - if no RenderKit instance can be returned for the specified identifier"
            //First sentence is more precise, so we just log a warning
            log.warn("Unknown RenderKit '" + renderKitId + "'.");
        }
        return renderkit;
    }


    public Iterator<String> getRenderKitIds()
    {
        return _renderkits.keySet().iterator();
    }
}
