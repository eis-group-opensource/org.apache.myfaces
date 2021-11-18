/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.StringTokenizer;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public final class UserRoleUtils
{
    //private static final Log log = LogFactory.getLog(UserRoleUtils.class);

    /**
     * Constructor (private one)
     */
    private UserRoleUtils(){
        
    }
    
    /**
     * Gets the comma separated list of visibility user roles from the given component
     * and checks if current user is in one of these roles.
     * @param component a user role aware component
     * @return true if no user roles are defined for this component or
     *              user is in one of these roles, false otherwise
     */
    public static boolean isVisibleOnUserRole(UIComponent component)
    {
        String userRole;
        if (component instanceof UserRoleAware)
        {
            userRole = ((UserRoleAware)component).getVisibleOnUserRole();
        }
        else
        {
            userRole = (String)component.getAttributes().get(UserRoleAware.VISIBLE_ON_USER_ROLE_ATTR);
        }

        if (userRole == null)
        {
            // no restriction
            return true;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        StringTokenizer st = new StringTokenizer(userRole, ",");
        while (st.hasMoreTokens())
        {
            if (facesContext.getExternalContext().isUserInRole(st.nextToken().trim()))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets the comma separated list of enabling user roles from the given component
     * and checks if current user is in one of these roles.
     * @param component a user role aware component
     * @return true if no user roles are defined for this component or
     *              user is in one of these roles, false otherwise
     */
    public static boolean isEnabledOnUserRole(UIComponent component)
    {
        String userRole;
        if (component instanceof UserRoleAware)
        {
            userRole = ((UserRoleAware)component).getEnabledOnUserRole();
        }
        else
        {
            userRole = (String)component.getAttributes().get(UserRoleAware.ENABLED_ON_USER_ROLE_ATTR);
        }

        if (userRole == null)
        {
            // no restriction
            return true;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        StringTokenizer st = new StringTokenizer(userRole, ",");
        while (st.hasMoreTokens())
        {
            if (facesContext.getExternalContext().isUserInRole(st.nextToken().trim()))
            {
                return true;
            }
        }
        return false;
    }

}
