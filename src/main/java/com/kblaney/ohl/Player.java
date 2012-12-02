package com.kblaney.ohl;

import com.google.common.base.Optional;
import com.kblaney.commons.lang.ArgAssert;

public final class Player
{
  private final String name;
  private final PlayerType type;
  private final Optional<Integer> sweaterNum;
  private final PlayerStats stats;
  private final PlayerBio bio;
  private final PlayerStreaks streaks;

  public Player(final String name, final PlayerType type,
        final Optional<Integer> sweaterNum, final PlayerStats stats, final PlayerBio bio,
        final PlayerStreaks streaks)
  {
    this.name = ArgAssert.notNull(name, "name");
    this.type = ArgAssert.notNull(type, "playerType");
    this.sweaterNum = sweaterNum;
    this.stats = ArgAssert.notNull(stats, "stats");
    this.bio = ArgAssert.notNull(bio, "bio");
    this.streaks = ArgAssert.notNull(streaks, "streaks");

    if (sweaterNum.isPresent())
    {
      final int minSweaterNum = 1;
      ArgAssert.notTooSmall(sweaterNum.get(), minSweaterNum, "Sweater number must be positive: " + sweaterNum);
    }
  }

  public String getName() { return name; }
  public PlayerType getType() { return type; }
  public Optional<Integer> getSweaterNum() { return sweaterNum; }
  public PlayerStats getStats() { return stats; }
  public PlayerBio getBio() { return bio; }
  public PlayerStreaks getStreaks() { return streaks; }
}
