package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.kblaney.commons.io.UrlContentsGetter;
import com.kblaney.ohl.gamesheets.StatsProvider;
import com.kblaney.commons.io.UsAsciiUrlContentsGetter;
import com.kblaney.commons.lang.ArgAssert;
import com.kblaney.commons.xml.XmlUtil;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.TransformerException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Ontario Hockey League website.
 */
public class Website implements StatsProvider
{
  private static final String PROTOCOL = "http";
  private static final String HOST = "www.ontariohockeyleague.com";
  private static final String STATS = "/stats/";
  private static final String TEAM_STATS_DISPLAY_PHP =
        STATS + "statdisplay.php";
  private static final String PLAYER_STATS_PHP = STATS + "player.php";
  private static final String PLAYER_GAME_BY_GAME_PHP =
        STATS + "gamebygame.php";
  private static final String SCORING_TYPE = "skaters";
  private static final String GOALIES_TYPE = "goalies";
  private static final String TYPE = "type";
  private static final String TEAM_NUM = "subType";
  private static final String PAIR_SEPARATOR = "&";
  private static final String SEASON_ID_KEY = "season_id";
  private static final String SEASON_ID = "42";
  private static final String PLAYER_STATS_FILE = TEAM_STATS_DISPLAY_PHP +
        PhpUtil.PAIRS_SEPARATOR +
        PhpUtil.getKeyValueString(TYPE, SCORING_TYPE) + PAIR_SEPARATOR +
        PhpUtil.getKeyValueString(SEASON_ID_KEY, SEASON_ID);
  private static final URL PLAYER_STATS_URL;

