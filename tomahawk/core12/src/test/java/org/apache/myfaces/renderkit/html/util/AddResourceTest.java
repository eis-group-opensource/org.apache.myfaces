/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.webapp.filter.servlet.AbstractAttributeMap;
import org.apache.myfaces.webapp.filter.servlet.RequestHeaderMap;
import org.apache.myfaces.webapp.filter.servlet.RequestHeaderValuesMap;
import org.apache.shale.test.mock.MockExternalContext;
import org.apache.shale.test.mock.MockPrintWriter;
import org.apache.shale.test.mock.MockResponseWriter;

/**
 * Unit test for the AddResource class which can output script, style and inline
 * javascript into the header or body of an HTML response page.
 */
public class AddResourceTest extends AbstractTomahawkViewControllerTestCase
{
    public AddResourceTest(String name)
    {
        super(name);
    }

    public void testGetInstance()
    {

       Map cacheMap = new LinkedHashMap();

        AddResource instance1 = AddResourceFactory.getInstance(null,cacheMap, "/test1", null);
        assertNotNull(instance1);

        /* no longer true
        AddResource instance2 = AddResourceFactory.getInstance(null, "/test2", null);
        assertNotSame(instance1, instance2);
        */

        AddResourceFactory.getInstance(null,cacheMap, "/test1", null);
    }
    
    /*
    public void setUp()
    {
        // Make sure a FacesContext configured from some previous test case
        // doesn't interfere with the test case we are about to run...
        FacesContextHelper.setCurrentInstance(null);
    }

    public void tearDown()
    {
        // Make sure a FacesContext we may have configured in the test case
        // just completed doesn't interfere with test cases run later.
        FacesContextHelper.setCurrentInstance(null);
    }
    */

    /**
     * Simple test helper class to allow unit tests to configure
     * mock FacesContext objects as the "current instance".
     * <p>
     * The method FacesContext.setCurrentInstance is protected, and
     * hence cannot be accessed by unit tests wanting to configure
     * a mock object as the value seen by code calling method
     * FacesContext.getCurrentInstance().
     */
    /*
    private static abstract class FacesContextHelper extends FacesContext
    {
        public static void setCurrentInstance(FacesContext other)
        {
            FacesContext.setCurrentInstance(other);
        }
    }*/

    /**
     * Configure a fake JSF environment for a test, consisting of a
     * FacesContext and dependent objects.
     * <p>
     * EasyMock control objects are used to emulate the necessary bits.
     */
    /*
    private static class MockState
    {
        Writer _writer;
        ResponseWriter _htmlResponseWriter;
        MockControl _servletRequestControl;
        HttpServletRequest _servletRequest;
        MockControl _servletResponseControl;
        HttpServletResponse _servletResponse;
        FacesContext _facesContext;

        public void setup() throws IOException
        {
            // set up a writer object to be the "response" output stream.
            _writer = new StringWriter();
            String contentType = "text/html";
            String charEncoding = "UTF-8";
            _htmlResponseWriter = new HtmlResponseWriterImpl(_writer, contentType, charEncoding);

            // Mock ServletRequest object that:
            // * returns "/test" for context path
            // * returns "/test/foo.jsp" for servlet path
            // * returns "null" for getPathInfo
            // * returns "null" for getHeader
            // * returns "null" for getAttribute
            // * returns null for getSession
            _servletRequestControl = MockControl.createControl(HttpServletRequest.class);
            _servletRequest = (HttpServletRequest) _servletRequestControl.getMock();
            _servletRequest.getContextPath();
            _servletRequestControl.setReturnValue("/test", MockControl.ZERO_OR_MORE);
            _servletRequest.getServletPath();
            _servletRequestControl.setReturnValue("/test/foo.jsp", MockControl.ZERO_OR_MORE);
            _servletRequest.getPathInfo();
            _servletRequestControl.setReturnValue("", MockControl.ZERO_OR_MORE);
            _servletRequest.getHeader("");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequest.getAttribute("");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequest.setAttribute("", "");
            _servletRequestControl.setMatcher(MockControl.ALWAYS_MATCHER);
            _servletRequestControl.setVoidCallable(MockControl.ZERO_OR_MORE);
            _servletRequest.getSession(false);
            _servletRequestControl.setReturnValue(null, MockControl.ZERO_OR_MORE);
            _servletRequestControl.replay();

            // Mock ServletResponse object that:
            // * returns appropriate encoded URLs
            // * returns a PrintWriter wrapping this object's writer member for
            //   calls to getWriter
            _servletResponseControl = MockControl.createControl(HttpServletResponse.class);
            _servletResponse = (HttpServletResponse) _servletResponseControl.getMock();
            _servletResponse.encodeURL("/test/scripts/script1");
            _servletResponseControl.setReturnValue("encoded(/test/scripts/script1)", MockControl.ZERO_OR_MORE);
            _servletResponse.getWriter();
            _servletResponseControl.setReturnValue(new PrintWriter(_writer), MockControl.ZERO_OR_MORE);
            _servletResponse.getCharacterEncoding();
            _servletResponseControl.setReturnValue("UTF-8", MockControl.ZERO_OR_MORE);
            _servletResponseControl.replay();

            // The FacesContext needs FactoryFinder configured.
            FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY, ApplicationFactoryImpl.class.getName());
            FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY, RenderKitFactoryImpl.class.getName());

            // Now create a FacesContext....
            _facesContext = new ServletFacesContextImpl(null,
                    _servletRequest, _servletResponse);
            _facesContext.setResponseWriter(_htmlResponseWriter);
        }

        public void verifyControls()
        {
            _servletRequestControl.verify();
            _servletResponseControl.verify();
        }
    }*/

