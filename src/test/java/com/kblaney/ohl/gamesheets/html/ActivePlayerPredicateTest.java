package com.kblaney.ohl.gamesheets.html;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import org.junit.Before;
import org.junit.Test;

public final class ActivePlayerPredicateTest
{
  private String playerName;
  private Optional<Integer> sweaterNum;
  private PlayerBio bio;
  private PlayerStreaks streaks;
  private PlayerStats statsWithZeroGamesPlayed;
  private PlayerStats statsWithSomeGamesPlayed;
  private Predicate<Player> predicate;

  @Before
  public void setUp()
  {
    playerName = "WAYNE GRETZKY";
    sweaterNum = Optional.of(99);
    bio = new PlayerBio.Builder().build();
    streaks = new PlayerStreaks.Builder().build();
    statsWithZeroGamesPlayed = new PlayerStats.Builder().build();
    statsWithSomeGamesPlayed = new PlayerStats.Builder().setNumGamesPlayed(8).
          build();
    predicate = new ActivePlayerPredicate();
  }

  @Test
  public void apply_veteranWithSomeGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.VETERAN, sweaterNum,
          statsWithSomeGamesPlayed, bio, streaks);
    assertTrue(predicate.apply(player));
  }

  @Test
  public void apply_rookieWithSomeGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.ROOKIE, sweaterNum,
          statsWithSomeGamesPlayed, bio, streaks);
    assertTrue(predicate.apply(player));
  }

  @Test
  public void apply_inactivePlayerWithSomeGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.NOT_ACTIVE,
          sweaterNum, statsWithSomeGamesPlayed, bio, streaks);
    assertFalse(predicate.apply(player));
  }

  @Test
  public void apply_veteranWithZeroGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.VETERAN, sweaterNum,
          statsWithZeroGamesPlayed, bio, streaks);
    assertFalse(predicate.apply(player));
  }

  @Test
  public void apply_rookieWithZeroGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.ROOKIE, sweaterNum,
          statsWithZeroGamesPlayed, bio, streaks);
    assertFalse(predicate.apply(player));
  }

  @Test
  public void apply_inactivePlayerWithZeroGamesPlayed()
  {
    final Player player = new Player(playerName, PlayerType.NOT_ACTIVE,
          sweaterNum, statsWithZeroGamesPlayed, bio, streaks);
    assertFalse(predicate.apply(player));
  }
}
