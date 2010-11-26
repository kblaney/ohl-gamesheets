package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TeamTest
{
  private String name;
  private int num;
  private Team team;

  @Before
  public void setUp()
  {
    name = "NAME";
    num = 2;
    team = new Team(name, num);
  }

  @Test
  public void getName()
  {
    assertEquals(name, team.getName());
  }

  @Test
  public void getNum()
  {
    assertEquals(num, team.getNum());
  }

  @Test
  public void equals_null()
  {
    assertFalse(team.equals(null));
  }

  @Test
  public void equals_notTeam()
  {
    assertFalse(team.equals("A"));
  }

  @Test
  public void equals_equalInstance()
  {
    assertTrue(team.equals(new Team(name, num)));
  }

  @Test
  public void equals_differentName()
  {
    final String differentName = name + "A";
    assertFalse(team.equals(new Team(differentName, num)));
  }

  @Test
  public void equals_differentNum()
  {
    final int differentNum = num + 1;
    assertFalse(team.equals(new Team(name, differentNum)));
  }

  @Test
  public void equals_nullNameSameNum()
  {
    assertTrue(new Team(null, num).equals(new Team(null, num)));
  }

  @Test
  public void testToString()
  {
    name = "A";
    num = 4;
    assertEquals("A:4", new Team(name, num).toString());
  }
}
