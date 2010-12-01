package com.kblaney.ohl.gamesheets;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class HtmlGamesheetsFileWriterTest
{
  private StringWriterToFile stringWriterToFile;
  private HtmlGamesheetsWriter htmlGamesheetsWriter;

  @Before
  public void setUp()
  {
    stringWriterToFile = mock(StringWriterToFile.class);
    htmlGamesheetsWriter = new HtmlGamesheetsFileWriter(stringWriterToFile);
  }

  @Test
  public void write() throws Exception
  {
    final String homeTeamGamesheet = "HOME_TEAM";
    final String roadTeamGamesheet = "ROAD_TEAM";
    final HtmlGamesheets htmlGamesheets = new HtmlGamesheets(homeTeamGamesheet,
          roadTeamGamesheet);
    htmlGamesheetsWriter.write(htmlGamesheets);
    verify(stringWriterToFile).write(eq(homeTeamGamesheet), any(File.class));
    verify(stringWriterToFile).write(eq(roadTeamGamesheet), any(File.class));
  }

  @Test
  public void getDescription()
  {
    assertNotNull(htmlGamesheetsWriter.getDescription());
  }
}
