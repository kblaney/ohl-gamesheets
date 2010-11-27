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
      final String numGamesPlayedString = Nodes.getChildNodeValue(
            tableRowNode, 4);
      final String numGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 5);
      final String numAssistsString = Nodes.getChildNodeValue(
            tableRowNode, 6);
      final String numPointsString = Nodes.getChildNodeValue(
            tableRowNode, 7);
      final String plusMinusString = StringUtils.remove(
            Nodes.getChildNodeValue(tableRowNode, 8), '+');
      final String numPenaltyMinutesString = Nodes.getChildNodeValue(
            tableRowNode, 9);
      final String numPowerplayGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 10);
      final String numShorthandedGoalsString = Nodes.getChildNodeValue(
            tableRowNode, 11);

      final int numGamesPlayed = Integer.parseInt(numGamesPlayedString);
      final int numGoals = Integer.parseInt(numGoalsString);
      final int numAssists = Integer.parseInt(numAssistsString);
      final int numPoints = Integer.parseInt(numPointsString);
      final int plusMinus = Integer.parseInt(plusMinusString);
      final int numPenaltyMinutes = Integer.parseInt(
            numPenaltyMinutesString);
      final int numPowerplayGoals = Integer.parseInt(
            numPowerplayGoalsString);
      final int numShorthandedGoals = Integer.parseInt(
            numShorthandedGoalsString);

      return new PlayerStats.Builder().setNumGamesPlayed(numGamesPlayed).
            setNumGoals(numGoals).
            setNumAssists(numAssists).
            setNumPoints(numPoints).
            setPlusMinus(plusMinus).
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
