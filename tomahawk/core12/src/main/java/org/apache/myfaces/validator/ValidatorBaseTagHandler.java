/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.validator;

import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.jsf.ValidateHandler;
import com.sun.facelets.tag.jsf.ValidatorConfig;

public class ValidatorBaseTagHandler extends ValidateHandler
{

    public ValidatorBaseTagHandler(ValidatorConfig config)
    {
        super(config);
        // TODO Auto-generated constructor stub
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
