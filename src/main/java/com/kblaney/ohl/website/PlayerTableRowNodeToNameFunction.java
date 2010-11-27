package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToNameFunction implements Function<Node, String>
{
  public String apply(Node node)
  {
    final Node linkNode = node.getChildNodes().item(2);
    return linkNode.getFirstChild().getFirstChild().getNodeValue();
  }
}
