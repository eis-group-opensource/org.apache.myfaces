/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Convenient class to store whether a dummyForm needs to be rendered and its params.
 * This class will be stored in the request when a dummyForm is needed to be rendered in the page.
 * AddResources will add it from a method called from the ExtensionsFilter.
 * <p>
 * All this replaces the old system based in a DummyFormResponseWriter
 *
 * @author Bruno Aranda (latest modification by $Author: grantsmith $)
 * @version $Revision: 472792 $ $Date: 2006-11-09 08:34:47 +0200 (Thu, 09 Nov 2006) $
 */
public class DummyFormRequestInfo
{

   private Set _dummyFormParams = null;

   public String getDummyFormName()
   {
       return DummyFormUtils.DUMMY_FORM_NAME;
   }

   public void addDummyFormParameter(String paramName)
   {
       if (_dummyFormParams == null)
       {
           _dummyFormParams = new HashSet();
       }
       _dummyFormParams.add(paramName);
   }

   public Set getDummyFormParams()
   {
       return _dummyFormParams;
   }
}
