package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgAssert;

/**
 * The biographical information of one player.
 *
 * <p>
 * Note that this class does not have a public constructor.  Rather, instances
 * are created using the PlayerBio.Builder class.
 * </p>
 */
public final class PlayerBio
{
  public static final class Builder
  {
    private String birthYear;
    private String position;
    private String height;
    private String weight;
    private String birthplace;

    public PlayerBio build()
    {
      final PlayerBio playerBio = new PlayerBio();
      playerBio.birthYear = birthYear;
      playerBio.position = position;
      playerBio.height = height;
      playerBio.weight = weight;
      playerBio.birthplace = birthplace;
      return playerBio;
    }

    public Builder setBirthYear(final String birthYear)
    {
      ArgAssert.notNull(birthYear, "birthYear");
      this.birthYear = birthYear;
      return this;
    }

    public Builder setPosition(final String position)
    {
      ArgAssert.notNull(position, "position");
      this.position = position;
      return this;
    }

    public Builder setHeight(final String height)
    {
      ArgAssert.notNull(height, "height");
      this.height = height;
      return this;
    }

    public Builder setWeight(final String weight)
    {
      ArgAssert.notNull(weight, "weight");
      this.weight = weight;
      return this;
    }

    public Builder setBirthplace(final String birthplace)
    {
      ArgAssert.notNull(birthplace, "birthplace");
      this.birthplace = birthplace;
      return this;
    }
  }

  private String birthYear;
  private String position;
  private String height;
  private String weight;
  private String birthplace;

  private PlayerBio() {}

  public String getBirthYear() { return birthYear; }
  public String getPosition() { return position; }
  public String getHeight() { return height; }
  public String getBirthplace() { return birthplace; }
  public String getWeight() { return weight; }
}
