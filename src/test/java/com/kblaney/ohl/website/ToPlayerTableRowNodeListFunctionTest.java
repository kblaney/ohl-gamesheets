package com.kblaney.ohl.website;

import java.net.URL;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class ToPlayerTableRowNodeListFunctionTest
{
  private int teamNum;
  private URL url;
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private TeamNumToNodeListFunction function;

  @Before
  public void setUp() throws Exception
  {
    teamNum = 9;
    url = Urls.getSkaterStatsUrl(teamNum);
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    function = new ToPlayerTableRowNodeListFunction(urlToDomDocumentFunction);
  }

  @Test(expected = IllegalStateException.class)
  public void apply_noTableNode() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<a>" + getTableHeaderRow() + getPlayerTableRow() + "</a>"));
    function.apply(teamNum).getLength();
  }

  private String getTableHeaderRow()
  {
    return "<tr><th/><th/><th/><th>PIMPG</th></tr>";
  }

  private String getPlayerTableRow()
  {
    return "<tr><td/><td/><td><a/></td></tr>";
  }

  @Test
  public void apply_zeroPlayers() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<table>" + getTableHeaderRow() + "</table>"));
    assertEquals(0, function.apply(teamNum).getLength());
  }

  @Test
  public void apply_twoPlayers() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<table>" + getTableHeaderRow() +
          getPlayerTableRow() + getPlayerTableRow() +
          "</table>"));
    assertEquals(2, function.apply(teamNum).getLength());
  }
}
