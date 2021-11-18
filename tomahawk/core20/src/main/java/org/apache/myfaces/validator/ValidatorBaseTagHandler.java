/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.ValidatorConfig;
import javax.faces.view.facelets.ValidatorHandler;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

@JSFFaceletTag
public class ValidatorBaseTagHandler extends ValidatorHandler
{

    public ValidatorBaseTagHandler(ValidatorConfig config)
    {
        super(config);
    }

    @Override
    protected MetaRuleset createMetaRuleset(Class type)
    {
        MetaRuleset ruleSet = super.createMetaRuleset(type);
        
        //Add rule to handle EL expressions
        ruleSet.addRule(_ValidatorRule.Instance);
        
        return ruleSet;
    }
}
