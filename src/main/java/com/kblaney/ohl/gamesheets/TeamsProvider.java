package com.kblaney.ohl.gamesheets;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.kblaney.ohl.Teams;
import java.io.IOException;

final class TeamsProvider implements Provider<Teams>
{
  private final StatsProvider statsProvider;

  @Inject
  TeamsProvider(final StatsProvider statsProvider)
  {
    this.statsProvider = statsProvider;
  }

  public Teams get()
  {
    try
    {
      return statsProvider.getTeams();
    }
    catch (final IOException e)
    {
      throw new IllegalStateException(e);
    }
  }
}
