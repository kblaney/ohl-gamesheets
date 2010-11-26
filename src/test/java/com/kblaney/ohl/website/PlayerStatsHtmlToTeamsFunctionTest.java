package com.kblaney.ohl.website;

import com.kblaney.ohl.Team;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import com.google.common.base.Function;
import java.util.Collections;
import java.util.Set;
import java.util.List;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerStatsHtmlToTeamsFunctionTest
{
  private String playerStatsHtml;
  private String teamsSelectListHtml;
  private Function<String, String> toTeamsSelectListHtmlFunction;
  private Function<String, List<String>> toTeamOptionsHtmlFunction;
  private Function<String, Team> toTeamFunction;
  private Function<String, Set<Team>> toTeamsFunction;

  @Before
  public void setUp()
  {
    playerStatsHtml = "PLAYER_STATS_HTML";
    teamsSelectListHtml = "TEAMS_SELECT_LIST_HTML";
    toTeamsSelectListHtmlFunction = mock(Function.class);
    when(toTeamsSelectListHtmlFunction.apply(playerStatsHtml)).
            thenReturn(teamsSelectListHtml);
    toTeamOptionsHtmlFunction = mock(Function.class);
    toTeamFunction = mock(Function.class);
    toTeamsFunction = new PlayerStatsHtmlToTeamsFunction(
            toTeamsSelectListHtmlFunction, toTeamOptionsHtmlFunction,
            toTeamFunction);
  }

  @Test
  public void apply_zeroTeams()
  {
    when(toTeamOptionsHtmlFunction.apply(teamsSelectListHtml)).
            thenReturn(Collections.<String>emptyList());
    assertTrue(toTeamsFunction.apply(playerStatsHtml).isEmpty());
  }

  @Test
  public void apply_oneTeam()
  {
    final String teamOptionHtml = "TEAM_OPTION_HTML";
    when(toTeamOptionsHtmlFunction.apply(teamsSelectListHtml)).
            thenReturn(Lists.newArrayList(teamOptionHtml));
    final Team team = new Team("Boston Bruins", /*teamNum=*/2);
    when(toTeamFunction.apply(teamOptionHtml)).thenReturn(team);
    assertEquals(Sets.newHashSet(team), toTeamsFunction.apply(playerStatsHtml));
  }

  @Test
  public void apply_threeTeams()
  {
    final String firstTeamOptionHtml = "FIRST_TEAM_OPTION_HTML";
    final String secondTeamOptionHtml = "SECOND_TEAM_OPTION_HTML";
    final String thirdTeamOptionHtml = "THIRD_TEAM_OPTION_HTML";
    when(toTeamOptionsHtmlFunction.apply(teamsSelectListHtml)).
            thenReturn(Lists.newArrayList(firstTeamOptionHtml,
            secondTeamOptionHtml, thirdTeamOptionHtml));

    final Team firstTeam = new Team("Buffalo Sabres", /*teamNum=*/3);
    final Team secondTeam = new Team("New York Rangers", /*teamNum=*/14);
    final Team thirdTeam = new Team("Vancouver Canucks", /*teamNum=*/28);
    when(toTeamFunction.apply(firstTeamOptionHtml)).thenReturn(firstTeam);
    when(toTeamFunction.apply(secondTeamOptionHtml)).thenReturn(secondTeam);
    when(toTeamFunction.apply(thirdTeamOptionHtml)).thenReturn(thirdTeam);

    assertEquals(Sets.newHashSet(firstTeam, secondTeam, thirdTeam),
            toTeamsFunction.apply(playerStatsHtml));
  }
}
