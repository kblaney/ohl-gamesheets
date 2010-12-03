package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerStatsTest
{
  private int numGamesPlayed;
  private int numGoals;
  private int numAssists;
  private int numPoints;
  private int plusMinus;
  private int numPenaltyMinutes;
  private int numPowerPlayGoals;
  private int numShorthandedGoals;
  private PlayerStats stats;

  @Before
  public void setUp()
  {
    numGamesPlayed = 8;
    numGoals = 6;
    numAssists = 3;
    numPoints = 9;
    plusMinus = -2;
    numPenaltyMinutes = 13;
    numPowerPlayGoals = 2;
    numShorthandedGoals = 1;
    stats = new PlayerStats.Builder().setNumGamesPlayed(numGamesPlayed).
          setNumGoals(numGoals).setNumAssists(numAssists).
          setNumPoints(numPoints).setPlusMinus(plusMinus).
          setNumPenaltyMinutes(numPenaltyMinutes).
          setNumPowerPlayGoals(numPowerPlayGoals).
          setNumShorthandedGoals(numShorthandedGoals).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumGamesPlayed()
  {
    new PlayerStats.Builder().setNumGamesPlayed(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumGoals()
  {
    new PlayerStats.Builder().setNumGoals(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumAssists()
  {
    new PlayerStats.Builder().setNumAssists(-7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumPoints()
  {
    new PlayerStats.Builder().setNumPoints(-182);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumPenaltyMinutes()
  {
    new PlayerStats.Builder().setNumPenaltyMinutes(-12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumPowerPlayGoals()
  {
    new PlayerStats.Builder().setNumPowerPlayGoals(-9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumShorthandedGoals()
  {
    new PlayerStats.Builder().setNumShorthandedGoals(-88);
  }

  @Test
  public void getNumGamesPlayed()
  {
    assertEquals(numGamesPlayed, stats.getNumGamesPlayed());
  }

  @Test
  public void getNumGoals()
  {
    assertEquals(numGoals, stats.getNumGoals());
  }

  @Test
  public void getNumAssists()
  {
    assertEquals(numAssists, stats.getNumAssists());
  }

  @Test
  public void getNumPoints()
  {
    assertEquals(numPoints, stats.getNumPoints());
  }

  @Test
  public void getPlusMinus()
  {
    assertEquals(plusMinus, stats.getPlusMinus());
  }

  @Test
  public void getNumPenaltyMinutes()
  {
    assertEquals(numPenaltyMinutes, stats.getNumPenaltyMinutes());
  }

  @Test
  public void getNumPowerPlayGoals()
  {
    assertEquals(numPowerPlayGoals, stats.getNumPowerPlayGoals());
  }

  @Test
  public void getNumShorthandedGoals()
  {
    assertEquals(numShorthandedGoals, stats.getNumShorthandedGoals());
  }
}
