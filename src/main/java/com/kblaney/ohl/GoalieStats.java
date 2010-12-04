package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
      this.goalsAgainstAverage =
            ArgAssert.notNegative(goalsAgainstAverage, "goalsAgainstAverage");
      return this;
    }

    public Builder setNumGamesPlayed(final int numGamesPlayed)
    {
      this.numGamesPlayed =
            ArgAssert.notNegative(numGamesPlayed, "numGamesPlayed");
      return this;
    }

    public Builder setNumGoalsAgainst(final int numGoalsAgainst)
    {
      this.numGoalsAgainst =
            ArgAssert.notNegative(numGoalsAgainst, "numGoalsAgainst");
      return this;
    }

    public Builder setNumRegulationLosses(final int numRegulationLosses)
    {
      this.numRegulationLosses =
            ArgAssert.notNegative(numRegulationLosses, "numRegulationLosses");
      return this;
    }

    public Builder setNumMinutesPlayed(final int numMinutesPlayed)
    {
      this.numMinutesPlayed =
            ArgAssert.notNegative(numMinutesPlayed, "numMinutesPlayed");
      return this;
    }

    public Builder setNumSaves(final int numSaves)
    {
      this.numSaves = ArgAssert.notNegative(numSaves, "numSaves");
      return this;
    }

    public Builder setNumShotsAgainst(final int numShotsAgainst)
    {
      this.numShotsAgainst =
            ArgAssert.notNegative(numShotsAgainst, "numShotsAgainst");
      return this;
    }

    public Builder setNumShutouts(final int numShutouts)
    {
      this.numShutouts = ArgAssert.notNegative(numShutouts, "numShutouts");
      return this;
    }

    public Builder setNumOvertimeLosses(final int numOvertimeLosses)
    {
      this.numOvertimeLosses =
            ArgAssert.notNegative(numOvertimeLosses, "numOvertimeLosses");
      return this;
    }

    public Builder setNumShootoutLosses(final int numShootoutLosses)
    {
      this.numShootoutLosses =
            ArgAssert.notNegative(numShootoutLosses, "numShootoutLosses");
      return this;
    }

    public Builder setNumWins(final int numWins)
    {
      this.numWins = ArgAssert.notNegative(numWins, "numWins");
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
  private int numWins;
  private int numRegulationLosses;
  private int numOvertimeLosses;
  private int numShootoutLosses;
  private int numShutouts;
  private int numShotsAgainst;
  private int numSaves;
  private int numGoalsAgainst;
  private double goalsAgainstAverage;
  private double savePercentage;

  private GoalieStats() {}

  public int getNumGamesPlayed() { return numGamesPlayed; }
  public int getNumMinutesPlayed() { return numMinutesPlayed; }
  public int getNumWins() { return numWins; }
  public int getNumRegulationLosses() { return numRegulationLosses; }
  public int getNumOvertimeLosses() { return numOvertimeLosses; }
  public int getNumShootoutLosses() { return numShootoutLosses; }
  public int getNumShutouts() { return numShutouts; }
  public int getNumShotsAgainst() { return numShotsAgainst; }
  public int getNumGoalsAgainst() { return numGoalsAgainst; }
  public int getNumSaves() { return numSaves; }
  public double getGoalsAgainstAverage() { return goalsAgainstAverage; }
  public double getSavePercentage() { return savePercentage; }

  @Override
  public boolean equals(final Object thatObject)
  {
    if (thatObject == null)
    {
      return false;
    }
    if (thatObject.getClass().equals(getClass()))
    {
      final GoalieStats that = (GoalieStats) thatObject;
      return ((numGamesPlayed == that.numGamesPlayed) &&
            (numMinutesPlayed == that.numMinutesPlayed) &&
            (numWins == that.numWins) &&
            (numRegulationLosses == that.numRegulationLosses) &&
            (numOvertimeLosses == that.numOvertimeLosses) &&
            (numShootoutLosses == that.numShootoutLosses) &&
            (numShutouts == that.numShutouts) &&
            (numShotsAgainst == that.numShotsAgainst) &&
            (numSaves == that.numSaves) &&
            (numGoalsAgainst == that.numGoalsAgainst) &&
            (goalsAgainstAverage == that.goalsAgainstAverage) &&
            (savePercentage == that.savePercentage));
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(numGamesPlayed).append(numWins).
          append(numRegulationLosses).append(numOvertimeLosses).
          append(numShootoutLosses).append(numShutouts).append(numShotsAgainst).
          append(numSaves).append(numGoalsAgainst).append(goalsAgainstAverage).
          append(savePercentage).toHashCode();
  }

  @Override
  public String toString()
  {
    final StringBuilder s = new StringBuilder();
    final char separator = ';';
    s.append("GP:").append(numGamesPlayed).append(separator);
    s.append("MIN:").append(numMinutesPlayed).append(separator);
    s.append("W:").append(numWins).append(separator);
    s.append("RL:").append(numRegulationLosses).append(separator);
    s.append("OTL:").append(numOvertimeLosses).append(separator);
    s.append("SOL:").append(numShootoutLosses).append(separator);
    s.append("SO:").append(numShutouts).append(separator);
    s.append("SA:").append(numShotsAgainst).append(separator);
    s.append("S:").append(numSaves).append(separator);
    s.append("GA:").append(numGoalsAgainst).append(separator);
    s.append("GAA:").append(goalsAgainstAverage).append(separator);
    s.append("SP:").append(savePercentage);
    return s.toString();
  }
}
