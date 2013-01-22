package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.ohl.PlayerBio;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

final class PlayerIdToBioFunction implements Function<String, PlayerBio>
{
  private final Function<String, Document> toBioDocumentFunction;
  private final Function<Document, Node> bioDocumentToBioDivNodeFunction;
  private final Function<Document, String> bioDocumentToGameByGameFilePathFunction;

  @Inject
  public PlayerIdToBioFunction(final Function<String, Document> toBioDocumentFunction,
        final Function<Document, Node> bioDocumentToBioDivNodeFunction,
        final Function<Document, String> bioDocumentToGameByGameFilePathFunction)
  {
    this.toBioDocumentFunction = toBioDocumentFunction;
    this.bioDocumentToBioDivNodeFunction = bioDocumentToBioDivNodeFunction;
    this.bioDocumentToGameByGameFilePathFunction = bioDocumentToGameByGameFilePathFunction;
  }

  public PlayerBio apply(final String playerId)
  {
    try
    {
      final Document bioDocument = toBioDocumentFunction.apply(playerId);
      final Node bioDivNode = bioDocumentToBioDivNodeFunction.apply(bioDocument);
      final String gameByGameFilePath = bioDocumentToGameByGameFilePathFunction.apply(bioDocument);
      return new PlayerBio.Builder().setBirthYear(getBirthYear(bioDivNode)).setPosition(getPosition(bioDivNode))
            .setHeight(getHeight(bioDivNode)).setWeight(getWeight(bioDivNode)).setHometown(getHometown(bioDivNode))
            .setGameByGameFilePath(gameByGameFilePath).build();
    }
    catch (final IllegalStateException e)
    {
      return new PlayerBio.Builder().build();
    }
  }

  private String getBirthYear(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToBirthYearFunction().apply(bioDivNode);
  }

  private String getPosition(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToPositionFunction().apply(bioDivNode);
  }

  private String getHeight(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToHeightFunction().apply(bioDivNode);
  }

  private String getWeight(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToWeightFunction().apply(bioDivNode);
  }

  private String getHometown(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToHometownFunction().apply(bioDivNode);
  }
}
