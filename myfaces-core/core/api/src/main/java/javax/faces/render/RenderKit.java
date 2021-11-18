/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.render;

import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseStream;
import java.io.Writer;
import java.io.OutputStream;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public abstract class RenderKit
{
    public abstract void addRenderer(String family,
                                     String rendererType,
                                     Renderer renderer);

    public abstract Renderer getRenderer(String family,
                                         String rendererType);

    public abstract ResponseStateManager getResponseStateManager();

    public abstract ResponseWriter createResponseWriter(Writer writer,
                                                        String contentTypeList,
                                                        String characterEncoding);

    public abstract ResponseStream createResponseStream(OutputStream out);

}
