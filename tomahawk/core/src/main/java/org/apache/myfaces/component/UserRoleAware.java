/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component;

/**
 * Behavioral interface.
 * Components that support user role checking should implement this interface
 * to optimize property access.
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public interface UserRoleAware
{
    static final String ENABLED_ON_USER_ROLE_ATTR = "enabledOnUserRole";
    static final String VISIBLE_ON_USER_ROLE_ATTR = "visibleOnUserRole";

    /**
     * If user is in given role, this component will be rendered 
     * normally. If not, no hyperlink is rendered but all nested 
     * tags (=body) are rendered.
     * 
     * @JSFProperty
     * @return
     */
    String getEnabledOnUserRole();
    
    void setEnabledOnUserRole(String userRole);

    /**
     *  If user is in given role, this component will be rendered 
     *  normally. If not, nothing is rendered and the body of this 
     *  tag will be skipped.
     * 
     * @JSFProperty
     * @return
     */
    String getVisibleOnUserRole();
    
    void setVisibleOnUserRole(String userRole);
}