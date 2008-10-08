package com.kblaney.ohl;

import com.kblaney.commons.html.HtmlUtil;
import com.kblaney.commons.http.HttpUtil;
import com.kblaney.commons.lang.ArgChecker;
import com.kblaney.commons.lang.StringUtil;
import com.kblaney.commons.lang.SystemUtil;
import com.kblaney.commons.xml.XmlUtil;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This is a class about the Ontario Hockey League.
 */
public final class Ohl
{
   /**
    * Keys are the team name (for example, "Belleville Bulls").  Values are the
    * team's number as a String.
    */
   private static Map<String, String> teamNums;

   private static final int STATS_TABLE_GROUP_NUM = 0;
   private static final Pattern STATS_TABLE_PATTERN = Pattern.compile(
         "<SELECT name=\"subStat\".*?</SELECT>", Pattern.DOTALL );

   private static final int TEAM_NUM_GROUP_NUM = 1;
   private static final int TEAM_NAME_GROUP_NUM = 2;
   private static final Pattern TEAM_ROW_PATTERN = Pattern
         .compile( "OPTION value.*?subType=(\\d+).*?>(.*?)<", Pattern.DOTALL );

   private static final String REGULAR_TD = "<td class='content'>(.*?)</td>";
   private static final String NAME_TD =
         "<td class='content'><a href='player.php\\?(.*?)' " +
         "class='content'>(.*?)</a></td>";

   private static final int GOALIE_IS_ROOKIE_GROUP_NUM = 1;
   private static final int GOALIE_SWEATER_NUM_GROUP_NUM = 2;
   private static final int GOALIE_HREF_GROUP_NUM = 3;
   private static final int GOALIE_NAME_GROUP_NUM = 4;
   private static final int GOALIE_GAMES_PLAYED_GROUP_NUM = 5;
   private static final int GOALIE_MINUTES_PLAYED_GROUP_NUM = 6;
   private static final int GOALIE_GOALS_AGAINST_GROUP_NUM = 7;
   private static final int GOALIE_SHUTOUTS_GROUP_NUM = 8;
   private static final int GOALIE_GOALS_AGAINST_AVERAGE_GROUP_NUM = 9;
   private static final int GOALIE_WINS_GROUP_NUM = 10;
   private static final int GOALIE_LOSSES_GROUP_NUM = 11;
   private static final int GOALIE_TIES_GROUP_NUM = 12;
   private static final int GOALIE_SHOTS_AGAINST_GROUP_NUM = 13;
   private static final int GOALIE_SAVES_GROUP_NUM = 14;
   private static final int GOALIE_SAVE_PERCENTAGE_GROUP_NUM = 15;
   private static final Pattern GOALIE_STATS_ROW_PATTERN = Pattern.compile(
         REGULAR_TD + // isRookie
         REGULAR_TD + // sweaterNum
         NAME_TD +
         REGULAR_TD + // gamesPlayed
         REGULAR_TD + // minutes
         REGULAR_TD + // goalsAgainst
         REGULAR_TD + // shutouts
         REGULAR_TD + // goalsAgainstAverage
         REGULAR_TD + // wins
         REGULAR_TD + // losses
         REGULAR_TD + // ties
         REGULAR_TD + // shotsAgainst
         REGULAR_TD + // saves
         REGULAR_TD,  // savePercentage
         Pattern.MULTILINE | Pattern.CASE_INSENSITIVE );

