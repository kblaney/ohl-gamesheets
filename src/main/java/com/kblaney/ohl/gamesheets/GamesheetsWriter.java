package com.kblaney.ohl.gamesheets;

import java.io.IOException;

interface GamesheetsWriter
{
  void write(Gamesheets gamesheets) throws IOException;
  String getDescription();
}
