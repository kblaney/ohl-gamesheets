package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.lang.ArgChecker;
import com.kblaney.commons.lang.SystemUtil;
import java.util.Comparator;
import java.util.List;

public class Player
{
   private String name;
   private PlayerType playerType = PlayerType.VETERAN;
   private int sweaterNum = 0;
   private PlayerStats stats;
   private PlayerBio bio;
   private PlayerStreaks streaks;

   public static class PointsComparator implements Comparator
   {
      public int compare( Object obj1, Object obj2 )
      {
         Player player1 = (Player) obj1;
         Player player2 = (Player) obj2;

         if (player1.getStats().getNumPoints() == player2.getStats()
               .getNumPoints())
         {
            if (player1.getStats().getNumGoals() == player2.getStats()
                  .getNumGoals())
            {
               if (player1.getStats().getNumGamesPlayed() == player2
                     .getStats().getNumGamesPlayed())
               {
                  return 0;
               }
               else if (player1.getStats().getNumGamesPlayed() < player2
                     .getStats().getNumGamesPlayed())
               {
                  return -1;
               }
               else
               {
                  return 1;
               }
            }
            else if (player1.getStats().getNumGoals() < player2.getStats()
                  .getNumGoals())
            {
               return 1;
            }
            else
            {
               return -1;
            }
         }
         else if (player1.getStats().getNumPoints() < player2.getStats()
               .getNumPoints())
         {
            return 1;
         }
         else
         {
            return -1;
         }
      }
   }

   public Player( final String name, final PlayerType playerType,
         final int sweaterNum, final PlayerStats stats, final PlayerBio bio,
         final PlayerStreaks streaks )
   {
      setName( name );
      setPlayerType( playerType );
      setSweaterNum( sweaterNum );
      setStats( stats );
      setBio( bio );
      setStreaks( streaks );
   }

   public String getName()
   {
      return this.name;
   }

   public void setName( final String name )
   {
      this.name = name;
   }

   public PlayerType getPlayerType()
   {
      return this.playerType;
   }

   public void setPlayerType( final PlayerType playerType )
   {
      this.playerType = playerType;
   }

   public int getSweaterNum()
   {
      return this.sweaterNum;
   }

   public void setSweaterNum( final int sweaterNum )
   {
      // On the OHL site, some players have a sweater number of 0, so we
      // check for non-negativity rather than positivity.
      //
      ArgChecker.checkIfNegative( sweaterNum, "sweaterNum" );
      
      this.sweaterNum = sweaterNum;
   }

   public PlayerStats getStats()
   {
      // Return a defensive copy.
      //
      return new PlayerStats( this.stats );
   }

   public void setStats( final PlayerStats stats )
   {
      ArgChecker.checkIfNull( stats, "stats" );
      
      // Make a defensive copy.
      //
      this.stats = new PlayerStats( stats );
   }

   public PlayerBio getBio()
   {
      // Return a defensive copy.
      //
      return new PlayerBio( this.bio );
   }

   public void setBio( final PlayerBio bio )
   {
      ArgChecker.checkIfNull( bio, "bio" );

      // Make a defensive copy.
      //
      this.bio = new PlayerBio( bio );
   }

   public PlayerStreaks getStreaks()
   {
      // Return a defensive copy.
      //
      return new PlayerStreaks( this.streaks );
   }

   public void setStreaks( final PlayerStreaks streaks )
   {
      ArgChecker.checkIfNull( streaks, "streaks" );
      
      // Make a defensive copy.
      //
      this.streaks = new PlayerStreaks( streaks );
   }

   public static String getHtmlTable( final List<Player> players )
   {
      ArgChecker.checkIfNull( players, "players" );

      StringBuffer htmlTable = new StringBuffer(
            HtmlUtil.TABLE_START );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      htmlTable.append( getHtmlTableHeader() );
      htmlTable.append( SystemUtil.LINE_SEPARATOR );
      for (Player player : players)
      {
         htmlTable.append( getHtmlTableRow( player ) );
         htmlTable.append( SystemUtil.LINE_SEPARATOR );
      }
      htmlTable.append( HtmlUtil.TABLE_END );

      return htmlTable.toString();
   }

   private static String getHtmlTableHeader()
   {
      StringBuffer htmlTableHeader = new StringBuffer();
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

   private static String getHtmlTableRow( final Player player )
   {
      ArgChecker.checkIfNull( player, "player" );

      StringBuffer htmlTableRow = new StringBuffer( HtmlUtil.TABLE_ROW_START );
      htmlTableRow.append( HtmlUtil.getTableElement( getRookieString( player
            .getPlayerType() ), false ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getSweaterNum() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getName(), false ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumGamesPlayed() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumAssists() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumPoints() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getPlusMinus() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumPenaltyMinutes() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumPowerPlayGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStats().getNumShorthandedGoals() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStreaks().getGoalStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStreaks().getAssistStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( Integer.toString( player
            .getStreaks().getPointStreak() ), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getBio()
            .getBirthYear(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getBio()
            .getPosition(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getBio()
            .getHeight(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getBio()
            .getWeight(), true ) );
      htmlTableRow.append( HtmlUtil.getTableElement( player.getBio()
            .getHomeTown(), false ) );

      htmlTableRow.append( HtmlUtil.TABLE_ROW_END );
      return htmlTableRow.toString();
   }

   private static String getRookieString( final PlayerType playerType )
   {
      String rookieString = null;
      
      if (playerType == PlayerType.ROOKIE)
      {
         rookieString = "*";
      }
      else
      {
         rookieString = "&nbsp;";
      }
      
      return rookieString;
   }
}
