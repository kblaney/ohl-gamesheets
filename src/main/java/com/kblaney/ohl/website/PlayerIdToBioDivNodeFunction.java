package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.commons.xml.XmlUtil;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerIdToBioDivNodeFunction implements Function<String, Node>
{
  public Node apply(final String playerId)
  {
    try
    {
      final URL url = Urls.getPlayerBioUrl(playerId);
      final Document document = XmlUtil.getXmlDocument(url);
      final Node bioDivNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(),
            "//div[@class='profile']/div[@class='details']/table");
      if (bioDivNode == null)
      {
        throw new IllegalStateException(
              "Can't find bio div node for player with ID: " + playerId);
      }
      else
      {
        return bioDivNode;
      }
    }
    catch (TransformerException e)
    {
      throw new IllegalStateException(e);
    }
  }
}
