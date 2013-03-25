package com.kblaney.ohl.gamesheets.ui;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import com.google.common.collect.Sets;
import com.kblaney.ohl.ProgressIndicator;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.Teams;
import com.kblaney.ohl.gamesheets.Gamesheets;
import com.kblaney.ohl.gamesheets.GamesheetsGetter;
import com.kblaney.ohl.gamesheets.GamesheetsWriter;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.uispec4j.Button;
import org.uispec4j.ComboBox;
import org.uispec4j.UISpec4J;
import org.uispec4j.Window;

public final class CreateGamesheetsFrameTest
{
  private String homeTeamName;
  private String roadTeamName;
  private Team homeTeam;
  private Team roadTeam;
  private CreateGamesheetsFrame frame;
  private GamesheetsWriter htmlGamesheetsWriter;
  private Gamesheets htmlGamesheets;
  private Window window;

  @Before
  public void setUp() throws Exception
  {
    homeTeamName = "Belleville Bulls";
    roadTeamName = "Kingston Frontenacs";
    homeTeam = getTeamWithName(homeTeamName);
    roadTeam = getTeamWithName(roadTeamName);
    final Set<Team> teams = Sets.newHashSet(homeTeam, roadTeam, getTeamWithName("Barrie Colts"),
          getTeamWithName("Erie Otters"));

    UISpec4J.init();
    final StatsProvider statsProvider = mock(StatsProvider.class);
    when(statsProvider.getTeams()).thenReturn(new Teams(teams));

    final String homeTeamGamesheet = "HOME_TEAM_GAMESHEET";
    final String roadTeamGamesheet = "ROAD_TEAM_GAMESHEET";
    htmlGamesheets = new Gamesheets(homeTeamGamesheet, roadTeamGamesheet);
    final GamesheetsGetter htmlGamesheetsGetter = mock(GamesheetsGetter.class);
    when(
          htmlGamesheetsGetter.getGamesheets(eq(homeTeam), eq(roadTeam), any(Calendar.class),
                any(ProgressIndicator.class))).thenReturn(htmlGamesheets);
    htmlGamesheetsWriter = mock(GamesheetsWriter.class);
    frame = new CreateGamesheetsFrame(statsProvider, htmlGamesheetsGetter, htmlGamesheetsWriter);
    window = new Window(frame);
  }

  @After
  public void tearDown()
  {
    window.dispose();
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

  @Test
  @Ignore("This test intermittently fails.  Why?")
  public void createGamesheets() throws Exception
  {
    final ComboBox homeTeamComboBox = window.getComboBox(CreateGamesheetsFrame.HOME_TEAM_COMBO_BOX_NAME);
    homeTeamComboBox.select(homeTeamName);
    final ComboBox roadTeamComboBox = window.getComboBox(CreateGamesheetsFrame.ROAD_TEAM_COMBO_BOX_NAME);
    roadTeamComboBox.select(roadTeamName);
    final Button button = window.getButton("Create gamesheets!");
    button.click();
    verify(htmlGamesheetsWriter).write(htmlGamesheets);
    verify(htmlGamesheetsWriter).getDescription();
  }

  @Test
  public void actionPerformed_unknownActionCommand() throws Exception
  {
    final ActionEvent actionEvent = mock(ActionEvent.class);
    when(actionEvent.getActionCommand()).thenReturn("UNKNOWN");
    frame.actionPerformed(actionEvent);
    assertEquals("OHL Gamesheets", window.getTitle());
  }

  @Test
  public void setPlayerInProgress() throws Exception
  {
    frame.setPlayerInProgress("PLAYER_NAME");
    assertTrue(true);
  }
}
