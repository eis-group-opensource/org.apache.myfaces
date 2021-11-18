/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public interface ActionSource
{
    /**
     * @deprecated Replaced by ActionSource2.getActionExpression
     */
    public javax.faces.el.MethodBinding getAction();

    /**
     * @deprecated Replaced by ActionSource2.setActionExpression
     */
    public void setAction(javax.faces.el.MethodBinding action);

    /**
     * @deprecated Replaced by getActionListeners
     */
    public javax.faces.el.MethodBinding getActionListener();

    public void setActionListener(javax.faces.el.MethodBinding actionListener);

    public boolean isImmediate();

    public void setImmediate(boolean immediate);

    public void addActionListener(javax.faces.event.ActionListener listener);

    public javax.faces.event.ActionListener[] getActionListeners();

    public void removeActionListener(javax.faces.event.ActionListener listener);
}
