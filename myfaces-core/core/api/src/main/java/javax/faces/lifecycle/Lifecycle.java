/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.lifecycle;

import javax.faces.FacesException;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class Lifecycle
{
    public abstract void addPhaseListener(javax.faces.event.PhaseListener listener);

    public abstract void execute(javax.faces.context.FacesContext context)
            throws FacesException;

    public abstract javax.faces.event.PhaseListener[] getPhaseListeners();

    public abstract void removePhaseListener(javax.faces.event.PhaseListener listener);

    public abstract void render(javax.faces.context.FacesContext context)
            throws FacesException;
}
