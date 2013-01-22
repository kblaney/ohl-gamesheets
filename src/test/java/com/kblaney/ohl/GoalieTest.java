package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class GoalieTest
{
  private String name;
  private GoalieStats stats;
  private Goalie goalie;
  private Goalie equalGoalie;

  @Before
  public void setUp()
  {
    name = "GOALIE_NAME";
    stats = new GoalieStats.Builder().build();
    goalie = new Goalie(name, stats);
    equalGoalie = new Goalie(name, stats);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullName()
  {
    new Goalie(null, stats);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullStats()
  {
    new Goalie(name, null);
  }

  @Test
  public void getName()
  {
    assertEquals(name, goalie.getName());
  }

  @Test
  public void getStats()
  {
    assertEquals(stats, goalie.getStats());
  }

  @Test
  public void equals_null()
  {
    assertFalse(goalie.equals(null));
  }

  @Test
  public void equals_wrongType()
  {
    assertFalse(goalie.equals("A"));
  }

  @Test
  public void equals_unequalName()
  {
    final Goalie unequalGoalie = new Goalie("UNEQUAL_NAME", stats);
    assertFalse(goalie.equals(unequalGoalie));
  }

  @Test
  public void equals_unequalStats()
  {
    final GoalieStats unequalStats = new GoalieStats.Builder().setNumGamesPlayed(1).build();
    final Goalie unequalGoalie = new Goalie(name, unequalStats);
    assertFalse(goalie.equals(unequalGoalie));
  }

  @Test
  public void equals_equalInstance()
  {
    assertEquals(goalie, equalGoalie);
  }

  @Test
  public void hashCode_equalInstance()
  {
    assertEquals(goalie.hashCode(), equalGoalie.hashCode());
  }

  @Test
  public void testToString()
  {
    assertTrue(goalie.toString().startsWith(name));
  }
}
