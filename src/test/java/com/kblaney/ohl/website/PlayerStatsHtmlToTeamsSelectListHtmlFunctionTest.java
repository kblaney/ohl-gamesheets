package com.kblaney.ohl.website;

import com.google.common.base.Function;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerStatsHtmlToTeamsSelectListHtmlFunctionTest
{
  private Function<String, String> function;

  @Before
  public void setUp()
  {
    function = new PlayerStatsHtmlToTeamsSelectListHtmlFunction();
  }

  @Test
  public void apply_noTeamSelectList()
  {
    final String playerStatsHtml = "PLAYER_STATS_HTML";
    try
    {
      function.apply(playerStatsHtml);
      fail();
    }
    catch (final IllegalArgumentException e)
    {
      assertTrue(e.getMessage().contains(playerStatsHtml));
    }
  }

  @Test
  public void apply_teamSelectList()
  {
    final String playerStatsHtml =
            "Before<SELECT name=\"subStat\">Hello</SELECT>After";
    assertEquals("<SELECT name=\"subStat\">Hello</SELECT>",
            function.apply(playerStatsHtml));
  }

  @Test
  public void apply_teamSelectListContainsNewline()
  {
    final String playerStatsHtml =
            "Before<SELECT name=\"subStat\">A\nB\nC</SELECT>After";
    assertEquals("<SELECT name=\"subStat\">A\nB\nC</SELECT>",
            function.apply(playerStatsHtml));
  }

  @Test
  public void apply_twoSelectEndTags()
  {
    final String playerStatsHtml =
            "Before<SELECT name=\"subStat\">A</SELECT>After<SELECT>B</SELECT>";
    assertEquals("<SELECT name=\"subStat\">A</SELECT>",
            function.apply(playerStatsHtml));
  }
}
