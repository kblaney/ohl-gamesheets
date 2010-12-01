package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

public final class GoalieStats
{
  public static class Builder
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

    public GoalieStats build()
    {
      final GoalieStats goalieStats = new GoalieStats();
      goalieStats.numGamesPlayed = numGamesPlayed;
      goalieStats.numMinutesPlayed = numMinutesPlayed;
      goalieStats.numGoalsAgainst = numGoalsAgainst;
      goalieStats.numShutouts = numShutouts;
      goalieStats.goalsAgainstAverage = goalsAgainstAverage;
      goalieStats.numWins = numWins;
      goalieStats.numRegulationLosses = numRegulationLosses;
      goalieStats.numOvertimeLosses = numOvertimeLosses;
      goalieStats.numShootoutLosses = numShootoutLosses;
      goalieStats.numShotsAgainst = numShotsAgainst;
      goalieStats.numSaves = numSaves;
      goalieStats.savePercentage = savePercentage;
      return goalieStats;
    }

    public Builder setGoalsAgainstAverage(final double goalsAgainstAverage)
    {
      ArgAssert.notNegative(goalsAgainstAverage, "goalsAgainstAverage");

      this.goalsAgainstAverage = goalsAgainstAverage;
      return this;
    }

    public Builder setNumGamesPlayed(final int numGamesPlayed)
    {
      ArgAssert.notNegative(numGamesPlayed, "numGamesPlayed");

      this.numGamesPlayed = numGamesPlayed;
      return this;
    }

    public Builder setNumGoalsAgainst(final int numGoalsAgainst)
    {
      ArgAssert.notNegative(numGoalsAgainst, "numGoalsAgainst");

      this.numGoalsAgainst = numGoalsAgainst;
      return this;
    }

    public Builder setNumRegulationLosses(final int numRegulationLosses)
    {
      ArgAssert.notNegative(numRegulationLosses, "numRegulationLosses");

      this.numRegulationLosses = numRegulationLosses;
      return this;
    }

    public Builder setNumMinutesPlayed(final int numMinutesPlayed)
    {
      ArgAssert.notNegative(numMinutesPlayed, "numMinutesPlayed");

      this.numMinutesPlayed = numMinutesPlayed;
      return this;
    }

    public Builder setNumSaves(final int numSaves)
    {
      ArgAssert.notNegative(numSaves, "numSaves");

      this.numSaves = numSaves;
      return this;
    }

    public Builder setNumShotsAgainst(final int numShotsAgainst)
    {
      ArgAssert.notNegative(numShotsAgainst, "numShotsAgainst");

      this.numShotsAgainst = numShotsAgainst;
      return this;
    }

    public Builder setNumShutouts(final int numShutouts)
    {
      ArgAssert.notNegative(numShutouts, "numShutouts");

      this.numShutouts = numShutouts;
      return this;
    }

    public Builder setNumOvertimeLosses(final int numOvertimeLosses)
    {
      ArgAssert.notNegative(numOvertimeLosses, "numOvertimeLosses");

      this.numOvertimeLosses = numOvertimeLosses;
      return this;
    }

    public Builder setNumShootoutLosses(final int numShootoutLosses)
    {
      ArgAssert.notNegative(numShootoutLosses, "numShootoutLosses");

      this.numShootoutLosses = numShootoutLosses;
      return this;
    }

    public Builder setNumWins(final int numWins)
    {
      ArgAssert.notNegative(numWins, "numWins");

      this.numWins = numWins;
      return this;
    }

    public Builder setSavePercentage(final double savePercentage)
    {
      ArgAssert.notNegative(savePercentage, "savePercentage");
      ArgAssert.notTooLarge(savePercentage, 1, "savePercentage");

      this.savePercentage = savePercentage;
      return this;
    }
  }

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

  private GoalieStats() {}

  public double getGoalsAgainstAverage() { return goalsAgainstAverage; }
  public int getNumGamesPlayed() { return numGamesPlayed; }
  public int getNumGoalsAgainst() { return numGoalsAgainst; }
  public int getNumRegulationLosses() { return numRegulationLosses; }
  public int getNumMinutesPlayed() { return numMinutesPlayed; }
  public int getNumSaves() { return numSaves; }
  public int getNumShotsAgainst() { return numShotsAgainst; }
  public int getNumShutouts() { return numShutouts; }
  public int getNumOvertimeLosses() { return numOvertimeLosses; }
  public int getNumShootoutLosses() { return numShootoutLosses; }
  public int getNumWins() { return numWins; }
  public double getSavePercentage() { return savePercentage; }
}
