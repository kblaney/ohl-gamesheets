package com.kblaney.ohl.gamesheets;

import com.google.inject.AbstractModule;

public final class GuiceGamesheetsModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(HtmlGamesheetsGetter.class).to(HtmlGamesheetsGetterImpl.class);
  }
}
