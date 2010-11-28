package com.kblaney.ohl.website;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kblaney.ohl.gamesheets.StatsProvider;

public final class GuiceWebsiteModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(StatsProvider.class).to(Website.class).in(Scopes.SINGLETON);
  }
}