    public void testAddJavaScriptHere() throws IOException
    {
        //MockState mockState = new MockState();
        //mockState.setup();

        request.setPathElements("/test", "", "", "");
        // now start the test
        AddResource instance1 = AddResourceFactory.getInstance(null,null,"/test", null);
        instance1.addJavaScriptHere(facesContext, "/scripts/script1");

        // verify that our mock objects got the expected callbacks
        //mockState.verifyControls();

        // verify that:
        // *script tag is output
        // * type attribute is right,
        // * URL has context path prepended to it
        // * URL has been encoded
        // * HTML comments have been used to hide script from non-script-aware
        //   browsers.
        //
        // NOTE: are comments required to hide this script from browsers when
        // there isn't any script body content (just a src attr)?
        String scriptValue = ((MockResponseWriter)facesContext.getResponseWriter()).getWriter().toString(); 
        
        assertEquals(scriptValue, "<script type=\"text/javascript\" src=\"/test/scripts/script1\"/>");
    }
    
    public static class CustomMockExternalContext extends MockExternalContext
    {
        private Map _requestHeaderMap;
        private Map _requestHeaderValuesMap;
        
        public CustomMockExternalContext(ServletContext context,
                HttpServletRequest request, HttpServletResponse response)
        {
            super(context, request, response);
        }

        @Override
        public Map getRequestHeaderMap()
        {
            if (_requestHeaderMap == null)
            {
                _requestHeaderMap = new RequestHeaderMap((HttpServletRequest)request);
            }
            return _requestHeaderMap;                        
        }

        @Override
        public Map getRequestHeaderValuesMap()
        {
            if (_requestHeaderValuesMap == null)
            {
                _requestHeaderValuesMap = new RequestHeaderValuesMap((HttpServletRequest)request);
            }
            return _requestHeaderValuesMap;
        }
    }
    
    public static class RequestHeaderMap extends AbstractAttributeMap
    {
        private final HttpServletRequest _httpServletRequest;

        RequestHeaderMap(HttpServletRequest httpServletRequest)
        {
            _httpServletRequest = httpServletRequest;
        }

        protected Object getAttribute(String key)
        {
            return _httpServletRequest.getHeader(key);
        }

        protected void setAttribute(String key, Object value)
        {
            throw new UnsupportedOperationException(
                "Cannot set HttpServletRequest Header");
        }

        protected void removeAttribute(String key)
        {
            throw new UnsupportedOperationException(
                "Cannot remove HttpServletRequest Header");
        }

        protected Enumeration getAttributeNames()
        {
            return _httpServletRequest.getHeaderNames();
        }

        public void putAll(Map t)
        {
            throw new UnsupportedOperationException();
        }


        public void clear()
        {
            throw new UnsupportedOperationException();
        }    
    }

    public static class RequestHeaderValuesMap extends AbstractAttributeMap
    {
        private final HttpServletRequest _httpServletRequest;
        private final Map                _valueCache = new HashMap();

        RequestHeaderValuesMap(HttpServletRequest httpServletRequest)
        {
            _httpServletRequest = httpServletRequest;
        }

        protected Object getAttribute(String key)
        {
            Object ret = _valueCache.get(key);
            if (ret == null)
            {
                _valueCache.put(key, ret = toArray(_httpServletRequest
                    .getHeaders(key)));
            }

            return ret;
        }

        protected void setAttribute(String key, Object value)
        {
            throw new UnsupportedOperationException(
                "Cannot set HttpServletRequest HeaderValues");
        }

        protected void removeAttribute(String key)
        {
            throw new UnsupportedOperationException(
                "Cannot remove HttpServletRequest HeaderValues");
        }

        protected Enumeration getAttributeNames()
        {
            return _httpServletRequest.getHeaderNames();
        }

        private String[] toArray(Enumeration e)
        {
            List ret = new ArrayList();

            while (e.hasMoreElements())
            {
                ret.add(e.nextElement());
            }

            return (String[]) ret.toArray(new String[ret.size()]);
        }
    }

    public void testWriteWithFullHeader() throws IOException
    {
        //MockState mockState = new MockState();
        //mockState.setup();
        externalContext = new CustomMockExternalContext(servletContext, request, response);
        facesContext.setExternalContext(externalContext);

        request.setPathElements("/test", "", "", "");
        String originalResponse =
            "<html><head></head><body></body></html>";

        AddResource ar = AddResourceFactory.getInstance(null,null,"/test", null);
        ar.parseResponse(request,originalResponse,response);
        ar.writeWithFullHeader(request,response);
        ar.writeResponse(request,response);

        //mockState.verifyControls();

        String returnedResponse = new String(((MockPrintWriter)response.getWriter()).content());
        
        //System.out.println(facesContext.getResponseWriter().toString());
        assertEquals(originalResponse, returnedResponse);
    }
}
