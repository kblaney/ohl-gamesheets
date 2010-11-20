package com.kblaney.ohl.gamesheets;

/**
 * An indicator of progress.
 */
public interface ProgressIndicator
{
  /**
   * Sets the player in progress.
   *
   * @param playerName the name of the player in progress, which can't be null
   */
  void setPlayerInProgress(String playerName);
}
