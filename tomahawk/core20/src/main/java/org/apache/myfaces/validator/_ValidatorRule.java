/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;

final class _ValidatorRule extends MetaRule {

    final static class ValueExpressionMetadata extends Metadata {

        private final String name;

        private final TagAttribute attr;

        private final Class type;

        public ValueExpressionMetadata(String name, Class type,
                TagAttribute attr) {
            this.name = name;
            this.attr = attr;
            this.type = type;
        }

        public void applyMetadata(FaceletContext ctx, Object instance) {
            ((ValidatorBase) instance).setValueExpression(this.name, this.attr
                    .getValueExpression(ctx, this.type));
        }

    }

    /*
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

    }*/

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
                //if (FacesAPI.getComponentVersion(meta.getTargetClass()) >= 12) {
                    return new ValueExpressionMetadata(name, type, attribute);
                //} else {
                //    return new ValueBindingMetadata(name, type, attribute);
                //}
            }
        }
        return null;
    }
}
