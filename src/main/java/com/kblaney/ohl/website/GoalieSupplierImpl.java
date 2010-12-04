package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import org.w3c.dom.Node;

final class GoalieSupplierImpl implements GoalieSupplier
{
  private final Function<Node, String> tableRowNodeToNameFunction;
  private final Function<Node, GoalieStats> tableRowNodeToStatsFunction;

  @Inject
  public GoalieSupplierImpl(
        final Function<Node, String> tableRowNodeToNameFunction,
        final Function<Node, GoalieStats> tableRowNodeToStatsFunction)
  {
    this.tableRowNodeToNameFunction = tableRowNodeToNameFunction;
    this.tableRowNodeToStatsFunction = tableRowNodeToStatsFunction;
  }

  public Goalie get(final Node tableRowNode,
        final ProgressIndicator progressIndicator)
  {
    final String name = tableRowNodeToNameFunction.apply(tableRowNode);
    progressIndicator.setPlayerInProgress(name);
    final GoalieStats stats = tableRowNodeToStatsFunction.apply(tableRowNode);
    return new Goalie(name, stats);
  }
}
