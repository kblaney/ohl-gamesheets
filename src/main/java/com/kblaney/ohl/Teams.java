package com.kblaney.ohl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kblaney.commons.lang.ArgAssert;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Teams
{
  private final Set<Team> teams;

  public Teams(final Set<? extends Team> teams)
  {
    this.teams = Sets.newHashSet(ArgAssert.notNull(teams, "teams"));
  }

  public List<String> getSortedTeamNames()
  {
    final List<String> teamNames = Lists.newArrayList();
    for (final Team team : teams)
    {
      teamNames.add(team.getName());
    }
    Collections.sort(teamNames);
    return teamNames;
  }

  public Team getTeamWithName(final String teamName)
  {
    for (final Team team : teams)
    {
      if (team.getName().equals(teamName))
      {
        return team;
      }
    }
    throw new IllegalArgumentException("No team with name: " + teamName);
  }

  @Override
  public boolean equals(final Object thatObject)
  {
    if (thatObject == null)
    {
      return false;
    }
    if (thatObject.getClass().equals(getClass()))
    {
      final Teams that = (Teams) thatObject;
      return teams.equals(that.teams);
    }
    else
    {
      return false;
    }
  }

  @Override
  public int hashCode()
  {
    return teams.hashCode();
  }

  @Override
  public String toString()
  {
    return Arrays.toString(teams.toArray(new Team[teams.size()]));
  }
}
