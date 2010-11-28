package com.kblaney.ohl.gamesheets;

import com.kblaney.ohl.Team;
import java.io.IOException;
import java.util.Calendar;

interface HtmlGamesheetsGetter
{
  HtmlGamesheets getGamesheets(Team homeTeam, Team roadTeam,
        Calendar gameDate, ProgressIndicator progressIndicator)
        throws IOException;
}
