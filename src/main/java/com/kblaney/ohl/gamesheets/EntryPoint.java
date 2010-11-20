package com.kblaney.ohl.gamesheets;

/**
 * The entry point of the application.
 */
public final class EntryPoint
{
  private EntryPoint() {}

  public static void main(final String[] args) throws Exception
  {
    final CreateGamesheetsFrame frame = new CreateGamesheetsFrame();
    frame.setVisible(true);
  }
}
