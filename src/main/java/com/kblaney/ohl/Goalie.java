package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

public final class Goalie
{
  private final String name;
  private final GoalieStats stats;

  /**
   * Constructs a new instance of Goalie.
   *
   * @param name the goalie's name, which can't be null
   * @param stats the goalie's statistics, which can't be null
   */
  public Goalie(final String name, final GoalieStats stats)
  {
    ArgAssert.notNull(name, "name");
    ArgAssert.notNull(stats, "stats");

    this.name = name;
    this.stats = stats;
  }

  public String getName()
  {
    return name;
  }

  public GoalieStats getStats()
  {
    return stats;
  }
}
