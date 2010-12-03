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
    this.name = ArgAssert.notNull(name, "name");
    this.playerType = ArgAssert.notNull(playerType, "playerType");
    this.sweaterNum = ArgAssert.notNegative(sweaterNum, "sweaterNum");
    this.stats = ArgAssert.notNull(stats, "stats");
    this.bio = ArgAssert.notNull(bio, "bio");
    this.streaks = ArgAssert.notNull(streaks, "streaks");
  }

  public String getName() { return name; }
  public PlayerType getPlayerType() { return playerType; }
  public int getSweaterNum() { return sweaterNum; }
  public PlayerStats getStats() { return stats; }
  public PlayerBio getBio() { return bio; }
  public PlayerStreaks getStreaks() { return streaks; }
}
