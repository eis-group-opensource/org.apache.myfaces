/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.render;

import javax.faces.context.FacesContext;
import java.util.Iterator;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class RenderKitFactory
{
    public static final String HTML_BASIC_RENDER_KIT = "HTML_BASIC";

    public abstract void addRenderKit(String renderKitId,
                                      RenderKit renderKit);

    public abstract RenderKit getRenderKit(FacesContext context,
                                           String renderKitId);

    public abstract Iterator<String> getRenderKitIds();
}
