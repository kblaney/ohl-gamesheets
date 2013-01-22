package com.kblaney.ohl.website;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.google.common.base.Function;
import com.kblaney.url.UrlFunction;
import java.io.IOException;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public final class PlayerIdToGameByGameRowNodeListFunctionTest
{
  private UrlFunction<Document> urlToDomDocumentFunction;
  private Function<String, NodeList> function;
  private String playerId;
  private URL url;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp()
  {
    urlToDomDocumentFunction = mock(UrlFunction.class);
    function = new PlayerGameByGameFilePathToGameByGameRowNodeListFunction(urlToDomDocumentFunction);
    playerId = "PLAYER_ID";
    url = Urls.getPlayerGameByGameUrl(playerId);
  }

  @Test
  public void apply_getUrlContentsFailure() throws Exception
  {
    final Throwable failure = new IOException();
    when(urlToDomDocumentFunction.convert(url)).thenThrow(failure);
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
    when(urlToDomDocumentFunction.convert(url)).thenReturn(new XmlToDomDocumentFunction().apply("<a/>"));
    assertEquals(0, function.apply(playerId).getLength());
  }

  @Test
  public void apply_twoGamesPlayed() throws Exception
  {
    when(urlToDomDocumentFunction.convert(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<a><div id=\"gamebygameBlock\"><table class=\"statsTable\">" +
                getOneGameTableRow() + getOneGameTableRow() + "</table></div></a>"));
    assertEquals(2, function.apply(playerId).getLength());
  }

  private String getOneGameTableRow()
  {
    return "<tr><td><a/></td></tr>";
  }
}
