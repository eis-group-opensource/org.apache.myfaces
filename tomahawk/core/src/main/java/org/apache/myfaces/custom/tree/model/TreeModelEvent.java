/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.model;


/**
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public class TreeModelEvent
{

    private Object source;
    private TreePath path;
    private int[] childIndices;
    private Object[] children;


    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * an array of Objects. All of the modified objects are siblings which are
     * direct descendents (not grandchildren) of the specified parent.
     * The positions at which the inserts, deletes, or changes occurred are
     * specified by an array of <code>int</code>. The indexes in that array
     * must be in order, from lowest to highest.
     *
     * @param source       the Object responsible for generating the event
     * @param path         an array of Object identifying the path to the parent of the modified item(s)
     * @param childIndices array that specifies the
     *                     index values of the removed items. The indices must be in sorted order, from lowest to highest
     * @param children     an array containing the inserted, removed, or changed objects
     */
    public TreeModelEvent(Object source, Object[] path, int[] childIndices, Object[] children)
    {
        this(source, new TreePath(path), childIndices, children);
    }


    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * a TreePath object.
     *
     * @param source       the Object responsible for generating the event
     * @param path         a TreePath object that identifies the path to the parent of the modified item(s)
     * @param childIndices array that specifies the index values of the modified items
     * @param children     an array containing the inserted, removed, or changed objects
     * @see #TreeModelEvent(Object,Object[],int[],Object[])
     */
    public TreeModelEvent(Object source, TreePath path, int[] childIndices, Object[] children)
    {
        this.source = source;
        this.path = path;
        this.childIndices = childIndices;
        this.children = children;
    }


    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * a TreePath object.
     *
     * @param source the Object responsible for generating the event
     * @param path   an array of Object identifying the path to the parent of the modified item(s)
     */
    public TreeModelEvent(Object source, Object[] path)
    {
        this(source, new TreePath(path));
    }


    /**
     * Used to create an event when nodes have been changed, inserted, or
     * removed, identifying the path to the parent of the modified items as
     * a TreePath object.
     *
     * @param source the Object responsible for generating the event
     * @param path   a TreePath object that identifies the path to the parent of the modified item(s)
     */
    public TreeModelEvent(Object source, TreePath path)
    {
        this.source = source;
        this.path = path;
        this.childIndices = new int[0];
    }


    /**
     * Answer the source of this event
     *
     * @return the source of this event
     */
    public Object getSource()
    {
        return source;
    }


    /**
     * For all events, except treeStructureChanged,
     * returns the parent of the changed nodes.
     * For treeStructureChanged events, returns the ancestor of the
     * structure that has changed. This and
     * <code>getChildIndices</code> are used to get a list of the effected
     * nodes.
     * <p/>
     * The one exception to this is a treeNodesChanged event that is to
     * identify the root, in which case this will return the root
     * and <code>getChildIndices</code> will return null.
     *
     * @return the TreePath used in identifying the changed nodes.
     */
    public TreePath getTreePath()
    {
        return path;
    }


    /**
     * Return the objects that are children of the node identified by
     * the path of this event at the locations specified by
     * <code>getChildIndices</code>. If this is a removal event the
     * returned objects are no longer children of the parent node.
     *
     * @return an array of Object containing the children specified by
     *         the event
     */
    public Object[] getChildren()
    {
        if (children != null)
        {
            Object[] answer = new Object[children.length];

            System.arraycopy(children, 0, answer, 0, children.length);
            return answer;
        }
        return null;
    }


    /**
     * Returns the values of the child indexes. If this is a removal event
     * the indexes point to locations in the initial list where items
     * were removed. If it is an insert, the indices point to locations
     * in the final list where the items were added. For node changes,
     * the indices point to the locations of the modified nodes.
     *
     * @return an array containing index locations for the children specified by the event
     */
    public int[] getChildIndices()
    {
        if (childIndices != null)
        {
            int[] answer = new int[childIndices.length];

            System.arraycopy(childIndices, 0, answer, 0, childIndices.length);
            return answer;
        }
        return null;
    }


    /**
     * Returns a string that displays and identifies this object's
     * properties.
     *
     * @return a String representation of this object
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(super.toString());
        if (path != null)
        {
            buffer.append(" path " + path);
        }
        if (childIndices != null)
        {
            buffer.append(" indices [ ");
            for (int i = 0; i < childIndices.length; i++)
            {
                buffer.append(Integer.toString(childIndices[i]) + " ");
            }
            buffer.append("]");
        }
        if (children != null)
        {
            buffer.append(" children [ ");
            for (int i = 0; i < children.length; i++)
            {
                buffer.append(children[i] + " ");
            }
            buffer.append("]");
        }
        return buffer.toString();
    }

}
