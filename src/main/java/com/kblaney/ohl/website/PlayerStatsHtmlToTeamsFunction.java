package com.kblaney.ohl.website;

import com.kblaney.ohl.Team;
import com.google.common.base.Function;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

final class PlayerStatsHtmlToTeamsFunction
      implements Function<String, Set<Team>>
{
  private final Function<String, String> toTeamsSelectListHtmlFunction;
  private final Function<String, List<String>> toTeamOptionsHtmlFunction;
  private final Function<String, Team> toTeamFunction;

  public PlayerStatsHtmlToTeamsFunction()
  {
    this(new PlayerStatsHtmlToTeamsSelectListHtmlFunction(),
            new TeamsSelectListHtmlToTeamOptionsHtmlFunction(),
            new TeamOptionHtmlToTeamFunction());
  }

  PlayerStatsHtmlToTeamsFunction(
          final Function<String, String> toTeamsSelectListHtmlFunction,
          final Function<String, List<String>> toTeamOptionsHtmlFunction,
          final Function<String, Team> toTeamFunction)
  {
    this.toTeamsSelectListHtmlFunction = toTeamsSelectListHtmlFunction;
    this.toTeamOptionsHtmlFunction = toTeamOptionsHtmlFunction;
    this.toTeamFunction = toTeamFunction;
  }

  public Set<Team> apply(final String playerStatsHtml)
  {
    final String teamsSelectListHtml =
          toTeamsSelectListHtmlFunction.apply(playerStatsHtml);
    final List<String> teamOptionsHtml =
          toTeamOptionsHtmlFunction.apply(teamsSelectListHtml);
    final Set<Team> teams = Sets.newHashSet();
    for (final String teamOptionHtml : teamOptionsHtml)
    {
      teams.add(toTeamFunction.apply(teamOptionHtml));
    }
    return teams;
  }
}
