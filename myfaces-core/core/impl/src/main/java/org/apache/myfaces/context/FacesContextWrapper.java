/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context;

import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import java.util.Iterator;


/**
 * Convenient class to wrap the current FacesContext.
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Anton Koinov
 * @version $Revision: 684465 $ $Date: 2008-08-10 14:38:21 +0300 (Sun, 10 Aug 2008) $
 */
public class FacesContextWrapper
    extends FacesContext
{
    //~ Instance fields ----------------------------------------------------------------------------

    private FacesContext _facesContext;

    //~ Constructors -------------------------------------------------------------------------------

    public FacesContextWrapper(FacesContext facesContext)
    {
        _facesContext = facesContext;
    }

    //~ Methods ------------------------------------------------------------------------------------

    public Application getApplication()
    {
        return _facesContext.getApplication();
    }

    public Iterator getClientIdsWithMessages()
    {
        return _facesContext.getClientIdsWithMessages();
    }

    public ExternalContext getExternalContext()
    {
        return _facesContext.getExternalContext();
    }

    public Severity getMaximumSeverity()
    {
        return _facesContext.getMaximumSeverity();
    }

    public Iterator getMessages()
    {
        return _facesContext.getMessages();
    }

    public Iterator getMessages(String clientId)
    {
        return _facesContext.getMessages(clientId);
    }

    public RenderKit getRenderKit()
    {
        return _facesContext.getRenderKit();
    }

    public boolean getRenderResponse()
    {
        return _facesContext.getRenderResponse();
    }

    public boolean getResponseComplete()
    {
        return _facesContext.getResponseComplete();
    }

    public void setResponseStream(ResponseStream responsestream)
    {
        _facesContext.setResponseStream(responsestream);
    }

    public ResponseStream getResponseStream()
    {
        return _facesContext.getResponseStream();
    }

    public void setResponseWriter(ResponseWriter responsewriter)
    {
        _facesContext.setResponseWriter(responsewriter);
    }

    public ResponseWriter getResponseWriter()
    {
        return _facesContext.getResponseWriter();
    }

    public void setViewRoot(UIViewRoot viewRoot)
    {
        _facesContext.setViewRoot(viewRoot);
    }

    public UIViewRoot getViewRoot()
    {
        return _facesContext.getViewRoot();
    }

    public void addMessage(String clientId, FacesMessage message)
    {
        _facesContext.addMessage(clientId, message);
    }

    public void release()
    {
        _facesContext.release();
    }

    public void renderResponse()
    {
        _facesContext.renderResponse();
    }

    public void responseComplete()
    {
        _facesContext.responseComplete();
    }
    
    public ELContext getELContext()
    {
        return _facesContext.getELContext();
    }
}
