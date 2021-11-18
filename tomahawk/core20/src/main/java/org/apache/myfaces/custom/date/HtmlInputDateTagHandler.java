/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.date;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

import org.apache.myfaces.custom.calendar.DateBusinessConverter;
import org.apache.myfaces.shared_tomahawk.util.ClassUtils;

/**
 * 
 * @since 1.1.10
 * @author Leonardo Uribe (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public class HtmlInputDateTagHandler extends ComponentHandler
{

    public HtmlInputDateTagHandler(ComponentConfig config)
    {
        super(config);
    }

    protected MetaRuleset createMetaRuleset(Class type)
    {
        MetaRuleset m = super.createMetaRuleset(type).alias("class", "styleClass");

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
                ((AbstractHtmlInputDate) instance)
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
                ((UIComponent) instance).setValueExpression("dateBusinessConverter",
                        attr.getValueExpression(ctx,
                                DateBusinessConverter.class));
            }
        }

        public Metadata applyRule(String name, TagAttribute attribute,
                MetadataTarget meta)
        {
            if (meta.isTargetInstanceOf(AbstractHtmlInputDate.class))
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
