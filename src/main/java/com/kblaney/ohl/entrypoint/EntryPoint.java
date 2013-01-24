package com.kblaney.ohl.entrypoint;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kblaney.ohl.gamesheets.CreateGamesheetsFrame;
import com.kblaney.ohl.gamesheets.html.GuiceHtmlGamesheetsModule;
import com.kblaney.ohl.website.GuiceWebsiteModule;

/**
 * The entry point of the application.
 */
public final class EntryPoint
{
  private EntryPoint()
  {
  }

  public static void main(final String[] args) throws Exception
  {
    final Injector injector = Guice.createInjector(new GuiceWebsiteModule(), new GuiceGamesheetsModule(),
          new GuiceHtmlGamesheetsModule());
    final CreateGamesheetsFrame frame = injector.getInstance(CreateGamesheetsFrame.class);
    frame.setVisible(true);
  }
}
