/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.webapp;

import java.util.Enumeration;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.discovery.tools.DiscoverSingleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.ManagedBeanBuilder;
import org.apache.myfaces.config.annotation.LifecycleProviderFactory;
import org.apache.myfaces.shared_impl.util.ClassUtils;
import org.apache.myfaces.util.ContainerUtils;

/**
 * Initialise the MyFaces system.
 * <p>
 * This context listener is registered by the JSP TLD file for the standard
 * JSF "f" components. Normally, servlet containers will automatically load
 * and process .tld files at startup time, and therefore register and run
 * this class automatically.
 * <p>
 * Some very old servlet containers do not do this correctly, so in those
 * cases this listener may be registered manually in web.xml. Registering
 * it twice (ie in both .tld and web.xml) will result in a harmless warning
 * message being generated. Very old versions of MyFaces Core do not register
 * the listener in the .tld file, so those also need a manual entry in web.xml.
 * However all versions since at least 1.1.2 have this entry in the tld.
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 1067544 $ $Date: 2011-02-06 00:33:47 +0200 (Sun, 06 Feb 2011) $
 */
public class StartupServletContextListener extends AbstractMyFacesListener
    implements ServletContextListener
{
    static final String FACES_INIT_DONE = StartupServletContextListener.class.getName() + ".FACES_INIT_DONE";
    /*comma delimited list of plugin classes which can be hooked into myfaces*/
    static final String FACES_INIT_PLUGINS = "org.apache.myfaces.FACES_INIT_PLUGINS";

    private static final byte FACES_INIT_PHASE_PREINIT = 0;
    private static final byte FACES_INIT_PHASE_POSTINIT = 1;
    private static final byte FACES_INIT_PHASE_PREDESTROY = 2;
    private static final byte FACES_INIT_PHASE_POSTDESTROY = 3;

    private static final Log log = LogFactory.getLog(StartupServletContextListener.class);

    private FacesInitializer _facesInitializer;
    private ServletContext _servletContext;
    
    public void contextInitialized(ServletContextEvent event)
    {
        if (_servletContext != null)
        {
            throw new IllegalStateException("context is already initialized");
        }
        _servletContext = event.getServletContext();
        
        Boolean b = (Boolean) _servletContext.getAttribute(FACES_INIT_DONE);
        if (b == null || b.booleanValue() == false)
        {
            FacesInitializer facesInitializer = getFacesInitializer();

            // Create startup FacesContext before initializing
            FacesContext facesContext = facesInitializer.initStartupFacesContext(_servletContext);

            dispatchInitializationEvent(event, FACES_INIT_PHASE_PREINIT);
            facesInitializer.initFaces(_servletContext);
            dispatchInitializationEvent(event, FACES_INIT_PHASE_POSTINIT);
            _servletContext.setAttribute(FACES_INIT_DONE, Boolean.TRUE);

            //Destroy startup FacesContext
            facesInitializer.destroyStartupFacesContext(facesContext);
        }
        else
        {
            log.info("MyFaces already initialized");
        }
    }


    public void contextDestroyed(ServletContextEvent event)
    {
        if (_facesInitializer != null && _servletContext != null)
        {
            // Create startup FacesContext before start undeploy
            FacesContext facesContext = _facesInitializer.initShutdownFacesContext(_servletContext);
            
            doPredestroy(event);
            
            _facesInitializer.destroyFaces(_servletContext);
            
            LifecycleProviderFactory.getLifecycleProviderFactory().release();

            // Destroy startup FacesContext, but note we do before publish postdestroy event on
            // plugins and before release factories.
            if (facesContext != null)
            {
                _facesInitializer.destroyShutdownFacesContext(facesContext);
            }
            
            FactoryFinder.releaseFactories();

            DiscoverSingleton.release(); //clears EnvironmentCache and prevents leaking classloader references
            dispatchInitializationEvent(event, FACES_INIT_PHASE_POSTDESTROY);
        }
        
        _servletContext = null;
    }

    protected FacesInitializer getFacesInitializer()
    {
        if (_facesInitializer == null)
        {
            if (ContainerUtils.isJsp21()) 
            {
                _facesInitializer = new Jsp21FacesInitializer();
            } 
            else 
            {
                _facesInitializer = new Jsp20FacesInitializer();
            }
        }
        
        return _facesInitializer;
    }

    /**
     * configure the faces initializer
     * 
     * @param facesInitializer
     */
    public void setFacesInitializer(FacesInitializer facesInitializer)
    {
        if (_facesInitializer != null && _facesInitializer != facesInitializer && _servletContext != null)
        {
            _facesInitializer.destroyFaces(_servletContext);
        }
        _facesInitializer = facesInitializer;
        if (_servletContext != null)
        {
            facesInitializer.initFaces(_servletContext);
        }
    }
    
    private void doPredestroy(ServletContextEvent event) {
                
           ServletContext ctx = event.getServletContext();

           dispatchInitializationEvent(event, FACES_INIT_PHASE_PREDESTROY);
           Enumeration<String> attributes = ctx.getAttributeNames();

           while(attributes.hasMoreElements()) 
           {
               String name = attributes.nextElement();
               Object value = ctx.getAttribute(name);
               doPreDestroy(value, name, ManagedBeanBuilder.APPLICATION);
           }
    }

    /**
     * the central initialisation event dispatcher which calls
     * our listeners
     * @param event
     * @param operation
     */
    private void dispatchInitializationEvent(ServletContextEvent event, int operation) {
        String [] pluginEntries = (String []) _servletContext.getAttribute(FACES_INIT_PLUGINS);

        if(pluginEntries == null) {
            String plugins = (String) _servletContext.getInitParameter(FACES_INIT_PLUGINS);
            log.info("Checking for plugins:"+FACES_INIT_PLUGINS);
            if(plugins == null) return;
            log.info("Plugins found");
            pluginEntries = plugins.split(",");
            _servletContext.setAttribute(FACES_INIT_PLUGINS, pluginEntries);
        }

        //now we process the plugins
        for(String plugin: pluginEntries) {
            log.info("Processing plugin:"+plugin);
            try {
                //for now the initializers have to be stateless to
                //so that we do not have to enforce that the initializer
                //must be serializable
                Class pluginClass = ClassUtils.getContextClassLoader().loadClass(plugin);
                if (pluginClass == null)
                {
                    pluginClass = this.getClass().getClassLoader().loadClass(plugin);
                }
                StartupListener initializer = (StartupListener) pluginClass.newInstance();
                
                switch(operation) {
                    case FACES_INIT_PHASE_PREINIT:
                        initializer.preInit(event);
                        break;
                    case FACES_INIT_PHASE_POSTINIT:
                        initializer.postInit(event);
                        break;
                    case FACES_INIT_PHASE_PREDESTROY:
                        initializer.preDestroy(event);
                        break;
                    default:
                        initializer.postDestroy(event);
                        break;
                }

               
            } catch (ClassNotFoundException e) {
                log.error(e);
            } catch (IllegalAccessException e) {
                log.error(e);
            } catch (InstantiationException e) {
                log.error(e);
            }

        }
        log.info("Processing plugins done");
    }

}
