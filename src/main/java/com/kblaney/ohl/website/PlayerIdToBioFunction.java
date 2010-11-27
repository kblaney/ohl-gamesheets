package com.kblaney.ohl.website;

import com.kblaney.ohl.PlayerBio;
import java.io.IOException;
import org.w3c.dom.Node;

final class PlayerIdToBioFunction
{
  public PlayerBio apply(final String playerId) throws IOException
  {
    final Node bioDivNode = new PlayerIdToBioDivNodeFunction().apply(playerId);
    return new PlayerBio.Builder().setBirthYear(getBirthYear(bioDivNode)).
          setPosition(getPosition(bioDivNode)).
          setHeight(getHeight(bioDivNode)).
          setWeight(getWeight(bioDivNode)).
          setHomeTown(getHomeTown(bioDivNode)).build();
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

  private String getHomeTown(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToHometownFunction().apply(bioDivNode);
  }
}
