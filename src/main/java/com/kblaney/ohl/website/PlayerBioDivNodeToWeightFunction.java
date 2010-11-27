package com.kblaney.ohl.website;

import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToWeightFunction
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Weight']");
      return Nodes.getFirstChildNodeValueOrEmpty(rowNode.getLastChild());
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get weight", e);
    }
  }
}
