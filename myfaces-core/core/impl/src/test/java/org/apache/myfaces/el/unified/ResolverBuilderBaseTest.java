/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.el.unified;

import static org.easymock.EasyMock.*;

import javax.el.ELResolver;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.el.convert.PropertyResolverToELResolver;
import org.apache.myfaces.el.convert.VariableResolverToELResolver;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mathias Broekelmann (latest modification by $Author: jakobk $)
 * @version $Revision: 986159 $ $Date: 2010-08-17 02:18:37 +0300 (Tue, 17 Aug 2010) $
 */
@SuppressWarnings("deprecation")
public class ResolverBuilderBaseTest extends TestCase
{
    private IMocksControl _mocksControl;
    private RuntimeConfig _runtimeConfig;
    private ResolverBuilderBase _testImpl;
    private List<ELResolver> _resolvers;

    public ResolverBuilderBaseTest()
    {
    }

    public void setUp()
    {
        _mocksControl = EasyMock.createNiceControl();
        _runtimeConfig = _mocksControl.createMock(RuntimeConfig.class);
        _testImpl = new ResolverBuilderBase(_runtimeConfig);
        _resolvers = new ArrayList<ELResolver>();
    }

    public void tearDown()
    {
        _mocksControl = null;
        _runtimeConfig = null;
        _testImpl = null;
        _resolvers = null;
    }

    /*
    public void testGetFacesConfigElResolvers() throws Exception
    {
        ELResolver resolver = _mocksControl.createMock(ELResolver.class);
        expect(_runtimeConfig.getFacesConfigElResolvers()).andReturn(resolver).anyTimes();
        _compositeELResolver = _mocksControl.createMock(CompositeELResolver.class);
        _compositeELResolver.add(eq(resolver));
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_compositeELResolver);
        _mocksControl.verify();
    }*/

    public void testGetApplicationElResolvers() throws Exception
    {
        ELResolver resolver = _mocksControl.createMock(ELResolver.class);
        expect(_runtimeConfig.getApplicationElResolvers()).andReturn(Arrays.asList(resolver)).anyTimes();
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_resolvers);
        _mocksControl.verify();
        Assert.assertEquals(Arrays.asList(resolver), _resolvers);
    }

    public void testGetVariableResolver() throws Exception
    {
        VariableResolver resolver = _mocksControl.createMock(VariableResolver.class);
        expect(_runtimeConfig.getVariableResolver()).andReturn(resolver).anyTimes();
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_resolvers);
        _mocksControl.verify();

        VariableResolverToELResolver elResolver
                = (VariableResolverToELResolver) _resolvers.get(0);
        Assert.assertEquals(resolver, elResolver.getVariableResolver());
    }

    public void testGetVariableResolverChainHead() throws Exception
    {
        VariableResolver resolver = _mocksControl.createMock(VariableResolver.class);
        EasyMock.expect(_runtimeConfig.getVariableResolverChainHead()).andReturn(resolver).anyTimes();
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_resolvers);
        _mocksControl.verify();

        VariableResolverToELResolver elResolver
                = (VariableResolverToELResolver) _resolvers.get(0);
        Assert.assertEquals(resolver, elResolver.getVariableResolver());
    }

    public void testGetPropertyResolver() throws Exception
    {
        PropertyResolver resolver = _mocksControl.createMock(PropertyResolver.class);
        expect(_runtimeConfig.getPropertyResolver()).andReturn(resolver).anyTimes();
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_resolvers);
        _mocksControl.verify();

        PropertyResolverToELResolver elResolver
                = (PropertyResolverToELResolver) _resolvers.get(0);
        Assert.assertEquals(resolver, elResolver.getPropertyResolver());
    }

    public void testGetPropertyResolverChainHead() throws Exception
    {
        PropertyResolver resolver = _mocksControl.createMock(PropertyResolver.class);
        EasyMock.expect(_runtimeConfig.getPropertyResolverChainHead()).andReturn(resolver).anyTimes();
        _mocksControl.replay();
        _testImpl.addFromRuntimeConfig(_resolvers);
        _mocksControl.verify();


        PropertyResolverToELResolver elResolver
                = (PropertyResolverToELResolver) _resolvers.get(0);
        Assert.assertEquals(resolver, elResolver.getPropertyResolver());
    }

}

