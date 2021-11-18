/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.validators;

import javax.faces.validator.ValidatorException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.custom.emailvalidator.EmailValidator;

public class EmailValidatorTestCase extends AbstractValidatorTestCase
{

  public EmailValidatorTestCase(String arg0) {
    super(arg0);
  }
  
  EmailValidator emailValidator;
  
  protected void setUp() throws Exception
  {
    super.setUp();
    emailValidator = new EmailValidator();    
  }

  protected void tearDown() throws Exception
  {
    super.tearDown();
  }

  public static Test suite()
  {
    return new TestSuite(EmailValidatorTestCase.class);
  }

  /**
   * Test when context is set to null
   */
  public void testNullContext()
  {

    doTestNullContext(component, emailValidator);
  }
  
  public void testWrongEmailAddress()
  {
    try
    {
      emailValidator.validate(facesContext, component, "matzew@apache");
      fail("Expected ValidatorException");
    }
    catch (ValidatorException ve)
    {
      // if exception then fine.
    }

  }

  public void testGoodEmailAddress()
  {
      emailValidator.validate(facesContext, component, "foo@apache.org");
  }
  
}