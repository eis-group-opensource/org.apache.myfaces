/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.shale.test.jmock.AbstractJmockJsfTestCase;

/**
 * Abstract basis clazz for Apache MyFaces' test kit.
 * 
 * @author Matthias Wessendorf
 */
public abstract class AbstractComponentTest extends AbstractJmockJsfTestCase
{
  public AbstractComponentTest(String arg0)
  {
    super(arg0);
  }
  
  /**
   * TODO
   */
  protected void setUp() throws Exception
  {
      super.setUp();
  }

  /**
   * TODO
   */
  protected void tearDown() throws Exception
  {
      super.tearDown();
  }
}