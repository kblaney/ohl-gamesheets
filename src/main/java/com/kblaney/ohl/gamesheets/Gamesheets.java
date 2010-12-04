package com.kblaney.ohl.gamesheets;

public final class Gamesheets
{
  private final String homeTeamGamesheet;
  private final String roadTeamGamesheet;

  public Gamesheets(final String homeTeamGamesheet,
        final String roadTeamGamesheet)
  {
    this.homeTeamGamesheet = homeTeamGamesheet;
    this.roadTeamGamesheet = roadTeamGamesheet;
  }

  public String getHomeTeamGamesheet()
  {
    return homeTeamGamesheet;
  }

  public String getRoadTeamGamesheet()
  {
    return roadTeamGamesheet;
  }
}
