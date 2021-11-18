/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.facelets.tag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.el.MethodExpression;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

/**
 * Optional Rule for binding Method[Binding|Expression] properties
 * 
 * @author Mike Kienenberger
 * @author Jacob Hookom
 */
public final class MethodRule extends MetaRule
{

    private final String methodName;

    private final Class<?> returnTypeClass;

    private final Class<?>[] params;

    public MethodRule(String methodName, Class<?> returnTypeClass, Class<?>[] params)
    {
        this.methodName = methodName;
        this.returnTypeClass = returnTypeClass;
        this.params = params;
    }

    public Metadata applyRule(String name, TagAttribute attribute, MetadataTarget meta)
    {
        if (false == name.equals(this.methodName))
            return null;

        if (MethodExpression.class.equals(meta.getPropertyType(name)))
        {
            Method method = meta.getWriteMethod(name);
            if (method != null)
            {
                return new MethodExpressionMetadata(method, attribute, this.returnTypeClass, this.params);
            }
        }

        return null;
    }

    private class MethodExpressionMetadata extends Metadata
    {
        private final Method _method;

        private final TagAttribute _attribute;

        private Class<?>[] _paramList;

        private Class<?> _returnType;

        public MethodExpressionMetadata(Method method, TagAttribute attribute, Class<?> returnType, 
                                        Class<?>[] paramList)
        {
            _method = method;
            _attribute = attribute;
            _paramList = paramList;
            _returnType = returnType;
        }

        public void applyMetadata(FaceletContext ctx, Object instance)
        {
            MethodExpression expr = _attribute.getMethodExpression(ctx, _returnType, _paramList);

            try
            {
                _method.invoke(instance, new Object[] { expr });
            }
            catch (InvocationTargetException e)
            {
                throw new TagAttributeException(_attribute, e.getCause());
            }
            catch (Exception e)
            {
                throw new TagAttributeException(_attribute, e);
            }
        }
    }
}
