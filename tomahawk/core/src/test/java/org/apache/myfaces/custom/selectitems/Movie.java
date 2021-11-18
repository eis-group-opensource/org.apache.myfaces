/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectitems;

/**
 * @author cagatay (latest modification by $Author$)
 * @version $Revision$ $Date$
 * Simple test entity
 */
public class Movie {
    private String name;
    
    private String director;
    
    public Movie() {}
    
    public Movie(String name, String director) {
        this.name = name;
        this.director = director;
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
}
