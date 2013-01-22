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
  private GoalieStats equalStats;

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
    stats = builder.setNumGamesPlayed(numGamesPlayed).setNumMinutesPlayed(numMinutesPlayed)
          .setNumGoalsAgainst(numGoalsAgainst).setNumShutouts(numShutouts).setGoalsAgainstAverage(goalsAgainstAverage)
          .setNumWins(numWins).setNumRegulationLosses(numRegulationLosses).setNumOvertimeLosses(numOvertimeLosses)
          .setNumShootoutLosses(numShootoutLosses).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setSavePercentage(savePercentage).build();
    equalStats = builder.setNumGamesPlayed(numGamesPlayed).setNumMinutesPlayed(numMinutesPlayed)
          .setNumGoalsAgainst(numGoalsAgainst).setNumShutouts(numShutouts).setGoalsAgainstAverage(goalsAgainstAverage)
          .setNumWins(numWins).setNumRegulationLosses(numRegulationLosses).setNumOvertimeLosses(numOvertimeLosses)
          .setNumShootoutLosses(numShootoutLosses).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setSavePercentage(savePercentage).build();
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

  @Test
  public void equals_null()
  {
    assertFalse(stats.equals(null));
  }

  @Test
  public void equals_wrongType()
  {
    assertFalse(stats.equals("A"));
  }

  @Test
  public void equals_unequalNumGamesPlayed()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed + 1)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumMinutesPlayed()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed + 1).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumWins()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins + 1).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumRegulationLosses()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses + 1)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumOvertimeLosses()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses + 1).setNumShootoutLosses(numShootoutLosses)
          .setNumShutouts(numShutouts).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setNumGoalsAgainst(numGoalsAgainst).setGoalsAgainstAverage(goalsAgainstAverage)
          .setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumShootoutLosses()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses + 1)
          .setNumShutouts(numShutouts).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setNumGoalsAgainst(numGoalsAgainst).setGoalsAgainstAverage(goalsAgainstAverage)
          .setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumShutouts()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses)
          .setNumShutouts(numShutouts + 1).setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves)
          .setNumGoalsAgainst(numGoalsAgainst).setGoalsAgainstAverage(goalsAgainstAverage)
          .setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumShotsAgainst()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst + 1).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumSaves()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves + 1).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalNumGoalsAgainst()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst + 1)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalGoalsAgainstAverage()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage / 2).setSavePercentage(savePercentage).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_unequalSavePercentage()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed)
          .setNumMinutesPlayed(numMinutesPlayed).setNumWins(numWins).setNumRegulationLosses(numRegulationLosses)
          .setNumOvertimeLosses(numOvertimeLosses).setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts)
          .setNumShotsAgainst(numShotsAgainst).setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst)
          .setGoalsAgainstAverage(goalsAgainstAverage).setSavePercentage(savePercentage / 2).build();
    assertFalse(stats.equals(unequalStats));
  }

  @Test
  public void equals_equalInstance()
  {
    assertEquals(stats, equalStats);
  }

  @Test
  public void hashCode_equalInstance()
  {
    assertEquals(stats.hashCode(), equalStats.hashCode());
  }

  @Test
  public void testToString()
  {
    numGamesPlayed = 21;
    numMinutesPlayed = 1199;
    numWins = 8;
    numRegulationLosses = 5;
    numOvertimeLosses = 2;
    numShootoutLosses = 3;
    numShutouts = 1;
    numShotsAgainst = 459;
    numSaves = 412;
    numGoalsAgainst = 47;
    goalsAgainstAverage = 2.35;
    savePercentage = 0.898;
    stats = new GoalieStats.Builder().setNumGamesPlayed(numGamesPlayed).setNumMinutesPlayed(numMinutesPlayed)
          .setNumWins(numWins).setNumRegulationLosses(numRegulationLosses).setNumOvertimeLosses(numOvertimeLosses)
          .setNumShootoutLosses(numShootoutLosses).setNumShutouts(numShutouts).setNumShotsAgainst(numShotsAgainst)
          .setNumSaves(numSaves).setNumGoalsAgainst(numGoalsAgainst).setGoalsAgainstAverage(goalsAgainstAverage)
          .setSavePercentage(savePercentage).build();
    assertEquals("GP:21;MIN:1199;W:8;RL:5;OTL:2;SOL:3;SO:1;SA:459;S:412;GA:47;GAA:2.35;SP:0.898", stats.toString());
  }
}
