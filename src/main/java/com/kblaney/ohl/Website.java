package com.kblaney.ohl;

import java.util.Set;

/**
 * The OHL website.
 */
public interface Website
{
   /**
    * Gets a set of all OHL teams.
    *
    * @return the teams
    */
   Set<Team> getTeams();
}
