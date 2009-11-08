package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

/**
 * HTML gamesheets.
 */
public class HtmlGamesheets
{
   private final String homeTeamGamesheet;
   private final String roadTeamGamesheet;

   public HtmlGamesheets( final String homeTeamGamesheet,
           final String roadTeamGamesheet )
   {
      ArgChecker.checkIfNull( homeTeamGamesheet, "homeTeamGamesheet" );
      ArgChecker.checkIfNull( roadTeamGamesheet, "roadTeamGamesheet" );

      this.homeTeamGamesheet = homeTeamGamesheet;
      this.roadTeamGamesheet = roadTeamGamesheet;
   }

   public String getHomeTeamGamesheet()
   {
      return this.homeTeamGamesheet;
   }

   public String getRoadTeamGamesheet()
   {
      return this.roadTeamGamesheet;
   }
}
