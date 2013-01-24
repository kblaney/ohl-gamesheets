package com.kblaney.ohl.entrypoint;

import com.kblaney.ohl.gamesheets.GamesheetsGetter;
import com.kblaney.ohl.gamesheets.GamesheetsWriter;
import com.kblaney.ohl.gamesheets.StringWriterToFile;
import com.kblaney.ohl.gamesheets.StringWriterToFileImpl;
import com.kblaney.ohl.gamesheets.html.HtmlGamesheetsFileWriter;
import com.kblaney.ohl.gamesheets.html.HtmlGamesheetsGetter;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.website.Website;

public final class GuiceGamesheetsModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(StatsProvider.class).to(Website.class).in(Scopes.SINGLETON);
    bind(GamesheetsGetter.class).to(HtmlGamesheetsGetter.class);
    bind(GamesheetsWriter.class).to(HtmlGamesheetsFileWriter.class);
    bind(StringWriterToFile.class).to(StringWriterToFileImpl.class);
  }
}
