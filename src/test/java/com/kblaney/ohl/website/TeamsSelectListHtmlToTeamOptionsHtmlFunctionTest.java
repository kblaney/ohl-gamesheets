package com.kblaney.ohl.website;

import com.google.common.collect.Lists;
import com.google.common.base.Function;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class TeamsSelectListHtmlToTeamOptionsHtmlFunctionTest
{
  private Function<String, List<String>> function;

  @Before
  public void setUp()
  {
    function = new TeamsSelectListHtmlToTeamOptionsHtmlFunction();
  }

  @Test
  public void apply_noTeamOptions()
  {
    assertTrue(function.apply("NO_TEAM_OPTIONS_HERE").isEmpty());
  }

  @Test
  public void apply_oneTeamOption()
  {
    assertEquals(Lists.newArrayList("OPTION valueBLAHBteam_id=12BLAH>KYLE<"),
          function.apply("ABC_OPTION valueBLAHBteam_id=12BLAH>KYLE<DEF"));
  }

  @Test
  public void apply_twoTeamOptions()
  {
    assertEquals(Lists.newArrayList("OPTION valueBLAHBteam_id=12BLAH>KYLE<", "OPTION valueBIGteam_id=9BLAH>A>A>B<"),
          function.apply("ABC_OPTION valueBLAHBteam_id=12BLAH>KYLE<DEF\n"
                + "DEF_OPTION valueBIGteam_id=9BLAH>A>A>B<<\n"));
  }
}
