package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.StatsProvider;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class GuiceWebsiteModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(StatsProvider.class).to(Website.class).in(Scopes.SINGLETON);
    bind(PlayerSupplier.class).to(PlayerSupplierImpl.class);
    bind(GoalieSupplier.class).to(GoalieSupplierImpl.class);
    bind(new TypeLiteral<Function<Node, GoalieStats>>() {}).
          to(GoalieTableRowNodeToStatsFunction.class);
    bind(new TypeLiteral<Function<Node, PlayerType>>() {}).
          to(PlayerTableRowNodeToPlayerTypeFunction.class);
    bind(new TypeLiteral<Function<Node, Integer>>() {}).
          to(PlayerTableRowNodeToSweaterNumFunction.class);
    bind(new TypeLiteral<Function<Node, PlayerStats>>() {}).
          to(PlayerTableRowNodeToStatsFunction.class);
    bind(new TypeLiteral<Function<String, PlayerBio>>() {}).
          to(PlayerIdToBioFunction.class);
    bind(PlayerStreaksSupplier.class).to(PlayerStreaksSupplierImpl.class);
    bind(new TypeLiteral<Function<String, Node>>() {}).
          to(PlayerIdToBioDivNodeFunction.class);
    bind(new TypeLiteral<Function<String, NodeList>>() {}).
          to(PlayerIdToGameByGameRowNodeListFunction.class);
    bind(new TypeLiteral<Function<Node, String>>() {}).
          to(PlayerTableRowNodeToNameFunction.class);
  }
}