   private static final int PLAYER_BIO_VALUE_GROUP_NUM = 1;
   private static final String PLAYER_BIO_AFTER_TEXT =
         ":</td><td class='content-k'>(.*?)</td>";
   private static final Pattern PLAYER_POSITION_PATTERN = Pattern.compile(
         "Pos\\." + PLAYER_BIO_AFTER_TEXT, Pattern.CASE_INSENSITIVE );
   private static final Pattern PLAYER_HEIGHT_PATTERN = Pattern.compile(
         "Height" + PLAYER_BIO_AFTER_TEXT, Pattern.CASE_INSENSITIVE );
   private static final Pattern PLAYER_WEIGHT_PATTERN = Pattern.compile(
         "Weight" + PLAYER_BIO_AFTER_TEXT, Pattern.CASE_INSENSITIVE );
   private static final Pattern PLAYER_BIRTH_DATE_PATTERN = Pattern.compile(
         "Birthdate" + PLAYER_BIO_AFTER_TEXT, Pattern.CASE_INSENSITIVE );
   private static final Pattern PLAYER_BIRTHPLACE_PATTERN = Pattern.compile(
         "Birthplace" + PLAYER_BIO_AFTER_TEXT, Pattern.CASE_INSENSITIVE );

   private static final String PROTOCOL = "http";
   private static final String HOST = "www.ontariohockeyleague.com";
   private static final String STATS = "/stats/";
   private static final String TEAM_STATS_DISPLAY_PHP =
         STATS + "statdisplay.php";
   private static final String PLAYER_STATS_PHP = STATS + "player.php";
   private static final String PLAYER_GAME_BY_GAME_PHP =
         STATS + "gamebygame.php";
   private static final String SEASON_ID = "32";
   private static final String SCORING_TYPE = "skaters";
   private static final String GOALIES_TYPE = "goalies";
   private static final String TYPE = "type";
   private static final String TEAM_NUM = "subType";
   private static final String HOST_FILE_SEPARATOR = "/";
   private static final String PHP_PAIRS_SEPARATOR = "?";
   private static final String KEY_VALUE_SEPARATOR = "=";
   private static final String PAIR_SEPARATOR = "&";
   private static final String PLAYER_STATS_FILE = TEAM_STATS_DISPLAY_PHP +
         PHP_PAIRS_SEPARATOR + getKeyValueString( TYPE, SCORING_TYPE );

   private static URL playerStatsUrl;
   static
   {
      try
      {
         playerStatsUrl = new URL( PROTOCOL, HOST, PLAYER_STATS_FILE );
      }
      catch (MalformedURLException malformedURLException)
      {
         System.err.println( malformedURLException.getMessage() );
         System.exit( 0 );
      }
   }

   /**
    * Constructor declared private to restrict instantiation.
    */
   private Ohl() throws MalformedURLException
   {
   }

   /**
    * Gets a set of the names of all teams in the league.
    *
    * @return a set of the names of all teams in the league.
    *
    * @throws IOException if an I/O problem occurs
    */
   public static Set<String> getTeamNames() throws IOException
   {
      if (teamNums == null)
      {
         final String playerStatsContent = HttpUtil
               .getContent( playerStatsUrl );
         final Matcher teamStatsTableMatcher = STATS_TABLE_PATTERN
               .matcher( playerStatsContent );
         if (teamStatsTableMatcher.find())
         {
            teamNums = new HashMap<String, String>();
            
            final Matcher teamRowMatcher = TEAM_ROW_PATTERN
                  .matcher( teamStatsTableMatcher
                  .group( STATS_TABLE_GROUP_NUM ) );
            while (teamRowMatcher.find())
            {
               final String teamNum = teamRowMatcher
                     .group( TEAM_NUM_GROUP_NUM );
               final String teamName = teamRowMatcher
                     .group( TEAM_NAME_GROUP_NUM ).trim();
               teamNums.put( teamName, teamNum );
            }
         }
         else
         {
            throw new IOException( "Can't find teams" );
         }
      }

      return teamNums.keySet();
   }

   /**
    * Gets a key and value string to use in a PHP query.
    *
    * @param key the key
    * @param value the value
    *
    * @return the key and value string
    */
   private static String getKeyValueString( final String key,
         final String value )
   {
      ArgChecker.checkIfNull( key, "key" );
      ArgChecker.checkIfNull( value, "value" );

      return key + KEY_VALUE_SEPARATOR + value;
   }

