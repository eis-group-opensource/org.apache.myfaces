/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.el;

/**
 * Provides methods to read, write and inspect properties of javabeans, Maps,
 * Arrays and Lists. This class is used by such things as the ValueBinding
 * implementation and the ManagedBeanBuilder to access JSF beans.
 *
 * See the javadoc of the <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 * for more details.
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 * @deprecated
 */
public abstract class PropertyResolver
{

    /**
     * @deprecated
     */
    public PropertyResolver()
    {
    }
    
    /**
     * Returns the datatype of the specified element within a list or array.
     * <p>
     * Param base must be of type Array or List.
     *
     * @deprecated
     */
    public abstract Class getType(Object base, int index)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * Returns the datatype of the specified javabean property on the
     * specified object.
     * <p>
     * Param base may be a map, in which case param property is used
     * as a key into the map, and the type of the object with that key
     * is returned. If there is no such key in the map, then Object.class
     * is returned.
     * <p>
     * Otherwise java.beans.Introspector is used to determine the actual
     * property type. If the base bean has no such property then a
     * PropertyNotFoundException is thrown.
     * 
     * @param base must not be null.
     * @param property must be of type String, must not be null and
     *  must not be an empty string.
     * @deprecated
     */
    public abstract Class getType(Object base, java.lang.Object property)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * Return the specified element from a list or array.
     * <p>
     * Param base must be of type Array or List. When the array is of a
     * primitive type, the appropriate wrapper is returned.
     * <p>
     * Null is returned when param index is "out of bounds" for the provided
     * base object.
     *  
     * @throws ReferenceSyntaxException if the base object is not an Array
     * or List.
     * @deprecated
     */
    public abstract Object getValue(Object base, int index)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * Return the current value of the specified property on the base
     * object.
     * <p>
     * If base is a Map, then Map.get(property) is returned. Null is
     * returned if there is no entry with that key.
     * <p>
     * Otherwise, java.beans.Introspector is applied to the base object
     * to find the associated PropertyDescriptor and the specified
     * read method is invoked.
     * 
     * @throws PropertyNotFoundException if the provided object does
     * not have the specified property.
     * @deprecated
     */
    public abstract Object getValue(Object base, java.lang.Object property)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * @deprecated
     */
    public abstract boolean isReadOnly(Object base, int index)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * @deprecated
     */
    public abstract boolean isReadOnly(Object base, java.lang.Object property)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * Replace the object at the specified index within the base collection
     * with the provided value.
     * <p>
     * Param base is expected to be an Array or List object.
     * 
     * @throws EvaluationException if the base object is not an Array or List.
     * @throws PropertyNotFoundException if the index is "out of bounds".
     *
     * @deprecated
     */
    public abstract void setValue(Object base, int index, java.lang.Object value)
        throws EvaluationException, PropertyNotFoundException;

    /**
     * Set the named property on the base object to the provided value.
     *
     * @deprecated
     */
    public abstract void setValue(Object base, Object property, java.lang.Object value)
        throws EvaluationException, PropertyNotFoundException;
}
