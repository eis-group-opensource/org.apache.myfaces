/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.renderkit.wml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */
public class ColumnRenderer extends GroupRenderer {
    private static Log log = LogFactory.getLog(ColumnRenderer.class);

    /** Creates a new instance of ColumnRenderer */
    public ColumnRenderer() {
        super();
        log.debug("created object " + this.getClass().getName());
    }
}

