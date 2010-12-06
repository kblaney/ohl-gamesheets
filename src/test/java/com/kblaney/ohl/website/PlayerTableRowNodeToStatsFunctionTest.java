package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerStats;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;

public final class PlayerTableRowNodeToStatsFunctionTest
{
  private Function<Node, PlayerStats> function;

  @Before
  public void setUp()
  {
    function = new PlayerTableRowNodeToStatsFunction();
  }

  @Test(expected = IllegalArgumentException.class)
  public void apply_notEnoughChildNodes() throws Exception
  {
    function.apply(new XmlToDomElementFunction().apply("<tr/>"));
  }

  @Test
  public void apply() throws Exception
  {
    final PlayerStats stats = function.apply(new XmlToDomElementFunction().
          apply("<tr><td/><td/><td/><td/><td>29</td><td>11</td><td>10</td>" +
          "<td>21</td><td>-7</td><td>52</td><td>4</td><td>3</td></tr>"));
    assertEquals(29, stats.getNumGamesPlayed());
    assertEquals(11, stats.getNumGoals());
    assertEquals(10, stats.getNumAssists());
    assertEquals(21, stats.getNumPoints());
    assertEquals(-7, stats.getPlusMinus());
    assertEquals(52, stats.getNumPenaltyMinutes());
    assertEquals(4, stats.getNumPowerPlayGoals());
    assertEquals(3, stats.getNumShorthandedGoals());
  }
}
