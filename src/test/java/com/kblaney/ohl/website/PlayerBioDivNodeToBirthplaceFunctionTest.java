package com.kblaney.ohl.website;

import org.apache.commons.lang.StringUtils;
import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToBirthplaceFunctionTest
{
  private Function<Node, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToBirthplaceFunction();
  }

  @Test
  public void apply_noBirthplace() throws Exception
  {
    final Element element = new XmlToElementFunction().apply(
          "<div><table><tr><td>Birthplace</td><td/></tr></table></div>");
    assertEquals(StringUtils.EMPTY, function.apply(element));
  }

  @Test
  public void apply_birthplaceHasNoComma() throws Exception
  {
    final Element element = getElementWithBirthplace("Belleville");
    assertEquals("Belleville", function.apply(element));
  }

  @Test
  public void apply_birthplaceHasOneComma() throws Exception
  {
    final Element element = getElementWithBirthplace("Belleville, ON");
    assertEquals("Belleville, ON", function.apply(element));
  }

  private Element getElementWithBirthplace(final String birthplace)
        throws Exception
  {
    return new XmlToElementFunction().apply(
          "<div><table><tr><td>Birthplace</td><td>" + birthplace +
          "</td></tr></table></div>");
  }

  @Test
  public void apply_birthplaceHasTwoCommas() throws Exception
  {
    final Element element = getElementWithBirthplace("Belleville, ON, Canada");
    assertEquals("Belleville, ON", function.apply(element));
  }
}
