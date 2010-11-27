package com.kblaney.ohl.website;

import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToPositionFunction
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Position']");
      final String positionLongForm = Nodes.getFirstChildNodeValueOrEmpty(
            rowNode.getLastChild());
      if (positionLongForm.contains("Defence"))
      {
        return "D";
      }
      else if ("Centre".equals(positionLongForm))
      {
        return "C";
      }
      else if ("Left Wing".equals(positionLongForm))
      {
        return "LW";
      }
      else if ("Right Wing".equals(positionLongForm))
      {
        return "RW";
      }
      else if ("Goaltender".equals(positionLongForm))
      {
        return "G";
      }
      else
      {
        return positionLongForm;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get position", e);
    }
  }
}
