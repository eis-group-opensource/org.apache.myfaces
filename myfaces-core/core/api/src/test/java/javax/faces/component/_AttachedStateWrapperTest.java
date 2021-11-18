/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

public class _AttachedStateWrapperTest extends TestCase {

  public static void main(String[] args) {
    junit.textui.TestRunner.run(_AttachedStateWrapperTest.class);
  }

  public _AttachedStateWrapperTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'javax.faces.component._AttachedStateWrapper._AttachedStateWrapper(Class, Object)'
   */
  public void test_AttachedStateWrapper() {
    _AttachedStateWrapper subject = new _AttachedStateWrapper(null, null);
    assertNull(subject.getWrappedStateObject());
    assertNull(subject.getClazz());
  }

  /*
   * Test method for 'javax.faces.component._AttachedStateWrapper.getClazz()'
   */
  public void testGetClazz() {
    _AttachedStateWrapper subject = new _AttachedStateWrapper(String.class, "foo");
    assertEquals(subject.getClazz(), String.class);
  }

  /*
   * Test method for 'javax.faces.component._AttachedStateWrapper.getWrappedStateObject()'
   */
  public void testGetWrappedStateObject() {
    _AttachedStateWrapper subject = new _AttachedStateWrapper(String.class, "foo");
    assertEquals(subject.getClazz(), String.class);
  }

  public void testSerialize() throws Exception {
    String foo = "foo";
    _AttachedStateWrapper subject = new _AttachedStateWrapper(String.class, foo);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(subject);
    oos.flush();
    baos.flush();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);
    _AttachedStateWrapper blorg = (_AttachedStateWrapper) ois
        .readObject();
    assertEquals(blorg.getWrappedStateObject(), subject.getWrappedStateObject());
    oos.close();
    ois.close();
  }
}
