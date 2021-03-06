/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: mathias
 * Date: 18.03.2007
 * Time: 01:19:19
 * To change this template use File | Settings | File Templates.
 */
public class UIComponentFindComponentTest extends AbstractUIComponentBaseTest
{
    @Test(expectedExceptions = {NullPointerException.class})
    public void testWithNullExperession() throws Exception
    {
        _testImpl.findComponent(null);
        assertNull(_testImpl.findComponent(""));
    }

    public void testWithEmptyExperession() throws Exception
    {
        assertNull(_testImpl.findComponent(""));
    }

    @Test
    public void testRootExpression() throws Exception
    {
        String expression = ":parent";
        UIComponent root = _mocksControl.createMock(UIComponent.class);
        UIComponent parent = _mocksControl.createMock(UIComponent.class);
        _testImpl.setId("testimpl");
        expect(_testImpl.getParent()).andReturn(parent).anyTimes();
        expect(parent.getParent()).andReturn(root).anyTimes();
        expect(root.getParent()).andReturn(null).anyTimes();
        expect(parent.getId()).andReturn("parent").anyTimes();
        expect(root.getId()).andReturn("root").anyTimes();
        expect(root.getFacetsAndChildren()).andReturn(Collections.singletonList(parent).iterator());

        _mocksControl.replay();

        assertEquals(parent, _testImpl.findComponent(expression));
    }

    @Test
    public void testRelativeExpression() throws Exception
    {
        String expression = "testimpl";
        UIComponent namingContainer = _mocksControl.createMock(TestNamingContainerComponent.class);
        UIComponent parent = _mocksControl.createMock(UIComponent.class);
        _testImpl.setId("testimpl");
        expect(_testImpl.getParent()).andReturn(parent).anyTimes();
        expect(parent.getParent()).andReturn(namingContainer).anyTimes();
        expect(parent.getId()).andReturn("parent").anyTimes();
        expect(namingContainer.getId()).andReturn("namingContainer").anyTimes();
        expect(namingContainer.getFacetsAndChildren()).andReturn(Collections.singletonList(parent).iterator());
        expect(parent.getFacetsAndChildren()).andReturn(Arrays.asList(new UIComponent[]{_testImpl}).iterator());

        _mocksControl.replay();

        assertEquals(_testImpl, _testImpl.findComponent(expression));
    }

    @Test
    public void testComplexRelativeExpression() throws Exception
    {
        String expression = "child1_1:testimpl";
        Collection<Method> mockedMethods = getMockedMethods();
        mockedMethods.add(UIComponentBase.class.getDeclaredMethod("getFacetsAndChildren", null));
        mockedMethods.add(UIComponentBase.class.getDeclaredMethod("getId", null));
        UIComponent namingContainer = _mocksControl.createMock(TestNamingContainerBaseComponent.class,
                mockedMethods.toArray(new Method[mockedMethods.size()]));

        expect(namingContainer.getId()).andReturn("namingContainer").anyTimes();
        _testImpl.setId("testimpl");
        UIComponent child1_1 = _mocksControl.createMock(TestNamingContainerComponent.class);
        expect(child1_1.getId()).andReturn("child1_1").anyTimes();
        expect(namingContainer.getFacetsAndChildren()).andReturn(Collections.singletonList(child1_1).iterator());

        expect(child1_1.findComponent(EasyMock.eq("testimpl"))).andReturn(_testImpl);

        _mocksControl.replay();

        assertEquals(_testImpl, namingContainer.findComponent(expression));
    }

    @Test
    public void testWithRelativeExpressionNamingContainer() throws Exception
    {
        String expression = "testimpl";
        Collection<Method> mockedMethods = getMockedMethods();
        mockedMethods.add(UIComponentBase.class.getDeclaredMethod("getFacetsAndChildren", null));
        mockedMethods.add(UIComponentBase.class.getDeclaredMethod("getId", null));
        UIComponent namingContainer = _mocksControl.createMock(TestNamingContainerBaseComponent.class,
                mockedMethods.toArray(new Method[mockedMethods.size()]));
        UIComponent parent = _mocksControl.createMock(UIComponent.class);
        _testImpl.setId("testimpl");
        expect(_testImpl.getParent()).andReturn(parent).anyTimes();
        expect(parent.getParent()).andReturn(namingContainer).anyTimes();
        expect(parent.getId()).andReturn("parent").anyTimes();
        expect(namingContainer.getId()).andReturn("namingContainer").anyTimes();
        expect(namingContainer.getFacetsAndChildren()).andReturn(Collections.singletonList(parent).iterator());
        expect(parent.getFacetsAndChildren()).andReturn(Arrays.asList(new UIComponent[]{_testImpl}).iterator());

        _mocksControl.replay();

        assertEquals(_testImpl, namingContainer.findComponent(expression));
    }

    @Test
    public void testOverriddenFindComponent() {
        UIViewRoot viewRoot = new UIViewRoot();
        UIData uiData = new UIData()
        {
            public UIComponent findComponent(String expr)
            {
                return super.findComponent(stripRowIndex(expr));
            }

            public String stripRowIndex(String searchId) {
                if (searchId.length() > 0 && Character.isDigit(searchId.charAt(0)))
                {
                    for (int i = 1; i < searchId.length(); ++i)
                    {
                        char c = searchId.charAt(i);
                        if (c == SEPARATOR_CHAR)
                        {
                            searchId = searchId.substring(i + 1);
                            break;
                        }
                        if (!Character.isDigit(c))
                        {
                            break;
                        }
                    }
                }
                return searchId;
            }
        };
        uiData.setId("data");
        UIColumn column = new UIColumn();
        column.setId("column");
        UICommand command = new UICommand();
        command.setId("command");
        viewRoot.getChildren().add(uiData);
        uiData.getChildren().add(column);
        column.getChildren().add(command);

        assertNull(viewRoot.findComponent(":xx"));
        assertEquals(uiData, viewRoot.findComponent(":data"));
        assertEquals(column, viewRoot.findComponent(":data:column"));
        assertEquals(command, viewRoot.findComponent(":data:command"));
        assertEquals(command, viewRoot.findComponent("data:1:command"));
        assertEquals(command, viewRoot.findComponent(":data:1:command"));
    }

    @Test
    public void testXXFindComponent() {
        UIViewRoot viewRoot = new UIViewRoot();
        UIData uiData = new UIData();
        uiData.setId("x");
        UIColumn column = new UIColumn();
        column.setId("column");
        UICommand command = new UICommand();
        command.setId("x");
        viewRoot.getChildren().add(uiData);
        uiData.getChildren().add(column);
        column.getChildren().add(command);

        assertNull(viewRoot.findComponent(":xx"));
        assertNotNull(viewRoot.findComponent(":x"));
        assertEquals(column, viewRoot.findComponent(":x:column"));
        assertEquals(command, viewRoot.findComponent(":x:x"));
    }

    public abstract static class TestNamingContainerComponent extends UIComponent implements NamingContainer
    {
    }

    public abstract static class TestNamingContainerBaseComponent extends UIComponentBase implements NamingContainer
    {
    }
}
