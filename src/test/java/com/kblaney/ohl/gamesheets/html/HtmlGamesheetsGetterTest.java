package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.gamesheets.GamesheetsGetter;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public final class HtmlGamesheetsGetterTest
{
  private StatsProvider statsProvider;
  private Function<List<Player>, String> playersToHtmlTableFunction;
  private Function<List<Goalie>, String> goaliesToHtmlTableFunction;
  private GamesheetsGetter htmlGamesheetsGetter;
  private Team homeTeam;
  private Team roadTeam;
  private Calendar gameDate;
  private ProgressIndicator progressIndicator;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp()
  {
    statsProvider = mock(StatsProvider.class);
    playersToHtmlTableFunction = mock(Function.class);
    goaliesToHtmlTableFunction = mock(Function.class);
    htmlGamesheetsGetter = new HtmlGamesheetsGetter(statsProvider,
          playersToHtmlTableFunction, goaliesToHtmlTableFunction);
    homeTeam = getTeamWithName("Belleville Bulls");
    roadTeam = getTeamWithName("Kingston Frontenacs");
    gameDate = mock(Calendar.class);
    progressIndicator = mock(ProgressIndicator.class);
  }

  @Test
  public void getGamesheets_zeroPlayers() throws Exception
  {
    when(statsProvider.getPlayers(any(Team.class), eq(progressIndicator))).
          thenReturn(Collections.<Player>emptyList());
    when(statsProvider.getGoalies(any(Team.class), eq(progressIndicator))).
          thenReturn(Collections.<Goalie>emptyList());
    htmlGamesheetsGetter.getGamesheets(homeTeam, roadTeam, gameDate,
          progressIndicator);
  }

  Team getTeamWithName(final String name)
  {
    final Team team = mock(Team.class);
    when(team.getName()).thenReturn(name);
    return team;
  }
}
