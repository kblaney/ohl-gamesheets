package com.kblaney.ohl.gamesheets;

import com.kblaney.ohl.Team;
import java.io.IOException;
import java.util.Calendar;

public interface GamesheetsGetter
{
  Gamesheets getGamesheets(Team homeTeam, Team roadTeam,
        Calendar gameDate, ProgressIndicator progressIndicator)
        throws IOException;
}
