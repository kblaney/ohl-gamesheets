package com.kblaney.ohl.gamesheets;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class GamesheetsTest
{
  private String homeTeamGamesheet;
  private String roadTeamGamesheet;
  private Gamesheets gamesheets;

  @Before
  public void setUp()
  {
    homeTeamGamesheet = "HOME_TEAM_GAMESHEET";
    roadTeamGamesheet = "ROAD_TEAM_GAMESHEET";
    gamesheets = new Gamesheets(homeTeamGamesheet, roadTeamGamesheet);
  }

  @Test
  public void getHomeTeamGamesheet()
  {
    assertEquals(homeTeamGamesheet, gamesheets.getHomeTeamGamesheet());
  }

  @Test
  public void getRoadTeamGamesheet()
  {
    assertEquals(roadTeamGamesheet, gamesheets.getRoadTeamGamesheet());
  }
}