   /**
    * Gets the player scoring URL for a specified team name and type.
    *
    * @param teamName the team name
    * @param type the type, either <code>SCORING_TYPE</code> or
    *           <code>GOALIES_TYPE</code>
    *
    * @return the URL that contains the team's data of the specified type
    */
   private static URL getPlayerScoringUrl( final String teamName,
         final String type )
   {
      ArgChecker.checkIfNull( teamName, "teamName" );
      
      if (teamNums != null)
      {
         final String teamNum = teamNums.get( teamName );
         if (teamNum != null)
         {
            final String file = TEAM_STATS_DISPLAY_PHP + PHP_PAIRS_SEPARATOR +
                  getKeyValueString( TYPE, type ) + PAIR_SEPARATOR +
                  getKeyValueString( TEAM_NUM, teamNum );
 
            try
            {
               return new URL( PROTOCOL, HOST, file );
            }
            catch (MalformedURLException malformedURLException)
            {
               throw new InternalError();
            }
         }
         else
         {
            throw new IllegalArgumentException( teamName );
         }
      }
      else
      {
         throw new IllegalStateException();
      }
   }

   /**
    * Gets a list of the players on the specified team.
    *
    * @param teamName the team name
    * @param isHomeTeam true if we are getting players for the home team
    * @param createGamesheetsFrame the frame used to provide progress
    *
    * @return a list of the players on the specified team
    *
    * @throws IOException if an I/O problem occurs
    */
   private static List<Player> getPlayers( final String teamName,
         final boolean isHomeTeam,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      ArgChecker.checkIfNull( teamName, "teamName" );

      try
      {
         final URL playerScoringUrl = getPlayerScoringUrl( teamName,
               SCORING_TYPE );
         final Document playerScoringDocument = XmlUtil
               .getXmlDocument( playerScoringUrl );
         final Node playerScoringTableNode = XPathAPI.selectSingleNode(
               playerScoringDocument.getDocumentElement(),
               "//table[tr[th='PIMPG']]" );

         if (playerScoringTableNode != null)
         {
            final List<Player> players = new ArrayList<Player>();
            final NodeList playerRowNodeList = XPathAPI.selectNodeList(
                  playerScoringTableNode, "tr[td[position()=3][a]]" );

            for (int i = 0; i < playerRowNodeList.getLength(); i++)
            {
               final Node playerRowNode = playerRowNodeList.item( i );
               final Player player = getPlayer( playerRowNode, isHomeTeam,
                     createGamesheetsFrame );
               if ((player.getPlayerType() != PlayerType.NOT_ACTIVE) ||
                       (player.getName().equals( "Tyler Donati")))
               {
                   players.add( player );
               }
            }

            return players;
         }
         else
         {
            throw new IOException( "Can not find player scoring table" );
         }
      }
      catch (TransformerException t)
      {
         throw new IOException( "Transformer exception when getting " +
                 "player scoring table");
      }
   }

   /**
    * Gets the goalies on the specified team.
    *
    * @param teamName the team name
    * @param createGamesheetsFrame the create gamesheets frame
    *
    * @return the goalies on the specified team
    * @throws IOException if an I/O problem occurs
    */
   private static List<Goalie> getGoalies( final String teamName,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      ArgChecker.checkIfNull( teamName, "teamName" );

      try
      {
         final URL teamGoaliesUrl = getPlayerScoringUrl( teamName,
               GOALIES_TYPE );
         final Document goalieDocument = XmlUtil
               .getXmlDocument( teamGoaliesUrl );
         final Node goalieTableNode = XPathAPI.selectSingleNode(
               goalieDocument.getDocumentElement(), "//table[tr[th='SVS']]" );

         if (goalieTableNode != null)
         {
            final List<Goalie> goalies = new ArrayList<Goalie>();
            final NodeList goalieRowNodeList = XPathAPI.selectNodeList(
                  goalieTableNode, "tr[td[position()=3][a]]" );

            for (int i = 0; i < goalieRowNodeList.getLength(); i++)
            {
               final Node goalieRowNode = goalieRowNodeList.item( i );
               final Goalie goalie = getGoalie( goalieRowNode,
                     createGamesheetsFrame );
               goalies.add( goalie );
            }

            return goalies;
         }
         else
         {
            throw new IOException( "Can not find goalie stats table" );
         }
      }
      catch (TransformerException t)
      {
         throw new IOException( "Transformer exception when getting " +
                 "goalie stats table");
      }
   }

