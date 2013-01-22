package com.kblaney.ohl.gamesheets;

import java.io.IOException;

public interface GamesheetsWriter
{
  void write(Gamesheets gamesheets) throws IOException;

  String getDescription();
}
