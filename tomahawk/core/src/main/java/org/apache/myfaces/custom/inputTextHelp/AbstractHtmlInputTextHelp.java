/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.inputTextHelp;

import org.apache.myfaces.component.html.ext.HtmlInputText;

/**
 * Extends standard inputText by helptext support. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:inputTextHelp"
 *   class = "org.apache.myfaces.custom.inputTextHelp.HtmlInputTextHelp"
 *   tagClass = "org.apache.myfaces.custom.inputTextHelp.HtmlInputTextHelpTag"
 * @since 1.1.7
 * @author Thomas Obereder
 * @version $Date: 2005-07-02 15:32:34 +01:00 (Thu, 09 Jun 2005)
 */
public abstract class AbstractHtmlInputTextHelp extends HtmlInputText
{
    public static final String JS_FUNCTION_SELECT_TEXT = "selectText";
    public static final String JS_FUNCTION_RESET_HELP = "resetHelpValue";
    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlInputTextHelp";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.TextHelp";

    /**
     * @JSFProperty
     */
    public abstract String getHelpText();

    /**
     * @JSFProperty
     *   defaultValue="false"
     */
    public abstract boolean isSelectText();

}
