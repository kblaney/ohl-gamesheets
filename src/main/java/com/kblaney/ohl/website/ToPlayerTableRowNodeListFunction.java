package com.kblaney.ohl.website;

import com.google.inject.Inject;
import com.kblaney.url.UrlFunction;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class ToPlayerTableRowNodeListFunction implements TeamNumToNodeListFunction
{
  private final UrlFunction<Document> urlToDomDocumentFunction;

  @Inject
  public ToPlayerTableRowNodeListFunction(final UrlFunction<Document> urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public NodeList apply(final int teamNum) throws IOException
  {
    final Document document = getDocument(teamNum);
    final Node tableNode = getTableNode(document);
    return getNodeList(tableNode);
  }

  private Document getDocument(final int teamNum) throws IOException
  {
    final URL url = Urls.getSkaterStatsUrl(teamNum);
    return urlToDomDocumentFunction.convert(url);
  }

  private Node getTableNode(final Document document) throws IOException
  {
    final String xpath = "//table[tr[th='PIMPG']]";
    try
    {
      final Node tableNode = XPathAPI.selectSingleNode(document.getDocumentElement(), xpath);
      if (tableNode == null)
      {
        throw new IllegalStateException("Can't find players scoring table using xpath: " + xpath);
      }
      else
      {
        return tableNode;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Invalid players table xpath: " + xpath, e);
    }
  }

  private NodeList getNodeList(final Node tableNode)
  {
    final String xpath = "tr[td[position()=3][a]]";
    try
    {
      return XPathAPI.selectNodeList(tableNode, xpath);
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Can't get player node list using xpath: " + xpath, e);
    }
  }
}
