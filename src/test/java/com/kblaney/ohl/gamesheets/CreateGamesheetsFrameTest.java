package com.kblaney.ohl.gamesheets;

import org.uispec4j.UISpec4J;
import com.google.common.collect.Sets;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.Teams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Window;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class CreateGamesheetsFrameTest
{
  private Window window;

  @Before
  public void setUp() throws Exception
  {
    UISpec4J.init();
    final StatsProvider statsProvider = mock(StatsProvider.class);
    when(statsProvider.getTeams()).thenReturn(getTeams());
    final HtmlGamesheetsGetter htmlGamesheetsGetter =
          mock(HtmlGamesheetsGetter.class);
    window = new Window(new CreateGamesheetsFrame(statsProvider,
          htmlGamesheetsGetter));
  }

  @After
  public void tearDown()
  {
    window.dispose();
  }

  private Teams getTeams()
  {
    return new Teams(Sets.newHashSet(
          getTeamWithName("Belleville Bulls"),
          getTeamWithName("Barrie Colts"),
          getTeamWithName("Kingston Frontenacs"),
          getTeamWithName("Peterborough Petes"),
          getTeamWithName("Ottawa 67s")));
  }

  private Team getTeamWithName(final String name)
  {
    return new Team()
    {
      public String getName()
      {
        return name;
      }
    };
  }

  @Test
  public void title() throws Exception
  {
    assertEquals("OHL Gamesheets", window.getTitle());
  }
}
