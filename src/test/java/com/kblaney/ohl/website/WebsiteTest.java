package com.kblaney.ohl.website;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import com.kblaney.url.UrlFunction;
import java.net.URL;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class WebsiteTest
{
  private URL url;
  private String playerStatsHtml;
  private UrlFunction<String> urlToContentFunction;
  private Function<String, Set<NumberedTeam>> toTeamsFunction;
  private PlayerSupplier playerSupplier;
  private GoalieSupplier goalieSupplier;
  private TeamNumToNodeListFunction playerTableRowNodeListSupplier;
  private TeamNumToNodeListFunction goalieTableRowNodeListSupplier;
  private StatsProvider website;
  private Team team;
  private ProgressIndicator progressIndicator;
  private Node node;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() throws Exception
  {
    url = Urls.getPlayerStatsUrl();
    playerStatsHtml = "PLAYER_STATS_HTML";
    urlToContentFunction = mock(UrlFunction.class);
    when(urlToContentFunction.convert(url)).thenReturn(playerStatsHtml);
    toTeamsFunction = mock(Function.class);
    playerSupplier = mock(PlayerSupplier.class);
    goalieSupplier = mock(GoalieSupplier.class);
    playerTableRowNodeListSupplier = mock(TeamNumToNodeListFunction.class);
    goalieTableRowNodeListSupplier = mock(TeamNumToNodeListFunction.class);
    website = new Website(urlToContentFunction, toTeamsFunction, playerSupplier, goalieSupplier,
          playerTableRowNodeListSupplier, goalieTableRowNodeListSupplier);
    team = mock(Team.class);
    progressIndicator = mock(ProgressIndicator.class);
    node = mock(Node.class);
  }

  @Test
  public void getTeams_oneTeamTwoInvocations() throws Exception
  {
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(Sets.newHashSet(new NumberedTeam("TEAM_1", 1)));
    assertEquals(Lists.newArrayList("TEAM_1"), website.getTeams().getSortedTeamNames());
    assertEquals(Lists.newArrayList("TEAM_1"), website.getTeams().getSortedTeamNames());
  }

  @Test
  public void getTeams_threeTeamsOneInvocation() throws Exception
  {
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam("TEAM_B", 1), new NumberedTeam("TEAM_C", 2), new NumberedTeam("TEAM_A", 3)));
    assertEquals(Lists.newArrayList("TEAM_A", "TEAM_B", "TEAM_C"), website.getTeams().getSortedTeamNames());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPlayers_nullTeam() throws Exception
  {
    website.getPlayers(null, progressIndicator);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPlayers_nullProgressIndicator() throws Exception
  {
    website.getPlayers(team, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getPlayers_threeTeamsTeamNotFound() throws Exception
  {
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam("TEAM_B", 1), new NumberedTeam("TEAM_C", 2), new NumberedTeam("TEAM_A", 3)));
    website.getPlayers(team, progressIndicator);
  }

  @Test
  public void getPlayers_threeTeamsZeroPlayersOnTeam() throws Exception
  {
    final String teamName = "TEAM_A";
    final int teamNum = 1;
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam(teamName, teamNum), new NumberedTeam("TEAM_B", 2), new NumberedTeam(
                "TEAM_C", 3)));
    final NodeList emptyNodeList = getEmptyNodeList();
    when(playerTableRowNodeListSupplier.apply(teamNum)).thenReturn(emptyNodeList);
    when(team.getName()).thenReturn(teamName);
    assertEquals(Lists.newArrayList(), website.getPlayers(team, progressIndicator));
    verifyNoMoreInteractions(progressIndicator);
  }

  private NodeList getEmptyNodeList()
  {
    final NodeList nodeList = mock(NodeList.class);
    when(nodeList.getLength()).thenReturn(0);
    return nodeList;
  }

  @Test
  public void getPlayers_twoTeamsOneNonActivePlayerOnTeam() throws Exception
  {
    final String teamName = "TEAM_A";
    final int teamNum = 1;
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam(teamName, teamNum), new NumberedTeam("TEAM_B", 2)));
    final NodeList nodeList = getNodeListOfLength(1);
    when(playerTableRowNodeListSupplier.apply(teamNum)).thenReturn(nodeList);
    final String playerName = "PLAYER_NAME";
    final Player player = getPlayer(playerName, PlayerType.NOT_ACTIVE);
    when(playerSupplier.getPlayer(node, progressIndicator)).thenReturn(player);
    when(team.getName()).thenReturn(teamName);
    assertEquals(1, website.getPlayers(team, progressIndicator).size());
  }

  private NodeList getNodeListOfLength(final int length)
  {
    final NodeList nodeList = mock(NodeList.class);
    when(nodeList.getLength()).thenReturn(length);
    for (int i = 0; i < length; i++)
    {
      when(nodeList.item(i)).thenReturn(node);
    }
    return nodeList;
  }

  private Player getPlayer(final String playerName, final PlayerType playerType)
  {
    final Optional<Integer> sweaterNum = Optional.of(19);
    return new Player(playerName, playerType, sweaterNum, new PlayerStats.Builder().build(),
          new PlayerBio.Builder().build(), new PlayerStreaks.Builder().build());
  }

  @Test
  public void getPlayers_twoTeamsOneActivePlayerOnTeam() throws Exception
  {
    final String teamName = "TEAM_A";
    final int teamNum = 1;
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam(teamName, teamNum), new NumberedTeam("TEAM_B", 2)));
    final NodeList nodeList = getNodeListOfLength(1);
    when(playerTableRowNodeListSupplier.apply(teamNum)).thenReturn(nodeList);
    final String playerName = "PLAYER_NAME";
    final Player player = getPlayer(playerName, PlayerType.VETERAN);
    when(playerSupplier.getPlayer(node, progressIndicator)).thenReturn(player);
    when(team.getName()).thenReturn(teamName);
    assertEquals(1, website.getPlayers(team, progressIndicator).size());
  }

  @Test
  public void getGoalie_twoTeamsOneGoaliePerTeam() throws Exception
  {
    final String teamName = "TEAM_A";
    final int teamNum = 1;
    when(toTeamsFunction.apply(playerStatsHtml)).thenReturn(
          Sets.newHashSet(new NumberedTeam(teamName, teamNum), new NumberedTeam("TEAM_B", 2)));
    final NodeList nodeList = getNodeListOfLength(1);
    when(goalieTableRowNodeListSupplier.apply(teamNum)).thenReturn(nodeList);
    final String goalieName = "GOALIE_NAME";
    final Goalie goalie = new Goalie(goalieName, new GoalieStats.Builder().build());
    when(goalieSupplier.get(node, progressIndicator)).thenReturn(goalie);
    when(team.getName()).thenReturn(teamName);
    assertEquals(1, website.getGoalies(team, progressIndicator).size());
  }
}
