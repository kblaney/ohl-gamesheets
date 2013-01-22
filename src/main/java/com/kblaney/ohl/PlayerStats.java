package com.kblaney.ohl;

import com.kblaney.assertions.ArgAssert;

public final class PlayerStats
{
  public static final class Builder
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

    public Builder setNumGamesPlayed(final int numGamesPlayed)
    {
      this.numGamesPlayed = ArgAssert.assertNotNegative(numGamesPlayed, "numGamesPlayed");
      return this;
    }

    public Builder setNumGoals(final int numGoals)
    {
      this.numGoals = ArgAssert.assertNotNegative(numGoals, "numGoals");
      return this;
    }

    public Builder setNumAssists(final int numAssists)
    {
      this.numAssists = ArgAssert.assertNotNegative(numAssists, "numAssists");
      return this;
    }

    public Builder setNumPoints(final int numPoints)
    {
      this.numPoints = ArgAssert.assertNotNegative(numPoints, "numPoints");
      return this;
    }

    public Builder setPlusMinus(final int plusMinus)
    {
      this.plusMinus = plusMinus;
      return this;
    }

    public Builder setNumPenaltyMinutes(final int numPenaltyMinutes)
    {
      this.numPenaltyMinutes = ArgAssert.assertNotNegative(numPenaltyMinutes, "numPenaltyMinutes");
      return this;
    }

    public Builder setNumPowerPlayGoals(final int numPowerPlayGoals)
    {
      this.numPowerPlayGoals = ArgAssert.assertNotNegative(numPowerPlayGoals, "numPowerPlayGoals");
      return this;
    }

    public Builder setNumShorthandedGoals(final int numShorthandedGoals)
    {
      this.numShorthandedGoals = ArgAssert.assertNotNegative(numShorthandedGoals, "numShorthandedGoals");
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

  private PlayerStats()
  {
  }

  public int getNumGamesPlayed()
  {
    return numGamesPlayed;
  }

  public int getNumGoals()
  {
    return numGoals;
  }

  public int getNumAssists()
  {
    return numAssists;
  }

  public int getNumPoints()
  {
    return numPoints;
  }

  public int getPlusMinus()
  {
    return plusMinus;
  }

  public int getNumPenaltyMinutes()
  {
    return numPenaltyMinutes;
  }

  public int getNumPowerPlayGoals()
  {
    return numPowerPlayGoals;
  }

  public int getNumShorthandedGoals()
  {
    return numShorthandedGoals;
  }
}
