/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectitems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.base.AbstractJsfTestCase;

/**
 * @author cagatay (latest modification by $Author: lu4242 $)
 * @version $Revision: 783163 $ $Date: 2009-06-09 18:37:19 -0500 (Mar, 09 Jun 2009) $
 */
public class UISelectItemsTest extends AbstractJsfTestCase{
    
    private UISelectItems selectItems;
    
    private Collection movieCollection;
    
    private Collection movieSelectItemsGroupCollection;
    
    private Map movieMap;
    
    private SelectItem[] movieSelectItems;

    public UISelectItemsTest(String testName) {
        super(testName);
    }
    
    public static Test suite() {
        return new TestSuite(UISelectItemsTest.class);
    }

    /**
     * Sets up the test environment for <s:selectItems value="#{PossibleValueHere}" var="Movie" itemLabel="#{Movie.name} itemValue="#{Movie.director}" />
     * Accepted possible values for the component can be a SelectItems array, a collection, a map and SelectItems group collection.
     */
    public void setUp() throws Exception{
        super.setUp();
        //component
        selectItems = new UISelectItems();
        ValueBinding itemLabelVb =  facesContext.getApplication().createValueBinding("#{Movie.name}");
        ValueBinding itemValueVb =  facesContext.getApplication().createValueBinding("#{Movie.director}");
        
        selectItems.setValueBinding("itemLabel", itemLabelVb);
        selectItems.setValueBinding("itemValue", itemValueVb);
        selectItems.getAttributes().put("var", "Movie");
        
        //entities
        Movie movie1 = new Movie("Godfather", "Francis Ford Coppola");
        Movie movie2 = new Movie("Goodfellas", "Martin Scorsese");
        Movie movie3 = new Movie("Casino", "Martin Scorsese");
        Movie movie4 = new Movie("Scarface", "Brian De Palma");
        
        //different value types
        movieSelectItems = new SelectItem[3];
        movieSelectItems[0] = new SelectItem(movie2.getDirector(), movie2.getName());
        movieSelectItems[1] = new SelectItem(movie3.getDirector(), movie3.getName());
        movieSelectItems[2] = new SelectItem(movie4.getDirector(), movie4.getName());
        
        movieCollection = new ArrayList();
        movieCollection.add(movie1);
        movieCollection.add(movie2);
        movieCollection.add(movie3);
        movieCollection.add(movie4);
        
        movieMap = new HashMap();
        movieMap.put(movie3.getName(), movie3);
        movieMap.put(movie4.getName(), movie4);

        movieSelectItemsGroupCollection = new ArrayList();
        movieSelectItemsGroupCollection.add(createSelectItemGroup("group1", movieSelectItems));
        movieSelectItemsGroupCollection.add(createSelectItemGroup("group2", movieSelectItems));
    }
    
    private SelectItemGroup createSelectItemGroup(String groupName, SelectItem[] items) {
        SelectItemGroup group = new SelectItemGroup();
        group.setLabel(groupName);
        group.setSelectItems(items);
         return group;
    }
    
    public void tearDown() throws Exception{
        selectItems = null;
        super.tearDown();
    }
    
    public void testCreateSelectItemsBySelectItems() {
        selectItems.setValue(movieSelectItems);
        SelectItem[] items = (SelectItem[]) selectItems.getValue();
        assertEquals(items[1].getValue(), new String("Martin Scorsese"));
        assertEquals(items[1].getLabel(), new String("Casino"));
        assertEquals(items.length, 3);
    }
    
    public void testCreateSelectItemsFromCollection() {
        selectItems.setValue(movieCollection);
        SelectItem[] items = (SelectItem[]) selectItems.getValue();
        assertEquals(items[0].getValue(), "Francis Ford Coppola");
        assertEquals(items[0].getLabel(), "Godfather");
        assertEquals(items.length, 4);
    }
    
    public void testCreateSelectItemsFromMap() {
        selectItems.setValue(movieMap);
        SelectItem[] items = (SelectItem[]) selectItems.getValue();
        assertEquals(items.length, 2);
    }
    
    public void testCreateSelectItemsFromSelectItemsGroupCollection() {
        selectItems.setValue(movieSelectItemsGroupCollection);
        SelectItem[] items = (SelectItem[]) selectItems.getValue();
        assertEquals(items[5].getValue(), "Brian De Palma");
        assertEquals(items[5].getLabel(),"Scarface");
        assertEquals(items.length, 6);
    }
}
