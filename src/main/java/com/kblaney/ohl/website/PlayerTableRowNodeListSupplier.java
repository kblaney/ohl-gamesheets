package com.kblaney.ohl.website;

import com.kblaney.commons.xml.XmlUtil;
import java.io.IOException;
import java.net.URL;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class PlayerTableRowNodeListSupplier
{
  public NodeList get(final int teamNum) throws IOException
  {
    final Document document = getDocument(teamNum);
    final Node tableNode = getTableNode(document);
    return getNodeList(tableNode);
  }

  private Document getDocument(final int teamNum) throws IOException
  {
    final URL url = Urls.getSkaterStatsUrl(teamNum);
    return XmlUtil.getXmlDocument(url);
  }

  private Node getTableNode(final Document document) throws IOException
  {
    try
    {
      final Node tableNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(), "//table[tr[th='PIMPG']]");
      if (tableNode == null)
      {
        throw new IOException("Can not find player scoring table");
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
            "Can't get player scoring node list: " + tableNode, e);
    }
  }
}
