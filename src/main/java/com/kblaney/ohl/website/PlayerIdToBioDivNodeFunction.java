package com.kblaney.ohl.website;

import com.kblaney.commons.xml.XmlUtil;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerIdToBioDivNodeFunction
{
  public Node apply(final String playerId) throws IOException
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
        throw new IOException(
              "Can't find bio div node for player with ID: " + playerId);
      }
      else
      {
        return bioDivNode;
      }
    }
    catch (TransformerException e)
    {
      throw new IOException(e);
    }
  }
}
