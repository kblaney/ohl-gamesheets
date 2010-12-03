package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerStreaksTest
{
  private int goalStreak;
  private int assistStreak;
  private int pointStreak;
  private PlayerStreaks streaks;
  private PlayerStreaks equalStreaks;

  @Before
  public void setUp()
  {
    goalStreak = 2;
    assistStreak = 3;
    pointStreak = 4;
    streaks = new PlayerStreaks.Builder().setGoalStreak(goalStreak).
          setAssistStreak(assistStreak).setPointStreak(pointStreak).build();
    equalStreaks = new PlayerStreaks.Builder().setGoalStreak(goalStreak).
          setAssistStreak(assistStreak).setPointStreak(pointStreak).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeGoalStreak()
  {
    new PlayerStreaks.Builder().setGoalStreak(-7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeAssistStreak()
  {
    new PlayerStreaks.Builder().setAssistStreak(-2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativePointStreak()
  {
    new PlayerStreaks.Builder().setPointStreak(-1);
  }

  @Test
  public void getGoalStreak()
  {
    assertEquals(goalStreak, streaks.getGoalStreak());
  }

  @Test
  public void getAssistStreak()
  {
    assertEquals(assistStreak, streaks.getAssistStreak());
  }

  @Test
  public void getPointStreak()
  {
    assertEquals(pointStreak, streaks.getPointStreak());
  }

  @Test
  public void equals_null()
  {
    assertFalse(streaks.equals(null));
  }

  @Test
  public void equals_wrongType()
  {
    assertFalse(streaks.equals("A"));
  }

  @Test
  public void equals_unequalGoalStreak()
  {
    streaks = new PlayerStreaks.Builder().setGoalStreak(3).
          setAssistStreak(2).setPointStreak(4).build();
    final PlayerStreaks unequalStreaks = new PlayerStreaks.Builder().
          setGoalStreak(2).setAssistStreak(2).setPointStreak(4).build();
    assertFalse(streaks.equals(unequalStreaks));
  }

  @Test
  public void equals_unequalAssistStreak()
  {
    streaks = new PlayerStreaks.Builder().setGoalStreak(1).
          setAssistStreak(2).setPointStreak(3).build();
    final PlayerStreaks unequalStreaks = new PlayerStreaks.Builder().
          setGoalStreak(1).setAssistStreak(3).setPointStreak(3).build();
    assertFalse(streaks.equals(unequalStreaks));
  }

  @Test
  public void equals_unequalPointStreak()
  {
    streaks = new PlayerStreaks.Builder().setGoalStreak(0).
          setAssistStreak(4).setPointStreak(4).build();
    final PlayerStreaks unequalStreaks = new PlayerStreaks.Builder().
          setGoalStreak(0).setAssistStreak(4).setPointStreak(5).build();
    assertFalse(streaks.equals(unequalStreaks));
  }

  @Test
  public void equals_equalGoalAssistAndPointStreak()
  {
    assertEquals(streaks, equalStreaks);
  }

  @Test
  public void equals_sameInstance()
  {
    assertEquals(streaks, streaks);
  }

  @Test
  public void hashCode_equalGoalAssistAndPointStreak()
  {
    assertEquals(streaks.hashCode(), equalStreaks.hashCode());
  }

  @Test
  public void testToString()
  {
    streaks = new PlayerStreaks.Builder().setGoalStreak(1).
          setAssistStreak(2).setPointStreak(3).build();
    assertEquals("1G:2A:3P", streaks.toString());
  }
}
