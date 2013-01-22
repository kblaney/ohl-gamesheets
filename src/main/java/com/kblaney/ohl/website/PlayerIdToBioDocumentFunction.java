package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.xml.UrlToDomDocumentFunction;
import java.io.IOException;
import java.net.URL;
import org.w3c.dom.Document;

final class PlayerIdToBioDocumentFunction implements Function<String, Document>
{
  private final UrlToDomDocumentFunction urlToDomDocumentFunction;

  @Inject
  public PlayerIdToBioDocumentFunction(final UrlToDomDocumentFunction urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public Document apply(final String playerId)
  {
    final URL url = Urls.getPlayerBioUrl(playerId);
    try
    {
      return urlToDomDocumentFunction.apply(url);
    }
    catch (final IOException e)
    {
      throw new IllegalStateException("Can't get player bio from url: " + url, e);
    }
  }
}
