package com.kblaney.ohl.gamesheets;

import com.kblaney.commons.lang.ArgAssert;

/**
 * HTML gamesheets.
 */
final class HtmlGamesheets
{
  private final String homeTeamGamesheet;
  private final String roadTeamGamesheet;

  public HtmlGamesheets(final String homeTeamGamesheet,
        final String roadTeamGamesheet)
  {
    ArgAssert.notNull(homeTeamGamesheet, "homeTeamGamesheet");
    ArgAssert.notNull(roadTeamGamesheet, "roadTeamGamesheet");

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
