package com.kblaney.ohl.website;

import org.apache.commons.lang.StringUtils;
import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToHometownFunctionTest
{
  private Function<Node, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToHometownFunction();
  }

  @Test
  public void apply_noHometown() throws Exception
  {
    final Element element = new XmlToDomElementFunction()
          .apply("<div><table><tr><td>Hometown</td><td/></tr></table></div>");
    assertEquals(StringUtils.EMPTY, function.apply(element));
  }

  @Test
  public void apply_hometownHasNoComma() throws Exception
  {
    final Element element = getElementWithHometown("Belleville");
    assertEquals("Belleville", function.apply(element));
  }

  @Test
  public void apply_hometownHasOneComma() throws Exception
  {
    final Element element = getElementWithHometown("Belleville, ON");
    assertEquals("Belleville, ON", function.apply(element));
  }

  private Element getElementWithHometown(final String hometown) throws Exception
  {
    return new XmlToDomElementFunction().apply("<div><table><tr><td>Hometown</td><td>" + hometown +
          "</td></tr></table></div>");
  }

  @Test
  public void apply_hometownHasTwoCommas() throws Exception
  {
    final Element element = getElementWithHometown("Belleville, ON, Canada");
    assertEquals("Belleville, ON", function.apply(element));
  }
}
