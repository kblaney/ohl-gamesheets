package com.kblaney.ohl.gamesheets;

import java.io.File;
import java.io.IOException;

public interface StringWriterToFile
{
  void write(String s, File file) throws IOException;
}
