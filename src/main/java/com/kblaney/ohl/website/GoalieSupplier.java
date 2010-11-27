package com.kblaney.ohl.website;

import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.GoalieStats;
import java.io.IOException;
import org.w3c.dom.Node;

final class GoalieSupplier
{
  public Goalie getGoalie(final Node tableRowNode) throws IOException
  {
    final String name = getGoalieName(tableRowNode);
    final String numGamesPlayedString = Nodes.getChildNodeValue(tableRowNode, 3);
    final String numMinutesPlayedString = Nodes.getChildNodeValue(tableRowNode, 4);
    final String numGoalsAgainstString = Nodes.getChildNodeValue(tableRowNode, 5);
    final String numShutoutsString = Nodes.getChildNodeValue(tableRowNode, 6);
    final String goalsAgainstAverageString = Nodes.getChildNodeValue(tableRowNode, 7);
    final String numWinsString = Nodes.getChildNodeValue(tableRowNode, 8);
    final String numLossesString = Nodes.getChildNodeValue(tableRowNode, 9);
    final String numOvertimeLossesString = Nodes.getChildNodeValue(tableRowNode, 10);
    final String numShootoutLossesString = Nodes.getChildNodeValue(tableRowNode, 11);
    final String numShotsAgainstString = Nodes.getChildNodeValue(tableRowNode, 12);
    final String numSavesString = Nodes.getChildNodeValue(tableRowNode, 13);
    final String savePercentageString = Nodes.getChildNodeValue(tableRowNode, 14);

    final int numGamesPlayed = Integer.parseInt(numGamesPlayedString);
    final int numMinutesPlayed = Integer.parseInt(numMinutesPlayedString);
    final int numGoalsAgainst = (int) Double.parseDouble(
          numGoalsAgainstString);
    final int numShutouts = Integer.parseInt(numShutoutsString);
    final double goalsAgainstAverage = Double.parseDouble(
          goalsAgainstAverageString);
    final int numWins = Integer.parseInt(numWinsString);
    final int numLosses = Integer.parseInt(numLossesString);
    final int numOvertimeLosses = Integer.parseInt(numOvertimeLossesString);
    final int numShootoutLosses = Integer.parseInt(numShootoutLossesString);
    final int numShotsAgainst = Integer.parseInt(numShotsAgainstString);
    final int numSaves = Integer.parseInt(numSavesString);
    final double savePercentage = Double.parseDouble(savePercentageString);

    final GoalieStats goalieStats = new GoalieStats.Builder().
          setNumGamesPlayed(numGamesPlayed).
          setNumMinutesPlayed(numMinutesPlayed).
          setNumGoalsAgainst(numGoalsAgainst).
          setNumShutouts(numShutouts).
          setGoalsAgainstAverage(goalsAgainstAverage).
          setNumWins(numWins).
          setNumRegulationLosses(numLosses).
          setNumOvertimeLosses(numOvertimeLosses).
          setNumShootoutLosses(numShootoutLosses).
          setNumShotsAgainst(numShotsAgainst).
          setNumSaves(numSaves).
          setSavePercentage(savePercentage).build();
    return new Goalie(name, goalieStats);
  }

  private String getGoalieName(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToNameFunction().apply(tableRowNode);
  }
}
