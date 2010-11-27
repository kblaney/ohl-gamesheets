package com.kblaney.ohl.website;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import static org.junit.Assert.*;

public final class PlayerBioDivNodeToPositionFunctionTest
{
  private PlayerBioDivNodeToPositionFunction function;

  @Before
  public void setUp()
  {
    function = new PlayerBioDivNodeToPositionFunction();
  }

  @Test
  public void apply_defenceman() throws Exception
  {
    assertEquals("D", function.apply(getElementWithPosition(" Defenceman ")));
  }

  private Element getElementWithPosition(final String birthdate)
        throws Exception
  {
    return new XmlToElementFunction().apply(
          "<div><table><tr><td>Position</td><td>" + birthdate +
          "</td></tr></table></div>");
  }

  @Test
  public void apply_rightDefenceman() throws Exception
  {
    assertEquals("D", function.apply(getElementWithPosition("Right Defence")));
  }

  @Test
  public void apply_leftDefenceman() throws Exception
  {
    assertEquals("D", function.apply(getElementWithPosition("Left Defence")));
  }

  @Test
  public void apply_centre() throws Exception
  {
    assertEquals("C", function.apply(getElementWithPosition("Centre")));
  }

  @Test
  public void apply_leftWing() throws Exception
  {
    assertEquals("LW", function.apply(getElementWithPosition("Left Wing")));
  }

  @Test
  public void apply_rightWing() throws Exception
  {
    assertEquals("RW", function.apply(getElementWithPosition("Right Wing")));
  }

  @Test
  public void apply_goaltender() throws Exception
  {
    assertEquals("G", function.apply(getElementWithPosition("Goaltender")));
  }

  @Test
  public void apply_unknown() throws Exception
  {
    assertEquals("F/D", function.apply(getElementWithPosition("F/D")));
  }
}
