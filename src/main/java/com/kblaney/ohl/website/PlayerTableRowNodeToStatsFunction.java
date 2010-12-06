package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerStats;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;

final class PlayerTableRowNodeToStatsFunction
      implements Function<Node, PlayerStats>
{
  public PlayerStats apply(final Node tableRowNode)
  {
    final int numChildNodes = tableRowNode.getChildNodes().getLength();
    if (numChildNodes >= 11)
    {
      final int numGamesPlayed = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 4));
      final int numGoals = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 5));
      final int numAssists = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 6));
      final int numPoints = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 7));
      final int plusMinus = Integer.parseInt(StringUtils.remove(
            Nodes.getChildNodeValue(tableRowNode, 8), '+'));
      final int numPenaltyMinutes = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 9));
      final int numPowerplayGoals = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 10));
      final int numShorthandedGoals = Integer.parseInt(
            Nodes.getChildNodeValue(tableRowNode, 11));

      return new PlayerStats.Builder().setNumGamesPlayed(numGamesPlayed).
            setNumGoals(numGoals).setNumAssists(numAssists).
            setNumPoints(numPoints).setPlusMinus(plusMinus).
            setNumPenaltyMinutes(numPenaltyMinutes).
            setNumPowerPlayGoals(numPowerplayGoals).
            setNumShorthandedGoals(numShorthandedGoals).build();
    }
    else
    {
      throw new IllegalArgumentException(
            "Not enough child nodes to get player stats: " + numChildNodes);
    }
  }
}
