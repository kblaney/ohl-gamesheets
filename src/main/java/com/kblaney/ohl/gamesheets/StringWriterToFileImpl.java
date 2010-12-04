package com.kblaney.ohl.gamesheets;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public final class StringWriterToFileImpl implements StringWriterToFile
{
  public void write(final String s, final File file) throws IOException
  {
    FileUtils.writeStringToFile(file, s);
  }
}
