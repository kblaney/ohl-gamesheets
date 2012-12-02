package com.kblaney.ohl;

import static org.junit.Assert.assertEquals;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

public final class PlayerTest
{
  private String name;
  private PlayerType playerType;
  private Optional<Integer> sweaterNum;
  private PlayerStats stats;
  private PlayerBio bio;
  private PlayerStreaks streaks;
  private Player player;

  @Before
  public void setUp()
  {
    name = "PLAYER_NAME";
    playerType = PlayerType.VETERAN;
    sweaterNum = Optional.of(23);
    stats = new PlayerStats.Builder().build();
    bio = new PlayerBio.Builder().build();
    streaks = new PlayerStreaks.Builder().build();
    player = new Player(name, playerType, sweaterNum, stats, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullName()
  {
    new Player(null, playerType, sweaterNum, stats, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullPlayerType()
  {
    new Player(name, null, sweaterNum, stats, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_negativeSweaterNum()
  {
    new Player(name, playerType, Optional.of(-1), stats, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_sweaterNumOfZero()
  {
    new Player(name, playerType, Optional.of(0), stats, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullStats()
  {
    new Player(name, playerType, sweaterNum, null, bio, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullBio()
  {
    new Player(name, playerType, sweaterNum, stats, null, streaks);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullStreaks()
  {
    new Player(name, playerType, sweaterNum, stats, bio, null);
  }

  @Test
  public void getName()
  {
    assertEquals(name, player.getName());
  }

  @Test
  public void getPlayerType()
  {
    assertEquals(playerType, player.getType());
  }

  @Test
  public void getSweaterNum_knownSweaterNum()
  {
    assertEquals(sweaterNum, player.getSweaterNum());
  }

  @Test
  public void getSweaterNum_unknownSweaterNum()
  {
    final Optional<Integer> unknownSweaterNum = Optional.absent();
    player = new Player(name, playerType, unknownSweaterNum, stats, bio, streaks);
    assertEquals(unknownSweaterNum, player.getSweaterNum());
  }

  @Test
  public void getStats()
  {
    assertEquals(stats, player.getStats());
  }

  @Test
  public void getBio()
  {
    assertEquals(bio, player.getBio());
  }

  @Test
  public void getStreaks()
  {
    assertEquals(streaks, player.getStreaks());
  }
}
