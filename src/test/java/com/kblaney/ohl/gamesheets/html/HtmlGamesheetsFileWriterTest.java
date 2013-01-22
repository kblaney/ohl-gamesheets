package com.kblaney.ohl.gamesheets.html;

import com.kblaney.ohl.gamesheets.Gamesheets;
import com.kblaney.ohl.gamesheets.GamesheetsWriter;
import com.kblaney.ohl.gamesheets.StringWriterToFile;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class HtmlGamesheetsFileWriterTest
{
  private StringWriterToFile stringWriterToFile;
  private GamesheetsWriter gamesheetsWriter;

  @Before
  public void setUp()
  {
    stringWriterToFile = mock(StringWriterToFile.class);
    gamesheetsWriter = new HtmlGamesheetsFileWriter(stringWriterToFile);
  }

  @Test
  public void write() throws Exception
  {
    final String homeTeamGamesheet = "HOME_TEAM";
    final String roadTeamGamesheet = "ROAD_TEAM";
    final Gamesheets htmlGamesheets = new Gamesheets(homeTeamGamesheet, roadTeamGamesheet);
    gamesheetsWriter.write(htmlGamesheets);
    verify(stringWriterToFile).write(eq(homeTeamGamesheet), any(File.class));
    verify(stringWriterToFile).write(eq(roadTeamGamesheet), any(File.class));
  }

  @Test
  public void getDescription()
  {
    assertNotNull(gamesheetsWriter.getDescription());
  }
}
