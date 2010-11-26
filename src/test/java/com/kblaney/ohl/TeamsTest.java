package com.kblaney.ohl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TeamsTest
{
  private String teamName;
  private Team team;
  private Teams emptyTeams;
  private Teams singletonTeams;

  @Before
  public void setUp()
  {
    teamName = "Belleville Bulls";
    team = getTeamWithName(teamName);
    emptyTeams = new Teams(Sets.<Team>newHashSet());
    singletonTeams = new Teams(Sets.newHashSet(team));
  }

  private Team getTeamWithName(final String name)
  {
    return new Team()
    {
      @Override
      public String getName()
      {
        return name;
      }
    };
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullTeams()
  {
    new Teams(null);
  }

  @Test
  public void getSortedTeamNames()
  {
    final Teams teams = new Teams(Sets.newHashSet(
          getTeamWithName("Belleville Bulls"),
          getTeamWithName("Barrie Colts"),
          getTeamWithName("Erie Otters")));
    assertEquals(Lists.newArrayList("Barrie Colts", "Belleville Bulls",
          "Erie Otters"), teams.getSortedTeamNames());
  }

  @Test
  public void getTeamWithName_noTeamWithName()
  {
    final String invalidTeamName = "NO_TEAM";
    try
    {
      singletonTeams.getTeamWithName(invalidTeamName);
      fail();
    }
    catch (final IllegalArgumentException e)
    {
      assertTrue(e.getMessage().contains(invalidTeamName));
    }
  }

  @Test
  public void getTeamWithName_teamWithName()
  {
    final Teams teams = new Teams(Sets.newHashSet(team,
          getTeamWithName("Barrie Colts"), getTeamWithName("Erie Otters")));
    assertEquals(team, teams.getTeamWithName(teamName));
  }

  @Test
  public void equals_null()
  {
    assertFalse(emptyTeams.equals(null));
  }

  @Test
  public void equals_wrongType()
  {
    assertFalse(emptyTeams.equals("A"));
  }

  @Test
  public void equals_sameInstance()
  {
    assertEquals(emptyTeams, emptyTeams);
  }

  @Test
  public void equals_equalInstance()
  {
    assertEquals(singletonTeams, new Teams(Sets.newHashSet(team)));
  }

  @Test
  public void equals_differentTeams()
  {
    assertFalse(emptyTeams.equals(singletonTeams));
  }

  @Test
  public void hashCode_equalInstance()
  {
    assertEquals(singletonTeams.hashCode(), singletonTeams.hashCode());
  }

  @Test
  public void testToString()
  {
    assertNotNull(new Teams(Sets.newHashSet(
          getTeamWithName("Belleville Bulls"),
          getTeamWithName("Barrie Colts"),
          getTeamWithName("Erie Otters"))).toString());
  }
}
