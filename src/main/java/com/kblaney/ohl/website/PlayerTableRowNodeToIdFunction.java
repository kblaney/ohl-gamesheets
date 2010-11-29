package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToIdFunction implements Function<Node, String>
{
  public String apply(Node node)
  {
    final String hrefAttrValue = getHrefAttrValue(node);
    return hrefAttrValue.substring(hrefAttrValue.indexOf("?") + 1);
  }

  private String getHrefAttrValue(final Node node)
  {
    final Node playerLinkNode = node.getChildNodes().item(2);
    return playerLinkNode.getFirstChild().getAttributes().getNamedItem(
          "href").getNodeValue();
  }
}
