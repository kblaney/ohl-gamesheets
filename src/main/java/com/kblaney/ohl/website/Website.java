package com.kblaney.ohl.website;

import com.kblaney.ohl.Teams;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.kblaney.commons.io.UrlContentsGetter;
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
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import com.kblaney.ohl.gamesheets.StatsProvider;
import java.io.IOException;
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
  private UrlContentsGetter urlContentsGetter = new UsAsciiUrlContentsGetter();
  private Function<String, Set<NumberedTeam>> toTeamsFunction =
        new PlayerStatsHtmlToTeamsFunction();
  private Set<NumberedTeam> numberedTeams;

  /** {@inheritDoc} */
  public Teams getTeams() throws IOException
  {
    if (numberedTeams == null)
    {
      final String playerStatsHtml =
            urlContentsGetter.getContentsOf(Urls.getPlayerStatsUrl());
      numberedTeams = toTeamsFunction.apply(playerStatsHtml);
    }

    return new Teams(numberedTeams);
  }

  /** {@inheritDoc} */
  public List<Player> getPlayers(final Team team,
        final ProgressIndicator progressIndicator) throws IOException
  {
    ArgAssert.notNull(team, "team");
    ArgAssert.notNull(progressIndicator, "progressIndicator");

    final NodeList tableRowNodeList = new PlayerTableRowNodeListSupplier().get(
          getTeamNum(team), /*isForSkaters=*/true);

    final List<Player> players = Lists.newArrayList();
    for (int i = 0; i < tableRowNodeList.getLength(); i++)
    {
      final Node tableRowNode = tableRowNodeList.item(i);
      final Player player = getPlayer(tableRowNode, progressIndicator);
      if (player.getPlayerType() != PlayerType.NOT_ACTIVE)
      {
        players.add(player);
      }
    }
    return players;
  }

  private int getTeamNum(final Team team)
  {
    for (final NumberedTeam numberedTeam : numberedTeams)
    {
      if (numberedTeam.getName().equals(team.getName()))
      {
        return numberedTeam.getNum();
      }
    }
    throw new IllegalArgumentException("Team num not found: " + team);
  }

  private Player getPlayer(final Node playerTableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    if ((playerTableRowNode.getChildNodes() != null) &&
          (playerTableRowNode.getChildNodes().getLength() == 14))
    {
      final String playerId = getPlayerId(playerTableRowNode);
      final String playerName = getPlayerName(playerTableRowNode);

      progressIndicator.setPlayerInProgress(playerName);

      final PlayerType playerType = getPlayerType(playerTableRowNode);
      final int sweaterNum = getSweaterNum(playerTableRowNode);
      final PlayerStats playerStats = getPlayerStats(playerTableRowNode);
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

  private String getPlayerId(final Node playerTableRowNode)
  {
    return new PlayerTableRowNodeToIdFunction().apply(playerTableRowNode);
  }

  private String getPlayerName(final Node playerTableRowNode)
  {
    final int playerNameChildIndex = 2;
    final Node playerLinkNode = playerTableRowNode.getChildNodes().item(
          playerNameChildIndex);
    return playerLinkNode.getFirstChild().getFirstChild().getNodeValue();
  }

  private PlayerType getPlayerType(final Node playerTableRowNode)
  {
    final int playerTypeChildIndex = 0;
    final String isRookie = "*";
    // Veterans have a non-breaking space in the player type column.
    final String isVeteran = "\u00A0";

    final String nodeValue = Nodes.getChildNodeValue(
          playerTableRowNode, playerTypeChildIndex);
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

  private int getSweaterNum(final Node playerTableRowNode)
  {
    final int sweaterNumChildIndex = 1;
    final String sweaterNumAsString = Nodes.getChildNodeValue(
          playerTableRowNode, sweaterNumChildIndex);

    final int sweaterNumIfParseError = 0;
    return NumberUtils.toInt(sweaterNumAsString, sweaterNumIfParseError);
  }

  private PlayerStats getPlayerStats(final Node playerTableRowNode)
  {
    final int numChildNodes = playerTableRowNode.getChildNodes().getLength();
    if (numChildNodes >= 11)
    {
      final String numGamesPlayedString = Nodes.getChildNodeValue(
            playerTableRowNode, 4);
      final String numGoalsString = Nodes.getChildNodeValue(
            playerTableRowNode, 5);
      final String numAssistsString = Nodes.getChildNodeValue(
            playerTableRowNode, 6);
      final String numPointsString = Nodes.getChildNodeValue(
            playerTableRowNode, 7);
      final String plusMinusString = StringUtils.remove(
            Nodes.getChildNodeValue(playerTableRowNode, 8), '+');
      final String numPenaltyMinutesString = Nodes.getChildNodeValue(
            playerTableRowNode, 9);
      final String numPowerplayGoalsString = Nodes.getChildNodeValue(
            playerTableRowNode, 10);
      final String numShorthandedGoalsString = Nodes.getChildNodeValue(
            playerTableRowNode, 11);

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
      final URL playerBioUrl = Urls.getPlayerBioUrl(playerId);
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
        return new PlayerBio.Builder().
              setBirthYear(getBirthYear(playerBioTableNode)).
              setPosition(getPosition(playerBioTableNode)).
              setHeight(getHeight(playerBioTableNode)).
              setWeight(getWeight(playerBioTableNode)).
              setHomeTown(getHomeTown(playerBioTableNode)).build();
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
      final URL playerGameByGameUrl = Urls.getPlayerGameByGameUrl(playerId);
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

  private String getBirthYear(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node birthdateRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Birthdate']");
    final String birthDate = Nodes.getFirstChildNodeValueOrEmpty(
          birthdateRowNode.getLastChild());
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
    final String positionLongForm = Nodes.getFirstChildNodeValueOrEmpty(
          positionRowNode.getLastChild());
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
    final String height = Nodes.getFirstChildNodeValueOrEmpty(
          heightRowNode.getLastChild());
    return height.replace('\'', '.');
  }

  private String getWeight(final Node playerBioDivNode)
        throws TransformerException
  {
    final Node weightRowNode = XPathAPI.selectSingleNode(
          playerBioDivNode, "//tr[td='Weight']");
    return Nodes.getFirstChildNodeValueOrEmpty(weightRowNode.getLastChild());
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

  /** {@inheritDoc} */
  public List<Goalie> getGoalies(final Team team) throws IOException
  {
    ArgAssert.notNull(team, "team");

    try
    {
      final URL teamGoaliesUrl = Urls.getPlayerScoringUrl(getTeamNum(team),
            /*isForSkaters=*/false);
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
    final String numGamesPlayedString = Nodes.getChildNodeValue(
          goalieRowNode, 3);
    final String numMinutesPlayedString = Nodes.getChildNodeValue(
          goalieRowNode, 4);
    final String numGoalsAgainstString = Nodes.getChildNodeValue(
          goalieRowNode, 5);
    final String numShutoutsString = Nodes.getChildNodeValue(goalieRowNode,
          6);
    final String goalsAgainstAverageString = Nodes.getChildNodeValue(
          goalieRowNode, 7);
    final String numWinsString = Nodes.getChildNodeValue(goalieRowNode, 8);
    final String numLossesString = Nodes.getChildNodeValue(goalieRowNode, 9);
    final String numOvertimeLossesString =
          Nodes.getChildNodeValue(goalieRowNode, 10);
    final String numShootoutLossesString =
          Nodes.getChildNodeValue(goalieRowNode, 11);
    final String numShotsAgainstString = Nodes.getChildNodeValue(
          goalieRowNode, 12);
    final String numSavesString = Nodes.getChildNodeValue(goalieRowNode, 13);
    final String savePercentageString = Nodes.getChildNodeValue(
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
