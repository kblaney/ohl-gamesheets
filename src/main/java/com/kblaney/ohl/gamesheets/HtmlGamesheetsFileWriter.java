package com.kblaney.ohl.gamesheets;

import com.google.inject.Inject;
import java.io.File;
import java.io.IOException;

final class HtmlGamesheetsFileWriter implements HtmlGamesheetsWriter
{
  private final StringWriterToFile stringWriterToFile;
  private final File directory = new File(System.getProperty("user.home"));

  @Inject
  public HtmlGamesheetsFileWriter(final StringWriterToFile stringWriterToFile)
  {
    this.stringWriterToFile = stringWriterToFile;
  }

  public void write(final HtmlGamesheets htmlGamesheets) throws IOException
  {
    final File homeTeamGamesheetFile = new File(directory, "home.html");
    final File roadTeamGamesheetFile = new File(directory, "road.html");
    stringWriterToFile.write(htmlGamesheets.getHomeTeamGamesheet(),
          homeTeamGamesheetFile);
    stringWriterToFile.write(htmlGamesheets.getRoadTeamGamesheet(),
          roadTeamGamesheetFile);
  }

  public String getDescription()
  {
    return directory.getAbsolutePath();
  }
}
