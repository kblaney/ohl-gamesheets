package com.kblaney.ohl.website;

import com.google.common.base.Function;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerBioDocumentToBioDivNodeFunction implements Function<Document, Node>
{
  public Node apply(final Document playerBioDocument)
  {
    final String xpath = "//div[@class='profile']/div[@class='details']/table";
    try
    {
      final Node bioDivNode = XPathAPI.selectSingleNode(
            playerBioDocument.getDocumentElement(), xpath);
      if (bioDivNode == null)
      {
        throw new IllegalStateException("Can't find bio div node");
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
}
