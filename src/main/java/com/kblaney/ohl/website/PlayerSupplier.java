package com.kblaney.ohl.website;

import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import org.w3c.dom.Node;

final class PlayerSupplier
{
  public Player getPlayer(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    if ((tableRowNode.getChildNodes() != null) &&
          (tableRowNode.getChildNodes().getLength() == 14))
    {
      final String playerId = getPlayerId(tableRowNode);
      final String playerName = getPlayerName(tableRowNode);

      progressIndicator.setPlayerInProgress(playerName);

      final PlayerType playerType = getPlayerType(tableRowNode);
      final int sweaterNum = getSweaterNum(tableRowNode);
      final PlayerStats playerStats = getPlayerStats(tableRowNode);
      final PlayerBio playerBio = getPlayerBio(playerId);
      final PlayerStreaks playerStreaks = getPlayerStreaks(playerId,
            playerBio.getPosition());

      return new Player(playerName, playerType, sweaterNum, playerStats,
            playerBio, playerStreaks);
    }
    else
    {
      throw new IllegalArgumentException(
            "Wrong # child nodes in player table row node: " + tableRowNode);
    }
  }
  private String getPlayerId(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToIdFunction().apply(tableRowNode);
  }

  private String getPlayerName(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToNameFunction().apply(tableRowNode);
  }

  private PlayerType getPlayerType(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToPlayerTypeFunction().apply(tableRowNode);
  }

  private int getSweaterNum(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToSweaterNumFunction().apply(tableRowNode);
  }

  private PlayerStats getPlayerStats(final Node tableRowNode)
  {
    return new PlayerTableRowNodeToStatsFunction().apply(tableRowNode);
  }

  private PlayerBio getPlayerBio(final String playerId) throws IOException
  {
    return new PlayerIdToBioFunction().apply(playerId);
  }

  private PlayerStreaks getPlayerStreaks(final String playerId,
        final String position)
  {
    return new PlayerStreaksSupplier().get(playerId, position);
  }
}
