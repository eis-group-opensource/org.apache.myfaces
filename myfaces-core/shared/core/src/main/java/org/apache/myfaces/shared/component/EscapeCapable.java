/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.component;

/**
 * EscapeCapable interface for extended components
 * By default, escape is true, and the components have the default behaviour.
 * When escape is false, the renderer should not escape output.
 *
 * @author Grant Smith (latest modification by $Author: grantsmith $)
 */

public interface EscapeCapable {
    boolean isEscape();
    void setEscape(boolean escape);
}
