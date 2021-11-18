/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.context.ReleaseableExternalContext;

/**
 * A FacesContext implementation which will be set as the current instance
 * during container startup and shutdown and which provides a basic set of
 * FacesContext functionality.
 * 
 * @author Jakob Korherr (latest modification by $Author: jakobk $)
 * @version $Revision: 963629 $ $Date: 2010-07-13 04:29:07 -0500 (Mar, 13 Jul 2010) $
 */
public class StartupFacesContextImpl extends FacesContextImplBase
{
    
    public static final String EXCEPTION_TEXT = "This method is not supported during ";

    private boolean _startup;
    
    public StartupFacesContextImpl(
            ExternalContext externalContext, 
            ReleaseableExternalContext defaultExternalContext,
            boolean startup)
    {
        // setCurrentInstance is called in constructor of super class
        super(externalContext, defaultExternalContext);
        
        _startup = startup;
    }

    // ~ Methods which are valid by spec to be called during startup and shutdown------
    
    // public final UIViewRoot getViewRoot() implemented in super-class
    // public void release() implemented in super-class
    // public final ExternalContext getExternalContext() implemented in super-class
    // public Application getApplication() implemented in super-class
    // public boolean isProjectStage(ProjectStage stage) implemented in super-class
    
    // ~ Methods which can be called during startup and shutdown, but are not
    //   officially supported by the spec--------------------------------------
    
    // all other methods on FacesContextImplBase
    
    // ~ Methods which are unsupported during startup and shutdown-------------

    @Override
    public final FacesMessage.Severity getMaximumSeverity()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }
    
    @Override
    public final Iterator<FacesMessage> getMessages()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }
    
    
    @Override
    public final Iterator<String> getClientIdsWithMessages()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final Iterator<FacesMessage> getMessages(final String clientId)
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final void addMessage(final String clientId, final FacesMessage message)
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final void renderResponse()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final void responseComplete()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final boolean getRenderResponse()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final boolean getResponseComplete()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final void setResponseStream(final ResponseStream responseStream)
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final ResponseStream getResponseStream()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final void setResponseWriter(final ResponseWriter responseWriter)
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }

    @Override
    public final ResponseWriter getResponseWriter()
    {
        assertNotReleased();
        throw new UnsupportedOperationException(EXCEPTION_TEXT + _getTime());
    }
    
    // ~ private Methods ------------------------------------------------------
    
    /**
     * Returns startup or shutdown as String according to the field _startup.
     * @return
     */
    private String _getTime()
    {
        return _startup ? "startup" : "shutdown";
    }
    
}
