package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerIdToBioDivNodeFunction implements Function<String, Node>
{
  private final UrlToDomDocumentFunction urlToDomDocumentFunction;

  @Inject
  public PlayerIdToBioDivNodeFunction(
        final UrlToDomDocumentFunction urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public Node apply(final String playerId)
  {
    final String xpath = "//div[@class='profile']/div[@class='details']/table";
    try
    {
      final Document document = getDomDocument(playerId);
      final Node bioDivNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(), xpath);
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
    catch (final TransformerException e)
    {
      throw new IllegalStateException(
            "Invalid xpath to find bio div node: " + xpath, e);
    }
  }

  private Document getDomDocument(final String playerId)
  {
    final URL url = Urls.getPlayerBioUrl(playerId);
    try
    {
      return urlToDomDocumentFunction.apply(url);
    }
    catch (final IOException e)
    {
      throw new IllegalStateException(
            "Can't get player bio from url: " + url, e);
    }
  }
}