   /**
    * Gets a player from a specified table row node.
    * @param playerRowNode the player's table row node
    * @param isHomeTeam true if we are getting players for the home team
    * @param createGamesheetsFrame the frame used to provide progress
    * @return the player
    * @throws IOException if an I/O problem occurs
    */
   private static Player getPlayer( final Node playerRowNode,
         final boolean isHomeTeam,
         final CreateGamesheetsFrame createGamesheetsFrame ) throws IOException
   {
      ArgChecker.checkIfNull( playerRowNode, "playerRowNode" );

      if (( playerRowNode.getChildNodes() != null ) &&
            ( playerRowNode.getChildNodes().getLength() == 14 ))
      {         
         final String playerId = getPlayerId( playerRowNode );
         final String playerName = getPlayerName( playerRowNode );
         
         if (isHomeTeam)
         {
            createGamesheetsFrame.setHomeTeamPlayer( playerName );
         }
         else
         {
            createGamesheetsFrame.setRoadTeamPlayer( playerName );
         }
         
         final PlayerType playerType = getPlayerType( playerRowNode );
         final int sweaterNum = getSweaterNum( playerRowNode );
         final PlayerStats playerStats = getPlayerStats(
               playerRowNode );
         final PlayerBio playerBio = getPlayerBio( playerId );
         final PlayerStreaks playerStreaks = getPlayerStreaks( playerId,
               playerBio.getPosition() );
         
         return new Player( playerName, playerType, sweaterNum, playerStats,
               playerBio, playerStreaks );
      }
      else
      {
         throw new IllegalArgumentException( "Wrong number of child nodes" );
      }
   }

   /**
    * Gets a goalie from a specified goalie row node.
    * @param goalieRowNode the goalie row node
    * @param createGamesheetsFrame the frame used to provide progress
    * @return the goalie
    * @throws IOException if an I/O problem occurs
    */
   private static Goalie getGoalie( Node goalieRowNode,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      ArgChecker.checkIfNull( goalieRowNode, "goalieRowNode" );

      final String name = getPlayerName( goalieRowNode );
      final String numGamesPlayedString = getPlayerRowNodeValue(
            goalieRowNode, 3 );
      final String numMinutesPlayedString = getPlayerRowNodeValue(
            goalieRowNode, 4 );
      final String numGoalsAgainstString = getPlayerRowNodeValue(
            goalieRowNode, 5 );
      final String numShutoutsString = getPlayerRowNodeValue( goalieRowNode,
            6 );
      final String goalsAgainstAverageString = getPlayerRowNodeValue(
            goalieRowNode, 7 );
      final String numWinsString = getPlayerRowNodeValue( goalieRowNode, 8 );
      final String numLossesString = getPlayerRowNodeValue( goalieRowNode, 9 );
      final String numOvertimeLossesString =
              getPlayerRowNodeValue( goalieRowNode, 10 );
      final String numShootoutLossesString =
              getPlayerRowNodeValue( goalieRowNode, 11 );
      final String numShotsAgainstString = getPlayerRowNodeValue(
            goalieRowNode, 12 );
      final String numSavesString = getPlayerRowNodeValue( goalieRowNode, 13 );
      final String savePercentageString = getPlayerRowNodeValue(
            goalieRowNode, 14 );

      final int numGamesPlayed = Integer.parseInt( numGamesPlayedString );
      final int numMinutesPlayed = Integer.parseInt( numMinutesPlayedString );
      final int numGoalsAgainst = (int) Double.parseDouble( numGoalsAgainstString );
      final int numShutouts = Integer.parseInt( numShutoutsString );
      final double goalsAgainstAverage = Double
            .parseDouble( goalsAgainstAverageString );
      final int numWins = Integer.parseInt( numWinsString );
      final int numLosses = Integer.parseInt( numLossesString );
      final int numOvertimeLosses = Integer.parseInt( numOvertimeLossesString );
      final int numShootoutLosses = Integer.parseInt( numShootoutLossesString );
      final int numShotsAgainst = Integer.parseInt( numShotsAgainstString );
      final int numSaves = Integer.parseInt( numSavesString );
      final double savePercentage = Double.parseDouble( savePercentageString );

      final GoalieStats goalieStats = new GoalieStats( numGamesPlayed,
            numMinutesPlayed, numGoalsAgainst, numShutouts,
            goalsAgainstAverage, numWins, numLosses, numOvertimeLosses,
            numShootoutLosses, numShotsAgainst, numSaves, savePercentage );
      return new Goalie( name, goalieStats );
   }

