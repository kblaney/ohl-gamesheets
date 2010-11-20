package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * A goalie's statistics.
 */
public class GoalieStats
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

  /**
   * Gets the goals against average.
   *
   * @return the goals against average
   */
  public double getGoalsAgainstAverage()
  {
    return goalsAgainstAverage;
  }

  /**
   * Gets the number of games played.
   *
   * @return the number of games played
   */
  public int getNumGamesPlayed()
  {
    return numGamesPlayed;
  }

  /**
   * Gets the number of goals against.
   *
   * @return the number of goals against
   */
  public int getNumGoalsAgainst()
  {
    return numGoalsAgainst;
  }

  /**
   * Gets the number of regulation losses.
   *
   * @return the number of regulation losses
   */
  public int getNumRegulationLosses()
  {
    return numRegulationLosses;
  }

  /**
   * Gets the number of minutes played.
   *
   * @return the number of minutes played
   */
  public int getNumMinutesPlayed()
  {
    return numMinutesPlayed;
  }

  /**
   * Gets the number of saves.
   *
   * @return the number of saves
   */
  public int getNumSaves()
  {
    return numSaves;
  }

  /**
   * Gets the number of shots against.
   *
   * @return the number of shots against
   */
  public int getNumShotsAgainst()
  {
    return numShotsAgainst;
  }

  /**
   * Gets the number of shutouts.
   *
   * @return the number of shutouts
   */
  public int getNumShutouts()
  {
    return numShutouts;
  }

  /**
   * Gets the number of overtime losses.
   *
   * @return the number of overtime losses
   */
  public int getNumOvertimeLosses()
  {
    return numOvertimeLosses;
  }

  /**
   * Gets the number of shootout losses.
   *
   * @return the number of shootout losses
   */
  public int getNumShootoutLosses()
  {
    return numShootoutLosses;
  }

  /**
   * Gets the number of wins.
   *
   * @return the number of wins
   */
  public int getNumWins()
  {
    return numWins;
  }

  /**
   * Gets the save percentage.
   *
   * @return the save percentage
   */
  public double getSavePercentage()
  {
    return savePercentage;
  }
}
