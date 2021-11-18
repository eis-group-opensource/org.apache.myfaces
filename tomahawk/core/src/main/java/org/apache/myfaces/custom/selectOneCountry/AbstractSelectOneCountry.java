/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneCountry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.myfaces.component.html.ext.HtmlSelectOneMenu;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;

/**
 * A localized list of countries choose box. The value binds to the 
 * country ISO 3166 code. This is the same code as for 
 * java.util.Locale.getCountry(). The official codes list is 
 * available here : 
 * 
 * http://www.iso.ch/iso/en/prods-services/iso3166ma/02iso-3166-code-lists/list-en1.html 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:selectOneCountry"
 *   class = "org.apache.myfaces.custom.selectOneCountry.SelectOneCountry"
 *   tagClass = "org.apache.myfaces.custom.selectOneCountry.SelectOneCountryTag"
 * @since 1.1.7
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractSelectOneCountry extends HtmlSelectOneMenu {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneCountry";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneCountryRenderer";

    public AbstractSelectOneCountry() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * Integer equals to the maximum number of characters in the country name.
     * 
     * @JSFProperty
     */
    public abstract Integer getMaxLength();
    
    /**
     * Integer equals to the maximum number of characters in the country name.
     * 
     * @JSFProperty
     */
    public abstract String getEmptySelection();

    private Set getFilterSet(){
        List selectItems = RendererUtils.getSelectItemList( this );
        Set set = new HashSet( selectItems.size() );

        for (Iterator i = selectItems.iterator(); i.hasNext(); )
            set.add( ((SelectItem)i.next()).getValue().toString().toUpperCase() );

        return set;
    }

    protected List getCountriesChoicesAsSelectItemList(){
        //return RendererUtils.getSelectItemList(component);

        Set filterSet = getFilterSet();

        String[] availableCountries = Locale.getISOCountries();

        Locale currentLocale;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if( viewRoot != null )
            currentLocale = viewRoot.getLocale();
        else
            currentLocale = facesContext.getApplication().getDefaultLocale();


        TreeMap map = new TreeMap();
        // TreeMap is sorted according to the keys' natural order

        for(int i=0; i<availableCountries.length; i++){
            String countryCode = availableCountries[i];
            if( ! filterSet.isEmpty() && ! filterSet.contains(countryCode))
                continue;
            Locale tmp = new Locale(countryCode, countryCode);
            map.put(tmp.getDisplayCountry(currentLocale), countryCode);
        }

        List countriesSelectItems = new ArrayList( map.size() );
        if(getEmptySelection() != null)
            countriesSelectItems.add(new SelectItem("", getEmptySelection()));

        Integer maxLength = getMaxLength();
        int maxDescriptionLength = maxLength==null ? Integer.MAX_VALUE : maxLength.intValue();
        if( maxDescriptionLength < 5 )
            maxDescriptionLength = 5;

        for(Iterator i = map.keySet().iterator(); i.hasNext(); ){
            String countryName = (String) i.next();
            String countryCode = (String) map.get( countryName );
            String label;
            if( countryName.length() <= maxDescriptionLength )
                label = countryName;
            else
                label = countryName.substring(0, maxDescriptionLength-3)+"...";

            countriesSelectItems.add( new SelectItem(countryCode, label) );
        }

        return countriesSelectItems;
    }

    protected void validateValue(FacesContext context, Object value) {
        UISelectItems selectItems = new UISelectItems();
        selectItems.setTransient(true);
        selectItems.setValue(getCountriesChoicesAsSelectItemList());
        getChildren().add(selectItems);
        
        super.validateValue(context,value);
    }
}
