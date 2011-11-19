package com.kblaney.ohl.website;

import com.kblaney.ohl.PlayerBio;
import org.w3c.dom.Element;
import com.google.common.base.Function;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerIdToBioFunctionTest
{
  @Test
  @SuppressWarnings("unchecked")
  public void apply() throws Exception
  {
    final Element bioDivElement = new XmlToDomElementFunction().apply(
          "<div><table>" +
          "<tr><td>Position</td><td>Centre</td></tr>" +
          "<tr><td>Height</td><td>6'03\"</td></tr>" +
          "<tr><td>Weight</td><td>187</td></tr>" +
          "<tr><td>Birthdate</td><td>March 19, 1993</td></tr>" +
          "<tr><td>Hometown</td><td>Belleville, ON, Canada</td></tr>" +
          "</table></div>");
    final String playerId = "1234";
    final Function<String, Node> toBioDivNodeFunction = mock(Function.class);
    when(toBioDivNodeFunction.apply(playerId)).thenReturn(bioDivElement);
    final PlayerBio bio = new PlayerIdToBioFunction(toBioDivNodeFunction).apply(
          playerId);
    assertEquals("C", bio.getPosition());
    assertEquals("6.03", bio.getHeight());
    assertEquals("187", bio.getWeight());
    assertEquals("1993", bio.getBirthYear());
    assertEquals("Belleville, ON", bio.getHometown());
  }
}
