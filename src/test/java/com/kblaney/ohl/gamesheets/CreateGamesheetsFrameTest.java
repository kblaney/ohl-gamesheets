package com.kblaney.ohl.gamesheets;

import com.google.common.collect.Lists;
import com.kblaney.ohl.Player;
import java.util.List;
import java.util.Set;
import org.uispec4j.ComboBox;
import org.uispec4j.Button;
import org.uispec4j.UISpec4J;
import com.google.common.collect.Sets;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.Teams;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uispec4j.Window;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class CreateGamesheetsFrameTest
{
  private String homeTeamName;
  private String roadTeamName;
  private Team homeTeam;
  private Team roadTeam;
  private CreateGamesheetsFrame frame;
  private HtmlGamesheetsWriter htmlGamesheetsWriter;
  private HtmlGamesheets htmlGamesheets;
  private Window window;

  @Before
  public void setUp() throws Exception
  {
    homeTeamName = "Belleville Bulls";
    roadTeamName = "Kingston Frontenacs";
    homeTeam = getTeamWithName(homeTeamName);
    roadTeam = getTeamWithName(roadTeamName);
    final Set<Team> teams = Sets.newHashSet(homeTeam, roadTeam,
          getTeamWithName("Barrie Colts"), getTeamWithName("Erie Otters"));

    UISpec4J.init();
    final StatsProvider statsProvider = mock(StatsProvider.class);
    when(statsProvider.getTeams()).thenReturn(new Teams(teams));

    final String homeTeamGamesheet = "HOME_TEAM_GAMESHEET";
    final String roadTeamGamesheet = "ROAD_TEAM_GAMESHEET";
    htmlGamesheets = new HtmlGamesheets(homeTeamGamesheet, roadTeamGamesheet);
    final HtmlGamesheetsGetter htmlGamesheetsGetter =
          mock(HtmlGamesheetsGetter.class);
    when(htmlGamesheetsGetter.getGamesheets(eq(homeTeam), eq(roadTeam),
          any(Calendar.class), any(ProgressIndicator.class))).
          thenReturn(htmlGamesheets);
    htmlGamesheetsWriter = mock(HtmlGamesheetsWriter.class);
    frame = new CreateGamesheetsFrame(statsProvider, htmlGamesheetsGetter,
          htmlGamesheetsWriter);
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
  public void createGamesheets() throws Exception
  {
    final ComboBox homeTeamComboBox = window.getComboBox(
          CreateGamesheetsFrame.HOME_TEAM_COMBO_BOX_NAME);
    homeTeamComboBox.select(homeTeamName);
    final ComboBox roadTeamComboBox = window.getComboBox(
          CreateGamesheetsFrame.ROAD_TEAM_COMBO_BOX_NAME);
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
