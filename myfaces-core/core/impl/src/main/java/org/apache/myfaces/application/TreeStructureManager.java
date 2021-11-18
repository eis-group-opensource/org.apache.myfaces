/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

import org.apache.myfaces.shared_impl.util.ClassUtils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 684459 $ $Date: 2008-08-10 14:13:56 +0300 (Sun, 10 Aug 2008) $
 */
public class TreeStructureManager
{
    //private static final Log log = LogFactory.getLog(TreeStructureManager.class);

    //private FacesContext _facesContext;

    public TreeStructureManager()
    {
        //_facesContext = facesContext;
    }

    public Object buildTreeStructureToSave(UIViewRoot viewRoot)
    {
        return internalBuildTreeStructureToSave(viewRoot);
    }

    private TreeStructComponent internalBuildTreeStructureToSave(UIComponent component)
    {
        TreeStructComponent structComp = new TreeStructComponent(component.getClass().getName(),
                                                                 component.getId());

        //children
        if (component.getChildCount() > 0)
        {
            List childList = component.getChildren();
            List<TreeStructComponent> structChildList = new ArrayList<TreeStructComponent>();
            for (int i = 0, len = childList.size(); i < len; i++)
            {
                UIComponent child = (UIComponent)childList.get(i);
                if (!child.isTransient())
                {
                    TreeStructComponent structChild = internalBuildTreeStructureToSave(child);
                    structChildList.add(structChild);
                }
            }
            TreeStructComponent[] childArray = structChildList.toArray(new TreeStructComponent[structChildList.size()]);
            structComp.setChildren(childArray);
        }

        //facets
        Map<String, UIComponent> facetMap = component.getFacets();
        if (!facetMap.isEmpty())
        {
            List<Object[]> structFacetList = new ArrayList<Object[]>();
            for (Iterator it = facetMap.entrySet().iterator(); it.hasNext(); )
            {
                Map.Entry entry = (Map.Entry)it.next();
                UIComponent child = (UIComponent)entry.getValue();
                if (!child.isTransient())
                {
                    String facetName = (String)entry.getKey();
                    TreeStructComponent structChild = internalBuildTreeStructureToSave(child);
                    structFacetList.add(new Object[] {facetName, structChild});
                }
            }
            Object[] facetArray = structFacetList.toArray(new Object[structFacetList.size()]);
            structComp.setFacets(facetArray);
        }

        return structComp;
    }


    public UIViewRoot restoreTreeStructure(Object treeStructRoot)
    {
        if (treeStructRoot instanceof TreeStructComponent)
        {
            return (UIViewRoot)internalRestoreTreeStructure((TreeStructComponent)treeStructRoot);
        }
        
        
        throw new IllegalArgumentException("TreeStructure of type " + treeStructRoot.getClass().getName() + " is not supported.");
        
    }

    private UIComponent internalRestoreTreeStructure(TreeStructComponent treeStructComp)
    {
        String compClass = treeStructComp.getComponentClass();
        String compId = treeStructComp.getComponentId();
        UIComponent component = (UIComponent)ClassUtils.newInstance(compClass);
        component.setId(compId);

        //children
        TreeStructComponent[] childArray = treeStructComp.getChildren();
        if (childArray != null)
        {
            List<UIComponent> childList = component.getChildren();
            for (int i = 0, len = childArray.length; i < len; i++)
            {
                UIComponent child = internalRestoreTreeStructure(childArray[i]);
                childList.add(child);
            }
        }

        //facets
        Object[] facetArray = treeStructComp.getFacets();
        if (facetArray != null)
        {
            Map<String, UIComponent> facetMap = component.getFacets();
            for (int i = 0, len = facetArray.length; i < len; i++)
            {
                Object[] tuple = (Object[])facetArray[i];
                String facetName = (String)tuple[0];
                TreeStructComponent structChild = (TreeStructComponent)tuple[1];
                UIComponent child = internalRestoreTreeStructure(structChild);
                facetMap.put(facetName, child);
            }
        }

        return component;
    }


    public static class TreeStructComponent
            implements Serializable
    {
        private static final long serialVersionUID = 5069109074684737231L;
        private String _componentClass;
        private String _componentId;
        private TreeStructComponent[] _children = null;    // Array of children
        private Object[] _facets = null;                   // Array of Array-tuples with Facetname and TreeStructComponent

        TreeStructComponent(String componentClass, String componentId)
        {
            _componentClass = componentClass;
            _componentId = componentId;
        }

        public String getComponentClass()
        {
            return _componentClass;
        }

        public String getComponentId()
        {
            return _componentId;
        }

        void setChildren(TreeStructComponent[] children)
        {
            _children = children;
        }

        TreeStructComponent[] getChildren()
        {
            return _children;
        }

        Object[] getFacets()
        {
            return _facets;
        }

        void setFacets(Object[] facets)
        {
            _facets = facets;
        }
    }

}