   private static PlayerStreaks getPlayerStreaks( final String playerId,
         final String playerPosition )
   {
      ArgChecker.checkIfNull( playerId, "playerId" );
      ArgChecker.checkIfNull( playerPosition, "playerPosition" );

      int goalStreak = 0;
      int assistStreak = 0;
      int pointStreak = 0;

      if (!playerPosition.equals( "G" ))
      {
         final URL playerGameByGameUrl = getPlayerGameByGameUrl( playerId );
         final Document playerGameByGameDocument = XmlUtil
               .getXmlDocument( playerGameByGameUrl );

         try
         {
            final int goalIndex = 4;
            final int assistIndex = 5;
            final int pointIndex = 6;
            final NodeList nodeList = XPathAPI.selectNodeList(
                  playerGameByGameDocument.getDocumentElement(),
                  "//tr[td[a[contains(@href,'game-report')]]]" );
            if (nodeList.getLength() > 0)
            {
               boolean onGoalStreak = true;
               boolean onAssistStreak = true;
               boolean onPointStreak = true;

               int i = nodeList.getLength() - 1;
               while (onPointStreak && i >= 0)
               {
                  final Node gameRowNode = nodeList.item( i );
                  final int numGoals = StringUtil.getInt( getGameRowText(
                        gameRowNode, goalIndex ), 0 );
                  final int numAssists = StringUtil.getInt( getGameRowText(
                        gameRowNode, assistIndex ), 0 );
                  final int numPoints = StringUtil.getInt( getGameRowText(
                        gameRowNode, pointIndex ), 0 );
                  if (numGoals > 0)
                  {
                     if (onGoalStreak)
                     {
                        goalStreak++;
                     }
                  }
                  else
                  {
                     onGoalStreak = false;
                  }
                  if (numAssists > 0)
                  {
                     if (onAssistStreak)
                     {
                        assistStreak++;
                     }
                  }
                  else
                  {
                     onAssistStreak = false;
                  }
                  if (numPoints > 0)
                  {
                     if (onPointStreak)
                     {
                        pointStreak++;
                     }
                  }
                  else
                  {
                     onPointStreak = false;
                  }

                  i--;
               }

            }
         }
         catch (final TransformerException e)
         {
            throw new IllegalStateException( e );
         }
      }

      return new PlayerStreaks( goalStreak, assistStreak, pointStreak );
   }

   private static String getGameRowText( final Node gameRowNode,
         final int itemIndex )
   {
      ArgChecker.checkIfNull( gameRowNode, "gameRowNode" );
      if (gameRowNode.getChildNodes().getLength() >= itemIndex )
      {
         final Node node = gameRowNode.getChildNodes().item( itemIndex );
         if (node.getFirstChild() != null)
         {
            return node.getFirstChild().getNodeValue();
         }
      }
      
      return null;
   }

