/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.el.unified.resolver;

import com.google.inject.Binder;
import com.google.inject.Module;

public class ShoppingModule implements Module {

    public void configure(Binder binder) {

        binder.bind(Order.class).to(BulkOrder.class);
        
    }

}