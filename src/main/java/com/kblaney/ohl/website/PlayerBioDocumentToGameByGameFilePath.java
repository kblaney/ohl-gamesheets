package com.kblaney.ohl.website;

import javax.xml.transform.TransformerException;
import com.google.common.base.Function;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerBioDocumentToGameByGameFilePath implements
      Function<Document, String>
{
  public String apply(final Document playerBioDocument)
  {
    try
    {
      final Node gameByGameStatsNode = XPathAPI.selectSingleNode(
            playerBioDocument.getDocumentElement(),
            "//div[@class='stats']/div[@class='button']/a");
      return gameByGameStatsNode.getAttributes().getNamedItem("href")
            .getNodeValue();
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Transformer exception", e);
    }
  }
}
