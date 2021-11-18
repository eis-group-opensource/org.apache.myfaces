/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces;

/**
 * Implement a testcase which might throw an exception
 *  
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 511863 $ $Date: 2007-02-26 17:46:34 +0200 (Mon, 26 Feb 2007) $
 */
public interface TestRunner {
    void run() throws Throwable;
}
