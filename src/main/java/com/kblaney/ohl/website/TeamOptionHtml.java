package com.kblaney.ohl.website;

import java.util.regex.Pattern;

final class TeamOptionHtml
{
  static final Pattern PATTERN = Pattern.compile(
        "OPTION value.*?subType=(\\d+).*?>(.*?)<", Pattern.DOTALL);
  static final int TEAM_NUM_GROUP_NUM = 1;
  static final int TEAM_NAME_GROUP_NUM = 2;

  private TeamOptionHtml() {}
}
