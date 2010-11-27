package com.kblaney.ohl.website;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.junit.Test;
import static org.junit.Assert.*;

public final class NodesTest
{
  @Test
  public void getChildNodeValue_noNodeValue()
  {
    final Document document = Tidy.createEmptyDocument();
    final Element rootElement = document.createElement("A");
    rootElement.appendChild(document.createElement("B"));
    assertNull(Nodes.getChildNodeValue(rootElement, 0));
  }

  @Test
  public void getChildNodeValue_nodeValue()
  {
    final Document document = Tidy.createEmptyDocument();
    final Element rootElement = document.createElement("A");
    final Node childElement =
          rootElement.appendChild(document.createElement("B"));
    childElement.appendChild(document.createTextNode("C"));
    assertEquals("C", Nodes.getChildNodeValue(rootElement, 0));
  }

  @Test
  public void getFirstChildNodeValueOrEmpty_noChildren()
  {
    final Document document = Tidy.createEmptyDocument();
    final Element rootElement = document.createElement("A");
    assertEquals("", Nodes.getFirstChildNodeValueOrEmpty(rootElement));
  }

  @Test
  public void getFirstChildNodeValueOrEmpty_oneChild()
  {
    final Document document = Tidy.createEmptyDocument();
    final Element rootElement = document.createElement("A");
    rootElement.appendChild(document.createTextNode("B"));
    assertEquals("B", Nodes.getFirstChildNodeValueOrEmpty(rootElement));
  }
}
