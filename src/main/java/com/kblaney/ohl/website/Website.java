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
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import com.kblaney.ohl.gamesheets.StatsProvider;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.xml.transform.TransformerException;
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

  private Player getPlayer(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    return new PlayerSupplier().getPlayer(tableRowNode, progressIndicator);
  }

  /** {@inheritDoc} */
  public List<Goalie> getGoalies(final Team team) throws IOException
  {
    ArgAssert.notNull(team, "team");

    try
    {
      final URL url = Urls.getPlayerScoringUrl(getTeamNum(team),
            /*isForSkaters=*/false);
      final Document document = XmlUtil.getXmlDocument(url);
      final Node tableNode = XPathAPI.selectSingleNode(
            document.getDocumentElement(), "//table[tr[th='SVS']]");

      if (tableNode != null)
      {
        final NodeList tableRowNodeList = XPathAPI.selectNodeList(
              tableNode, "tr[td[position()=3][a]]");

        final List<Goalie> goalies = new ArrayList<Goalie>();
        for (int i = 0; i < tableRowNodeList.getLength(); i++)
        {
          final Node tableRowNode = tableRowNodeList.item(i);
          final Goalie goalie = getGoalie(tableRowNode);
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

  private Goalie getGoalie(final Node rowNode) throws IOException
  {
    final String name = getPlayerName(rowNode);
    final String numGamesPlayedString = Nodes.getChildNodeValue(rowNode, 3);
    final String numMinutesPlayedString = Nodes.getChildNodeValue(rowNode, 4);
    final String numGoalsAgainstString = Nodes.getChildNodeValue(rowNode, 5);
    final String numShutoutsString = Nodes.getChildNodeValue(rowNode, 6);
    final String goalsAgainstAverageString = Nodes.getChildNodeValue(rowNode, 7);
    final String numWinsString = Nodes.getChildNodeValue(rowNode, 8);
    final String numLossesString = Nodes.getChildNodeValue(rowNode, 9);
    final String numOvertimeLossesString = Nodes.getChildNodeValue(rowNode, 10);
    final String numShootoutLossesString = Nodes.getChildNodeValue(rowNode, 11);
    final String numShotsAgainstString = Nodes.getChildNodeValue(rowNode, 12);
    final String numSavesString = Nodes.getChildNodeValue(rowNode, 13);
    final String savePercentageString = Nodes.getChildNodeValue(rowNode, 14);

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

  private String getPlayerName(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToNameFunction().apply(tableRowNode);
  }
}