   /**
    * Gets a player bio from a specified player ID.
    * @param playerId the player's ID
    * @return the player bio
    * @throws IOException if an I/O problem occurs
    */
   private static PlayerBio getPlayerBio( final String playerId )
         throws IOException
   {
      try
      {
         final URL playerBioUrl = getPlayerBioUrl( playerId );
         final Document playerBioDocument = XmlUtil.getXmlDocument(
               playerBioUrl );
         final Node playerBioDivNode = XPathAPI.selectSingleNode(
               playerBioDocument.getDocumentElement(),
               "//div[@id='playerSummary'][table[tr[td['Name:']]]]" );
         if (playerBioDivNode == null)
         {
            throw new IOException( "Can't find player bio table node" );
         }
         else
         {
            final String birthdate = getBirthdate( playerBioDivNode );
            final String position = getPosition( playerBioDivNode );
            final String height = getHeight( playerBioDivNode );
            final String weight = getWeight( playerBioDivNode );
            final String hometown = getHometowm( playerBioDivNode );

            return new PlayerBio( birthdate, position, height, weight,
                  hometown );
         }
      }
      catch (TransformerException e)
      {
         throw new IOException( e.getMessage() );
      }
   }

   private static String getFirstChildNodeValue( final Node node )
   {
       if (node.getFirstChild() == null)
       {
           return "";
       }
       else
       {
           return node.getFirstChild().getNodeValue();
       }
   }
           
   private static String getBirthdate( final Node playerBioDivNode )
         throws TransformerException
   {
      final Node birthdateRowNode = XPathAPI.selectSingleNode(
            playerBioDivNode, "//tr[td='Birthdate:']" );
      return getFirstChildNodeValue( birthdateRowNode.getLastChild() );
   }

   private static String getPosition( final Node playerBioDivNode )
         throws TransformerException
   {
      final Node positionRowNode = XPathAPI.selectSingleNode(
            playerBioDivNode, "//tr[td='Pos.:']" );
      return getFirstChildNodeValue( positionRowNode.getLastChild() );
   }

   private static String getHeight( final Node playerBioDivNode )
         throws TransformerException
   {
      final Node heightRowNode = XPathAPI.selectSingleNode(
            playerBioDivNode, "//tr[td='Height:']" );
      final String height = getFirstChildNodeValue(
              heightRowNode.getLastChild() );
      return height.replace( '\'', '.' );
   }

   private static String getWeight( final Node playerBioDivNode )
         throws TransformerException
   {
      final Node weightRowNode = XPathAPI.selectSingleNode(
            playerBioDivNode, "//tr[td='Weight:']" );
      return getFirstChildNodeValue( weightRowNode.getLastChild() );
   }

   private static String getHometowm( final Node playerBioDivNode )
         throws TransformerException
   {
      final Node hometownRowNode = XPathAPI.selectSingleNode(
            playerBioDivNode, "//tr[td='Hometown:']" );

      final String hometown;
      if (hometownRowNode.getLastChild().hasChildNodes())
      {
         final String nodeValue = hometownRowNode.getLastChild().
                 getFirstChild().getNodeValue();
         final Pattern pattern = Pattern.compile( "^[^,]+, [^,]+" );
         final Matcher matcher = pattern.matcher( nodeValue );
         if (matcher.find())
         {
             hometown = matcher.group();
         }
         else
         {
             hometown = nodeValue;
         }
      }
      else
      {
         hometown = "";
      }

      return hometown;
   }

   private static String shortenBirthplace( final String birthplace )
   {
      String shortenedBirthplace = birthplace;

      final int indexOfFirstComma = birthplace.indexOf( ',' );
      if (indexOfFirstComma != -1)
      {
         final int indexOfSecondComma = birthplace.indexOf( ',',
            indexOfFirstComma + 1 );
         if (indexOfSecondComma != -1)
         {
            shortenedBirthplace = birthplace.substring( 0, indexOfSecondComma);
         }
      }

      return shortenedBirthplace;
   }

   private static URL getPlayerBioUrl( final String playerId )
   {
      ArgChecker.checkIfNull( playerId, "playerId" );

      try
      {
         return new URL( PROTOCOL, HOST,
               PLAYER_STATS_PHP + PHP_PAIRS_SEPARATOR + playerId );
      }
      catch (MalformedURLException malformedURLException)
      {
         System.err.println( malformedURLException.getMessage() );
         System.exit( 0 );

         // The following line is only necessary so the compiler
         // does not complain that the method "must return a URL".
         //
         return null;
      }
   }

