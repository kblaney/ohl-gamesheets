package com.kblaney.ohl.gamesheets.html;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import java.util.List;

public final class GuiceHtmlGamesheetsModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(new TypeLiteral<Function<List<Player>, String>>()
    {
    }).to(PlayerHtmlTableGetter.class);
    bind(new TypeLiteral<Function<List<Goalie>, String>>()
    {
    }).to(GoalieHtmlTableGetter.class);
  }
}
