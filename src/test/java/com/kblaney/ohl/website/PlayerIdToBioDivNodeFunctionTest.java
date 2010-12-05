package com.kblaney.ohl.website;

import java.net.URL;
import com.google.common.base.Function;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerIdToBioDivNodeFunctionTest
{
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private Function<String, Node> function;
  private String playerId;
  private URL url;

  @Before
  public void setUp()
  {
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    function = new PlayerIdToBioDivNodeFunction(urlToDomDocumentFunction);
    playerId = "PLAYER_ID";
    url = Urls.getPlayerBioUrl(playerId);
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
  public void apply_noBioDivNode() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<A/>"));
    try
    {
      function.apply(playerId);
      fail();
    }
    catch (final IllegalStateException e)
    {
      assertTrue(e.getMessage().contains(playerId));
    }
  }

  @Test
  public void apply_success() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<a><div class=\"profile\"><div class=\"details\"><table/></div></div></a>"));
    assertNotNull(function.apply(playerId));
  }
}
