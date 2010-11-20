package com.kblaney.ohl.gamesheets;

import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.Player;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Provides statistics about the Ontario Hockey League (OHL).
 */
public interface StatsProvider
{
  /**
   * Gets the names of all teams in the league.
   *
   * @return a set of the names of all teams in the league.
   *
   * @throws IOException if can't get the team names
   */
  Set<String> getTeamNames() throws IOException;

  /**
   * Gets the players on a specified team.  This method returns a list sorted
   * by points in descending order, then goals in descending order, then games
   * played in ascending order.
   *
   * @param teamName the team name, which can't be null and must be a valid
   * OHL team name
   * @param progressIndicator the progress indicator, which can't be null
   *
   * @return a list of the players on the specified team
   *
   * @throws IOException if can't get the players
   */
  List<Player> getPlayers(String teamName,
        ProgressIndicator progressIndicator) throws IOException;

  /**
   * Gets the goalies on a specified team.
   *
   * @param teamName the team name, which can't be null and must be a valid
   * OHL team name
   *
   * @return a list of the goalies on the specified team
   *
   * @throws IOException if can't get the goalies
   */
  List<Goalie> getGoalies(String teamName) throws IOException;
}
