package com.kblaney.ohl.gamesheets;

import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerType;
import com.kblaney.ohl.Player;
import com.google.common.collect.Lists;
import com.google.common.base.Function;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerHtmlTableGetterTest
{
  private Function<List<Player>, String> tableGetter;

  @Before
  public void setUp()
  {
    tableGetter = new PlayerHtmlTableGetter();
  }

  @Test
  public void apply_zeroPlayers()
  {
    assertNumPlayersInTable(Collections.<Player>emptyList());
  }

  private void assertNumPlayersInTable(final List<Player> players)
  {
    final int numHeaderRows = 1;
    final int expectedNumTableRows = players.size() + numHeaderRows;
    final String table = tableGetter.apply(players);
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table, "<tr>"));
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table,
          "</tr>"));
  }

  @Test
  public void apply_onePlayer()
  {
    assertNumPlayersInTable(Lists.newArrayList(
          getVeteranPlayer("PLAYER_NAME")));
  }

  private Player getVeteranPlayer(final String name)
  {
    return getPlayer(name, PlayerType.VETERAN);
  }

  private Player getPlayer(final String name, final PlayerType playerType)
  {
    return new Player(name, playerType, /*sweaterNum=*/22,
          new PlayerStats.Builder().build(),
          new PlayerBio.Builder().setBirthYear("1994").
          setBirthplace("Belleville, ON").setHeight("6.02").setPosition("LW").
          setWeight("195").build(),
          new PlayerStreaks.Builder().build());
  }

  @Test
  public void apply_oneVeteranAndOneRookie()
  {
    final Player veteran = getVeteranPlayer("VETERAN");
    final Player rookie = getPlayer("ROOKIE", PlayerType.ROOKIE);
    assertNumPlayersInTable(Lists.newArrayList(veteran, rookie));
  }
}
