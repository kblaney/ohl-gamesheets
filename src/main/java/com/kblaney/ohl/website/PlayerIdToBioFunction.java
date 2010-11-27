package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.kblaney.ohl.PlayerBio;
import java.io.IOException;
import org.w3c.dom.Node;

final class PlayerIdToBioFunction
{
  private final Function<String, Node> toBioDivNodeFunction;

  public PlayerIdToBioFunction()
  {
    this(new PlayerIdToBioDivNodeFunction());
  }

  PlayerIdToBioFunction(final Function<String, Node> toBioDivNodeFunction)
  {
    this.toBioDivNodeFunction = toBioDivNodeFunction;
  }

  public PlayerBio apply(final String playerId) throws IOException
  {
    final Node bioDivNode = toBioDivNodeFunction.apply(playerId);
    return new PlayerBio.Builder().setBirthYear(getBirthYear(bioDivNode)).
          setPosition(getPosition(bioDivNode)).
          setHeight(getHeight(bioDivNode)).
          setWeight(getWeight(bioDivNode)).
          setBirthplace(getBirthplace(bioDivNode)).build();
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

  private String getBirthplace(final Node bioDivNode)
  {
    return new PlayerBioDivNodeToBirthplaceFunction().apply(bioDivNode);
  }
}
