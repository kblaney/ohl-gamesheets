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

    /**
     * Builds a GoalieStats instance.
     *
     * @return the instance
     */
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

    /**
     * Sets the goals against average.
     *
     * @param goalsAgainstAverage the goals against average, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setGoalsAgainstAverage(final double goalsAgainstAverage)
    {
      ArgAssert.notNegative(goalsAgainstAverage, "goalsAgainstAverage");

      this.goalsAgainstAverage = goalsAgainstAverage;
      return this;
    }

    /**
     * Sets the number of games played.
     *
     * @param numGamesPlayed the number of games played, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumGamesPlayed(final int numGamesPlayed)
    {
      ArgAssert.notNegative(numGamesPlayed, "numGamesPlayed");

      this.numGamesPlayed = numGamesPlayed;
      return this;
    }

    /**
     * Sets the number of goals against.
     *
     * @param numGoalsAgainst the number of goals against, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumGoalsAgainst(final int numGoalsAgainst)
    {
      ArgAssert.notNegative(numGoalsAgainst, "numGoalsAgainst");

      this.numGoalsAgainst = numGoalsAgainst;
      return this;
    }

    /**
     * Sets the number of regulation losses.
     *
     * @param numLosses the number of regulation losses, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumRegulationLosses(final int numRegulationLosses)
    {
      ArgAssert.notNegative(numRegulationLosses, "numRegulationLosses");

      this.numRegulationLosses = numRegulationLosses;
      return this;
    }

    /**
     * Sets the number of minutes played.
     *
     * @param numMinutesPlayed the number of minutes played, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumMinutesPlayed(final int numMinutesPlayed)
    {
      ArgAssert.notNegative(numMinutesPlayed, "numMinutesPlayed");

      this.numMinutesPlayed = numMinutesPlayed;
      return this;
    }

    /**
     * Sets the number of saves.
     *
     * @param numSaves the number of saves, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setNumSaves(final int numSaves)
    {
      ArgAssert.notNegative(numSaves, "numSaves");

      this.numSaves = numSaves;
      return this;
    }

    /**
     * Sets the number of shots against.
     *
     * @param numShotsAgainst the number of shots against, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumShotsAgainst(final int numShotsAgainst)
    {
      ArgAssert.notNegative(numShotsAgainst, "numShotsAgainst");

      this.numShotsAgainst = numShotsAgainst;
      return this;
    }

    /**
     * Sets the number of shutouts.
     *
     * @param numShutouts the number of shutouts, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setNumShutouts(final int numShutouts)
    {
      ArgAssert.notNegative(numShutouts, "numShutouts");

      this.numShutouts = numShutouts;
      return this;
    }

    /**
     * Sets the number of overtime losses.
     *
     * @param numOvertimeLosses the number of overtime losses, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumOvertimeLosses(final int numOvertimeLosses)
    {
      ArgAssert.notNegative(numOvertimeLosses, "numOvertimeLosses");

      this.numOvertimeLosses = numOvertimeLosses;
      return this;
    }

    /**
     * Sets the number of shootout losses.
     *
     * @param numShootoutLosses the number of shootout losses, which can't be
     * negative
     *
     * @return the builder instance
     */
    public Builder setNumShootoutLosses(final int numShootoutLosses)
    {
      ArgAssert.notNegative(numShootoutLosses, "numShootoutLosses");

      this.numShootoutLosses = numShootoutLosses;
      return this;
    }

    /**
     * Sets the number of wins.
     *
     * @param numWins the number of wins, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setNumWins(final int numWins)
    {
      ArgAssert.notNegative(numWins, "numWins");

      this.numWins = numWins;
      return this;
    }

    /**
     * Sets the save percentage.
     *
     * @param savePercentage the save percentage, which must be between 0 and
     * 1 inclusive
     *
     * @return the builder instance
     */
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

  public double getGoalsAgainstAverage()
  {
    return goalsAgainstAverage;
  }

  public int getNumGamesPlayed()
  {
    return numGamesPlayed;
  }

  public int getNumGoalsAgainst()
  {
    return numGoalsAgainst;
  }

  public int getNumRegulationLosses()
  {
    return numRegulationLosses;
  }

  public int getNumMinutesPlayed()
  {
    return numMinutesPlayed;
  }

  public int getNumSaves()
  {
    return numSaves;
  }

  public int getNumShotsAgainst()
  {
    return numShotsAgainst;
  }

  public int getNumShutouts()
  {
    return numShutouts;
  }

  public int getNumOvertimeLosses()
  {
    return numOvertimeLosses;
  }

  public int getNumShootoutLosses()
  {
    return numShootoutLosses;
  }

  public int getNumWins()
  {
    return numWins;
  }

  public double getSavePercentage()
  {
    return savePercentage;
  }
}
