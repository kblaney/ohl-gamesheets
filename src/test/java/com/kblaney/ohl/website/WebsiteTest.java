package com.kblaney.ohl.website;

import com.google.common.collect.Sets;
import java.net.URL;
import com.google.common.base.Function;
import com.kblaney.commons.io.UrlContentsGetter;
import com.kblaney.ohl.StatsProvider;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class WebsiteTest
{
  private UrlContentsGetter urlContentsGetter;
  private Function<String, Set<NumberedTeam>> toTeamsFunction;
  private PlayerSupplier playerSupplier;
  private GoalieSupplier goalieSupplier;
  private TeamNumToNodeListFunction playerTableRowNodeListSupplier;
  private TeamNumToNodeListFunction goalieTableRowNodeListSupplier;
  private StatsProvider website;

  @Before
  public void setUp()
  {
    urlContentsGetter = mock(UrlContentsGetter.class);
    toTeamsFunction = mock(Function.class);
    playerSupplier = mock(PlayerSupplier.class);
    goalieSupplier = mock(GoalieSupplier.class);
    playerTableRowNodeListSupplier = mock(TeamNumToNodeListFunction.class);
    goalieTableRowNodeListSupplier = mock(TeamNumToNodeListFunction.class);
    website = new Website(urlContentsGetter, toTeamsFunction, playerSupplier,
          goalieSupplier, playerTableRowNodeListSupplier,
          goalieTableRowNodeListSupplier);
  }

  @Test
  public void getTeams_oneTeamTwoInvocations() throws Exception
  {
    final URL url = Urls.getPlayerStatsUrl();
    final String playerStatsHtml = "PLAYER_STATS_HTML";
    when(urlContentsGetter.getContentsOf(url)).thenReturn(playerStatsHtml);
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam("TEAM_1", 1)));
    assertEquals(1, website.getTeams().getSortedTeamNames().size());
    assertEquals(1, website.getTeams().getSortedTeamNames().size());
  }

  @Test
  public void getTeams_threeTeamsOneInvocation() throws Exception
  {
    final URL url = Urls.getPlayerStatsUrl();
    final String playerStatsHtml = "PLAYER_STATS_HTML";
    when(urlContentsGetter.getContentsOf(url)).thenReturn(playerStatsHtml);
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam("TEAM_1", 1),
          new NumberedTeam("TEAM_2", 2), new NumberedTeam("TEAM_3", 3)));
    assertEquals(3, website.getTeams().getSortedTeamNames().size());
  }
}
