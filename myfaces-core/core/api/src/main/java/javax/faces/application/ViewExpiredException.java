/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.application;

import javax.faces.FacesException;

/**
 * See Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Stan Silvert
 */
public class ViewExpiredException extends FacesException {
    
    private String viewId;
    
    public ViewExpiredException() {
    }
    
    public ViewExpiredException(String viewId) {
        this.viewId = viewId;
    }
    
    public ViewExpiredException(String message, String viewId) {
        super(message);
        this.viewId = viewId;
    }
    
    public ViewExpiredException(String message, Throwable cause, String viewId) {
        super(message, cause);
        this.viewId = viewId;
    }
    
    public ViewExpiredException(Throwable cause, String viewId) {
        super(cause);
        this.viewId = viewId;
    }

    public String getViewId() {
        return viewId;
    }
    
    public String getMessage() {
        if (viewId != null) {
            return viewId + super.getMessage();
        }
        
        return super.getMessage();
    }
}
