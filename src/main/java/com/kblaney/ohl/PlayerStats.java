package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * One player's statistics.
 */
public class PlayerStats
{
  public static class Builder
  {
    private int numGamesPlayed;
    private int numGoals;
    private int numAssists;
    private int numPoints;
    private int plusMinus;
    private int numPenaltyMinutes;
    private int numPowerPlayGoals;
    private int numShorthandedGoals;

    public PlayerStats build()
    {
      final PlayerStats playerStats = new PlayerStats();
      playerStats.numGamesPlayed = numGamesPlayed;
      playerStats.numGoals = numGoals;
      playerStats.numAssists = numAssists;
      playerStats.numPoints = numPoints;
      playerStats.numPenaltyMinutes = numPenaltyMinutes;
      playerStats.plusMinus = plusMinus;
      playerStats.numPowerPlayGoals = numPowerPlayGoals;
      playerStats.numShorthandedGoals = numShorthandedGoals;
      return playerStats;
    }

    /**
     * Sets the number of games played.
     *
     * @param numGamesPlayed the number of games played, which can't be
     * negative
     *
     * @return the build instance
     */
    public Builder setNumGamesPlayed(final int numGamesPlayed)
    {
      ArgAssert.notNegative(numGamesPlayed, "numGamesPlayed");

      this.numGamesPlayed = numGamesPlayed;
      return this;
    }

    /**
     * Sets the number of goals.
     *
     * @param numGoals the number of goals, which can't be negative
     *
     * @return the build instance
     */
    public Builder setNumGoals(final int numGoals)
    {
      ArgAssert.notNegative(numGoals, "numGoals");

      this.numGoals = numGoals;
      return this;
    }

    /**
     * Sets the number of assists.
     *
     * @param numAssists the number of assists, which can't be negative
     *
     * @return the build instance
     */
    public Builder setNumAssists(final int numAssists)
    {
      ArgAssert.notNegative(numAssists, "numAssists");

      this.numAssists = numAssists;
      return this;
    }

    /**
     * Sets the number of points.
     *
     * @param numPoints the number of points, which can't be negative
     *
     * @return the build instance
     */
    public Builder setNumPoints(final int numPoints)
    {
      ArgAssert.notNegative(numPoints, "numPoints");

      this.numPoints = numPoints;
      return this;
    }

    /**
     * Sets the plus minus.
     *
     * @param plusMinus the plus minus
     *
     * @return the build instance
     */
    public Builder setPlusMinus(final int plusMinus)
    {
      this.plusMinus = plusMinus;

      return this;
    }

    /**
     * Sets the number of penalty minutes.
     *
     * @param numPenaltyMinutes the number of penalty minutes, which can't be
     * negative
     *
     * @return the build instance
     */
    public Builder setNumPenaltyMinutes(final int numPenaltyMinutes)
    {
      ArgAssert.notNegative(numPenaltyMinutes, "numPenaltyMinutes");

      this.numPenaltyMinutes = numPenaltyMinutes;
      return this;
    }

    /**
     * Sets the number of power play goals.
     *
     * @param numPowerPlayGoals the number of power play goals, which can't be
     * negative
     *
     * @return the build instance
     */
    public Builder setNumPowerPlayGoals(final int numPowerPlayGoals)
    {
      ArgAssert.notNegative(numPowerPlayGoals, "numPowerPlayGoals");

      this.numPowerPlayGoals = numPowerPlayGoals;
      return this;
    }

    /**
     * Sets the number of shorthanded goals.
     *
     * @param numShorthandedGoals the number of shorthanded goals, which can't
     * be negative
     *
     * @return the build instance
     */
    public Builder setNumShorthandedGoals(final int numShorthandedGoals)
    {
      ArgAssert.notNegative(numShorthandedGoals, "numShorthandedGoals");

      this.numShorthandedGoals = numShorthandedGoals;
      return this;
    }
  }

  private int numGamesPlayed;
  private int numGoals;
  private int numAssists;
  private int numPoints;
  private int plusMinus;
  private int numPenaltyMinutes;
  private int numPowerPlayGoals;
  private int numShorthandedGoals;

  private PlayerStats() {}

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
   * Gets the number of goals.
   *
   * @return the number of goals
   */
  public int getNumGoals()
  {
    return numGoals;
  }

  /**
   * Gets the number of assists.
   *
   * @return the number of assists
   */
  public int getNumAssists()
  {
    return numAssists;
  }

  /**
   * Gets the number of points.
   *
   * @return the number of points
   */
  public int getNumPoints()
  {
    return numPoints;
  }

  /**
   * Gets the plus minus.
   *
   * @return the plus minus
   */
  public int getPlusMinus()
  {
    return plusMinus;
  }

  /**
   * Gets the number of penalty minutes.
   *
   * @return the number of penalty minutes
   */
  public int getNumPenaltyMinutes()
  {
    return numPenaltyMinutes;
  }

  /**
   * Gets the number of power play goals.
   *
   * @return the number of power play goals
   */
  public int getNumPowerPlayGoals()
  {
    return numPowerPlayGoals;
  }

  /**
   * Gets the number of shorthanded goals.
   *
   * @return the number of shorthanded goals
   */
  public int getNumShorthandedGoals()
  {
    return numShorthandedGoals;
  }
}
