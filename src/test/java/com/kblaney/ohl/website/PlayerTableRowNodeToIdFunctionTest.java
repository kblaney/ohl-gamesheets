package com.kblaney.ohl.website;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToIdFunctionTest
{
  private XmlToDomElementFunction toElementFunction;

  @Before
  public void setUp() throws Exception
  {
    toElementFunction = new XmlToDomElementFunction();
  }

  @Test
  public void apply() throws Exception
  {
    final Element element = toElementFunction.apply("<tr><td/><td/><td><a href=\"blah?1234\"></a></td></tr>");
    assertEquals("1234", new PlayerTableRowNodeToIdFunction().apply(element));
  }
}
