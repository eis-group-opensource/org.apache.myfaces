/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.html.ext;

/**
 * 
 * @since 1.1.7
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 704778 $ $Date: 2008-10-15 07:27:46 +0300 (Wed, 15 Oct 2008) $
 *
 */
public interface MessageProperties
{

    /**
     *  If present, instead of rendering the message summary, 
     *  a MessageFormat with this attribute as pattern is created. 
     *  
     *  The format method of this MessageFormat is called with the 
     *  message summary as the first argument and the label of the 
     *  associated component (if any) as the second argument. 
     *  
     *  Example: "{0}:"
     * 
     * @JSFProperty
     */
    public String getSummaryFormat();
    
    public void setSummaryFormat(String summaryFormat);
    
    /**
     * If present, instead of rendering the message detail, 
     * a MessageFormat with this attribute as pattern is created. 
     * 
     * The format method of this MessageFormat is called with the 
     * message detail as the first argument and the label of the 
     * associated component (if any) as the second argument. 
     * 
     * Example: "The input in field {1} is wrong: {0}"
     * 
     * @JSFProperty
     */
    public String getDetailFormat();
    
    public void setDetailFormat(String detailFormat);
    
    /**
     *  If present, all occurrences of the id of the component for 
     *  which the message is rendered will be replaced by the label. 
     *  
     *  Default: true.
     * 
     * @JSFProperty
     *   defaultValue="true"
     */
    public boolean isReplaceIdWithLabel();
    
    public void setReplaceIdWithLabel(boolean replaceIdWithLabel);
    
    /**
     * If set to true, an empty span element is rendered. 
     * Useful if there is an inputAjax field and the corresponding 
     * error message is displayed there.
     * 
     * @JSFProperty
     *   defaultValue="false"
     */    
    public boolean isForceSpan();
    
    public void setForceSpan(boolean forceSpan);
}
