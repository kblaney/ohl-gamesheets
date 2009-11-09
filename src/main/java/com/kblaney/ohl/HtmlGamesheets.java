package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

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
      ArgAssert.notNull( homeTeamGamesheet, "homeTeamGamesheet" );
      ArgAssert.notNull( roadTeamGamesheet, "roadTeamGamesheet" );

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
