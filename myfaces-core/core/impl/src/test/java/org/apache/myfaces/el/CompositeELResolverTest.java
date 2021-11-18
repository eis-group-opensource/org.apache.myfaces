/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el;

import static org.easymock.EasyMock.*;
import static org.testng.AssertJUnit.*;

import java.beans.FeatureDescriptor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.el.ELContext;
import javax.el.ELResolver;

import org.easymock.IAnswer;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 527076 $ $Date: 2007-04-10 13:01:32 +0300 (Tue, 10 Apr 2007) $
 */
public class CompositeELResolverTest
{
    private IMocksControl _mocksControl;
    private ELContext _elContext;
    private CompositeELResolver _testImpl;

    @BeforeMethod
    public void setup()
    {
        _mocksControl = EasyMock.createControl();
        _elContext = _mocksControl.createMock(ELContext.class);
        _testImpl = new CompositeELResolver();
    }

    @Test
    public void testFeatureDescriptorsIterator()
    {
        ELResolver notnullFDResolver = _mocksControl.createMock(ELResolver.class);
        ELResolver emptyFDResolver = _mocksControl.createMock(ELResolver.class);
        Object base = new Object();

        _testImpl.add(notnullFDResolver);
        _testImpl.add(emptyFDResolver);
        _testImpl.add(notnullFDResolver);

        final List<FeatureDescriptor> fds = new ArrayList<FeatureDescriptor>();
        fds.add(new FeatureDescriptor());
        fds.add(null);
        fds.add(new FeatureDescriptor());
        fds.add(null);
        expect(notnullFDResolver.getFeatureDescriptors(eq(_elContext), eq(base))).andAnswer(
                new IAnswer<Iterator<FeatureDescriptor>>()
                {
                    public Iterator<FeatureDescriptor> answer() throws Throwable
                    {
                        return fds.iterator();
                    }
                }).anyTimes();

        expect(emptyFDResolver.getFeatureDescriptors(eq(_elContext), eq(base))).andReturn(
                Collections.EMPTY_LIST.iterator());

        _mocksControl.replay();

        Iterator<FeatureDescriptor> descriptors = _testImpl.getFeatureDescriptors(_elContext, base);

        assertNotNull(descriptors);
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(0), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(2), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(0), descriptors.next());
        assertEquals(true, descriptors.hasNext());
        assertEquals(fds.get(2), descriptors.next());
        assertEquals(false, descriptors.hasNext());

    }
}
