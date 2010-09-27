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
        if (p1.getStats().getNumGamesPlayed()
              == p2.getStats().getNumGamesPlayed())
        {
          // The players have the same number of points, goals, assists,
          // and games played, so we consider them equal.
          //
          return 0;
        }
        else if (p1.getStats().getNumGamesPlayed() < p2.getStats().
              getNumGamesPlayed())
        {
          // The players have the same number of points, goals, and
          // assists, but player 1 has fewer games played so we consider
          // player 1 less than player 2 (so that player 1 appears
          // earlier in an ordered list).
          //
          return -1;
        }
        else
        {
          // The players have the same number of points, goals, and
          // assists, but player 2 has fewer games played so we consider
          // player 2 less than player 1.
          //
          return 1;
        }
      }
      else if (p1.getStats().getNumGoals() < p2.getStats().getNumGoals())
      {
        // The players have the same number of points, but player 1 has
        // fewer goals so we consider player 2 less than player 1.
        //
        return 1;
      }
      else
      {
        // The players have the same number of points, but player 2 has
        // fewer goals so we consider player 1 less than player 2.
        //
        return -1;
      }
    }
    else if (p1.getStats().getNumPoints() < p2.getStats().getNumPoints())
    {
      // Player 1 has fewer points so we consider player 2 less than
      // player 1.
      //
      return 1;
    }
    else
    {
      // Player 2 has fewer points so we consider player 1 less than
      // player 2.
      //
      return -1;
    }
  }
}
