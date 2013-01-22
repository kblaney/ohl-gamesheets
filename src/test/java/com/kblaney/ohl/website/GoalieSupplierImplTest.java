package com.kblaney.ohl.website;

import com.kblaney.ohl.Goalie;
import com.google.common.base.Function;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.gamesheets.ProgressIndicator;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class GoalieSupplierImplTest
{
  private Goalie goalie;
  private Node tableRowNode;
  private ProgressIndicator progressIndicator;
  private Function<Node, String> tableRowNodeToNameFunction;
  private Function<Node, GoalieStats> tableRowNodeToStatsFunction;
  private GoalieSupplier supplier;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp()
  {
    final String name = "GOALIE_NAME";
    final GoalieStats stats = new GoalieStats.Builder().setNumGamesPlayed(3).setNumWins(1).setNumRegulationLosses(2)
          .setNumGoalsAgainst(10).setNumSaves(90).setNumShotsAgainst(100).setNumMinutesPlayed(179)
          .setGoalsAgainstAverage(3.33).setSavePercentage(0.9).build();
    goalie = new Goalie(name, stats);

    tableRowNode = mock(Node.class);
    progressIndicator = mock(ProgressIndicator.class);

    tableRowNodeToNameFunction = mock(Function.class);
    when(tableRowNodeToNameFunction.apply(tableRowNode)).thenReturn(name);

    tableRowNodeToStatsFunction = mock(Function.class);
    when(tableRowNodeToStatsFunction.apply(tableRowNode)).thenReturn(stats);

    supplier = new GoalieSupplierImpl(tableRowNodeToNameFunction, tableRowNodeToStatsFunction);
  }

  @Test
  public void get()
  {
    assertEquals(goalie, supplier.get(tableRowNode, progressIndicator));
  }
}
