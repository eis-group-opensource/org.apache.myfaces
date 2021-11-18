/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.calendar;

import javax.faces.component.UIComponent;

import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyValueBinding;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.html.HtmlComponentHandler;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public class HtmlInputCalendarTagHandler extends HtmlComponentHandler
{

    public HtmlInputCalendarTagHandler(ComponentConfig config)
    {
        super(config);
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {
        MetaRuleset m = super.createMetaRuleset(type);

        m.addRule(DateBusinessConverterRule.INSTANCE);

        return m;
    }

    public static class DateBusinessConverterRule extends MetaRule
    {
        public final static DateBusinessConverterRule INSTANCE = new DateBusinessConverterRule();

        final static class LiteralConverterMetadata extends Metadata
        {

            private final Class dateBusinessConverterClass;

            public LiteralConverterMetadata(String dateBusinessConverterClass)
            {
                try
                {
                    this.dateBusinessConverterClass = ClassUtils
                            .classForName(dateBusinessConverterClass);
                }
                catch(ClassNotFoundException e)
                {
                    throw new IllegalArgumentException("Class referenced in calendarConverter not found: "+dateBusinessConverterClass);
                }
                catch(ClassCastException e)
                {
                    throw new IllegalArgumentException("Class referenced in calendarConverter is not instance of org.apache.myfaces.custom.calendar.CalendarConverter: "+dateBusinessConverterClass);
                }
            }

            public void applyMetadata(FaceletContext ctx, Object instance)
            {
                ((AbstractHtmlInputCalendar) instance)
                        .setDateBusinessConverter((DateBusinessConverter) ClassUtils
                                .newInstance(dateBusinessConverterClass));
            }
        }

        final static class DynamicConverterMetadata extends Metadata
        {
            private final TagAttribute attr;

            public DynamicConverterMetadata(TagAttribute attr)
            {
                this.attr = attr;
            }

            public void applyMetadata(FaceletContext ctx, Object instance)
            {
                ((UIComponent) instance).setValueBinding("dateBusinessConverter",
                        new LegacyValueBinding(attr.getValueExpression(ctx,
                                DateBusinessConverter.class)));
            }
        }

        public Metadata applyRule(String name, TagAttribute attribute,
                MetadataTarget meta)
        {
            if (meta.isTargetInstanceOf(AbstractHtmlInputCalendar.class))
            {
                if ("dateBusinessConverter".equals(name)) {
                    if (attribute.isLiteral()) {
                        return new LiteralConverterMetadata(attribute.getValue());
                    } else {
                        return new DynamicConverterMetadata(attribute);
                    }
                }
            }
            return null;
        }
    }
}
