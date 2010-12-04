package com.kblaney.ohl.website;

import com.google.inject.Inject;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class GoalieTableRowNodeListSupplier
{
  private final UrlToDomDocumentFunction urlToDomDocumentFunction;

  @Inject
  public GoalieTableRowNodeListSupplier(
        final UrlToDomDocumentFunction urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public NodeList get(final int teamNum) throws IOException
  {
    final Document document = getDocument(teamNum);
    final Node tableNode = getTableNode(document);
    return getNodeList(tableNode);
  }

  private Document getDocument(final int teamNum)throws IOException
  {
    final URL url = Urls.getGoalieStatsUrl(teamNum);
    return urlToDomDocumentFunction.apply(url);
  }

  private Node getTableNode(final Document document)
  {
    try
    {
      final Node tableNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(), "//table[tr[th='SVS']]");
      if (tableNode == null)
      {
        throw new IllegalStateException("Can not find goalies table");
      }
      else
      {
        return tableNode;
      }
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException(
            "Can't get player scoring table node: " + document, e);
    }
  }

  private NodeList getNodeList(final Node tableNode)
  {
    try
    {
      return XPathAPI.selectNodeList(tableNode, "tr[td[position()=3][a]]");
    }
    catch (final TransformerException e)
    {
      throw new IllegalStateException(
            "Can't get goalie node list: " + tableNode, e);
    }
  }
}
