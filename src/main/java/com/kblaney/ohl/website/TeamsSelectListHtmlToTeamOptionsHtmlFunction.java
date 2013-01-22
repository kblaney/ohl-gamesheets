package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.regex.Matcher;

final class TeamsSelectListHtmlToTeamOptionsHtmlFunction implements Function<String, List<String>>
{
  public List<String> apply(final String teamsSelectListHtml)
  {
    final List<String> htmlOptions = Lists.newArrayList();
    final Matcher matcher = TeamOptionHtml.PATTERN.matcher(teamsSelectListHtml);
    while (matcher.find())
    {
      htmlOptions.add(matcher.group());
    }
    return htmlOptions;
  }
}
