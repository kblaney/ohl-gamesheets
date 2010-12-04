package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Goalie
{
  private final String name;
  private final GoalieStats stats;

  public Goalie(final String name, final GoalieStats stats)
  {
    this.name = ArgAssert.notNull(name, "name");
    this.stats = ArgAssert.notNull(stats, "stats");
  }

  public String getName() { return name; }
  public GoalieStats getStats() { return stats; }

  @Override
  public boolean equals(final Object thatObject)
  {
    if (thatObject == null)
    {
      return false;
    }
    if (thatObject.getClass().equals(getClass()))
    {
      final Goalie that = (Goalie) thatObject;
      return ((ObjectUtils.equals(name, that.name)) &&
            (ObjectUtils.equals(stats, that.stats)));
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(name).append(stats).toHashCode();
  }

  @Override
  public String toString()
  {
    return name + ":" + stats;
  }
}
