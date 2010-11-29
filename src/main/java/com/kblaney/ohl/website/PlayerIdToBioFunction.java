package com.kblaney.ohl.website;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.kblaney.ohl.PlayerBio;
import org.w3c.dom.Node;

final class PlayerIdToBioFunction implements Function<String, PlayerBio>
{
  private final Function<String, Node> toBioDivNodeFunction;

  @Inject
  public PlayerIdToBioFunction(
        final Function<String, Node> toBioDivNodeFunction)
  {
    this.toBioDivNodeFunction = toBioDivNodeFunction;
  }

  public PlayerBio apply(final String playerId)
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
