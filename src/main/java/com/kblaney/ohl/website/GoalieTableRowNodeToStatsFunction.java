package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.GoalieStats;
import org.apache.commons.lang.math.NumberUtils;
import org.w3c.dom.Node;

final class GoalieTableRowNodeToStatsFunction implements Function<Node, GoalieStats>
{
  public GoalieStats apply(final Node tableRowNode)
  {
    final int numGamesPlayed = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 3));
    final int numMinutesPlayed = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 4));
    final int numGoalsAgainst = (int) Double.parseDouble(Nodes.getChildNodeValue(tableRowNode, 5));
    final int numShutouts = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 6));
    final double goalsAgainstAverage = NumberUtils.toDouble(Nodes.getChildNodeValue(tableRowNode, 7));
    final int numWins = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 8));
    final int numLosses = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 9));
    final int numOvertimeLosses = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 10));
    final int numShootoutLosses = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 11));
    final int numShotsAgainst = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 12));
    final int numSaves = NumberUtils.toInt(Nodes.getChildNodeValue(tableRowNode, 13));
    final double savePercentage = NumberUtils.toDouble(Nodes.getChildNodeValue(tableRowNode, 14));

    return new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed).setNumMinutesPlayed(numMinutesPlayed)
          .setNumGoalsAgainst(numGoalsAgainst).setNumShutouts(numShutouts).setGoalsAgainstAverage(goalsAgainstAverage)
          .setNumWins(numWins).setNumRegulationLosses(numLosses).setNumOvertimeLosses(numOvertimeLosses)
          .setNumShootoutLosses(numShootoutLosses).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setSavePercentage(savePercentage).build();
  }
}
