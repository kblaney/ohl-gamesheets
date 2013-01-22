package com.kblaney.ohl.website;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.kblaney.xml.UrlReader;
import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public final class ToGoalieTableRowNodeListFunctionTest
{
  private int teamNum;
  private URL url;
  private UrlReader<Document> urlToDomDocumentFunction;
  private TeamNumToNodeListFunction function;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() throws Exception
  {
    teamNum = 2;
    url = Urls.getGoalieStatsUrl(teamNum);
    urlToDomDocumentFunction = mock(UrlReader.class);
    function = new ToGoalieTableRowNodeListFunction(urlToDomDocumentFunction);
  }

  @Test(expected = IllegalStateException.class)
  public void apply_noTableNode() throws Exception
  {
    when(urlToDomDocumentFunction.readFrom(url)).thenReturn(
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
    when(urlToDomDocumentFunction.readFrom(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<table>" + getTableHeaderRow() + "</table>"));
    assertEquals(0, function.apply(teamNum).getLength());
  }

  @Test
  public void apply_twoGoalies() throws Exception
  {
    when(urlToDomDocumentFunction.readFrom(url)).thenReturn(
          new XmlToDomDocumentFunction().apply("<table>" + getTableHeaderRow() + getGoalieTableRow() +
                getGoalieTableRow() + "</table>"));
    assertEquals(2, function.apply(teamNum).getLength());
  }
}
