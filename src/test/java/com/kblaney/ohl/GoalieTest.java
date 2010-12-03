package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class GoalieTest
{
  private String name;
  private GoalieStats stats;
  private Goalie goalie;

  @Before
  public void setUp()
  {
    name = "GOALIE_NAME";
    stats = new GoalieStats.Builder().build();
    goalie = new Goalie(name, stats);
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
}
