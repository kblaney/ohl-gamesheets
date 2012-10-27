package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
  public static final class Builder
  {
    private int goalStreak;
    private int assistStreak;
    private int pointStreak;

    public PlayerStreaks build()
    {
      final PlayerStreaks playerStreaks = new PlayerStreaks();
      playerStreaks.goalStreak = goalStreak;
      playerStreaks.assistStreak = assistStreak;
      playerStreaks.pointStreak = pointStreak;
      return playerStreaks;
    }

    public Builder setGoalStreak(final int goalStreak)
    {
      this.goalStreak = ArgAssert.notNegative(goalStreak, "goalStreak");
      return this;
    }

    public Builder setAssistStreak(final int assistStreak)
    {
      this.assistStreak = ArgAssert.notNegative(assistStreak, "assistStreak");
      return this;
    }

    public Builder setPointStreak(final int pointStreak)
    {
      this.pointStreak = ArgAssert.notNegative(pointStreak, "pointStreak");
      return this;
    }
  }

  private int goalStreak;
  private int assistStreak;
  private int pointStreak;

  private PlayerStreaks() {}

  public int getGoalStreak() { return goalStreak; }
  public int getAssistStreak() { return assistStreak; }
  public int getPointStreak() { return pointStreak; }

  @Override
  public boolean equals(final Object thatObject)
  {
    if (thatObject == null)
    {
      return false;
    }

    if (thatObject.getClass().equals(getClass()))
    {
      final PlayerStreaks that = (PlayerStreaks) thatObject;
      return ((goalStreak == that.goalStreak) &&
            (assistStreak == that.assistStreak) &&
            (pointStreak == that.pointStreak));
    }
    else
    {
      return false;
    }
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(goalStreak).append(assistStreak).
          append(pointStreak).toHashCode();
  }

  @Override
  public String toString()
  {
    return goalStreak + "G:" + assistStreak + "A:" + pointStreak + "P";
  }
}
