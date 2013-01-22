package com.kblaney.ohl.website;

import java.net.URL;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class ToGoalieTableRowNodeListFunctionTest
{
  private int teamNum;
  private URL url;
  private UrlToDomDocumentFunction urlToDomDocumentFunction;
  private TeamNumToNodeListFunction function;

  @Before
  public void setUp() throws Exception
  {
    teamNum = 2;
    url = Urls.getGoalieStatsUrl(teamNum);
    urlToDomDocumentFunction = mock(UrlToDomDocumentFunction.class);
    function = new ToGoalieTableRowNodeListFunction(urlToDomDocumentFunction);
  }

  @Test(expected = IllegalStateException.class)
  public void apply_noTableNode() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<a>" + getTableHeaderRow() + getGoalieTableRow() + "</a>"));
    function.apply(teamNum).getLength();
  }

  private String getTableHeaderRow()
  {
    return "<tr><th/><th/><th/><th>SVS</th></tr>";
  }

  private String getGoalieTableRow()
  {
    return "<tr><td/><td/><td><a/></td></tr>";
  }

  @Test
  public void apply_zeroGoalies() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<table>" + getTableHeaderRow() + "</table>"));
    assertEquals(0, function.apply(teamNum).getLength());
  }

  @Test
  public void apply_twoGoalies() throws Exception
  {
    when(urlToDomDocumentFunction.apply(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<table>" + getTableHeaderRow() + getGoalieTableRow() +
                getGoalieTableRow() + "</table>"));
    assertEquals(2, function.apply(teamNum).getLength());
  }
}
