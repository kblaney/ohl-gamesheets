package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.url.UrlFunction;
import java.io.IOException;
import java.net.URL;
import org.w3c.dom.Document;

final class PlayerIdToBioDocumentFunction implements Function<String, Document>
{
  private final UrlFunction<Document> urlToDomDocumentFunction;

  @Inject
  public PlayerIdToBioDocumentFunction(final UrlFunction<Document> urlToDomDocumentFunction)
  {
    this.urlToDomDocumentFunction = urlToDomDocumentFunction;
  }

  public Document apply(final String playerId)
  {
    final URL url = Urls.getPlayerBioUrl(playerId);
    try
    {
      return urlToDomDocumentFunction.convert(url);
    }
    catch (final IOException e)
    {
      throw new IllegalStateException("Can't get player bio from url: " + url, e);
    }
  }
}
