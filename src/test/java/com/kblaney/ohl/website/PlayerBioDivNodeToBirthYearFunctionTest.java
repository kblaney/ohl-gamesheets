package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToBirthYearFunctionTest
{
  private Function<Node, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToBirthYearFunction();
  }

  @Test
  public void apply_invalidBirthYear() throws Exception
  {
    final Element element = getElementWithBirthdate(" February 7, 199 ");
    assertEquals(StringUtils.EMPTY, function.apply(element));
  }

  private Element getElementWithBirthdate(final String birthdate) throws Exception
  {
    return new XmlToDomElementFunction().apply("<div><table><tr><td>Birthdate</td><td>" + birthdate +
          "</td></tr></table></div>");
  }

  @Test
  public void apply_birthYearPresent() throws Exception
  {
    final Element element = getElementWithBirthdate(" January 13, 1994 ");
    assertEquals("1994", function.apply(element));
  }

  @Test
  public void apply_2011BirthYearPresent() throws Exception
  {
    final Element element = getElementWithBirthdate(" January 13, 2011 ");
    assertEquals(StringUtils.EMPTY, function.apply(element));
  }
}
