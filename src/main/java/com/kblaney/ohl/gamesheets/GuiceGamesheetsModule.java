package com.kblaney.ohl.gamesheets;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.Teams;
import java.util.List;

public final class GuiceGamesheetsModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(HtmlGamesheetsGetter.class).to(HtmlGamesheetsGetterImpl.class);
    bind(new TypeLiteral<Function<List<Player>, String>>() {}).
          to(PlayerHtmlTableGetter.class);
    bind(new TypeLiteral<Function<List<Goalie>, String>>() {}).
          to(GoalieHtmlTableGetter.class);
    bind(Teams.class).toProvider(TeamsProvider.class);
  }
}
