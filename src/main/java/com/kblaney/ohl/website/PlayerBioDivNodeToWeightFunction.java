package com.kblaney.ohl.website;

import org.apache.commons.lang.StringUtils;
import com.google.common.base.Function;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToWeightFunction implements Function<Node, String>
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode, "//tr[td='Weight']");
      final String weight = Nodes.getFirstChildNodeValueOrEmpty(rowNode.getLastChild());
      if (weight.equals("0"))
      {
        return StringUtils.EMPTY;
      }
      else
      {
        return weight;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get weight", e);
    }
  }
}
