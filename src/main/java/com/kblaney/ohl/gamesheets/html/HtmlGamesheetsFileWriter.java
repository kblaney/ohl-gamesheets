package com.kblaney.ohl.gamesheets.html;

import com.google.inject.Inject;
import com.kblaney.ohl.gamesheets.Gamesheets;
import com.kblaney.ohl.gamesheets.GamesheetsWriter;
import com.kblaney.ohl.gamesheets.StringWriterToFile;
import java.io.File;
import java.io.IOException;

public final class HtmlGamesheetsFileWriter implements GamesheetsWriter
{
  private final StringWriterToFile stringWriterToFile;
  private final File directory = new File(System.getProperty("user.home"));

  @Inject
  public HtmlGamesheetsFileWriter(final StringWriterToFile stringWriterToFile)
  {
    this.stringWriterToFile = stringWriterToFile;
  }

  public void write(final Gamesheets gamesheets) throws IOException
  {
    final File homeTeamGamesheetFile = new File(directory, "home.html");
    final File roadTeamGamesheetFile = new File(directory, "road.html");
    stringWriterToFile.write(gamesheets.getHomeTeamGamesheet(),
          homeTeamGamesheetFile);
    stringWriterToFile.write(gamesheets.getRoadTeamGamesheet(),
          roadTeamGamesheetFile);
  }

  public String getDescription()
  {
    return directory.getAbsolutePath();
  }
}
