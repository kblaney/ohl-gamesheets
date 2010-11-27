package com.kblaney.ohl.website;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

final class XmlToElementFunction
{
  private final DocumentBuilder documentBuilder;

  public XmlToElementFunction() throws ParserConfigurationException
  {
    documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }

  public Element apply(final String xml) throws Exception
  {
    final InputSource inputSource = new InputSource(new StringReader(xml));
    final Document document = documentBuilder.parse(inputSource);
    return document.getDocumentElement();
  }
}
