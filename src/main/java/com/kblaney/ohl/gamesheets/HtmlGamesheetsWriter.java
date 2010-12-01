package com.kblaney.ohl.gamesheets;

import java.io.IOException;

interface HtmlGamesheetsWriter
{
  void write(HtmlGamesheets htmlGamesheets) throws IOException;
  String getDescription();
}
