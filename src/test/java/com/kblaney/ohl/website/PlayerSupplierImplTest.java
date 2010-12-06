package com.kblaney.ohl.website;

import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerStreaks;
import com.google.common.base.Function;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerSupplierImplTest
{
  private Node tableRowNode;
  private String playerName;
  private PlayerType playerType;
  private int sweaterNum = 29;
  private PlayerBio bio;
  private PlayerStats stats;
  private PlayerStreaks streaks;
  private PlayerSupplier supplier;

  @Before
  public void setUp() throws Exception
  {
    tableRowNode = mock(Node.class);

    playerName = "NAME";
    final Function<Node, String> toNameFunction = mock(Function.class);
    when(toNameFunction.apply(tableRowNode)).thenReturn(playerName);

    final String playerId = "ID";
    final Function<Node, String> toIdFunction = mock(Function.class);
    when(toIdFunction.apply(tableRowNode)).thenReturn(playerId);

    playerType = PlayerType.VETERAN;
    final Function<Node, PlayerType> toPlayerTypeFunction =
          mock(Function.class);
    when(toPlayerTypeFunction.apply(tableRowNode)).thenReturn(playerType);

    sweaterNum = 29;
    final Function<Node, Integer> toSweaterNumFunction = mock(Function.class);
    when(toSweaterNumFunction.apply(tableRowNode)).thenReturn(sweaterNum);

    final String position = "D";
    bio = new PlayerBio.Builder().setBirthYear("1990").
          setBirthplace("Kingston, ON").setHeight("5.11").setWeight("188").
          setPosition(position).build();
    final Function<String, PlayerBio> toBioFunction = mock(Function.class);
    when(toBioFunction.apply(playerId)).thenReturn(bio);

    stats = new PlayerStats.Builder().setNumGamesPlayed(11).build();
    final Function<Node, PlayerStats> toStatsFunction = mock(Function.class);
    when(toStatsFunction.apply(tableRowNode)).thenReturn(stats);

    streaks = new PlayerStreaks.Builder().setGoalStreak(1).setPointStreak(1).
          build();
    final PlayerStreaksSupplier streaksSupplier =
          mock(PlayerStreaksSupplier.class);
    when(streaksSupplier.get(playerId, position)).thenReturn(streaks);

    supplier = new PlayerSupplierImpl(toNameFunction, toIdFunction,
          toPlayerTypeFunction, toSweaterNumFunction, toStatsFunction,
          toBioFunction, streaksSupplier);
  }

  @Test
  public void get() throws Exception
  {
    final ProgressIndicator progressIndicator = mock(ProgressIndicator.class);
    final Player player = supplier.getPlayer(tableRowNode,progressIndicator);
    assertEquals(playerName, player.getName());
    assertEquals(playerType, player.getType());
    assertEquals(sweaterNum, player.getSweaterNum());
    assertEquals(bio, player.getBio());
    assertEquals(stats, player.getStats());
    assertEquals(streaks, player.getStreaks());
    verify(progressIndicator).setPlayerInProgress(playerName);
  }
}
