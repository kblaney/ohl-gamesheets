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
    if (p1.getStats().getNumPoints() == p2.getStats().getNumPoints())
    {
      if (p1.getStats().getNumGoals() == p2.getStats().getNumGoals())
      {
        if (p1.getStats().getNumGamesPlayed() ==
              p2.getStats().getNumGamesPlayed())
        {
          return 0;
        }
        else if (p1.getStats().getNumGamesPlayed() <
              p2.getStats().getNumGamesPlayed())
        {
          return -1;
        }
        else
        {
          return 1;
        }
      }
      else if (p1.getStats().getNumGoals() < p2.getStats().getNumGoals())
      {
        return 1;
      }
      else
      {
        return -1;
      }
    }
    else if (p1.getStats().getNumPoints() < p2.getStats().getNumPoints())
    {
      return 1;
    }
    else
    {
      return -1;
    }
  }
}
