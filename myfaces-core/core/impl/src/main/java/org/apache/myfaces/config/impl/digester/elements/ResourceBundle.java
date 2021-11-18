/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.impl.digester.elements;

/**
 * @author Mathias Broekelmann (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class ResourceBundle
{
    private String baseName;
    private String var;
    private String displayName;

    /**
     * @return the baseName
     */
    public String getBaseName()
    {
        return baseName;
    }

    /**
     * @param baseName
     *            the baseName to set
     */
    public void setBaseName(String baseName)
    {
        this.baseName = baseName;
    }

    /**
     * @return the var
     */
    public String getVar()
    {
        return var;
    }

    /**
     * @param var
     *            the var to set
     */
    public void setVar(String var)
    {
        this.var = var;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
