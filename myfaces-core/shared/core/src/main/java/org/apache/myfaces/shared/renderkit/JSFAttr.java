/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.renderkit;


/**
 * Constant declarations for JSF tags
 * @author Anton Koinov (latest modification by $Author: lu4242 $)
 * @version $Revision: 699137 $ $Date: 2008-09-26 03:39:13 +0300 (Pn, 26 Rgs 2008) $
 */
public interface JSFAttr
{
    //~ Static fields/initializers -----------------------------------------------------------------

    // Common Attributes
    String   ID_ATTR                        = "id";
    String   VALUE_ATTR                     = "value";
    String   BINDING_ATTR                   = "binding";
    String   STYLE_ATTR                     = "style";
    String   STYLE_CLASS_ATTR               = "styleClass";
    String   ESCAPE_ATTR                    = "escape";
    String   FORCE_ID_ATTR                  = "forceId";
    String   FORCE_ID_INDEX_ATTR            = "forceIdIndex";
    String   RENDERED                       = "rendered";

    // Common Output Attributes
    String   FOR_ATTR                       = "for";
    String   CONVERTER_ATTR                 = "converter";

    // Ouput_Time Attributes
    String   TIME_STYLE_ATTR                = "timeStyle";
    String   TIMEZONE_ATTR                  = "timezone";

    // Common Input Attributes
    String   REQUIRED_ATTR                  = "required";
    String   VALIDATOR_ATTR                 = "validator";
    String   DISABLED_ATTR                  = "disabled";
    String   READONLY_ATTR                  = "readonly";

    // Input_Secret Attributes
    String   REDISPLAY_ATTR                 = "redisplay";

    // Input_Checkbox Attributes
    String   LAYOUT_ATTR                    = "layout";

    // Select_Menu Attributes
    String   SIZE_ATTR                     = "size";

    // SelectMany Checkbox List/ Select One Radio Attributes
    String BORDER_ATTR                 = "border";
    String DISABLED_CLASS_ATTR         = "disabledClass";
    String ENABLED_CLASS_ATTR          = "enabledClass";

    // Common Command Attributes
    /**@deprecated */
    String   COMMAND_CLASS_ATTR           = "commandClass";
    String   LABEL_ATTR                   = "label";
    String   IMAGE_ATTR                   = "image";
    String   ACTION_ATTR                 = "action";
    String   IMMEDIATE_ATTR              = "immediate";


    // Command_Button Attributes
    String   TYPE_ATTR                    = "type";

    // Common Panel Attributes
    /**@deprecated */
    String   PANEL_CLASS_ATTR       = "panelClass";
    String   FOOTER_CLASS_ATTR      = "footerClass";
    String   HEADER_CLASS_ATTR      = "headerClass";
    String   COLUMN_CLASSES_ATTR    = "columnClasses";
    String   ROW_CLASSES_ATTR       = "rowClasses";

    // Panel_Grid Attributes
    String   COLUMNS_ATTR          = "columns";
    String   COLSPAN_ATTR          = "colspan"; // extension
    String   CAPTION_CLASS_ATTR    = "captionClass";
    String   CAPTION_STYLE_ATTR    = "captionStyle";

    // UIMessage and UIMessages attributes
    String SHOW_SUMMARY_ATTR            = "showSummary";
    String SHOW_DETAIL_ATTR             = "showDetail";
    String GLOBAL_ONLY_ATTR             = "globalOnly";

    // HtmlOutputMessage attributes
    String ERROR_CLASS_ATTR            = "errorClass";
    String ERROR_STYLE_ATTR            = "errorStyle";
    String FATAL_CLASS_ATTR            = "fatalClass";
    String FATAL_STYLE_ATTR            = "fatalStyle";
    String INFO_CLASS_ATTR             = "infoClass";
    String INFO_STYLE_ATTR             = "infoStyle";
    String WARN_CLASS_ATTR             = "warnClass";
    String WARN_STYLE_ATTR             = "warnStyle";
    String TITLE_ATTR                  = "title";
    String TOOLTIP_ATTR                = "tooltip";

    // GraphicImage attributes
    String URL_ATTR                    = "url";

    // UISelectItem attributes
    String ITEM_DISABLED_ATTR          = "itemDisabled";
    String ITEM_DESCRIPTION_ATTR       = "itemDescription";
    String ITEM_LABEL_ATTR             = "itemLabel";
    String ITEM_VALUE_ATTR             = "itemValue";
    String ITEM_ESCAPED_ATTR           = "itemEscaped";

    // UIData attributes
    String ROWS_ATTR                   = "rows";
    String VAR_ATTR                    = "var";
    String FIRST_ATTR                  = "first";

    // dataTable (extended) attributes
    String ROW_ID                      = "rowId";
    String ROW_STYLECLASS_ATTR         = "rowStyleClass";
    String ROW_STYLE_ATTR              = "rowStyle";

    // Alternate locations (instead of using AddResource)
    String JAVASCRIPT_LOCATION         = "javascriptLocation";
    String IMAGE_LOCATION              = "imageLocation";
    String STYLE_LOCATION              = "styleLocation";

    String ACCEPTCHARSET_ATTR          = "acceptcharset";
    
    //~ Myfaces Extensions -------------------------------------------------------------------------------

    // UISortData attributes
    String COLUMN_ATTR                 = "column";
    String ASCENDING_ATTR              = "ascending";
    
    // HtmlSelectManyCheckbox attributes
    String LAYOUT_WIDTH_ATTR           = "layoutWidth";

}
