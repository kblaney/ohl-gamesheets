package com.kblaney.ohl.website;

import com.kblaney.ohl.Team;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class NumberedTeamTest
{
  private String name;
  private int num;
  private NumberedTeam team;

  @Before
  public void setUp()
  {
    name = "NAME";
    num = 2;
    team = new NumberedTeam(name, num);
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
    assertTrue(team.equals(new NumberedTeam(name, num)));
  }

  @Test
  public void equals_differentName()
  {
    final String differentName = name + "A";
    assertFalse(team.equals(new NumberedTeam(differentName, num)));
  }

  @Test
  public void equals_equalNameDifferentType()
  {
    final Team differentType = new Team()
    {
      @Override
      public String getName()
      {
        return name;
      }
    };
    assertTrue(team.equals(differentType));
  }

  @Test
  public void equals_differentNum()
  {
    final int differentNum = num + 1;
    assertTrue(team.equals(new NumberedTeam(name, differentNum)));
  }

  @Test
  public void equals_nullNameSameNum()
  {
    assertTrue(new NumberedTeam(null, num).equals(new NumberedTeam(null, num)));
  }

  @Test
  public void testToString()
  {
    name = "A";
    num = 4;
    assertEquals("A:4", new NumberedTeam(name, num).toString());
  }
}
