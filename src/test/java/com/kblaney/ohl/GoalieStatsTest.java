package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class GoalieStatsTest
{
  private int numGamesPlayed;
  private int numMinutesPlayed;
  private int numGoalsAgainst;
  private int numShutouts;
  private double goalsAgainstAverage;
  private int numWins;
  private int numRegulationLosses;
  private int numOvertimeLosses;
  private int numShootoutLosses;
  private int numShotsAgainst;
  private int numSaves;
  private double savePercentage;
  private GoalieStats.Builder builder;
  private GoalieStats stats;

  @Before
  public void setUp()
  {
    numGamesPlayed = 7;
    numMinutesPlayed = 399;
    numGoalsAgainst = 20;
    numShutouts = 1;
    goalsAgainstAverage = 2.85;
    numWins = 6;
    numRegulationLosses = 5;
    numOvertimeLosses = 4;
    numShootoutLosses = 3;
    numShotsAgainst = 188;
    numSaves = 180;
    savePercentage = 0.945;
    builder = new GoalieStats.Builder();
    stats = builder.setNumGamesPlayed(numGamesPlayed).
          setNumMinutesPlayed(numMinutesPlayed).
          setNumGoalsAgainst(numGoalsAgainst).
          setNumShutouts(numShutouts).
          setGoalsAgainstAverage(goalsAgainstAverage).
          setNumWins(numWins).
          setNumRegulationLosses(numRegulationLosses).
          setNumOvertimeLosses(numOvertimeLosses).
          setNumShootoutLosses(numShootoutLosses).
          setNumShotsAgainst(numShotsAgainst).
          setNumSaves(numSaves).
          setSavePercentage(savePercentage).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeGoalsAgainstAverage()
  {
    builder.setGoalsAgainstAverage(-2.22);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumGamesPlayed()
  {
    builder.setNumGamesPlayed(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumGoalsAgainst()
  {
    builder.setNumGoalsAgainst(-3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumRegulationLosses()
  {
    builder.setNumRegulationLosses(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumMinutesPlayed()
  {
    builder.setNumMinutesPlayed(-9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumSaves()
  {
    builder.setNumSaves(-12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumShotsAgainst()
  {
    builder.setNumShotsAgainst(-12);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumShutouts()
  {
    builder.setNumShutouts(-11);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumOvertimeLosses()
  {
    builder.setNumOvertimeLosses(-7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumShootoutLosses()
  {
    builder.setNumShootoutLosses(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumWins()
  {
    builder.setNumWins(-4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeSavePercentage()
  {
    builder.setSavePercentage(-0.988);
  }

  @Test(expected = IllegalArgumentException.class)
  public void savePercentageTooLarge()
  {
    builder.setSavePercentage(1.001);
  }

  @Test
  public void getGoalsAgainstAverage()
  {
    final double epsilon = 0;
    assertEquals(goalsAgainstAverage, stats.getGoalsAgainstAverage(), epsilon);
  }

  @Test
  public void getNumGamesPlayed()
  {
    assertEquals(numGamesPlayed, stats.getNumGamesPlayed());
  }

  @Test
  public void getNumGoalsAgainst()
  {
    assertEquals(numGoalsAgainst, stats.getNumGoalsAgainst());
  }

  @Test
  public void getNumRegulationLosses()
  {
    assertEquals(numRegulationLosses, stats.getNumRegulationLosses());
  }

  @Test
  public void getNumMinutesPlayed()
  {
    assertEquals(numMinutesPlayed, stats.getNumMinutesPlayed());
  }

  @Test
  public void getNumSaves()
  {
    assertEquals(numSaves, stats.getNumSaves());
  }

  @Test
  public void getNumShotsAgainst()
  {
    assertEquals(numShotsAgainst, stats.getNumShotsAgainst());
  }

  @Test
  public void getNumShutouts()
  {
    assertEquals(numShutouts, stats.getNumShutouts());
  }

  @Test
  public void getNumOvertimeLosses()
  {
    assertEquals(numOvertimeLosses, stats.getNumOvertimeLosses());
  }

  @Test
  public void getNumShootoutLosses()
  {
    assertEquals(numShootoutLosses, stats.getNumShootoutLosses());
  }

  @Test
  public void getNumWins()
  {
    assertEquals(numWins, stats.getNumWins());
  }

  @Test
  public void getSavePercentage()
  {
    final double epsilon = 0;
    assertEquals(savePercentage, stats.getSavePercentage(), epsilon);
  }
}
