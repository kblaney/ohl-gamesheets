package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.ArgChecker;
import com.kblaney.commons.lang.SystemUtil;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Gets HTML gamesheets.
 */
public final class HtmlGamesheetsGetter
{
   /**
    * Constructs a new instance of HtmlGamesheetsGetter.
    */
   public HtmlGamesheetsGetter()
   {
   }

   public HtmlGamesheets getGamesheets( final String homeTeamName,
         final String roadTeamName, final Calendar gameDate,
         final ProgressIndicator progressIndicator ) throws IOException
   {
      ArgChecker.checkIfNull( progressIndicator, "progressIndicator" );

      final String roadTeamGamesheet = getRoadTeamGamesheet( roadTeamName,
            progressIndicator );
      progressIndicator.setPlayerInProgress( "Done road team" );
      final String homeTeamGamesheet = getHomeTeamGamesheet( homeTeamName,
            roadTeamName, gameDate, progressIndicator );
      progressIndicator.setPlayerInProgress( "Done" );

      return new HtmlGamesheets( homeTeamGamesheet, roadTeamGamesheet );
   }

   private String getHomeTeamGamesheet( final String homeTeamName,
         final String roadTeamName, final Calendar gameDate,
         final ProgressIndicator progressIndicator ) throws IOException
   {
      final StringBuffer homeTeamGamesheet = new StringBuffer(
            getGameHeading( homeTeamName, roadTeamName, gameDate ) );
      homeTeamGamesheet.append( SystemUtil.LINE_SEPARATOR );
      homeTeamGamesheet.append( getGamesheet( homeTeamName,
            progressIndicator ) );

      return homeTeamGamesheet.toString();
   }

   private String getRoadTeamGamesheet( final String roadTeamName,
         final ProgressIndicator progressIndicator ) throws IOException
   {
      return getGamesheet( roadTeamName, progressIndicator ).toString();
   }

   private String getGameHeading( final String homeTeamName,
         final String roadTeamName, final Calendar gameDate )
   {
      ArgChecker.checkIfNull( gameDate, "gameDate" );

      final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "EEE., MMM. d, yyyy" );
      final FieldPosition fieldPosition = new FieldPosition( 0 );
      final StringBuffer bufferToAppendTo = new StringBuffer();
      final StringBuffer formattedGameDate = simpleDateFormat.format(
            gameDate.getTime(), bufferToAppendTo, fieldPosition );

      return HtmlUtil.getH3Heading( roadTeamName + " @ " +
            homeTeamName + " - " + formattedGameDate );
   }

   private StringBuffer getGamesheet( final String teamName,
         final ProgressIndicator progressIndicator ) throws IOException
   {
      final StatsProvider statsProvider = new Website();

      final List<Player> players = statsProvider.getPlayers( teamName,
              progressIndicator );
      Collections.sort( players, new PlayerPointsComparator() );
      final List<Goalie> goalies = statsProvider.getGoalies( teamName );

      final StringBuffer gamesheet = new StringBuffer(
            getTeamHeading( teamName ) );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( new PlayerHtmlTableGetter().getHtmlTable( players ) );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( HtmlUtil.LINE_BREAK );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( new GoalieHtmlTableGetter().getHtmlTable( goalies ) );

      return gamesheet;
   }

   private String getTeamHeading( final String teamName )
   {
      return HtmlUtil.getH3Heading( teamName );
   }
}
