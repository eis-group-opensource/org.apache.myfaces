/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces;

import junit.framework.TestCase;

public class FacesExceptionTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FacesExceptionTest.class);
    }

    public FacesExceptionTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Test method for 'javax.faces.FacesException.FacesException()'
     */
    public void testFacesException() {
        FacesException e = new FacesException();
        assertNull(e.getCause());
        assertNull(e.getMessage());
    }

    /*
     * Test method for 'javax.faces.FacesException.FacesException(Throwable)'
     */
    public void testFacesExceptionThrowable() {
        Throwable t = new Throwable();
        FacesException fe = new FacesException(t);
        assertEquals(t, fe.getCause());
    }

    /*
     * Test method for 'javax.faces.FacesException.FacesException(String)'
     */
    public void testFacesExceptionString() {
        String m = "Message";
        FacesException e = new FacesException(m);
        assertEquals(e.getMessage(), m);
    }

    /*
     * Test method for 'javax.faces.FacesException.FacesException(String, Throwable)'
     */
    public void testFacesExceptionStringThrowable() {
        String m = "Message";
        Throwable t = new Throwable();
        FacesException fe = new FacesException(m, t);
        assertEquals(t, fe.getCause());
        assertEquals(fe.getMessage(), m);
    }

    /*
     * Test method for 'javax.faces.FacesException.getCause()'
     */
    public void testGetCause() {

    }

}
