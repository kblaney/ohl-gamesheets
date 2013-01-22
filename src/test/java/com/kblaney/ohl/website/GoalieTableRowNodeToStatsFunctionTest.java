package com.kblaney.ohl.website;

import com.kblaney.ohl.GoalieStats;
import org.junit.Test;
import org.w3c.dom.Element;
import static org.junit.Assert.*;

public final class GoalieTableRowNodeToStatsFunctionTest
{
  @Test
  public void apply_someTableElementsEmpty() throws Exception
  {
    final Element tableRowNode = new XmlToDomElementFunction().apply("<tr><td/><td/><td><a>Malcolm Subban</a></td>"
          + "<td>0</td><td></td><td>30</td><td>0</td><td>3.06</td"
          + "><td>3</td><td>5</td><td>0</td><td>1</td><td>304</td>" + "<td>274</td><td>0.901</td></tr>");
    final GoalieStats expected = new GoalieStats.Builder().setNumGamesPlayed(0).setNumMinutesPlayed(0)
          .setNumGoalsAgainst(30).setNumShutouts(0).setGoalsAgainstAverage(3.06).setNumWins(3)
          .setNumRegulationLosses(5).setNumOvertimeLosses(0).setNumShootoutLosses(1).setNumShotsAgainst(304)
          .setNumSaves(274).setSavePercentage(0.901).build();
    assertEquals(expected, new GoalieTableRowNodeToStatsFunction().apply(tableRowNode));
  }

  @Test
  public void apply() throws Exception
  {
    final Element tableRowNode = new XmlToDomElementFunction().apply("<tr><td/><td/><td><a>Malcolm Subban</a></td>"
          + "<td>10</td><td>588</td><td>30</td><td>0</td><td>3.06</td"
          + "><td>3</td><td>5</td><td>0</td><td>1</td><td>304</td>" + "<td>274</td><td>0.901</td></tr>");
    final GoalieStats expected = new GoalieStats.Builder().setNumGamesPlayed(10).setNumMinutesPlayed(588)
          .setNumGoalsAgainst(30).setNumShutouts(0).setGoalsAgainstAverage(3.06).setNumWins(3)
          .setNumRegulationLosses(5).setNumOvertimeLosses(0).setNumShootoutLosses(1).setNumShotsAgainst(304)
          .setNumSaves(274).setSavePercentage(0.901).build();
    assertEquals(expected, new GoalieTableRowNodeToStatsFunction().apply(tableRowNode));
  }
}
