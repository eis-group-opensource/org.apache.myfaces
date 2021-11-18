/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Thomas Spiegl
 * @version $Revision: 775008 $ $Date: 2009-05-15 08:23:20 +0300 (Fri, 15 May 2009) $
 */
public class ApplicationFactoryImpl
    extends ApplicationFactory
{
    private static final Log log = LogFactory.getLog(ApplicationFactoryImpl.class);

    /**
     * Application is thread-safe (see Application javadoc)
     * "Application represents a per-web-application singleton object..."
     * FactoryFinder has a ClassLoader-Factory Map. Since each webapp has it's
     * own ClassLoader, each webapp will have it's own private factory instances.
     */
    private Application _application;
    
    private boolean _myfacesInstanceAddedToApplicationMap = false;

    public ApplicationFactoryImpl()
    {
        createAndLogNewApplication();
    }

    private void createAndLogNewApplication() {
        _application = new ApplicationImpl();
        putApplicationOnMap();
        if (log.isTraceEnabled()) log.trace("New ApplicationFactory instance created");
    }

    public void purgeApplication(){
        createAndLogNewApplication();
    }

    public Application getApplication()
    {
        //Put it on ApplicationMap, so javax.faces.application.Application
        //class can find it. This allows wrapped jsf 1.1 application instances
        //to work correctly in jsf 1.2 as ri does.
        if (_application != null && !_myfacesInstanceAddedToApplicationMap)
        {
            putApplicationOnMap();
        }

        return _application;
    }

    public void setApplication(Application application)
    {
        if (application == null)
        {
            throw new NullPointerException("Cannot set a null application in the ApplicationFactory");
        }
        _application = application;
        putApplicationOnMap();
    }
    
    private void putApplicationOnMap()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null)
        {
            ExternalContext externalContext = facesContext.getExternalContext();
            if (externalContext != null)
            {
                externalContext.
                    getApplicationMap().put("org.apache.myfaces.application.ApplicationImpl", _application);
                _myfacesInstanceAddedToApplicationMap = true;
            }
        }        
    }

}
