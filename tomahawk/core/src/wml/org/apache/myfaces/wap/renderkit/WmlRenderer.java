/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit;

import javax.faces.component.NamingContainer;
import javax.faces.render.Renderer;
/**
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */ 
public class WmlRenderer extends Renderer {

    /** Converts the standard char separator (':') to underscore ('_').
     *  The names of WML variables can consist of any combination of letters, digits, and underscores.
     */
    public String convertClientId(javax.faces.context.FacesContext context, String clientId) {

        String retValue = super.convertClientId(context, clientId);
        retValue = retValue.replace(NamingContainer.SEPARATOR_CHAR, '_');

        return (retValue);
    }
}
