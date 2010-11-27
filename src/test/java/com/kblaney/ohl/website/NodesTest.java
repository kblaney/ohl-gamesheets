package com.kblaney.ohl.website;

import org.junit.Before;
import org.w3c.dom.Element;
import org.junit.Test;
import static org.junit.Assert.*;

public final class NodesTest
{
  private XmlToElementFunction toElementFunction;

  @Before
  public void setUp() throws Exception
  {
    toElementFunction = new XmlToElementFunction();
  }

  @Test
  public void getChildNodeValue_noNodeValue() throws Exception
  {
    final Element element = toElementFunction.apply("<A><B></B></A>");
    assertNull(Nodes.getChildNodeValue(element, 0));
  }

  @Test
  public void getChildNodeValue_nodeValue() throws Exception
  {
    final Element element = toElementFunction.apply("<A><B>C</B></A>");
    assertEquals("C", Nodes.getChildNodeValue(element, 0));
  }

  @Test
  public void getFirstChildNodeValueOrEmpty_noChildren() throws Exception
  {
    final Element element = toElementFunction.apply("<A></A>");
    assertEquals("", Nodes.getFirstChildNodeValueOrEmpty(element));
  }

  @Test
  public void getFirstChildNodeValueOrEmpty_oneChild() throws Exception
  {
    final Element element = toElementFunction.apply("<A>B</A>");
    assertEquals("B", Nodes.getFirstChildNodeValueOrEmpty(element));
  }
}
