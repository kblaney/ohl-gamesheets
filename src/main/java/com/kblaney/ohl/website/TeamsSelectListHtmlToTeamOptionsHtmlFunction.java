package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class TeamsSelectListHtmlToTeamOptionsHtmlFunction
      implements Function<String, List<String>>
{
  public List<String> apply(final String teamsSelectListHtml)
  {
    final List<String> htmlOptions = Lists.newArrayList();
    final Pattern pattern = Pattern.compile(
        "OPTION value.*?subType=(\\d+).*?>(.*?)<", Pattern.DOTALL);
    final Matcher matcher = pattern.matcher(teamsSelectListHtml);
    while (matcher.find())
    {
      htmlOptions.add(matcher.group());
    }
    return htmlOptions;
  }
}