   private static URL getPlayerGameByGameUrl( final String playerId )
   {
      ArgChecker.checkIfNull( playerId, "playerId" );

      try
      {
         return new URL( PROTOCOL, HOST,
               PLAYER_GAME_BY_GAME_PHP + PHP_PAIRS_SEPARATOR + playerId );
      }
      catch (MalformedURLException malformedURLException)
      {
         System.err.println( malformedURLException.getMessage() );
         System.exit( 0 );

         // The following line is only necessary so the compiler
         // does not complain that the method "must return a URL".
         //
         return null;
      }
   }

   private static String getPlayerHref( final Node playerRowNode )
   {
      ArgChecker.checkIfNull( playerRowNode, "playerRowNode" );

      final int playerIdChildIndex = 2;
      final Node playerLinkNode = playerRowNode.getChildNodes().item(
            playerIdChildIndex );
      return playerLinkNode.getFirstChild().getAttributes().getNamedItem(
            "href" ).getNodeValue();
   }

   private static String getPlayerId( final Node playerRowNode )
   {
      final String playerHref = getPlayerHref( playerRowNode );
      return playerHref.substring( playerHref.indexOf( "?" ) + 1 );
   }

   private static String getPlayerName( final Node playerRowNode )
   {
      ArgChecker.checkIfNull( playerRowNode, "playerRowNode" );

      final int playerNameChildIndex = 2;
      final Node playerLinkNode = playerRowNode.getChildNodes().item(
            playerNameChildIndex );
      return playerLinkNode.getFirstChild().getFirstChild().getNodeValue();
   }

   private static PlayerType getPlayerType( final Node playerRowNode )
   {
      final int playerTypeChildIndex = 0;
      final String isRookie = "*";
      // Veterans have a non-breaking space in the player type column.
      final String isVeteran = "\u00A0";

      final String nodeValue = getPlayerRowNodeValue(
              playerRowNode, playerTypeChildIndex );
      if (isRookie.equals( nodeValue ))
      {
          return PlayerType.ROOKIE;
      }
      else if (isVeteran.equals( nodeValue ))
      {
          return PlayerType.VETERAN;
      }
      else
      {
          return PlayerType.NOT_ACTIVE;
      }
   }

   private static int getSweaterNum( final Node playerRowNode )
   {
      final int sweaterNumChildIndex = 1;

      final String sweaterNumAsString = getPlayerRowNodeValue( playerRowNode,
            sweaterNumChildIndex );

      final int sweaterNumIfParseError = 0;
      return StringUtil.getInt( sweaterNumAsString, sweaterNumIfParseError );
   }

   private static String getPlayerRowNodeValue( final Node playerRowNode,
         final int childNodeIndex )
   {
      ArgChecker.checkIfNull( playerRowNode, "playerRowNode" );

      if (( playerRowNode.getChildNodes() != null ) &&
            ( playerRowNode.getChildNodes().getLength() >= childNodeIndex ))
      {
         final Node childNode = playerRowNode.getChildNodes().item(
               childNodeIndex );
         if (childNode.getFirstChild() != null)
         {
            return childNode.getFirstChild().getNodeValue();
         }
         else
         {
            // No sweater number
            //
            return null;
         }
      }
      else
      {
         throw new IllegalArgumentException( "Invalid playerRowNode" );
      }
   }

