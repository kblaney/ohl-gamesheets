package com.kblaney.ohl.website;

import org.apache.commons.lang.StringUtils;
import com.google.common.base.Function;
import org.w3c.dom.NodeList;
import com.kblaney.ohl.PlayerStreaks;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class PlayerStreaksSupplierTest
{
  private String playerId;
  private String position;
  private PlayerStreaks notOnAStreak;
  private Function<String, NodeList> toGameRowNodeListFunction;
  private PlayerStreaksSupplierImpl streaksSupplier;

  @Before
  public void setUp()
  {
    playerId = "1234";
    position = "LW";
    notOnAStreak = new PlayerStreaks.Builder().build();
    toGameRowNodeListFunction = mock(Function.class);
    streaksSupplier = new PlayerStreaksSupplierImpl(toGameRowNodeListFunction);
  }

  @Test
  public void apply_goalie()
  {
    assertEquals(notOnAStreak, streaksSupplier.get(playerId, "G"));
  }

  @Test
  public void apply_noGamesPlayed()
  {
    final NodeList nodeList = mock(NodeList.class);
    when(nodeList.getLength()).thenReturn(0);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(notOnAStreak, streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_notEnoughTableCellsInOnlyGameRow() throws Exception
  {
    final NodeList nodeList = new XmlToElementFunction().apply(
          "<tbody><tr><td/></tr></tbody>").getChildNodes();
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(notOnAStreak, streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_noPointsInOnlyGameEmptyTableCells() throws Exception
  {
    final NodeList nodeList = new XmlToElementFunction().apply(
          "<tbody><tr><td/><td/><td/><td/><td/><td/><td/></tr></tbody>").
          getChildNodes();
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(notOnAStreak, streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_noPointsInOnlyGame() throws Exception
  {
    final NodeList nodeList = getNodeListForOneGame(/*numGoalsInGame=*/0,
          /*numAssistsInGame=*/0);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(notOnAStreak, streaksSupplier.get(playerId, position));
  }

  private NodeList getNodeListForOneGame(final int numGoalsInGame,
        final int numAssistsInGame) throws Exception
  {
    return getNodeList(getGameTableRow(numGoalsInGame, numAssistsInGame));
  }

  private NodeList getNodeList(final String... gameTableRows) throws Exception
  {
    final String gameTableRowsXml = "<tbody>" +
          StringUtils.join(gameTableRows) + "</tbody>";
    return new XmlToElementFunction().apply(gameTableRowsXml).getChildNodes();
  }

  private String getGameTableRow(final int numGoalsInGame,
        final int numAssistsInGame)
  {
    final String numGoals = Integer.toString(numGoalsInGame);
    final String numAssists = Integer.toString(numAssistsInGame);
    final String numPoints = Integer.toString(
          numGoalsInGame + numAssistsInGame);
    return "<tr><td/><td/><td/><td/>" +
          "<td>" + numGoals + "</td>" +
          "<td>" + numAssists + "</td>" +
          "<td>" + numPoints + "</td></tr>";
  }

  @Test
  public void apply_oneGoalInOnlyGame() throws Exception
  {
    final NodeList nodeList = getNodeListForOneGame(/*numGoalsInGame=*/1,
          /*numAssistsInGame=*/0);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(new PlayerStreaks.Builder().setGoalStreak(1).setPointStreak(1).
          build(), streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_goalAndAssistInOnlyGame() throws Exception
  {
    final NodeList nodeList = getNodeListForOneGame(/*numGoalsInGame=*/1,
          /*numAssistsInGame=*/1);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(new PlayerStreaks.Builder().setGoalStreak(1).
          setAssistStreak(1).setPointStreak(1).build(),
          streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_twoGameGoalStreakOneGameAssistStreak() throws Exception
  {
    final String thirdMostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/0, /*numAssistsInGame=*/0);
    final String secondMostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/1, /*numAssistsInGame=*/0);
    final String mostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/2, /*numAssistsInGame=*/2);
    final NodeList nodeList = getNodeList(thirdMostRecentGameTableRow,
          secondMostRecentGameTableRow, mostRecentGameTableRow);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(new PlayerStreaks.Builder().setGoalStreak(2).
          setAssistStreak(1).setPointStreak(2).build(),
          streaksSupplier.get(playerId, position));
  }

  @Test
  public void apply_oneGameGoalStreakTwoGamePointStreak() throws Exception
  {
    final String thirdMostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/0, /*numAssistsInGame=*/0);
    final String secondMostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/0, /*numAssistsInGame=*/2);
    final String mostRecentGameTableRow = getGameTableRow(
          /*numGoalsInGame=*/3, /*numAssistsInGame=*/0);
    final NodeList nodeList = getNodeList(thirdMostRecentGameTableRow,
          secondMostRecentGameTableRow, mostRecentGameTableRow);
    when(toGameRowNodeListFunction.apply(playerId)).thenReturn(nodeList);
    assertEquals(new PlayerStreaks.Builder().setGoalStreak(1).
          setPointStreak(2).build(), streaksSupplier.get(playerId, position));
  }
}
