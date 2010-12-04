package com.kblaney.ohl.website;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

final class XmlToDomDocumentFunction
{
  private final DocumentBuilder documentBuilder;

  public XmlToDomDocumentFunction() throws ParserConfigurationException
  {
    documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  }

  public Document apply(final String xml) throws Exception
  {
    final InputSource inputSource = new InputSource(new StringReader(xml));
    return documentBuilder.parse(inputSource);
  }
}