   private static PlayerStats getPlayerStats( final Node playerRowNode )
   {
      ArgChecker.checkIfNull( playerRowNode, "playerRowNode" );

      final int numChildNodes = playerRowNode.getChildNodes().getLength();
      if (numChildNodes >= 11)
      {
         final String numGamesPlayedString = getPlayerRowNodeValue(
               playerRowNode, 4 );
         final String numGoalsString = getPlayerRowNodeValue(
               playerRowNode, 5 );
         final String numAssistsString = getPlayerRowNodeValue(
               playerRowNode, 6 );
         final String numPointsString = getPlayerRowNodeValue(
               playerRowNode, 7 );
         final String plusMinusString = StringUtils.remove(
               getPlayerRowNodeValue( playerRowNode, 8 ), '+' );
         final String numPenaltyMinutesString = getPlayerRowNodeValue(
               playerRowNode, 9 );
         final String numPowerplayGoalsString = getPlayerRowNodeValue(
               playerRowNode, 10 );
         final String numShorthandedGoalsString = getPlayerRowNodeValue(
               playerRowNode, 11 );

         final int numGamesPlayed = Integer.parseInt( numGamesPlayedString );
         final int numGoals = Integer.parseInt( numGoalsString );
         final int numAssists = Integer.parseInt( numAssistsString );
         final int numPoints = Integer.parseInt( numPointsString );
         final int plusMinus = Integer.parseInt( plusMinusString );
         final int numPenaltyMinutes = Integer
               .parseInt( numPenaltyMinutesString );
         final int numPowerplayGoals = Integer
               .parseInt( numPowerplayGoalsString );
         final int numShorthandedGoals = Integer
               .parseInt( numShorthandedGoalsString );

         return new PlayerStats( numGamesPlayed, numGoals, numAssists,
               numPoints, plusMinus, numPenaltyMinutes, numPowerplayGoals,
               numShorthandedGoals );
      }
      else
      {
         throw new IllegalArgumentException(
               "Not enough child nodes: " + numChildNodes );
      }
   }

   public static Gamesheets getGamesheets( final String homeTeamName,
         final String roadTeamName, final Calendar gameDate,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      ArgChecker.checkIfNull( createGamesheetsFrame,
            "createGamesheetsFrame" );

      // We get the team names to ensure we know about the teams for which
      // the user wants gamesheets.
      //
      Ohl.getTeamNames();

      final String roadTeamGamesheet = getRoadTeamGamesheet( roadTeamName,
            createGamesheetsFrame );
      createGamesheetsFrame.setRoadTeamPlayer( "Done" );
      final String homeTeamGamesheet = getHomeTeamGamesheet( homeTeamName,
            roadTeamName, gameDate, createGamesheetsFrame );
      createGamesheetsFrame.setHomeTeamPlayer( "Done" );

      return new Gamesheets( homeTeamGamesheet, roadTeamGamesheet );
   }

   private static String getHomeTeamGamesheet( final String homeTeamName,
         final String roadTeamName, final Calendar gameDate,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      final StringBuffer homeTeamGamesheet = new StringBuffer(
            getGameHeading( homeTeamName, roadTeamName, gameDate ) );
      homeTeamGamesheet.append( SystemUtil.LINE_SEPARATOR );
      final boolean isHomeTeam = true;
      homeTeamGamesheet.append( getGamesheet( homeTeamName, isHomeTeam,
            createGamesheetsFrame ) );

      return homeTeamGamesheet.toString();
   }

   private static String getRoadTeamGamesheet( final String roadTeamName,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      final boolean isHomeTeam = false;
      return getGamesheet( roadTeamName, isHomeTeam, createGamesheetsFrame )
            .toString();
   }

   private static String getGameHeading( final String homeTeamName,
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

   private static StringBuffer getGamesheet( final String teamName,
         final boolean isHomeTeam,
         final CreateGamesheetsFrame createGamesheetsFrame )
         throws IOException
   {
      final List<Player> players = getPlayers( teamName, isHomeTeam,
            createGamesheetsFrame );
      Collections.sort( players, new Player.PointsComparator() );
      final List<Goalie> goalies = getGoalies( teamName,
            createGamesheetsFrame );

      final StringBuffer gamesheet = new StringBuffer(
            getTeamHeading( teamName ) );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( Player.getHtmlTable( players ) );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( HtmlUtil.LINE_BREAK );
      gamesheet.append( SystemUtil.LINE_SEPARATOR );
      gamesheet.append( Goalie.getHtmlTable( goalies ) );

      return gamesheet;
   }

   private static String getTeamHeading( final String teamName )
   {
      return HtmlUtil.getH3Heading( teamName );
   }
}
