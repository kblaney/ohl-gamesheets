package com.kblaney.ohl.website;

import com.kblaney.ohl.Team;
import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TeamOptionHtmlToTeamFunctionTest
{
  private Function<String, Team> function;

  @Before
  public void setUp()
  {
    function = new TeamOptionHtmlToTeamFunction();
  }

  @Test
  public void apply_noTeamPresent()
  {
    final String s = "NO TEAM IN HERE";
    try
    {
      function.apply(s);
      fail();
    }
    catch (final IllegalArgumentException e)
    {
      assertTrue(e.getMessage().contains(s));
    }
  }

  @Test
  public void apply_teamPresent()
  {
    final int teamNum = 0;
    assertEquals(new Team("Barrie Colts", teamNum),
          function.apply(getTeamOptionHtml(Integer.toString(teamNum))));
  }

  private String getTeamOptionHtml(final String teamNum)
  {
    return "<OPTION value=\"/stats/statdisplay.php?" +
          "type=skaters&subType=" + teamNum +
          "&amp;season_id=42&amp;leagueId=1&amp;lastActive=&amp;" +
          "singleSeason=&amp;confId=0\" >Barrie Colts </option>";
  }

  @Test
  public void apply_teamNumNotInt()
  {
    final String teamNum = "KYLE";
    final String teamOptionHtml = getTeamOptionHtml(teamNum);
    try
    {
      function.apply(teamOptionHtml);
      fail();
    }
    catch (final IllegalArgumentException e)
    {
      assertTrue(e.getMessage().contains(teamOptionHtml));
    }
  }
}
