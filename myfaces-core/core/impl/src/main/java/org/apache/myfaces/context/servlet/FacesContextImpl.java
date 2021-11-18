/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.context.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.myfaces.context.ReleaseableExternalContext;
import org.apache.myfaces.context.ReleaseableFacesContextFactory;
import org.apache.myfaces.context.portlet.PortletExternalContextImpl;
import org.apache.myfaces.el.unified.FacesELContext;
import org.apache.myfaces.shared_impl.util.NullIterator;


/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 938287 $ $Date: 2010-04-27 04:34:31 +0300 (Tue, 27 Apr 2010) $
 */
public class FacesContextImpl
        extends FacesContext
{
    //~ Instance fields ----------------------------------------------------------------------------

    private List                        _messageClientIds = null;
    private List                        _messages         = null;
    private Application                 _application;
    private ReleaseableExternalContext  _externalContext;
    private ResponseStream              _responseStream   = null;
    private ResponseWriter              _responseWriter   = null;
    private FacesMessage.Severity       _maximumSeverity  = null;
    private UIViewRoot                  _viewRoot;
    private boolean                     _renderResponse   = false;
    private boolean                     _responseComplete = false;
    private RenderKitFactory            _renderKitFactory;
    private boolean                     _released = false;
    private ELContext                   _elContext;
    private ReleaseableFacesContextFactory _facesContextFactory = null;
    
    // Variables used to cache values
    private RenderKit _cachedRenderKit = null;
    private String _cachedRenderKitId = null;

    //~ Constructors -------------------------------------------------------------------------------

    @Deprecated
    public FacesContextImpl(final PortletContext portletContext,
            final PortletRequest portletRequest,
            final PortletResponse portletResponse)
    {
        this(new PortletExternalContextImpl(portletContext,
                                    portletRequest,
                                    portletResponse));
    }
    
    @Deprecated
    public FacesContextImpl(final ServletContext servletContext,
                final ServletRequest servletRequest,
                final ServletResponse servletResponse)
    {
        this(new ServletExternalContextImpl(servletContext,
                                    servletRequest,
                                    servletResponse));
    }    
    
    public FacesContextImpl(final ReleaseableExternalContext externalContext,
                            final ReleaseableFacesContextFactory facesContextFactory)
    {
        this(externalContext);
        _facesContextFactory = facesContextFactory;
    }

    private FacesContextImpl(final ReleaseableExternalContext externalContext)
    {
        _externalContext = externalContext;
        FacesContext.setCurrentInstance(this);  //protected method, therefore must be called from here
        _application = ((ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY))
                .getApplication();
        _renderKitFactory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }

    //~ Methods ------------------------------------------------------------------------------------

    public final ExternalContext getExternalContext()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return (ExternalContext)_externalContext;
    }

    public final FacesMessage.Severity getMaximumSeverity()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _maximumSeverity;
    }

    public final Iterator getMessages()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return (_messages != null) ? _messages.iterator() : Collections.EMPTY_LIST.iterator();
    }

    public final Application getApplication()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }

        return _application;
    }

    public final Iterator getClientIdsWithMessages()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (_messages == null || _messages.isEmpty())
        {
            return NullIterator.instance();
        }

        final Set uniqueClientIds = new LinkedHashSet(_messageClientIds);
        return uniqueClientIds.iterator();
    }

    public final Iterator getMessages(final String clientId)
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (_messages == null)
        {
            return NullIterator.instance();
        }

        List lst = new ArrayList();
        for (int i = 0; i < _messages.size(); i++)
        {
            Object savedClientId = _messageClientIds.get(i);
            if (clientId == null)
            {
                if (savedClientId == null) lst.add(_messages.get(i));
            }
            else
            {
                if (clientId.equals(savedClientId)) lst.add(_messages.get(i));
            }
        }
        return lst.iterator();
    }

    public final RenderKit getRenderKit()
    {
        if (getViewRoot() == null)
        {
            return null;
        }

        String renderKitId = getViewRoot().getRenderKitId();

        if (renderKitId == null)
        {
            return null;
        }
        
        if (_cachedRenderKitId == null || !renderKitId.equals(_cachedRenderKitId))
        {
            _cachedRenderKitId = renderKitId;
            _cachedRenderKit = _renderKitFactory.getRenderKit(this, renderKitId);
        }
        
        return _cachedRenderKit;
    }

    public final boolean getRenderResponse()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _renderResponse;
    }

    public final boolean getResponseComplete()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _responseComplete;
    }

    public final void setResponseStream(final ResponseStream responseStream)
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (responseStream == null)
        {
            throw new NullPointerException("responseStream");
        }
        _responseStream = responseStream;
    }

    public final ResponseStream getResponseStream()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _responseStream;
    }

    public final void setResponseWriter(final ResponseWriter responseWriter)
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (responseWriter == null)
        {
            throw new NullPointerException("responseWriter");
        }
        _responseWriter = responseWriter;
    }

    public final ResponseWriter getResponseWriter()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _responseWriter;
    }

    public final void setViewRoot(final UIViewRoot viewRoot)
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (viewRoot == null)
        {
            throw new NullPointerException("viewRoot");
        }
        _viewRoot = viewRoot;
    }

    public final UIViewRoot getViewRoot()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        return _viewRoot;
    }

    public final void addMessage(final String clientId, final FacesMessage message)
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (message == null)
        {
            throw new NullPointerException("message");
        }

        if (_messages == null)
        {
            _messages             = new ArrayList();
            _messageClientIds     = new ArrayList();
        }
        _messages.add(message);
        _messageClientIds.add((clientId != null) ? clientId : null);
        FacesMessage.Severity serSeverity =  message.getSeverity();
        if (serSeverity != null) {
            if (_maximumSeverity == null)
            {
                _maximumSeverity = serSeverity;
            }
            else if (serSeverity.compareTo(_maximumSeverity) > 0)
            {
                _maximumSeverity = serSeverity;
            }
        }
    }

    public final void release()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        if (_facesContextFactory != null)
        {
            _facesContextFactory.release();
            _facesContextFactory = null;
        }
        if (_externalContext != null)
        {
            _externalContext.release();
            _externalContext = null;
        }

        _messageClientIds     = null;
        _messages             = null;
        _application          = null;
        _responseStream       = null;
        _responseWriter       = null;
        _viewRoot             = null;
        _cachedRenderKit = null;
        _cachedRenderKitId = null;

        _released             = true;
        FacesContext.setCurrentInstance(null);
    }

    public final void renderResponse()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        _renderResponse = true;
    }

    public final void responseComplete()
    {
        if (_released) {
            throw new IllegalStateException("FacesContext already released");
        }
        _responseComplete = true;
    }

    // Portlet need to do this to change from ActionRequest/Response to
    // RenderRequest/Response
    public final void setExternalContext(ReleaseableExternalContext extContext)
    {
        _externalContext = extContext;
        FacesContext.setCurrentInstance(this); //TODO: figure out if I really need to do this
    }

    public final ELContext getELContext() {
        if (_elContext != null) return _elContext;
        
        
        _elContext = new FacesELContext(getApplication().getELResolver(), this);
        
        ELContextEvent event = new ELContextEvent(_elContext);
        for (ELContextListener listener : getApplication().getELContextListeners()) {
            listener.contextCreated(event);
        }
        
        return _elContext;
    }
    
}
