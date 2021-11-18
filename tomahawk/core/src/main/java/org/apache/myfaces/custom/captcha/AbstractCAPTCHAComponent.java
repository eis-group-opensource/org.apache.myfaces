/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.captcha;

import javax.faces.component.UIComponentBase;

/**
 * 
 * @JSFComponent
 *   name = "t:captcha"
 *   class = "org.apache.myfaces.custom.captcha.CAPTCHAComponent"
 *   tagClass = "org.apache.myfaces.custom.captcha.CAPTCHATag"
 * @since 1.1.7
 * @author Hazem Saleh
 *
 */
public abstract class AbstractCAPTCHAComponent extends UIComponentBase {

    public static String COMPONENT_TYPE = "org.apache.myfaces.CAPTCHA";
    public static String COMPONENT_FAMILY = "org.apache.myfaces.CAPTCHA";
    public static String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.CAPTCHA";
    
    // Value binding constants
    public static final String ATTRIBUTE_CAPTCHA_SESSION_KEY_NAME = "captchaSessionKeyName";
    public static final String ATTRIBUTE_IMAGE_WIDTH = "imageWidth";
    public static final String ATTRIBUTE_IMAGE_HEIGHT = "imageHeight";
    
    /**
     * Determines the CAPTCHA session key name.
     * 
     * @JSFProperty
     * @return
     */
    public abstract String getCaptchaSessionKeyName();
    
    /**
     * Integer to indicate the CAPTCHA width. default is 290.
     * 
     * @JSFProperty
     */
    public abstract String getImageWidth();

    /**
     * Integer to indicate the CAPTCHA height. default is 81.
     * 
     * @JSFProperty
     */
    public abstract String getImageHeight();      
   
}
