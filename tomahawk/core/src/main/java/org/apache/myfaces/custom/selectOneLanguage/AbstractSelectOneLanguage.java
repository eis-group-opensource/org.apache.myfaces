/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.selectOneLanguage;

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
 * A localized list of languages choose box. The value binds to the 
 * language ISO 639 code (lowercase). This is the same code as 
 * for java.util.Locale.getLanguage(). The official codes 
 * list is available here : 
 * 
 * http://www.loc.gov/standards/iso639-2/englangn.html 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:selectOneLanguage"
 *   class = "org.apache.myfaces.custom.selectOneLanguage.SelectOneLanguage"
 *   tagClass = "org.apache.myfaces.custom.selectOneLanguage.SelectOneLanguageTag"
 * @since 1.1.7
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-04 05:40:30 +0300 (Thu, 04 Sep 2008) $
 */
public abstract class AbstractSelectOneLanguage extends HtmlSelectOneMenu {
    public static final String COMPONENT_TYPE = "org.apache.myfaces.SelectOneLanguage";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.SelectOneLanguageRenderer";

    private Integer _maxLength = null;
    
    private String _emptySelection = null;

    public AbstractSelectOneLanguage() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * Integer equals to the maximum number of characters in the language name.
     * 
     * @JSFProperty
     */
    public abstract Integer getMaxLength();
    
    /**
     * Label and value to be used when displaying an empty selection
     * 
     * @JSFProperty
     */
    public abstract String getEmptySelection();

    private Set getFilterSet(){
        List selectItems = RendererUtils.getSelectItemList( this );
        Set set = new HashSet( selectItems.size() );

        for (Iterator i = selectItems.iterator(); i.hasNext(); )
            set.add( ((SelectItem)i.next()).getValue().toString().toLowerCase() );

        return set;
    }

    protected List getLanguagesChoicesAsSelectItemList(){
        //return RendererUtils.getSelectItemList(component);

        Set filterSet = getFilterSet();

        String[] availableLanguages = Locale.getISOLanguages();

        Locale currentLocale;

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if( viewRoot != null )
            currentLocale = viewRoot.getLocale();
        else
            currentLocale = facesContext.getApplication().getDefaultLocale();


        TreeMap map = new TreeMap();
        // TreeMap is sorted according to the keys' natural order

        for(int i=0; i<availableLanguages.length; i++){
            String languageCode = availableLanguages[i];
            if( ! filterSet.isEmpty() && ! filterSet.contains(languageCode))
                continue;
            Locale tmp = new Locale(languageCode);
            map.put(tmp.getDisplayLanguage(currentLocale), languageCode);
        }

        List languagesSelectItems = new ArrayList( map.size() );
        if(getEmptySelection() != null)
            languagesSelectItems.add(new SelectItem("", getEmptySelection()));

        Integer maxLength = getMaxLength();
        int maxDescriptionLength = maxLength==null ? Integer.MAX_VALUE : maxLength.intValue();
        if( maxDescriptionLength < 5 )
            maxDescriptionLength = 5;

        for(Iterator i = map.keySet().iterator(); i.hasNext(); ){
            String languageName = (String) i.next();
            String languageCode = (String) map.get( languageName );
            String label;
            if( languageName.length() <= maxDescriptionLength )
                label = languageName;
            else
                label = languageName.substring(0, maxDescriptionLength-3)+"...";

            languagesSelectItems.add( new SelectItem(languageCode, label) );
        }

        return languagesSelectItems;
    }

    protected void validateValue(FacesContext context, Object value) {
        UISelectItems selectItems = new UISelectItems();
        selectItems.setTransient(true);
        selectItems.setValue(getLanguagesChoicesAsSelectItemList());
        getChildren().add(selectItems);
        
        super.validateValue(context,value);
    }
}
