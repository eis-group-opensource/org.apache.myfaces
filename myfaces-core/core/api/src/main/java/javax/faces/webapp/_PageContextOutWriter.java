/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.Writer;

/**
 * This Writer writes always to the current pageContext.getOut() Writer.
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
class _PageContextOutWriter
        extends Writer
{
    private PageContext _pageContext;

    public _PageContextOutWriter(PageContext pageContext)
    {
        _pageContext = pageContext;
    }

    public void close() throws IOException
    {
        _pageContext.getOut().close();
    }

    public void flush() throws IOException
    {
        _pageContext.getOut().flush();
    }

    public void write(char cbuf[], int off, int len) throws IOException
    {
        _pageContext.getOut().write(cbuf, off, len);
    }

    public void write(int c) throws IOException
    {
        _pageContext.getOut().write(c);
    }

    public void write(char cbuf[]) throws IOException
    {
        _pageContext.getOut().write(cbuf);
    }

    public void write(String str) throws IOException
    {
        _pageContext.getOut().write(str);
    }

    public void write(String str, int off, int len) throws IOException
    {
        _pageContext.getOut().write(str, off, len);
    }

}
