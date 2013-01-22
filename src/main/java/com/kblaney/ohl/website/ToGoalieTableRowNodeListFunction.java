package com.kblaney.ohl.website;

import com.google.inject.Inject;
import com.kblaney.xml.UrlReader;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class ToGoalieTableRowNodeListFunction implements TeamNumToNodeListFunction
{
  private final UrlReader<Document> urlToDomDocumentFunction;

  @Inject
  public ToGoalieTableRowNodeListFunction(final UrlReader<Document> urlToDomDocumentFunction)
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
    final URL url = Urls.getGoalieStatsUrl(teamNum);
    return urlToDomDocumentFunction.readFrom(url);
  }

  private Node getTableNode(final Document document)
  {
    final String xpath = "//table[tr[th='SVS']]";
    try
    {
      final Node tableNode = XPathAPI.selectSingleNode(document.getDocumentElement(), xpath);
      if (tableNode == null)
      {
        throw new IllegalStateException("Can't find goalies table using xpath: " + xpath);
      }
      else
      {
        return tableNode;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException("Invalid goalies table xpath: " + xpath, e);
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
      throw new IllegalStateException("Can't get goalie node list using xpath: " + xpath, e);
    }
  }
}