  static
  {
    try
    {
      PLAYER_STATS_URL = new URL(PROTOCOL, HOST, PLAYER_STATS_FILE);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  private UrlContentsGetter urlContentsGetter = new UsAsciiUrlContentsGetter();
  private Function<String, Set<Team>> toTeamsFunction =
        new PlayerStatsHtmlToTeamsFunction();
  private Set<Team> teams;

  /** {@inheritDoc} */
  public Set<String> getTeamNames() throws IOException
  {
    final Set<String> teamNames = Sets.newHashSet();
    for (final Team team : getTeams())
    {
      teamNames.add(team.getName());
    }
    return teamNames;
  }

  private Set<Team> getTeams() throws IOException
  {
    if (teams == null)
    {
      final String playerStatsHtml =
            urlContentsGetter.getContentsOf(PLAYER_STATS_URL);
      return toTeamsFunction.apply(playerStatsHtml);
    }

    return teams;
  }

  /** {@inheritDoc} */
  public List<Player> getPlayers(final String teamName,
        final ProgressIndicator progressIndicator)
        throws IOException
  {
    ArgAssert.notNull(teamName, "teamName");
    // TODO:  Validate legal team name

    try
    {
      final URL playerScoringUrl = getPlayerScoringUrl(teamName, SCORING_TYPE);
      final Document playerScoringDocument = XmlUtil.getXmlDocument(
            playerScoringUrl);
      final Node playerScoringTableNode = XPathAPI.selectSingleNode(
            playerScoringDocument.getDocumentElement(),
            "//table[tr[th='PIMPG']]");

      if (playerScoringTableNode != null)
      {
        final List<Player> players = new ArrayList<Player>();
        final NodeList playerRowNodeList = XPathAPI.selectNodeList(
              playerScoringTableNode, "tr[td[position()=3][a]]");

        for (int i = 0; i < playerRowNodeList.getLength(); i++)
        {
          final Node playerRowNode = playerRowNodeList.item(i);
          final Player player = getPlayer(playerRowNode,
                progressIndicator);
          if (player.getPlayerType() != PlayerType.NOT_ACTIVE)
          {
            players.add(player);
          }
        }

        return players;
      }
      else
      {
        throw new IOException("Can not find player scoring table");
      }
    }
    catch (final TransformerException e)
    {
      throw new IOException(
            "Transformer exception when getting player scoring table", e);
    }
  }

  /**
   * Gets the player scoring URL for a specified team name and type.
   *
   * @param teamName the team name
   * @param type the type, either <code>SCORING_TYPE</code> or
   * <code>GOALIES_TYPE</code>
   *
   * @return the URL that contains the team's data of the specified type
   */
  private URL getPlayerScoringUrl(final String teamName, final String type)
        throws IOException
  {
    final String teamNum = getTeamNum(teamName);
    if (teamNum != null)
    {
      final String file = TEAM_STATS_DISPLAY_PHP + PhpUtil.PAIRS_SEPARATOR +
            PhpUtil.getKeyValueString(TYPE, type) + PAIR_SEPARATOR +
            PhpUtil.getKeyValueString(TEAM_NUM, teamNum) + PAIR_SEPARATOR +
            PhpUtil.getKeyValueString(SEASON_ID_KEY, SEASON_ID);

      try
      {
        return new URL(PROTOCOL, HOST, file);
      }
      catch (final MalformedURLException e)
      {
        throw new IllegalStateException(e);
      }
    }
    else
    {
      throw new IllegalArgumentException(teamName);
    }
  }

  private String getTeamNum(final String teamName) throws IOException
  {
    for (final Team team : getTeams())
    {
      if (team.getName().equals(teamName))
      {
        return Integer.toString(team.getNum());
      }
    }
    throw new IllegalStateException("Invalid teamName:" + teamName);
  }

  private Player getPlayer(final Node playerRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    if ((playerRowNode.getChildNodes() != null) &&
          (playerRowNode.getChildNodes().getLength() == 14))
    {
      final String playerId = getPlayerId(playerRowNode);
      final String playerName = getPlayerName(playerRowNode);

      progressIndicator.setPlayerInProgress(playerName);

      final PlayerType playerType = getPlayerType(playerRowNode);
      final int sweaterNum = getSweaterNum(playerRowNode);
      final PlayerStats playerStats = getPlayerStats(
            playerRowNode);
      final PlayerBio playerBio = getPlayerBio(playerId);
      final PlayerStreaks playerStreaks = getPlayerStreaks(playerId,
            playerBio.getPosition());

      return new Player(playerName, playerType, sweaterNum, playerStats,
            playerBio, playerStreaks);
    }
    else
    {
      throw new IllegalArgumentException("Wrong number of child nodes");
    }
  }

  private String getPlayerId(final Node playerRowNode)
  {
    final String playerHref = getPlayerHref(playerRowNode);
    return playerHref.substring(playerHref.indexOf("?") + 1);
  }

  private String getPlayerName(final Node playerRowNode)
  {
    final int playerNameChildIndex = 2;
    final Node playerLinkNode = playerRowNode.getChildNodes().item(
          playerNameChildIndex);
    return playerLinkNode.getFirstChild().getFirstChild().getNodeValue();
  }

  private String getPlayerHref(final Node playerRowNode)
  {
    final int playerIdChildIndex = 2;
    final Node playerLinkNode = playerRowNode.getChildNodes().item(
          playerIdChildIndex);
    return playerLinkNode.getFirstChild().getAttributes().getNamedItem(
          "href").getNodeValue();
  }

  private PlayerType getPlayerType(final Node playerRowNode)
  {
    final int playerTypeChildIndex = 0;
    final String isRookie = "*";
    // Veterans have a non-breaking space in the player type column.
    final String isVeteran = "\u00A0";

    final String nodeValue = getPlayerRowNodeValue(
          playerRowNode, playerTypeChildIndex);
    if (isRookie.equals(nodeValue))
    {
      return PlayerType.ROOKIE;
    }
    else if (isVeteran.equals(nodeValue))
    {
      return PlayerType.VETERAN;
    }
    else
    {
      return PlayerType.NOT_ACTIVE;
    }
  }

  private int getSweaterNum(final Node playerRowNode)
  {
    final int sweaterNumChildIndex = 1;

    final String sweaterNumAsString = getPlayerRowNodeValue(playerRowNode,
          sweaterNumChildIndex);

    final int sweaterNumIfParseError = 0;
    return NumberUtils.toInt(sweaterNumAsString, sweaterNumIfParseError);
  }

  private PlayerStats getPlayerStats(final Node playerRowNode)
  {
    final int numChildNodes = playerRowNode.getChildNodes().getLength();
    if (numChildNodes >= 11)
    {
      final String numGamesPlayedString = getPlayerRowNodeValue(
            playerRowNode, 4);
      final String numGoalsString = getPlayerRowNodeValue(
            playerRowNode, 5);
      final String numAssistsString = getPlayerRowNodeValue(
            playerRowNode, 6);
      final String numPointsString = getPlayerRowNodeValue(
            playerRowNode, 7);
      final String plusMinusString = StringUtils.remove(
            getPlayerRowNodeValue(playerRowNode, 8), '+');
      final String numPenaltyMinutesString = getPlayerRowNodeValue(
            playerRowNode, 9);
      final String numPowerplayGoalsString = getPlayerRowNodeValue(
            playerRowNode, 10);
      final String numShorthandedGoalsString = getPlayerRowNodeValue(
            playerRowNode, 11);

      final int numGamesPlayed = Integer.parseInt(numGamesPlayedString);
      final int numGoals = Integer.parseInt(numGoalsString);
      final int numAssists = Integer.parseInt(numAssistsString);
      final int numPoints = Integer.parseInt(numPointsString);
      final int plusMinus = Integer.parseInt(plusMinusString);
      final int numPenaltyMinutes = Integer.parseInt(
            numPenaltyMinutesString);
      final int numPowerplayGoals = Integer.parseInt(
            numPowerplayGoalsString);
      final int numShorthandedGoals = Integer.parseInt(
            numShorthandedGoalsString);

      return new PlayerStats.Builder().setNumGamesPlayed(numGamesPlayed).
            setNumGoals(numGoals).
            setNumAssists(numAssists).
            setNumPoints(numPoints).
            setPlusMinus(plusMinus).
            setNumPenaltyMinutes(numPenaltyMinutes).
            setNumPowerPlayGoals(numPowerplayGoals).
            setNumShorthandedGoals(numShorthandedGoals).build();
    }
    else
    {
      throw new IllegalArgumentException(
            "Not enough child nodes: " + numChildNodes);
    }
  }

  private PlayerBio getPlayerBio(final String playerId) throws IOException
  {
    try
    {
      final URL playerBioUrl = getPlayerBioUrl(playerId);
      final Document playerBioDocument = XmlUtil.getXmlDocument(
            playerBioUrl);
      final Node playerBioTableNode = XPathAPI.selectSingleNode(
            playerBioDocument.getDocumentElement(),
            "//div[@class='profile']/div[@class='details']/table");
      if (playerBioTableNode == null)
      {
        throw new IOException("Can't find player bio table node");
      }
      else
      {
        final String birthYear = getBirthYear(playerBioTableNode);
        final String position = getPosition(playerBioTableNode);
        final String height = getHeight(playerBioTableNode);
        final String weight = getWeight(playerBioTableNode);
        final String homeTown = getHomeTown(playerBioTableNode);

        return new PlayerBio.Builder().
              setBirthYear(birthYear).
              setPosition(position).
              setHeight(height).
              setWeight(weight).
              setHomeTown(homeTown).build();
      }
    }
    catch (TransformerException e)
    {
      throw new IOException(e.getMessage());
    }
  }

  private PlayerStreaks getPlayerStreaks(final String playerId,
        final String playerPosition)
  {
    int goalStreak = 0;
    int assistStreak = 0;
    int pointStreak = 0;

    if (!playerPosition.equals("G"))
    {
      final URL playerGameByGameUrl = getPlayerGameByGameUrl(playerId);
      final Document playerGameByGameDocument = XmlUtil.getXmlDocument(
            playerGameByGameUrl);
      if (playerGameByGameDocument == null)
      {
        throw new IllegalStateException(
              "<html>Game-by-game URL not found: " + playerGameByGameUrl);
      }

      try
      {
        final int goalIndex = 4;
        final int assistIndex = 5;
        final int pointIndex = 6;
        final NodeList nodeList = XPathAPI.selectNodeList(
              playerGameByGameDocument.getDocumentElement(),
              "//div[@id='gamebygameBlock']/table[@class='statsTable']/tr[td[a]]");
        if (nodeList.getLength() > 0)
        {
          boolean onGoalStreak = true;
          boolean onAssistStreak = true;
          boolean onPointStreak = true;

          int i = nodeList.getLength() - 1;
          while (onPointStreak && i >= 0)
          {
            final Node gameRowNode = nodeList.item(i);
            final int numGoals = NumberUtils.toInt(getGameRowText(
                  gameRowNode, goalIndex), 0);
            final int numAssists = NumberUtils.toInt(getGameRowText(
                  gameRowNode, assistIndex), 0);
            final int numPoints = NumberUtils.toInt(getGameRowText(
                  gameRowNode, pointIndex), 0);
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
        throw new IllegalStateException(e);
      }
    }

    return new PlayerStreaks.Builder().setGoalStreak(goalStreak).
          setAssistStreak(assistStreak).
          setPointStreak(pointStreak).build();
  }

  private String getPlayerRowNodeValue(final Node playerRowNode,
        final int childNodeIndex)
  {
    ArgAssert.notNull(playerRowNode, "playerRowNode");

    if ((playerRowNode.getChildNodes() != null) &&
          (playerRowNode.getChildNodes().getLength() >= childNodeIndex))
    {
      final Node childNode = playerRowNode.getChildNodes().item(
            childNodeIndex);
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
      throw new IllegalArgumentException("Invalid playerRowNode");
    }
  }

  private URL getPlayerBioUrl(final String playerId)
  {
    ArgAssert.notNull(playerId, "playerId");

    try
    {
      return new URL(PROTOCOL, HOST,
            PLAYER_STATS_PHP + PhpUtil.PAIRS_SEPARATOR + playerId);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  private URL getPlayerGameByGameUrl(final String playerId)
  {
    ArgAssert.notNull(playerId, "playerId");

    try
    {
      return new URL(PROTOCOL, HOST,
            PLAYER_GAME_BY_GAME_PHP + PhpUtil.PAIRS_SEPARATOR + playerId);
    }
    catch (final MalformedURLException e)
    {
      throw new IllegalStateException(e);
    }
  }

  private String getBirthYear(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node birthdateRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Birthdate']");
    final String birthDate = getFirstChildNodeValue(birthdateRowNode.
          getLastChild());
    final Pattern p = Pattern.compile("\\d\\d\\d\\d");
    final Matcher m = p.matcher(birthDate);
    if (m.find())
    {
      return m.group();
    }
    else
    {
      return StringUtils.EMPTY;
    }
  }

  private String getPosition(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node positionRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Position']");
    final String positionLongForm = getFirstChildNodeValue(positionRowNode.
          getLastChild());
    if (positionLongForm.contains("Defence"))
    {
      return "D";
    }
    else if ("Centre".equals(positionLongForm))
    {
      return "C";
    }
    else if ("Left Wing".equals(positionLongForm))
    {
      return "LW";
    }
    else if ("Right Wing".equals(positionLongForm))
    {
      return "RW";
    }
    else if ("Goaltender".equals(positionLongForm))
    {
      return "G";
    }
    else
    {
      return positionLongForm;
    }
  }

  private String getHeight(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node heightRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Height']");
    final String height = getFirstChildNodeValue(
          heightRowNode.getLastChild());
    return height.replace('\'', '.');
  }

  private String getWeight(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node weightRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Weight']");
    return getFirstChildNodeValue(weightRowNode.getLastChild());
  }

  private String getHomeTown(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node hometownRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Birthplace']");

    final String hometown;
    if (hometownRowNode.getLastChild().hasChildNodes())
    {
      final String nodeValue = hometownRowNode.getLastChild().
            getFirstChild().getNodeValue();
      final Pattern pattern = Pattern.compile("^[^,]+, [^,]+");
      final Matcher matcher = pattern.matcher(nodeValue);
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

  private String getGameRowText(final Node gameRowNode, final int itemIndex)
  {
    if (gameRowNode.getChildNodes().getLength() >= itemIndex)
    {
      final Node node = gameRowNode.getChildNodes().item(itemIndex);
      if (node.getFirstChild() != null)
      {
        return node.getFirstChild().getNodeValue();
      }
    }

    return null;
  }

  private String getFirstChildNodeValue(final Node node)
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

  /** {@inheritDoc} */
  public List<Goalie> getGoalies(final String teamName) throws IOException
  {
    ArgAssert.notNull(teamName, "teamName");
    // TODO:  Validate legal team name

    try
    {
      final URL teamGoaliesUrl = getPlayerScoringUrl(teamName, GOALIES_TYPE);
      final Document goalieDocument = XmlUtil.getXmlDocument(
            teamGoaliesUrl);
      final Node goalieTableNode = XPathAPI.selectSingleNode(
            goalieDocument.getDocumentElement(), "//table[tr[th='SVS']]");

      if (goalieTableNode != null)
      {
        final List<Goalie> goalies = new ArrayList<Goalie>();
        final NodeList goalieRowNodeList = XPathAPI.selectNodeList(
              goalieTableNode, "tr[td[position()=3][a]]");

        for (int i = 0; i < goalieRowNodeList.getLength(); i++)
        {
          final Node goalieRowNode = goalieRowNodeList.item(i);
          final Goalie goalie = getGoalie(goalieRowNode);
          goalies.add(goalie);
        }

        return goalies;
      }
      else
      {
        throw new IOException("Can not find goalie stats table");
      }
    }
    catch (TransformerException t)
    {
      throw new IOException(
            "Transformer exception when getting goalie stats table");
    }
  }

  private Goalie getGoalie(final Node goalieRowNode) throws IOException
  {
    final String name = getPlayerName(goalieRowNode);
    final String numGamesPlayedString = getPlayerRowNodeValue(
          goalieRowNode, 3);
    final String numMinutesPlayedString = getPlayerRowNodeValue(
          goalieRowNode, 4);
    final String numGoalsAgainstString = getPlayerRowNodeValue(
          goalieRowNode, 5);
    final String numShutoutsString = getPlayerRowNodeValue(goalieRowNode,
          6);
    final String goalsAgainstAverageString = getPlayerRowNodeValue(
          goalieRowNode, 7);
    final String numWinsString = getPlayerRowNodeValue(goalieRowNode, 8);
    final String numLossesString = getPlayerRowNodeValue(goalieRowNode, 9);
    final String numOvertimeLossesString =
          getPlayerRowNodeValue(goalieRowNode, 10);
    final String numShootoutLossesString =
          getPlayerRowNodeValue(goalieRowNode, 11);
    final String numShotsAgainstString = getPlayerRowNodeValue(
          goalieRowNode, 12);
    final String numSavesString = getPlayerRowNodeValue(goalieRowNode, 13);
    final String savePercentageString = getPlayerRowNodeValue(
          goalieRowNode, 14);

    final int numGamesPlayed = Integer.parseInt(numGamesPlayedString);
    final int numMinutesPlayed = Integer.parseInt(numMinutesPlayedString);
    final int numGoalsAgainst = (int) Double.parseDouble(
          numGoalsAgainstString);
    final int numShutouts = Integer.parseInt(numShutoutsString);
    final double goalsAgainstAverage = Double.parseDouble(
          goalsAgainstAverageString);
    final int numWins = Integer.parseInt(numWinsString);
    final int numLosses = Integer.parseInt(numLossesString);
    final int numOvertimeLosses = Integer.parseInt(numOvertimeLossesString);
    final int numShootoutLosses = Integer.parseInt(numShootoutLossesString);
    final int numShotsAgainst = Integer.parseInt(numShotsAgainstString);
    final int numSaves = Integer.parseInt(numSavesString);
    final double savePercentage = Double.parseDouble(savePercentageString);

    final GoalieStats goalieStats = new GoalieStats.Builder().
          setNumGamesPlayed(numGamesPlayed).
          setNumMinutesPlayed(numMinutesPlayed).
          setNumGoalsAgainst(numGoalsAgainst).
          setNumShutouts(numShutouts).
          setGoalsAgainstAverage(goalsAgainstAverage).
          setNumWins(numWins).
          setNumRegulationLosses(numLosses).
          setNumOvertimeLosses(numOvertimeLosses).
          setNumShootoutLosses(numShootoutLosses).
          setNumShotsAgainst(numShotsAgainst).
          setNumSaves(numSaves).
          setSavePercentage(savePercentage).build();
    return new Goalie(name, goalieStats);
  }
}
