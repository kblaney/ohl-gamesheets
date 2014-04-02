package com.kblaney.ohl.website;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToHometownFunction implements Function<Node, String>
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode, "//tr[td='Hometown']");

      if (rowNode.getLastChild().hasChildNodes())
      {
        final String nodeValue = rowNode.getLastChild().getFirstChild().getNodeValue();
        final Pattern p = Pattern.compile("^([^,]+)[, ]+([^,]+)");
        final Matcher m = p.matcher(nodeValue);
        if (m.find())
        {
          return m.group(1) + ", " + m.group(2);
        }
        else
        {
          return nodeValue;
        }
      }
      else
      {
        return StringUtils.EMPTY;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get hometown", e);
    }
  }
}
