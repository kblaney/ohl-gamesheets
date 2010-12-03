package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

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
}
