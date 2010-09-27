package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * A player.
 */
public final class Player
{
  private final String name;
  private final PlayerType playerType;
  private final int sweaterNum;
  private final PlayerStats stats;
  private final PlayerBio bio;
  private final PlayerStreaks streaks;

  /**
   * Constructs a new instance of Player.
   *
   * @param name the player's name, which can't be null
   * @param playerType the player's type, which can't be null
   * @param sweaterNum, the player's sweater number, which can't be negative
   * @param stats, the player's statistics, which can't be null
   * @param bio the player's biographical information, which can't be null
   * @param streaks the player's streaks, which can't be null
   */
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

  /**
   * Gets the player's name.
   *
   * @return the player's name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets the player's type.
   *
   * @return the player's type
   */
  public PlayerType getPlayerType()
  {
    return playerType;
  }

  /**
   * Gets the player's sweater number.
   *
   * @return the player's sweater number
   */
  public int getSweaterNum()
  {
    return sweaterNum;
  }

  /**
   * Gets the player's statistics.
   *
   * @return the player's statistics
   */
  public PlayerStats getStats()
  {
    return stats;
  }

  /**
   * Gets the player's biographical information.
   *
   * @return the player's biographical information
   */
  public PlayerBio getBio()
  {
    return bio;
  }

  /**
   * Gets the player's streaks.
   *
   * @return the player's streaks
   */
  public PlayerStreaks getStreaks()
  {
    return streaks;
  }
}
