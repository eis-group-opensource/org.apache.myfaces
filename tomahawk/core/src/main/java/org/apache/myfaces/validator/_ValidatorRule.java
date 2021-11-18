/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.el.LegacyValueBinding;
import com.sun.facelets.tag.MetaRule;
import com.sun.facelets.tag.Metadata;
import com.sun.facelets.tag.MetadataTarget;
import com.sun.facelets.tag.TagAttribute;

final class _ValidatorRule extends MetaRule {

    final static class ValueBindingMetadata extends Metadata {

        private final String name;

        private final TagAttribute attr;

        private final Class type;

        public ValueBindingMetadata(String name, Class type, TagAttribute attr) {
            this.name = name;
            this.attr = attr;
            this.type = type;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ValidatorBase) instance).setValueBinding(this.name,
                    new LegacyValueBinding(this.attr.getValueExpression(ctx,
                            this.type)));
        }

    }

    public final static _ValidatorRule Instance = new _ValidatorRule();

    public _ValidatorRule() {
        super();
    }

    public Metadata applyRule(String name, TagAttribute attribute,
            MetadataTarget meta) {
        if (meta.isTargetInstanceOf(ValidatorBase.class)) {

            // if component and dynamic, then must set expression
            if (!attribute.isLiteral()) {
                Class type = meta.getPropertyType(name);
                if (type == null) {
                    type = Object.class;
                }
                return new ValueBindingMetadata(name, type, attribute);
            }
        }
        return null;
    }
}
