package com.kblaney.ohl.website;

import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class NodesTest
{
  @Test
  public void getChildNodeValue_noNodeValue()
  {
    final Node childNode = mock(Node.class);
    when(childNode.getFirstChild()).thenReturn(null);
    final NodeList nodeList = mock(NodeList.class);
    final int childNodeIndex = 2;
    when(nodeList.item(childNodeIndex)).thenReturn(childNode);
    final Node node = mock(Node.class);
    when(node.getChildNodes()).thenReturn(nodeList);
    assertNull(Nodes.getChildNodeValue(node, childNodeIndex));
  }
}
