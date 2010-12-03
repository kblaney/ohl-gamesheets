package com.kblaney.ohl;

import java.util.Comparator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerPointsComparatorTest
{
  private String name;
  private PlayerType type;
  private int sweaterNum;
  private PlayerBio bio;
  private PlayerStreaks streaks;
  private Comparator<Player> comparator;

  @Before
  public void setUp()
  {
    name = "PLAYER_NAME";
    type = PlayerType.ROOKIE;
    sweaterNum = 19;
    bio = new PlayerBio.Builder().setBirthYear("1990").
          setBirthplace("Belleville, ON").setHeight("6.02").setWeight("211").
          build();
    streaks = new PlayerStreaks.Builder().build();
    comparator = new PlayerPointsComparator();
  }

  @Test
  public void compare_equalPointsGoalsGamesPlayed()
  {
    final PlayerStats stats = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(3).setNumAssists(4).setNumPoints(7).
          build();
    final Player p1 = new Player(name, type, sweaterNum, stats, bio, streaks);
    final Player p2 = new Player(name, type, sweaterNum, stats, bio, streaks);
    assertEquals(0, comparator.compare(p1, p2));
  }

  @Test
  public void compare_equalPointsGoalsUnequalGamesPlayed()
  {
    final PlayerStats s1 = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(3).setNumAssists(4).setNumPoints(7).
          build();
    final Player p1 = new Player(name, type, sweaterNum, s1, bio, streaks);
    final PlayerStats s2 = new PlayerStats.Builder().
          setNumGamesPlayed(9).setNumGoals(3).setNumAssists(4).setNumPoints(7).
          build();
    final Player p2 = new Player(name, type, sweaterNum, s2, bio, streaks);
    assertTrue(comparator.compare(p1, p2) < 0);
    assertTrue(comparator.compare(p2, p1) > 0);
  }

  @Test
  public void compare_equalPointsUnequalGoals()
  {
    final PlayerStats s1 = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(2).setNumAssists(5).setNumPoints(7).
          build();
    final Player p1 = new Player(name, type, sweaterNum, s1, bio, streaks);
    final PlayerStats s2 = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(1).setNumAssists(6).setNumPoints(7).
          build();
    final Player p2 = new Player(name, type, sweaterNum, s2, bio, streaks);
    assertTrue(comparator.compare(p1, p2) < 0);
    assertTrue(comparator.compare(p2, p1) > 0);
  }

  @Test
  public void compare_unequalPoints()
  {
    final PlayerStats s1 = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(5).setNumAssists(4).setNumPoints(9).
          build();
    final Player p1 = new Player(name, type, sweaterNum, s1, bio, streaks);
    final PlayerStats s2 = new PlayerStats.Builder().
          setNumGamesPlayed(8).setNumGoals(2).setNumAssists(3).setNumPoints(5).
          build();
    final Player p2 = new Player(name, type, sweaterNum, s2, bio, streaks);
    assertTrue(comparator.compare(p1, p2) < 0);
    assertTrue(comparator.compare(p2, p1) > 0);
  }
}
