/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.context;

import javax.el.ELContext;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511304 $ $Date: 2007-02-24 19:02:53 +0200 (Sat, 24 Feb 2007) $
 */
public class MockFacesContext extends org.apache.shale.test.mock.MockFacesContext
{

    private ELContext _elContext;

    @Override
    public ELContext getELContext()
    {
        return _elContext;
    }

    public void setELContext(ELContext elContext)
    {
        _elContext = elContext;
    }

}
