package com.kblaney.ohl.website;

import com.kblaney.ohl.Teams;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.kblaney.commons.io.UrlContentsGetter;
import com.kblaney.commons.io.UsAsciiUrlContentsGetter;
import com.kblaney.commons.lang.ArgAssert;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import com.kblaney.ohl.StatsProvider;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Ontario Hockey League website.
 */
public final class Website implements StatsProvider
{
  private UrlContentsGetter urlContentsGetter = new UsAsciiUrlContentsGetter();
  private final PlayerSupplier playerSupplier;
  private final GoalieSupplier goalieSupplier;
  private final GoalieTableRowNodeListSupplier goalieTableRowNodeListSupplier;
  private Function<String, Set<NumberedTeam>> toTeamsFunction =
        new PlayerStatsHtmlToTeamsFunction();
  private Set<NumberedTeam> numberedTeams;

  @Inject
  Website(final PlayerSupplier playerSupplier,
        final GoalieSupplier goalieSupplier,
        final GoalieTableRowNodeListSupplier goalieTableRowNodeListSupplier)
  {
    this.playerSupplier = playerSupplier;
    this.goalieSupplier = goalieSupplier;
    this.goalieTableRowNodeListSupplier = goalieTableRowNodeListSupplier;
  }

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
          getTeamNum(team));

    final List<Player> players = Lists.newArrayList();
    for (int i = 0; i < tableRowNodeList.getLength(); i++)
    {
      final Node tableRowNode = tableRowNodeList.item(i);
      final Player player = getPlayer(tableRowNode, progressIndicator);
      if (player.getType() != PlayerType.NOT_ACTIVE)
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
    return playerSupplier.getPlayer(tableRowNode, progressIndicator);
  }

  /** {@inheritDoc} */
  public List<Goalie> getGoalies(final Team team,
        final ProgressIndicator progressIndicator) throws IOException
  {
    ArgAssert.notNull(team, "team");

    final NodeList tableRowNodeList = goalieTableRowNodeListSupplier.get(
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
