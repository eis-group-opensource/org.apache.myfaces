/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class _AttachedListStateWrapperTest extends TestCase {

  public static void main(String[] args) {
    junit.textui.TestRunner.run(_AttachedListStateWrapperTest.class);
  }

  public _AttachedListStateWrapperTest(String name) {
    super(name);
  }

  protected void setUp() throws Exception {
    super.setUp();
  }

  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /*
   * Test method for 'javax.faces.component._AttachedListStateWrapper._AttachedListStateWrapper(List)'
   */
  public void test_AttachedListStateWrapper() {
    List foo = new ArrayList();
    _AttachedListStateWrapper subject = new _AttachedListStateWrapper(foo);
    assertNotNull(subject.getWrappedStateList());
    assertTrue(subject.getWrappedStateList() == foo);
  }

  public void testSerialize() throws Exception {
    String foo = "foo";
    List list = new ArrayList();
    list.add(foo);
    _AttachedListStateWrapper subject = new _AttachedListStateWrapper(list);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(subject);
    oos.flush();
    baos.flush();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);
    _AttachedListStateWrapper blorg = (_AttachedListStateWrapper)ois.readObject();
    assertEquals(blorg.getWrappedStateList(), subject.getWrappedStateList());
    oos.close();
    ois.close();
  }
  
}
