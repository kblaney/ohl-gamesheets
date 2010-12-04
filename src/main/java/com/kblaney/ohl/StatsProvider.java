package com.kblaney.ohl;

import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import java.util.List;

/**
 * Provides statistics about the Ontario Hockey League (OHL).
 */
public interface StatsProvider
{
  Teams getTeams() throws IOException;

  /**
   * Gets the players on a specified team.  This method returns a list sorted
   * by points in descending order, then goals in descending order, then games
   * played in ascending order.
   *
   * @param team the team, which can't be null
   * @param progressIndicator the progress indicator, which can't be null
   *
   * @return a list of the players on the specified team
   *
   * @throws IOException if can't get the players
   */
  List<Player> getPlayers(Team team, ProgressIndicator progressIndicator)
          throws IOException;

  /**
   * Gets the goalies on a specified team.
   *
   * @param team the team, which can't be null
   * @param progressIndicator the progress indicator, which can't be null
   *
   * @return a list of the goalies on the specified team
   *
   * @throws IOException if can't get the goalies
   */
  List<Goalie> getGoalies(Team team, ProgressIndicator progressIndicator)
        throws IOException;
}
