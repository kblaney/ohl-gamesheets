package com.kblaney.ohl.website;

import com.google.common.base.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Node;

final class PlayerBioDivNodeToBirthYearFunction
      implements Function<Node, String>
{
  public String apply(final Node bioDivNode)
  {
    try
    {
      final Node rowNode = XPathAPI.selectSingleNode(bioDivNode,
            "//tr[td='Birthdate']");
      final String birthDate = Nodes.getFirstChildNodeValueOrEmpty(
            rowNode.getLastChild());
      final Pattern p = Pattern.compile("19\\d\\d");
      final Matcher m = p.matcher(birthDate);
      if (m.find())
      {
        return m.group();
      }
      else
      {
        return StringUtils.EMPTY;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get birth year", e);
    }
  }
}
