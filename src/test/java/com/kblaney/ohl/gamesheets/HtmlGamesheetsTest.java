package com.kblaney.ohl.gamesheets;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class HtmlGamesheetsTest
{
  private String homeTeamGamesheet;
  private String roadTeamGamesheet;
  private HtmlGamesheets htmlGamesheets;

  @Before
  public void setUp()
  {
    homeTeamGamesheet = "HOME_TEAM_GAMESHEET";
    roadTeamGamesheet = "ROAD_TEAM_GAMESHEET";
    htmlGamesheets = new HtmlGamesheets(homeTeamGamesheet, roadTeamGamesheet);
  }

  @Test
  public void getHomeTeamGamesheet()
  {
    assertEquals(homeTeamGamesheet, htmlGamesheets.getHomeTeamGamesheet());
  }

  @Test
  public void getRoadTeamGamesheet()
  {
    assertEquals(roadTeamGamesheet, htmlGamesheets.getRoadTeamGamesheet());
  }
}
