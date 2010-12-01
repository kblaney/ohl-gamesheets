package com.kblaney.ohl.gamesheets;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

final class HtmlGamesheetsFileWriter implements HtmlGamesheetsWriter
{
  private final File directory = new File(System.getProperty("user.home"));

  public void write(final HtmlGamesheets htmlGamesheets) throws IOException
  {
    final File homeTeamGamesheetFile = new File(directory, "home.html");
    final File roadTeamGamesheetFile = new File(directory, "road.html");
    FileUtils.writeStringToFile(homeTeamGamesheetFile,
          htmlGamesheets.getHomeTeamGamesheet());
    FileUtils.writeStringToFile(roadTeamGamesheetFile,
          htmlGamesheets.getRoadTeamGamesheet());
  }

  public String getDescription()
  {
    return directory.getAbsolutePath();
  }
}
