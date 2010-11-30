package com.kblaney.ohl.gamesheets;

import com.google.common.collect.Lists;
import com.google.common.base.Function;
import com.kblaney.ohl.Goalie;
import com.kblaney.ohl.GoalieStats;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class GoalieHtmlTableGetterTest
{
  private Function<List<Goalie>, String> tableGetter;

  @Before
  public void setUp()
  {
    tableGetter = new GoalieHtmlTableGetter();
  }

  @Test
  public void apply_zeroGoalies()
  {
    assertNumGoaliesInTable(Collections.<Goalie>emptyList());
  }

  private void assertNumGoaliesInTable(final List<Goalie> goalies)
  {
    final int numHeaderRows = 1;
    final int expectedNumTableRows = goalies.size() + numHeaderRows;
    final String table = tableGetter.apply(goalies);
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table, "<tr>"));
    assertEquals(expectedNumTableRows, StringUtils.countMatches(table,
          "</tr>"));
  }

  @Test
  public void apply_oneGoalie()
  {
    assertNumGoaliesInTable(Lists.newArrayList(getGoalie("GOALIE_NAME")));
  }

  private Goalie getGoalie(final String name)
  {
    return new Goalie(name, new GoalieStats.Builder().build());
  }

  @Test
  public void apply_twoGoalies()
  {
    assertNumGoaliesInTable(Lists.newArrayList(getGoalie("FIRST"),
          getGoalie("SECOND")));
  }
}
