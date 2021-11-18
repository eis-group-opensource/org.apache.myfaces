/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

/**
 * Interface implemented by components that provide a new "namespace" for the ids of their
 * child components.
 * <p>
 * Component ids must be unique between all descendants of a NamingContainer; the JSF library
 * will report a fatal error and refuse to process or render any view where two components
 * in the same NamingContainer have identical id values. However a component that is a descendant
 * of one NamingContainer component is permitted to have the same id as a component that is a
 * descendant of a different NamingContainer component.
 * <p>
 * Unique component ids are used to:
 * <ul>
 * <li>generate unique names for HTML form fields, etc</li>
 * <li>search for components via UIComponent.findComponent(String expr)</li>
 * <li>on re-render after postback, match up component declarations in the view templates
 * with existing components in the restored view tree.
 * </ul>
 * <p>
 * Requiring every component in a large view (which is possibly built by including or
 * composing multiple files together) to have an id which is different from every other id
 * is simply unmanageable; JSF certainly must provide <i>some</i> kind of id namespacing.
 * Therefore this base class is defined, and a few standard JSF components subclass it
 * (in particular, f:subview).
 * <p>
 * When generating clientId values during rendering, descendants of a NamingContainer instance
 * are allocated a clientId which is their own id prefixed with the clientId of the ancestor
 * NamingContainer, eg "parentId:childId". NamingContainer components can be nested within
 * other NamingContainer components, generating clientIds like "firstId:middleId:leafId". 
 * <p>
 * Not every component is a naming container; that would technically work, but the clientId
 * values generated would quickly grow excessively long.
 * <p>
 * See the javadoc for this class in the 
 * <a href="http://java.sun.com/j2ee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 * for further details.
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 685651 $ $Date: 2008-08-13 22:36:16 +0300 (Wed, 13 Aug 2008) $
 */
public interface NamingContainer
{
    public static final char SEPARATOR_CHAR = ':';
}
