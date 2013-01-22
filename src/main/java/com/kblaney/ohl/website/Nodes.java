package com.kblaney.ohl.website;

import org.w3c.dom.Node;

final class Nodes
{
  private Nodes()
  {
  }

  public static String getChildNodeValue(final Node node, final int childNodeIndex)
  {
    final Node childNode = node.getChildNodes().item(childNodeIndex);
    if (childNode.getFirstChild() == null)
    {
      return null;
    }
    else
    {
      return childNode.getFirstChild().getNodeValue();
    }
  }

  public static String getFirstChildNodeValueOrEmpty(final Node node)
  {
    if (node.getFirstChild() == null)
    {
      return "";
    }
    else
    {
      return node.getFirstChild().getNodeValue();
    }
  }
}
