package com.kblaney.ohl.website;

import org.w3c.dom.Node;

final class Nodes
{
  private Nodes() {}

  public static String getChildNodeValue(final Node node,
        final int childNodeIndex)
  {
    if ((node.getChildNodes() != null) &&
          (node.getChildNodes().getLength() >= childNodeIndex))
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
    else
    {
      throw new IllegalArgumentException("Invalid node");
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
