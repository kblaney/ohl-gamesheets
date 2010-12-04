package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToHeightFunctionTest
{
  private Function<Node, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToHeightFunction();
  }

  @Test
  public void apply_heightHasApostrophe() throws Exception
  {
    final Element element = getElementWithHeight("6'02");
    assertEquals("6.02", function.apply(element));
  }

  private Element getElementWithHeight(final String height) throws Exception
  {
    return new XmlToDomElementFunction().apply(
          "<div><table><tr><td>Height</td><td>" + height +
          "</td></tr></table></div>");
  }

  @Test
  public void apply_heightHasQuote() throws Exception
  {
    final Element element = getElementWithHeight("5.11\"");
    assertEquals("5.11", function.apply(element));
  }

  @Test
  public void apply_heightHasApostropheAndQuote() throws Exception
  {
    final Element element = getElementWithHeight("6'3\"");
    assertEquals("6.3", function.apply(element));
  }
}
