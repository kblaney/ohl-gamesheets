package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * One player's streaks.
 *
 * <p>
 * Note that this class does not have a public constructor.  Rather, instances
 * are created using the PlayerStreaks.Builder class.
 * </p>
 */
public final class PlayerStreaks
{
  /**
   * Builds PlayerStreaks instances.
   */
  public static final class Builder
  {
    private int goalStreak;
    private int assistStreak;
    private int pointStreak;

    /**
     * Builds a PlayerStreaks instance.
     *
     * @return the PlayerStreaks instance
     */
    public PlayerStreaks build()
    {
      final PlayerStreaks playerStreaks = new PlayerStreaks();
      playerStreaks.goalStreak = goalStreak;
      playerStreaks.assistStreak = assistStreak;
      playerStreaks.pointStreak = pointStreak;
      return playerStreaks;
    }

    /**
     * Sets the goal streak.
     *
     * @param goalStreak the goal streak, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setGoalStreak(final int goalStreak)
    {
      ArgAssert.notNegative(goalStreak, "goalStreak");
      this.goalStreak = goalStreak;
      return this;
    }

    /**
     * Sets the assist streak.
     *
     * @param assistStreak the assist streak, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setAssistStreak(final int assistStreak)
    {
      ArgAssert.notNegative(assistStreak, "assistStreak");
      this.assistStreak = assistStreak;
      return this;
    }

    /**
     * Sets the point streak.
     *
     * @param pointStreak the point streak, which can't be negative
     *
     * @return the builder instance
     */
    public Builder setPointStreak(final int pointStreak)
    {
      ArgAssert.notNegative(pointStreak, "pointStreak");
      this.pointStreak = pointStreak;
      return this;
    }
  }

  private int goalStreak;
  private int assistStreak;
  private int pointStreak;

  private PlayerStreaks() {}

  /**
   * Gets the goal streak.
   *
   * @return the goal streak
   */
  public int getGoalStreak()
  {
    return goalStreak;
  }

  /**
   * Gets the assist streak.
   *
   * @return the assist streak
   */
  public int getAssistStreak()
  {
    return assistStreak;
  }

  /**
   * Gets the point streak.
   *
   * @return the point streak
   */
  public int getPointStreak()
  {
    return pointStreak;
  }
}
