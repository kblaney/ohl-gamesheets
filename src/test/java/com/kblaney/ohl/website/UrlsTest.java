package com.kblaney.ohl.website;

import org.junit.Test;
import static org.junit.Assert.*;

public final class UrlsTest
{
  @Test
  public void getPlayerStatsUrl()
  {
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=skaters&season_id=44", Urls.getPlayerStatsUrl().toString());
  }

  @Test
  public void getSkaterStatsUrl()
  {
    final int teamNum = 17;
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=skaters&subType=17&season_id=44",
          Urls.getSkaterStatsUrl(teamNum).toString());
  }

  @Test
  public void getGoalieStatsUrl()
  {
    final int teamNum = 9;
    assertEquals("http://www.ontariohockeyleague.com/stats/statdisplay.php?" +
          "type=goalies&subType=9&season_id=44",
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
    final String playerId = "5678";
    assertEquals("http://www.ontariohockeyleague.com/stats/gamebygame.php?5678",
          Urls.getPlayerGameByGameUrl(playerId).toString());
  }
}
