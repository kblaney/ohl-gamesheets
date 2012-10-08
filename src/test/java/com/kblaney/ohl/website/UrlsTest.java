package com.kblaney.ohl.website;

import org.junit.Test;
import static org.junit.Assert.*;

public final class UrlsTest
{
  @Test
  public void getPlayerStatsUrl()
  {
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=skaters&season_id=46", Urls.getPlayerStatsUrl().toString());
  }

  @Test
  public void getSkaterStatsUrl()
  {
    final int teamNum = 17;
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=skaters&team_id=17&season_id=46",
          Urls.getSkaterStatsUrl(teamNum).toString());
  }

  @Test
  public void getGoalieStatsUrl()
  {
    final int teamNum = 9;
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=goalies&team_id=9&season_id=46",
          Urls.getGoalieStatsUrl(teamNum).toString());
  }

  @Test
  public void getPlayerBioUrl()
  {
    final String playerId = "1234";
    assertEquals("http://www.ontariohockeyleague.com/stats/player.php?1234",
          Urls.getPlayerBioUrl(playerId).toString());
  }

  @Test
  public void getPlayerGameByGameUrl()
  {
    final String gameByGameFilePath = "/roster/show/id/6640";
    assertEquals("http://www.ontariohockeyleague.com/roster/show/id/6640",
          Urls.getPlayerGameByGameUrl(gameByGameFilePath).toString());
  }
}
