/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.custom.dojo;

import java.io.Serializable;


/**
 * Dojo configuration holder helper
 * this is a helper class to generate
 * a dojo configuration
 * if a null value is set the toString
 * ignores the djconfig
 *
 * the toString method generates a full djconfig
 *
 * Warning if you adjust the dojo config params
 * do it before rendering
 * because this stuff is shared in the request
 * as a singleton value in normal circumstances!
 *
 * (unless you want to influence the dojo config
 * seriously afterwards please, follow this guideline
 * use the decode phase or something similar if not available)
 *
 * @author Werner Punz (latest modification by $Author: grantsmith $)
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public class DojoConfig implements Serializable {

    /**
     *
     */
    private static final long   serialVersionUID      = 1L;
    private static final String CONFIG_FOOTER         = "}; \n";
    private static final String CONFIG_HEADER         = "var djConfig = { \n";
    public static String        ATTR_DOJO_TYPE        = "dojoType";
    Boolean                     _allowQueryConfig     = null;
    String                      _baseScriptUri        = null;
    String                      _bindEncoding         = null;
    Boolean                     _debug                = null;
    Boolean                     _debugAtAllCosts      = null;
    String                      _debugContainerId     = null;
    Boolean                     _ignoreClassNames     = null;
    String                      _ioSendTransport      = null;
    Boolean                     _parseWidgets         = null;
    Boolean                     _preventBackButtonFix = null;
    String                      _searchIds            = null;
    Boolean                     _development           = null;
    
    //getters and setters for the djconfig
    public Boolean getAllowQueryConfig() {
        return _allowQueryConfig;
    }

    public String getBaseScriptUri() {
        return _baseScriptUri;
    }

    public String getBindEncoding() {
        return _bindEncoding;
    }

    public Boolean getDebug() {
        return _debug;
    }

    public Boolean getDebugAtAllCosts() {
        return _debugAtAllCosts;
    }

    public String getDebugContainerId() {
        return _debugContainerId;
    }

    public Boolean getIgnoreClassNames() {
        return _ignoreClassNames;
    }

    public String getIoSendTransport() {
        return _ioSendTransport;
    }

    public Boolean getParseWidgets() {
        return _parseWidgets;
    }

    public Boolean getPreventBackButtonFix() {
        return _preventBackButtonFix;
    }

    public String getSearchIds() {
        return _searchIds;
    }

    public void setAllowQueryConfig(Boolean allowQueryConfig) {
        this._allowQueryConfig = allowQueryConfig;
    }

    public void setBaseScriptUri(String baseScriptUri) {
        this._baseScriptUri = baseScriptUri;
    }

    public void setBindEncoding(String bindEncoding) {
        this._bindEncoding = bindEncoding;
    }

    public void setDebug(Boolean debug) {
        this._debug = debug;
    }

    public void setDebugAtAllCosts(Boolean debugAtAllCosts) {
        this._debugAtAllCosts = debugAtAllCosts;
    }

    public void setDebugContainerId(String debugContainerId) {
        this._debugContainerId = debugContainerId;
    }

    public void setIgnoreClassNames(Boolean ignoreClassNames) {
        this._ignoreClassNames = ignoreClassNames;
    }

    public void setIoSendTransport(String ioSendTransport) {
        this._ioSendTransport = ioSendTransport;
    }

    public void setParseWidgets(Boolean parseWidgets) {
        this._parseWidgets = parseWidgets;
    }

    public void setPreventBackButtonFix(Boolean preventBackButtonFix) {
        this._preventBackButtonFix = preventBackButtonFix;
    }

    public void setSearchIds(String searchIds) {
        this._searchIds = searchIds;
    }

    /**
     * returns a valid djconfig string
     */
    public String toString() {

        StringBuffer configBuilder = new StringBuffer(128);
        configBuilder.append(CONFIG_HEADER);

        createConfigEntry(configBuilder, "ioSendTransport", _ioSendTransport);
        createConfigEntry(configBuilder, "isDebug", _debug);
        createConfigEntry(configBuilder, "baseScriptUri", _baseScriptUri);
        createConfigEntry(configBuilder, "allowQueryConfig", _allowQueryConfig);
        createConfigEntry(configBuilder, "debugContainerId", _debugContainerId);
        createConfigEntry(configBuilder, "searchIds", _searchIds);
        createConfigEntry(configBuilder, "parseWidgets", _parseWidgets);
        createConfigEntry(configBuilder, "bindEncoding", _bindEncoding);
        createConfigEntry(configBuilder, "ignoreClassNames", _ignoreClassNames);
        createConfigEntry(configBuilder, "preventBackButtonFix", _preventBackButtonFix);
        createConfigEntry(configBuilder, "debugAtAllCosts", _debugAtAllCosts);

        configBuilder.append("\n");
        configBuilder.append(CONFIG_FOOTER);

        return configBuilder.toString();
    }

    /**
     * helper to create a new config entry in the expected djConfig syntax
     * @param target    the target stringbuffer which gets all the values
     * @param configKey      the name of the entry
     * @param configValue     the value of the entry
     */
    private final void createConfigEntry(StringBuffer target, String configKey, Object configValue) {

        if (configValue == null)
            return;

        if (target.indexOf(":") != -1)
            target.append(",\n");

        target.append(configKey);
        target.append(":");
        //strings have to be handled separately because we have to have them in quotes
        boolean isStringValue = (configValue instanceof String);
        if(isStringValue)
            target.append("'");
        target.append(configValue);
        if(isStringValue)
            target.append("'");

    }

    public Boolean getDevelopment() {
        return _development;
    }

    public void setDevelopment(Boolean development) {
        this._development = development;
    }

}
