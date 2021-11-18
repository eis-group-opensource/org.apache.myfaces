/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.validator;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;

import org.apache.shale.test.mock.MockFacesContext12;
import org.easymock.classextension.EasyMock;
import org.easymock.classextension.IMocksControl;
import org.testng.annotations.Test;

/**
 * @author Mathias Broekelmann (latest modification by $Author: lu4242 $)
 * @version $Revision: 600207 $ $Date: 2007-12-02 00:51:41 +0200 (Sun, 02 Dec 2007) $
 */
public class _MessageUtilsTest
{

    /**
     * Test method for
     * {@link javax.faces.validator._MessageUtils#getErrorMessage(javax.faces.context.FacesContext, java.lang.String, java.lang.Object[])}.
     */
    @Test
    public void testErrorMessage()
    {
        UIViewRoot root = new UIViewRoot();
        MockFacesContext12 facesContext = new MockFacesContext12();
        IMocksControl mocksControl = EasyMock.createControl();
        Application application = mocksControl.createMock(Application.class);
        ViewHandler viewHandler = mocksControl.createMock(ViewHandler.class);
        ELContext elContext = mocksControl.createMock(ELContext.class);
        ExpressionFactory expressionFactory = mocksControl.createMock(ExpressionFactory.class);
        ValueExpression valueExpression = mocksControl.createMock(ValueExpression.class);
        facesContext.setApplication(application);
        facesContext.setViewRoot(root);
        facesContext.setELContext(elContext);
        
        expect(application.getViewHandler()).andReturn(viewHandler);
        expect(viewHandler.calculateLocale(facesContext)).andReturn(Locale.ENGLISH);
        expect(application.getMessageBundle()).andReturn("javax.faces.Messages");
        expect(application.getExpressionFactory()).andReturn(expressionFactory);
        String s = "xxx: Validation Error: Value is greater than allowable maximum of \"xyz\"";
        expect(expressionFactory.createValueExpression(elContext,s,String.class)).andReturn(valueExpression);
        expect(valueExpression.getValue(elContext)).andReturn(s);
        mocksControl.replay();

        assertEquals(_MessageUtils.getErrorMessage(facesContext, "javax.faces.validator.DoubleRangeValidator.MAXIMUM",
                new Object[] { "xyz", "xxx" }).getDetail(),
                "xxx: Validation Error: Value is greater than allowable maximum of \"xyz\"");
    }

}
