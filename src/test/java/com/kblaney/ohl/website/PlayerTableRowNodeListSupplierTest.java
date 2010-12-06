package com.kblaney.ohl.website;

import java.net.URL;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerTableRowNodeListSupplierTest
{
  private int teamNum;
  private URL url;
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private PlayerTableRowNodeListSupplier supplier;

  @Before
  public void setUp() throws Exception
  {
    teamNum = 9;
    url = Urls.getSkaterStatsUrl(teamNum);
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    supplier = new PlayerTableRowNodeListSupplier(urlToDomDocumentFunction);
  }

  @Test(expected = IllegalStateException.class)
  public void get_noTableNode() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<a>" + getTableHeaderRow() + getPlayerTableRow() + "</a>"));
    supplier.get(teamNum).getLength();
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
  public void get_zeroPlayers() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<table>" + getTableHeaderRow() + "</table>"));
    assertEquals(0, supplier.get(teamNum).getLength());
  }

  @Test
  public void get_twoPlayers() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply(
          "<table>" + getTableHeaderRow() +
          getPlayerTableRow() + getPlayerTableRow() +
          "</table>"));
    assertEquals(2, supplier.get(teamNum).getLength());
  }
}
