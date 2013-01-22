package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.url.UrlFunction;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

final class PlayerGameByGameFilePathToGameByGameRowNodeListFunction implements Function<String, NodeList>
{
  private final UrlFunction<Document> urlToDomDocumentFunction;

  @Inject
  public PlayerGameByGameFilePathToGameByGameRowNodeListFunction(final UrlFunction<Document> urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public NodeList apply(final String gameByGameFilePath)
  {
    final Document document = getDocument(gameByGameFilePath);
    final String xpath = "//div[@id='gamebygameBlock']/table[@class='statsTable']/tr[td[a]]";
    try
    {
      return XPathAPI.selectNodeList(document.getDocumentElement(), xpath);
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Invalid xpath for game-by-game document: " + xpath, e);
    }
  }

  private Document getDocument(final String gameByGameFilePath)
  {
    final URL url = Urls.getPlayerGameByGameUrl(gameByGameFilePath);
    try
    {
      return urlToDomDocumentFunction.convert(url);
    }
    catch (final IOException e)
    {
      throw new IllegalStateException("Can't get game-by-game URL: " + url, e);
    }
  }
}
