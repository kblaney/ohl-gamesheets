package com.kblaney.ohl.website;

import java.net.URL;
import java.io.IOException;
import com.google.common.base.Function;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerIdToGameByGameRowNodeListFunctionTest
{
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private Function<String, NodeList> function;
  private String playerId;
  private URL url;

  @Before
  public void setUp()
  {
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    function = new PlayerGameByGameFilePathToGameByGameRowNodeListFunction(urlToDomDocumentFunction);
    playerId = "PLAYER_ID";
    url = Urls.getPlayerGameByGameUrl(playerId);
  }

  @Test
  public void apply_getUrlContentsFailure() throws Exception
  {
    final Throwable failure = new IOException();
    when(urlToDomDocumentFunction.apply(url)).thenThrow(failure);
    try
    {
      function.apply(playerId);
      fail();
    }
    catch (final IllegalStateException e)
    {
      assertEquals(failure, e.getCause());
      assertTrue(e.getMessage().contains(url.toString()));
    }
  }

  @Test
  public void apply_noGamesPlayed() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(new XmlToDomDocumentFunction().apply("<a/>"));
    assertEquals(0, function.apply(playerId).getLength());
  }

  @Test
  public void apply_twoGamesPlayed() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<a><div id=\"gamebygameBlock\"><table class=\"statsTable\">" +
                getOneGameTableRow() + getOneGameTableRow() + "</table></div></a>"));
    assertEquals(2, function.apply(playerId).getLength());
  }

  private String getOneGameTableRow()
  {
    return "<tr><td><a/></td></tr>";
  }
}
