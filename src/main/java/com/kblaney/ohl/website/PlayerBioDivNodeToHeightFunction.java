package com.kblaney.ohl.website;

import com.google.common.base.Function;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToHeightFunction
      implements Function<Node, String>
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Height']");
      final String height = Nodes.getFirstChildNodeValueOrEmpty(
            rowNode.getLastChild());
      return height.replace('\'', '.');
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get height", e);
    }
  }
}
