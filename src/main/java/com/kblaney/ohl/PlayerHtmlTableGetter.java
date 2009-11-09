package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.ArgAssert;
import com.kblaney.commons.lang.SystemUtil;
import java.util.List;

/**
 * Gets HTML tables for players.
 */
public final class PlayerHtmlTableGetter
{
   /**
    * Constructs a new instance of PlayerHtmlTableGetter.
    */
   public PlayerHtmlTableGetter()
   {
   }

   /**
    * Gets an HTML table using a specified list of players.
    *
    * @param players the list of players, which can't be null or empty
    *
    * @return the HTML table
    */
   public String getHtmlTable( final List<Player> players )
   {
      ArgAssert.notNull( players, "players" );

      StringBuffer htmlTable = new StringBuffer( HtmlUtil.TABLE_START );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      htmlTable.append( getHtmlTableHeader() );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      for (final Player player : players)
      {
         htmlTable.append( getHtmlTableRow( player ) );
         htmlTable.append( SystemUtil.LINE_SEPARATOR );
      }
      htmlTable.append( HtmlUtil.TABLE_END );

      return htmlTable.toString();
   }

   private String getHtmlTableHeader()
   {
      final StringBuffer htmlTableHeader = new StringBuffer();
      htmlTableHeader.append( HtmlUtil.TABLE_ROW_START );
      htmlTableHeader.append( HtmlUtil.getTableElement( "R", false ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "#", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "Name", false ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "GP", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "G", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "A", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "PT", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "+/-", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "PIM", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "PP", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "SH", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "G", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "A", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "P", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "Birth", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "P", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "H", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "W", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "Home Town", false ) );
      htmlTableHeader.append( HtmlUtil.TABLE_ROW_END );
      return htmlTableHeader.toString();
   }

   private String getHtmlTableRow( final Player player )
   {
      ArgAssert.notNull( player, "player" );

      final StringBuffer htmlTableRow = new StringBuffer(
              HtmlUtil.TABLE_ROW_START );
      htmlTableRow.append( HtmlUtil.getTableElement( getRookieString(
              player.getPlayerType() ), false ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getSweaterNum() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getName(), false ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumGamesPlayed() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumAssists() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumPoints() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getPlusMinus() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumPenaltyMinutes() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumPowerPlayGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStats().getNumShorthandedGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStreaks().getGoalStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStreaks().getAssistStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              player.getStreaks().getPointStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getBio().getBirthYear(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getBio().getPosition(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getBio().getHeight(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getBio().getWeight(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              player.getBio().getHomeTown(), false ) );
      htmlTableRow.append( HtmlUtil.TABLE_ROW_END );
      return htmlTableRow.toString();
   }

   private static String getRookieString( final PlayerType playerType )
   {
      if (playerType == PlayerType.ROOKIE)
      {
         return "*";
      }
      else
      {
         return "&nbsp;";
      }
   }
}
