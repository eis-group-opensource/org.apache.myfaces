/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.event;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import java.util.EventObject;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class PhaseEvent extends EventObject
{
    private static final long serialVersionUID = -7235692965954486239L;
    // FIELDS
    private FacesContext _facesContext;
    private PhaseId _phaseId;

    // CONSTRUCTORS
    public PhaseEvent(FacesContext facesContext, PhaseId phaseId, Lifecycle lifecycle)
    {
        super(lifecycle);
        if (facesContext == null) throw new NullPointerException("facesContext");
        if (phaseId == null) throw new NullPointerException("phaseId");
        if (lifecycle == null) throw new NullPointerException("lifecycle");

        _facesContext = facesContext;
        _phaseId = phaseId;
    }

    // METHODS
    public FacesContext getFacesContext()
    {
        return _facesContext;
    }

    public PhaseId getPhaseId()
    {
        return _phaseId;
    }

    @Override
    public int hashCode()
    {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((source == null) ? 0 : source.hashCode());
        result = PRIME * result + ((_facesContext == null) ? 0 : _facesContext.hashCode());
        result = PRIME * result + ((_phaseId == null) ? 0 : _phaseId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PhaseEvent other = (PhaseEvent) obj;
        if (source == null)
        {
            if (other.source != null)
                return false;
        }
        else if (!source.equals(other.source))
            return false;
        if (_facesContext == null)
        {
            if (other._facesContext != null)
                return false;
        }
        else if (!_facesContext.equals(other._facesContext))
            return false;
        if (_phaseId == null)
        {
            if (other._phaseId != null)
                return false;
        }
        else if (!_phaseId.equals(other._phaseId))
            return false;
        return true;
    }
    

    
}
