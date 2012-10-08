package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import java.io.IOException;
import org.w3c.dom.Node;

final class PlayerSupplierImpl implements PlayerSupplier
{
  private final Function<Node, String> tableRowNodeToNameFunction;
  private final Function<Node, String> tableRowNodeToIdFunction;
  private final Function<Node, PlayerType> tableRowNodeToPlayerTypeFunction;
  private final Function<Node, Integer> tableRowNodeToSweaterNumFunction;
  private final Function<Node, PlayerStats> tableRowNodeToStatsFunction;
  private final Function<String, PlayerBio> playerIdToBioFunction;
  private final PlayerStreaksSupplier streaksSupplier;

  @Inject
  public PlayerSupplierImpl(
        @Named("ToNameFunction") final Function<Node, String> tableRowNodeToNameFunction,
        @Named("ToIdFunction") final Function<Node, String> tableRowNodeToIdFunction,
        final Function<Node, PlayerType> tableRowNodeToPlayerTypeFunction,
        final Function<Node, Integer> tableRowNodeToSweaterNumFunction,
        final Function<Node, PlayerStats> tableRowNodeToStatsFunction,
        final Function<String, PlayerBio> playerIdToBioFunction,
        final PlayerStreaksSupplier streaksSupplier)
  {
    this.tableRowNodeToNameFunction = tableRowNodeToNameFunction;
    this.tableRowNodeToIdFunction = tableRowNodeToIdFunction;
    this.tableRowNodeToPlayerTypeFunction = tableRowNodeToPlayerTypeFunction;
    this.tableRowNodeToSweaterNumFunction = tableRowNodeToSweaterNumFunction;
    this.tableRowNodeToStatsFunction = tableRowNodeToStatsFunction;
    this.playerIdToBioFunction = playerIdToBioFunction;
    this.streaksSupplier = streaksSupplier;
  }

  public Player getPlayer(final Node tableRowNode,
        final ProgressIndicator progressIndicator) throws IOException
  {
    final String playerId = getPlayerId(tableRowNode);
    final String playerName = getPlayerName(tableRowNode);

    progressIndicator.setPlayerInProgress(playerName);

    final PlayerType playerType = getPlayerType(tableRowNode);
    final int sweaterNum = getSweaterNum(tableRowNode);
    final PlayerStats playerStats = getPlayerStats(tableRowNode);
    final PlayerBio playerBio = getPlayerBio(playerId);
    final PlayerStreaks playerStreaks = getPlayerStreaks(
          playerBio.getGameByGameFilePath(), playerBio.getPosition());
    return new Player(playerName, playerType, sweaterNum, playerStats,
          playerBio, playerStreaks);
  }

  private String getPlayerId(final Node tableRowNode)
  {
    return tableRowNodeToIdFunction.apply(tableRowNode);
  }

  private String getPlayerName(final Node tableRowNode)
  {
    return tableRowNodeToNameFunction.apply(tableRowNode);
  }

  private PlayerType getPlayerType(final Node tableRowNode)
  {
    return tableRowNodeToPlayerTypeFunction.apply(tableRowNode);
  }

  private int getSweaterNum(final Node tableRowNode)
  {
    return tableRowNodeToSweaterNumFunction.apply(tableRowNode);
  }

  private PlayerStats getPlayerStats(final Node tableRowNode)
  {
    return tableRowNodeToStatsFunction.apply(tableRowNode);
  }

  private PlayerBio getPlayerBio(final String playerId)
  {
    return playerIdToBioFunction.apply(playerId);
  }

  private PlayerStreaks getPlayerStreaks(final String gameByGameFilePath,
        final String position)
  {
    return streaksSupplier.get(gameByGameFilePath, position);
  }
}
