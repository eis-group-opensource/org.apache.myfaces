/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.config.annotation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;



public class TestDiscoverableLifecycleProvider implements DiscoverableLifecycleProvider
{
    private LifecycleProvider processor = new NoInjectionAnnotationLifecycleProvider();

    public boolean isAvailable()
    {
        return true;
    }

    public Object newInstance(String className) throws InstantiationException, IllegalAccessException, NamingException, InvocationTargetException, ClassNotFoundException
    {
        return processor.newInstance(className);
    }


    public void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException
    {
       processor.destroyInstance(o);
    }
}
