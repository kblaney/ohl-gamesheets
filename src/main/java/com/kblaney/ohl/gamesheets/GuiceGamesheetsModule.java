package com.kblaney.ohl.gamesheets;

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
