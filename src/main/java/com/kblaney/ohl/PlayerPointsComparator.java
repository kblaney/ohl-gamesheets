package com.kblaney.ohl;

import java.util.Comparator;

/**
 * A comparator that compares players based on their number of points, goals,
 * and games played.
 *
 * <p>
 * More specifically, a player with more points is considered less than a
 * player with fewer points.  If players have the same number of points, the
 * one with more goals is considered lesser.  If players have the same number
 * of points and goals, the one with fewer games played is considered lesser.
 * </p>
 */
public final class PlayerPointsComparator implements Comparator<Player>
{
  /** {@inheritDoc} */
  public int compare(final Player p1, final Player p2)
  {
    final PlayerStats s1 = p1.getStats();
    final PlayerStats s2 = p2.getStats();
    if (s1.getNumPoints() == s2.getNumPoints())
    {
      if (s1.getNumGoals() == s2.getNumGoals())
      {
        if (s1.getNumGamesPlayed() == s2.getNumGamesPlayed())
        {
          return 0;
        }
        else if (s1.getNumGamesPlayed() < s2.getNumGamesPlayed())
        {
          return -1;
        }
        else
        {
          return 1;
        }
      }
      else if (s1.getNumGoals() < s2.getNumGoals())
      {
        return 1;
      }
      else
      {
        return -1;
      }
    }
    else if (s1.getNumPoints() < s2.getNumPoints())
    {
      return 1;
    }
    else
    {
      return -1;
    }
  }
}
