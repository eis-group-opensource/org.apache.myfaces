/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectitems;

/**
 * @author cagatay (latest modification by $Author: lu4242 $)
 * @version $Revision: 783163 $ $Date: 2009-06-10 02:37:19 +0300 (Wed, 10 Jun 2009) $
 * Simple test entity
 */
public class Movie {
    private String name;
    
    private String director;
    
    private boolean disabled;
    
    private Boolean escaped;
    
    public Movie() {}
    
    public Movie(String name, String director) {
        this.name = name;
        this.director = director;
        this.disabled = false;
        this.escaped = Boolean.TRUE;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isDisabled()
    {
        return disabled;
    }

    public void setDisabled(boolean disabled)
    {
        this.disabled = disabled;
    }

    public Boolean getEscaped()
    {
        return escaped;
    }

    public void setEscaped(Boolean escaped)
    {
        this.escaped = escaped;
    }
}
