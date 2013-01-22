package com.kblaney.ohl;

import com.kblaney.assertions.ArgAssert;
import org.apache.commons.lang.StringUtils;

/**
 * The biographical information of one player.
 * 
 * <p>
 * Note that this class does not have a public constructor. Rather, instances are created using the PlayerBio.Builder
 * class.
 * </p>
 */
public final class PlayerBio
{
  public static final class Builder
  {
    private String birthYear = StringUtils.EMPTY;
    private String position = StringUtils.EMPTY;
    private String height = StringUtils.EMPTY;
    private String weight = StringUtils.EMPTY;
    private String hometown = StringUtils.EMPTY;
    private String gameByGameFilePath = StringUtils.EMPTY;

    public PlayerBio build()
    {
      final PlayerBio playerBio = new PlayerBio();
      playerBio.birthYear = birthYear;
      playerBio.position = position;
      playerBio.height = height;
      playerBio.weight = weight;
      playerBio.hometown = hometown;
      playerBio.gameByGameFilePath = gameByGameFilePath;
      return playerBio;
    }

    public Builder setBirthYear(final String birthYear)
    {
      this.birthYear = ArgAssert.assertNotNull(birthYear, "birthYear");
      return this;
    }

    public Builder setPosition(final String position)
    {
      this.position = ArgAssert.assertNotNull(position, "position");
      return this;
    }

    public Builder setHeight(final String height)
    {
      this.height = ArgAssert.assertNotNull(height, "height");
      return this;
    }

    public Builder setWeight(final String weight)
    {
      this.weight = ArgAssert.assertNotNull(weight, "weight");
      return this;
    }

    public Builder setHometown(final String hometown)
    {
      this.hometown = ArgAssert.assertNotNull(hometown, "hometown");
      return this;
    }

    public Builder setGameByGameFilePath(final String gameByGameFilePath)
    {
      this.gameByGameFilePath = ArgAssert.assertNotNull(gameByGameFilePath, "gameByGameFilePath");
      return this;
    }
  }

  private String birthYear;
  private String position;
  private String height;
  private String weight;
  private String hometown;
  private String gameByGameFilePath;

  private PlayerBio()
  {
  }

  public String getBirthYear()
  {
    return birthYear;
  }

  public String getPosition()
  {
    return position;
  }

  public String getHeight()
  {
    return height;
  }

  public String getWeight()
  {
    return weight;
  }

  public String getHometown()
  {
    return hometown;
  }

  public String getGameByGameFilePath()
  {
    return gameByGameFilePath;
  }
}
