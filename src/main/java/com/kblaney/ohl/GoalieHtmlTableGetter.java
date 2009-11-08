package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.ArgChecker;
import com.kblaney.commons.lang.SystemUtil;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Gets HTML tables for goalies.
 */
public final class GoalieHtmlTableGetter
{
   /**
    * Constructs a new instance of GoalieHtmlTableGetter.
    */
   public GoalieHtmlTableGetter()
   {
   }

   /**
    * Gets an HTML table using a specified list of goalies.
    *
    * @param goalies the list of goalies, which can't be null or empty
    *
    * @return the HTML table
    */
   public String getHtmlTable( final List<Goalie> goalies )
   {
      ArgChecker.checkIfNull( goalies, "goalies" );

      StringBuffer htmlTable = new StringBuffer(
            HtmlUtil.TABLE_START );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      htmlTable.append( getHtmlTableHeader() );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      for (Goalie goalie : goalies)
      {
         htmlTable.append( getHtmlTableRow( goalie ) );
         htmlTable.append( SystemUtil.LINE_SEPARATOR );
      }
      htmlTable.append( HtmlUtil.TABLE_END );

      return htmlTable.toString();
   }

   private String getHtmlTableHeader()
   {
      final StringBuffer htmlTableHeader = new StringBuffer();
      htmlTableHeader.append( HtmlUtil.TABLE_ROW_START );
      htmlTableHeader.append( HtmlUtil.getTableElement( "Name", false ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "GP", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "W", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "L", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "OTL", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "SOL", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "MIN", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "SO", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "SA", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "SV", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "GA", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "GAA", true ) );
      htmlTableHeader.append( HtmlUtil.getTableElement( "Sv%", true ) );
      htmlTableHeader.append( HtmlUtil.TABLE_ROW_END );
      return htmlTableHeader.toString();
   }

   private String getHtmlTableRow( final Goalie goalie )
   {
      ArgChecker.checkIfNull( goalie, "goalie" );

      final DecimalFormat gaaDecimalFormat = new DecimalFormat( "0.00" );
      final DecimalFormat savePercentageDecimalFormat =
              new DecimalFormat( "0.000" );

      final StringBuffer htmlTableRow =
              new StringBuffer( HtmlUtil.TABLE_ROW_START );
      htmlTableRow.append( HtmlUtil.getTableElement(
              goalie.getName(), false ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumGamesPlayed() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumWins() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumRegulationLosses() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumOvertimeLosses() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumShootoutLosses() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumMinutesPlayed() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumShutouts() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumShotsAgainst() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumSaves() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString(
              goalie.getStats().getNumGoalsAgainst() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( gaaDecimalFormat.format(
              goalie.getStats().getGoalsAgainstAverage() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement(
              savePercentageDecimalFormat.format( goalie.getStats().
              getSavePercentage() ), true ) );
      htmlTableRow.append( HtmlUtil.TABLE_ROW_END );
      return htmlTableRow.toString();
   }
}
