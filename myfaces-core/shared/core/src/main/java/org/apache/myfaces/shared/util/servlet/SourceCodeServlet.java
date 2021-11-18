/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SourceCodeServlet extends HttpServlet 
{
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
        String webPage = req.getServletPath();
        
        // remove the '*.source' suffix that maps to this servlet
        int chopPoint = webPage.indexOf(".source");
        
        webPage = webPage.substring(0, chopPoint - 1);
        webPage += "p"; // replace jsf with jsp
        
        // get the actual file location of the requested resource
        String realPath = getServletConfig().getServletContext().getRealPath(webPage);

        // output an HTML page
        res.setContentType("text/plain");

        // print some html
        ServletOutputStream out = res.getOutputStream();

        // print the file
        InputStream in = null;
        try 
        {
            in = new BufferedInputStream(new FileInputStream(realPath));
            int ch;
            while ((ch = in.read()) !=-1) 
            {
                out.print((char)ch);
            }
        }
        finally {
            if (in != null) in.close();  // very important
        }
    }
}
