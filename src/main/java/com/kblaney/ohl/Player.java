package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

public final class Player
{
  private final String name;
  private final PlayerType playerType;
  private final int sweaterNum;
  private final PlayerStats stats;
  private final PlayerBio bio;
  private final PlayerStreaks streaks;

  public Player(final String name, final PlayerType playerType,
        final int sweaterNum, final PlayerStats stats, final PlayerBio bio,
        final PlayerStreaks streaks)
  {
    ArgAssert.notNull(name, "name");
    ArgAssert.notNull(playerType, "playerType");
    ArgAssert.notNegative(sweaterNum, "sweaterNum");
    ArgAssert.notNull(stats, "stats");
    ArgAssert.notNull(bio, "bio");
    ArgAssert.notNull(streaks, "streaks");

    this.name = name;
    this.playerType = playerType;
    this.sweaterNum = sweaterNum;
    this.stats = stats;
    this.bio = bio;
    this.streaks = streaks;
  }

  public String getName()
  {
    return name;
  }

  public PlayerType getPlayerType()
  {
    return playerType;
  }

  public int getSweaterNum()
  {
    return sweaterNum;
  }

  public PlayerStats getStats()
  {
    return stats;
  }

  public PlayerBio getBio()
  {
    return bio;
  }

  public PlayerStreaks getStreaks()
  {
    return streaks;
  }
}
