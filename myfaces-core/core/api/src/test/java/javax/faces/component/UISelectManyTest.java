/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.shale.test.base.AbstractJsfTestCase;

import junit.framework.Test;

public class UISelectManyTest extends AbstractJsfTestCase {

  public UISelectManyTest(String name) {
    super(name);
  }

  public void testValidateRequiredNull() {

    facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    selectMany.setRequired(true);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, null);

    assertFalse(selectMany.isValid());
  }

  public void testValidateRequiredEmptyList() {
    
    facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    selectMany.setRequired(true);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, Collections.EMPTY_LIST);

    assertFalse(selectMany.isValid());
  }

  public void testValidateIntArray() {
    
    facesContext.getViewRoot().setLocale(_TEST_LOCALE);
      
    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue(new Integer(1));
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue(new Integer(2));
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue(new Integer(3));
    children.add(three);

    selectMany.validateValue(facesContext, new int[] { 2, 3 });

    assertTrue(selectMany.isValid());
  }

  public void testValidateStringArray() {

    facesContext.getViewRoot().setLocale(_TEST_LOCALE);

    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue("1");
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue("2");
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue("3");
    children.add(three);

    selectMany.validateValue(facesContext, new String[] { "2", "3" });

    assertTrue(selectMany.isValid());
  }

  public void testValidateStringList() {

    facesContext.getViewRoot().setLocale(_TEST_LOCALE);
      
    UISelectMany selectMany = new UISelectMany();
    selectMany.setId("selectMany");
    selectMany.setRendererType(null);
    List children = selectMany.getChildren();

    UISelectItem one = new UISelectItem();
    one.setItemValue("1");
    children.add(one);

    UISelectItem two = new UISelectItem();
    two.setItemValue("2");
    children.add(two);

    UISelectItem three = new UISelectItem();
    three.setItemValue("3");
    children.add(three);

    selectMany.validateValue(facesContext, Arrays.asList(new String[] { "2", "3" }));

    assertTrue(selectMany.isValid());
  }

  static private final Locale _TEST_LOCALE = new Locale("xx", "TEST");
}
