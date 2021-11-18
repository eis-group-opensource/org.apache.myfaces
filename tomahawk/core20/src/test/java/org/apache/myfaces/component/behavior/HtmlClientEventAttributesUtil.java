/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.component.behavior;

import org.apache.myfaces.shared_impl.renderkit.ClientBehaviorEvents;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;

public class HtmlClientEventAttributesUtil
{
    public static HtmlRenderedClientEventAttr[] generateClientBehaviorEventAttrs()
    {
        HtmlRenderedClientEventAttr[] attrs = new HtmlRenderedClientEventAttr[]{
                new HtmlRenderedClientEventAttr(HTML.ONCLICK_ATTR, ClientBehaviorEvents.CLICK),
                new HtmlRenderedClientEventAttr(HTML.ONDBLCLICK_ATTR, ClientBehaviorEvents.DBLCLICK),
                new HtmlRenderedClientEventAttr(HTML.ONKEYDOWN_ATTR, ClientBehaviorEvents.KEYDOWN),
                new HtmlRenderedClientEventAttr(HTML.ONKEYPRESS_ATTR, ClientBehaviorEvents.KEYPRESS),
                new HtmlRenderedClientEventAttr(HTML.ONKEYUP_ATTR, ClientBehaviorEvents.KEYUP),
                new HtmlRenderedClientEventAttr(HTML.ONMOUSEDOWN_ATTR, ClientBehaviorEvents.MOUSEDOWN),
                new HtmlRenderedClientEventAttr(HTML.ONMOUSEMOVE_ATTR, ClientBehaviorEvents.MOUSEMOVE),
                new HtmlRenderedClientEventAttr(HTML.ONMOUSEOUT_ATTR, ClientBehaviorEvents.MOUSEOUT),
                new HtmlRenderedClientEventAttr(HTML.ONMOUSEOVER_ATTR, ClientBehaviorEvents.MOUSEOVER),
                new HtmlRenderedClientEventAttr(HTML.ONMOUSEUP_ATTR, ClientBehaviorEvents.MOUSEUP)
        };

        return attrs;
    }
    
    public static HtmlRenderedClientEventAttr[] generateClientBehaviorInputEventAttrs()
    {
        return (HtmlRenderedClientEventAttr[]) 
            org.apache.myfaces.shared_impl.util.ArrayUtils.concat( 
                generateClientBehaviorEventAttrs(),
                new HtmlRenderedClientEventAttr[]{
                    new HtmlRenderedClientEventAttr(HTML.ONBLUR_ATTR, ClientBehaviorEvents.BLUR),
                    new HtmlRenderedClientEventAttr(HTML.ONFOCUS_ATTR, ClientBehaviorEvents.FOCUS),
                    new HtmlRenderedClientEventAttr(HTML.ONSELECT_ATTR, ClientBehaviorEvents.SELECT),
                    new HtmlRenderedClientEventAttr(HTML.ONCHANGE_ATTR, ClientBehaviorEvents.CHANGE),
                    new HtmlRenderedClientEventAttr(HTML.ONCHANGE_ATTR, ClientBehaviorEvents.VALUECHANGE)
                });
    }
}
