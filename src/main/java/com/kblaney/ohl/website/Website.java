package com.kblaney.ohl.website;

import com.kblaney.ohl.Teams;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.kblaney.commons.io.UrlContentsGetter;
import com.kblaney.commons.lang.ArgAssert;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import com.kblaney.ohl.StatsProvider;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class Website implements StatsProvider
{
  private final UrlContentsGetter urlContentsGetter;
  private final Function<String, Set<NumberedTeam>> toTeamsFunction;
  private final PlayerSupplier playerSupplier;
  private final GoalieSupplier goalieSupplier;
  private final TeamNumToNodeListFunction playerTableRowNodeListSupplier;
  private final TeamNumToNodeListFunction goalieTableRowNodeListSupplier;
  private Set<NumberedTeam> numberedTeams;

  @Inject
  Website(final UrlContentsGetter urlContentsGetter,
        final Function<String, Set<NumberedTeam>> toTeamsFunction,
        final PlayerSupplier playerSupplier,
        final GoalieSupplier goalieSupplier,
        @Named("Players")
        final TeamNumToNodeListFunction playerTableRowNodeListSupplier,
        @Named("Goalies")
        final TeamNumToNodeListFunction goalieTableRowNodeListSupplier)
  {
    this.urlContentsGetter = urlContentsGetter;
    this.toTeamsFunction = toTeamsFunction;
    this.playerSupplier = playerSupplier;
    this.goalieSupplier = goalieSupplier;
    this.playerTableRowNodeListSupplier = playerTableRowNodeListSupplier;
    this.goalieTableRowNodeListSupplier = goalieTableRowNodeListSupplier;
  }

  /** {@inheritDoc} */
  public Teams getTeams() throws IOException
  {
    return new Teams(getNumberedTeams());
  }

  private Set<NumberedTeam> getNumberedTeams() throws IOException
  {
    if (numberedTeams == null)
    {
      final String playerStatsHtml =
            urlContentsGetter.getContentsOf(Urls.getPlayerStatsUrl());
      numberedTeams = toTeamsFunction.apply(playerStatsHtml);
    }
    return numberedTeams;
  }

  /** {@inheritDoc} */
  public List<Player> getPlayers(final Team team,
        final ProgressIndicator progressIndicator) throws IOException
  {
    ArgAssert.notNull(team, "team");
    ArgAssert.notNull(progressIndicator, "progressIndicator");

    final NodeList tableRowNodeList = playerTableRowNodeListSupplier.apply(
          getTeamNum(team));

    final List<Player> players = Lists.newArrayList();
    for (int i = 0; i < tableRowNodeList.getLength(); i++)
    {
      final Node tableRowNode = tableRowNodeList.item(i);
      players.add(getPlayer(tableRowNode, progressIndicator));
    }
    return players;
  }

  private int getTeamNum(final Team team) throws IOException
  {
    for (final NumberedTeam numberedTeam : getNumberedTeams())
    {
      if (numberedTeam.getName().equals(team.getName()))
      {
        return numberedTeam.getNum();
      }
    }
    throw new IllegalArgumentException("Team not found: " + team);
  }

  private Player getPlayer(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    return playerSupplier.getPlayer(tableRowNode, progressIndicator);
  }

  /** {@inheritDoc} */
  public List<Goalie> getGoalies(final Team team,
        final ProgressIndicator progressIndicator) throws IOException
  {
    ArgAssert.notNull(team, "team");

    final NodeList tableRowNodeList = goalieTableRowNodeListSupplier.apply(
          getTeamNum(team));

    final List<Goalie> goalies = Lists.newArrayList();
    for (int i = 0; i < tableRowNodeList.getLength(); i++)
    {
      final Node tableRowNode = tableRowNodeList.item(i);
      final Goalie goalie = getGoalie(tableRowNode, progressIndicator);
      goalies.add(goalie);
    }
    return goalies;
  }

  private Goalie getGoalie(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    return goalieSupplier.get(tableRowNode, progressIndicator);
  }
}
