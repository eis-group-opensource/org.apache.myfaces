/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.el.ExpressionFactory;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;


/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Anton Koinov
 * @version $Revision: 777341 $ $Date: 2009-05-22 04:06:09 +0300 (Pn, 22 Geg 2009) $
 */
public final class ClassUtils
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final Log log                  = LogFactory.getLog(ClassUtils.class);

    public static final Class BOOLEAN_ARRAY_CLASS = boolean[].class;
    public static final Class BYTE_ARRAY_CLASS    = byte[].class;
    public static final Class CHAR_ARRAY_CLASS    = char[].class;
    public static final Class SHORT_ARRAY_CLASS   = short[].class;
    public static final Class INT_ARRAY_CLASS     = int[].class;
    public static final Class LONG_ARRAY_CLASS    = long[].class;
    public static final Class FLOAT_ARRAY_CLASS   = float[].class;
    public static final Class DOUBLE_ARRAY_CLASS  = double[].class;
    public static final Class OBJECT_ARRAY_CLASS  = Object[].class;
    public static final Class BOOLEAN_OBJECT_ARRAY_CLASS = Boolean[].class;
    public static final Class BYTE_OBJECT_ARRAY_CLASS = Byte[].class;
    public static final Class CHARACTER_OBJECT_ARRAY_CLASS = Character[].class;
    public static final Class SHORT_OBJECT_ARRAY_CLASS = Short[].class;
    public static final Class INTEGER_OBJECT_ARRAY_CLASS = Integer[].class;
    public static final Class LONG_OBJECT_ARRAY_CLASS = Long[].class;
    public static final Class FLOAT_OBJECT_ARRAY_CLASS = Float[].class;
    public static final Class DOUBLE_OBJECT_ARRAY_CLASS = Double[].class;
    public static final Class STRING_OBJECT_ARRAY_CLASS = String[].class;

    public static final Map COMMON_TYPES = new HashMap(64);
    static
    {
        COMMON_TYPES.put("byte", Byte.TYPE);
        COMMON_TYPES.put("char", Character.TYPE);
        COMMON_TYPES.put("double", Double.TYPE);
        COMMON_TYPES.put("float", Float.TYPE);
        COMMON_TYPES.put("int", Integer.TYPE);
        COMMON_TYPES.put("long", Long.TYPE);
        COMMON_TYPES.put("short", Short.TYPE);
        COMMON_TYPES.put("boolean", Boolean.TYPE);
        COMMON_TYPES.put("void", Void.TYPE);
        COMMON_TYPES.put("java.lang.Object", Object.class);
        COMMON_TYPES.put("java.lang.Boolean", Boolean.class);
        COMMON_TYPES.put("java.lang.Byte", Byte.class);
        COMMON_TYPES.put("java.lang.Character", Character.class);
        COMMON_TYPES.put("java.lang.Short", Short.class);
        COMMON_TYPES.put("java.lang.Integer", Integer.class);
        COMMON_TYPES.put("java.lang.Long", Long.class);
        COMMON_TYPES.put("java.lang.Float", Float.class);
        COMMON_TYPES.put("java.lang.Double", Double.class);
        COMMON_TYPES.put("java.lang.String", String.class);

        COMMON_TYPES.put("byte[]", BYTE_ARRAY_CLASS);
        COMMON_TYPES.put("char[]", CHAR_ARRAY_CLASS);
        COMMON_TYPES.put("double[]", DOUBLE_ARRAY_CLASS);
        COMMON_TYPES.put("float[]", FLOAT_ARRAY_CLASS);
        COMMON_TYPES.put("int[]", INT_ARRAY_CLASS);
        COMMON_TYPES.put("long[]", LONG_ARRAY_CLASS);
        COMMON_TYPES.put("short[]", SHORT_ARRAY_CLASS);
        COMMON_TYPES.put("boolean[]", BOOLEAN_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Object[]", OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Boolean[]", BOOLEAN_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Byte[]", BYTE_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Character[]", CHARACTER_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Short[]", SHORT_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Integer[]", INTEGER_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Long[]", LONG_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Float[]", FLOAT_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.Double[]", DOUBLE_OBJECT_ARRAY_CLASS);
        COMMON_TYPES.put("java.lang.String[]", STRING_OBJECT_ARRAY_CLASS);
        // array of void is not a valid type
    }

    /** utility class, do not instantiate */
    private ClassUtils()
    {
        // utility class, disable instantiation
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * Tries a Class.loadClass with the context class loader of the current thread first and
     * automatically falls back to the ClassUtils class loader (i.e. the loader of the
     * myfaces.jar lib) if necessary.
     *
     * @param type fully qualified name of a non-primitive non-array class
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws ClassNotFoundException
     */
    public static Class classForName(String type)
        throws ClassNotFoundException
    {
        if (type == null) throw new NullPointerException("type");
        try
        {
            // Try WebApp ClassLoader first
            return Class.forName(type,
                                 false, // do not initialize for faster startup
                                 getContextClassLoader());
        }
        catch (ClassNotFoundException ignore)
        {
            // fallback: Try ClassLoader for ClassUtils (i.e. the myfaces.jar lib)
            return Class.forName(type,
                                 false, // do not initialize for faster startup
                                 ClassUtils.class.getClassLoader());
        }
    }


    /**
     * Same as {@link #classForName(String)}, but throws a RuntimeException
     * (FacesException) instead of a ClassNotFoundException.
     *
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws FacesException if class not found
     */
    public static Class simpleClassForName(String type)
    {
        try
        {
            return classForName(type);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Class " + type + " not found", e);
            throw new FacesException(e);
        }
    }


    /**
     * Similar as {@link #classForName(String)}, but also supports primitive types
     * and arrays as specified for the JavaType element in the JavaServer Faces Config DTD.
     *
     * @param type fully qualified class name or name of a primitive type, both optionally
     *             followed by "[]" to indicate an array type
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws ClassNotFoundException
     */
    public static Class javaTypeToClass(String type)
        throws ClassNotFoundException
    {
        if (type == null) throw new NullPointerException("type");

        // try common types and arrays of common types first
        Class clazz = (Class) COMMON_TYPES.get(type);
        if (clazz != null)
        {
            return clazz;
        }

        int len = type.length();
        if (len > 2 && type.charAt(len - 1) == ']' && type.charAt(len - 2) == '[')
        {
            String componentType = type.substring(0, len - 2);
            Class componentTypeClass = classForName(componentType);
            return Array.newInstance(componentTypeClass, 0).getClass();
        }

        return classForName(type);
        
    }


    /**
     * Same as {@link #javaTypeToClass(String)}, but throws a RuntimeException
     * (FacesException) instead of a ClassNotFoundException.
     *
     * @return the corresponding Class
     * @throws NullPointerException if type is null
     * @throws FacesException if class not found
     */
    public static Class simpleJavaTypeToClass(String type)
    {
        try
        {
            return javaTypeToClass(type);
        }
        catch (ClassNotFoundException e)
        {
            log.error("Class " + type + " not found", e);
            throw new FacesException(e);
        }
    }

    public static InputStream getResourceAsStream(String resource)
    {
        InputStream stream = getContextClassLoader()
                                .getResourceAsStream(resource);
        if (stream == null)
        {
            // fallback
            stream = ClassUtils.class.getClassLoader().getResourceAsStream(resource);
        }
        return stream;
    }

    /**
     * @param resource       Name of resource(s) to find in classpath
     * @param defaultObject  The default object to use to determine the class loader (if none associated with current thread.)
     * @return Iterator over URL Objects
     */
    public static Iterator getResources(String resource, Object defaultObject)
    {
        try
        {
            Enumeration resources = getCurrentLoader(defaultObject).getResources(resource);
            List lst = new ArrayList();
            while (resources.hasMoreElements())
            {
                lst.add(resources.nextElement());
            }
            return lst.iterator();
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
    }


    public static Object newInstance(String type)
        throws FacesException
    {
        if (type == null) return null;
        return newInstance(simpleClassForName(type));
    }

    public static Object newInstance(String type, Class expectedType) throws FacesException
    {
        return newInstance(type, expectedType == null ? null : new Class[] {expectedType});
    }

    public static Object newInstance(String type, Class[] expectedTypes)
    {
        if (type == null)
            return null;        
        
        Class clazzForName = simpleClassForName(type);
        
        if(expectedTypes != null)
        {
            for (int i = 0, size = expectedTypes.length; i < size; i++)
            {
                if (!expectedTypes[i].isAssignableFrom(clazzForName))
                {
                    throw new FacesException("'" + type + "' does not implement expected type '" + expectedTypes[i]
                            + "'");
                }
            }
        }
        
        return newInstance(clazzForName);
    }

    public static Object newInstance(Class clazz)
        throws FacesException
    {
        try
        {
            return clazz.newInstance();
        }
        catch(NoClassDefFoundError e)
        {
            log.error("Class : "+clazz.getName()+" not found.",e);
            throw new FacesException(e);
        }
        catch (InstantiationException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
        catch (IllegalAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new FacesException(e);
        }
    }

    public static Object convertToType(Object value, Class desiredClass)
    {
        if (value == null) return null;

        try
        {
            ExpressionFactory expFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
            return expFactory.coerceToType(value, desiredClass);
        }
        catch (Exception e)
        {
            String message = "Cannot coerce " + value.getClass().getName()
                             + " to " + desiredClass.getName();
            log.error(message, e);
            throw new FacesException(message, e);
        }
    }

    /**
     * Gets the ClassLoader associated with the current thread.  Returns the class loader associated with
     * the specified default object if no context loader is associated with the current thread.
     *
     * @param defaultObject The default object to use to determine the class loader (if none associated with current thread.)
     * @return ClassLoader
     */
    protected static ClassLoader getCurrentLoader(Object defaultObject)
    {
        ClassLoader loader = getContextClassLoader();
        if(loader == null)
        {
            loader = defaultObject.getClass().getClassLoader();
        }
        return loader;
    }
    
    /**
     * Gets the ClassLoader associated with the current thread.  Includes a check for priviledges 
     * against java2 security to ensure no security related exceptions are encountered. 
     *
     * @since 3.0.6
     * @return ClassLoader
     */
    public static ClassLoader getContextClassLoader()
    {
        if (System.getSecurityManager() != null) 
        {
            try {
                ClassLoader cl = AccessController.doPrivileged(new PrivilegedExceptionAction<ClassLoader>()
                        {
                            public ClassLoader run() throws PrivilegedActionException
                            {
                                return Thread.currentThread().getContextClassLoader();
                            }
                        });
                return cl;
            }
            catch (PrivilegedActionException pae)
            {
                throw new FacesException(pae);
            }
        }
        else
        {
            return Thread.currentThread().getContextClassLoader();
        }
    }
}
