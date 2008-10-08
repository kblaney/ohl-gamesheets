package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

public class Gamesheets
{
   private String homeTeamGamesheet;
   private String roadTeamGamesheet;

   public Gamesheets( final String homeTeamGamesheet, final String roadTeamGamesheet )
   {
      setHomeTeamGamesheet( homeTeamGamesheet );
      setRoadTeamGamesheet( roadTeamGamesheet );
   }

   public String getHomeTeamGamesheet()
   {
      return this.homeTeamGamesheet;
   }

   public void setHomeTeamGamesheet( String homeTeamGamesheet )
   {
      ArgChecker.checkIfNull( homeTeamGamesheet, "homeTeamGamesheet" );
      
      this.homeTeamGamesheet = homeTeamGamesheet;
   }

   public String getRoadTeamGamesheet()
   {
      return this.roadTeamGamesheet;
   }

   public void setRoadTeamGamesheet( String roadTeamGamesheet )
   {
      ArgChecker.checkIfNull( roadTeamGamesheet, "roadTeamGamesheet" );
      
      this.roadTeamGamesheet = roadTeamGamesheet;
   }
}
