package com.kblaney.ohl.gamesheets.html;

import static org.junit.Assert.*;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.kblaney.ohl.Player;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerStreaks;
import com.kblaney.ohl.PlayerType;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

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
    final String table = tableGetter.apply(Collections.<Player> emptyList());
    assertNumPlayersInTable(table, 0);
  }

  private void assertNumPlayersInTable(final String table, final int expectedNumPlayers)
  {
    final int numHeaderRows = 1;
    final int expectedNumTableRows = expectedNumPlayers + numHeaderRows;
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table, "<tr>"));
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table, "</tr>"));
  }

  @Test
  public void apply_oneVeteranPlayerWithKnownSweaterNum()
  {
    final Optional<Integer> sweaterNum = Optional.of(19);
    final Player player = getPlayer(PlayerType.VETERAN, sweaterNum);
    final String table = tableGetter.apply(Lists.newArrayList(player));

    assertOnePlayerInTable(table);
    assertZeroRookiesInTable(table);
    // We only expect one empty cell (in the rookie column).
    assertOneEmptyTableCell(table);
  }

  private Player getPlayer(final PlayerType playerType, final Optional<Integer> sweaterNum)
  {
    return new Player("Wayne Gretzky", playerType, sweaterNum, new PlayerStats.Builder().build(),
          new PlayerBio.Builder().setBirthYear("1994").setHometown("Belleville, ON").setHeight("6.02")
                .setPosition("LW").setWeight("195").build(), new PlayerStreaks.Builder().build());
  }

  private void assertOnePlayerInTable(final String table)
  {
    final int expectedNumPlayers = 1;
    assertNumPlayersInTable(table, expectedNumPlayers);
  }

  private void assertOneEmptyTableCell(final String table)
  {
    final int expectedNumEmptyTableCells = 1;
    assertNumEmptyTableCells(table, expectedNumEmptyTableCells);
  }

  private void assertZeroRookiesInTable(final String table)
  {
    assertFalse(table.contains(PlayerHtmlTableGetter.ROOKIE_TABLE_CELL));
  }

  private void assertNumEmptyTableCells(final String table, final int expectedNumEmptyTableCells)
  {
    assertEquals(expectedNumEmptyTableCells, StringUtils.countMatches(table, PlayerHtmlTableGetter.EMPTY_TABLE_CELL));
  }

  @Test
  public void apply_oneVeteranPlayerWithUnknownSweaterNum()
  {
    final Optional<Integer> sweaterNum = Optional.absent();
    final Player player = getPlayer(PlayerType.VETERAN, sweaterNum);
    final String table = tableGetter.apply(Lists.newArrayList(player));

    assertOnePlayerInTable(table);
    assertZeroRookiesInTable(table);
    // We expect two empty cells (in the rookie and sweater number columns).
    assertTwoEmptyTableCells(table);
  }

  private void assertTwoEmptyTableCells(final String table)
  {
    final int expectedNumEmptyTableCells = 2;
    assertNumEmptyTableCells(table, expectedNumEmptyTableCells);
  }

  @Test
  public void apply_oneRookieWithKnownSweaterNum()
  {
    final Optional<Integer> sweaterNum = Optional.of(12);
    final Player player = getPlayer(PlayerType.ROOKIE, sweaterNum);
    final String table = tableGetter.apply(Lists.newArrayList(player));

    assertOnePlayerInTable(table);
    assertOneRookieInTable(table);
    assertZeroEmptyTableCells(table);
  }

  private void assertZeroEmptyTableCells(final String table)
  {
    final int expectedNumEmptyTableCells = 0;
    assertNumEmptyTableCells(table, expectedNumEmptyTableCells);
  }

  @Test
  public void apply_oneRookieWithUnknownSweaterNum()
  {
    final Optional<Integer> sweaterNum = Optional.absent();
    final Player player = getPlayer(PlayerType.ROOKIE, sweaterNum);
    final String table = tableGetter.apply(Lists.newArrayList(player));

    assertOnePlayerInTable(table);
    assertOneRookieInTable(table);
    // We expect one empty cell (in the sweater number columns).
    assertOneEmptyTableCell(table);
  }

  private void assertOneRookieInTable(final String table)
  {
    final int expectedNumRookieTableCells = 1;
    assertEquals(expectedNumRookieTableCells, StringUtils.countMatches(table, PlayerHtmlTableGetter.ROOKIE_TABLE_CELL));
  }

  @Test
  public void apply_fourPlayers()
  {
    final List<Player> players = Lists.newArrayList(getPlayer(PlayerType.ROOKIE, Optional.of(9)),
          getPlayer(PlayerType.ROOKIE, Optional.<Integer> absent()), getPlayer(PlayerType.VETERAN, Optional.of(10)),
          getPlayer(PlayerType.VETERAN, Optional.<Integer> absent()));
    final String table = tableGetter.apply(players);

    final int expectedNumPlayers = 4;
    assertNumPlayersInTable(table, expectedNumPlayers);
  }
}
