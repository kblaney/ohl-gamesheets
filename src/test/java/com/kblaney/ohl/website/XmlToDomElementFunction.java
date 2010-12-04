package com.kblaney.ohl.website;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;

final class XmlToDomElementFunction
{
  private final XmlToDomDocumentFunction toDocumentFunction;

  public XmlToDomElementFunction() throws ParserConfigurationException
  {
    toDocumentFunction = new XmlToDomDocumentFunction();
  }

  public Element apply(final String xml) throws Exception
  {
    return toDocumentFunction.apply(xml).getDocumentElement();
  }
}
