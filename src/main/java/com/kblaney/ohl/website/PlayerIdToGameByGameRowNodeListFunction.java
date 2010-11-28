package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.commons.xml.XmlUtil;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

final class PlayerIdToGameByGameRowNodeListFunction
      implements Function<String, NodeList>
{
  public NodeList apply(final String playerId)
  {
      final URL gameByGameUrl = Urls.getPlayerGameByGameUrl(playerId);
      final Document gameByGameDocument = XmlUtil.getXmlDocument(gameByGameUrl);
      if (gameByGameDocument == null)
      {
        throw new IllegalStateException(
              "<html>Game-by-game URL not found: " + gameByGameUrl);
      }

      try
      {
        return XPathAPI.selectNodeList(gameByGameDocument.getDocumentElement(),
              "//div[@id='gamebygameBlock']/table[@class='statsTable']/tr[td[a]]");
      }
      catch (final TransformerException e)
      {
        throw new IllegalStateException(e);
    }
  }
}
