package com.kblaney.ohl;

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
  /**
   * Builds PlayerBio instances.
   */
  public static final class Builder
  {
    private String birthYear;
    private String position;
    private String height;
    private String weight;
    private String homeTown;

    /**
     * Builds a PlayerBio instance.
     *
     * @return the PlayerBio instance
     */
    public PlayerBio build()
    {
      final PlayerBio playerBio = new PlayerBio();
      playerBio.birthYear = birthYear;
      playerBio.position = position;
      playerBio.height = height;
      playerBio.weight = weight;
      playerBio.homeTown = homeTown;
      return playerBio;
    }

    /**
     * Sets the player's birth date.
     *
     * @param birthDate the birth date
     *
     * @return the builder instance
     */
    public Builder setBirthYear(final String birthDate)
    {
      this.birthYear = birthDate;
      return this;
    }

    /**
     * Sets the player's position.
     *
     * @param position the position
     *
     * @return the builder instance
     */
    public Builder setPosition(final String position)
    {
      this.position = position;
      return this;
    }

    /**
     * Sets the player's height.
     *
     * @param height the height
     *
     * @return the builder instance
     */
    public Builder setHeight(final String height)
    {
      this.height = height;
      return this;
    }

    /**
     * Sets the player's weight.
     *
     * @param weight the weight
     *
     * @return the builder instance
     */
    public Builder setWeight(final String weight)
    {
      this.weight = weight;
      return this;
    }

    /**
     * Sets the player's home town.
     *
     * @param homeTown the homeTown
     *
     * @return the builder instance
     */
    public Builder setHomeTown(final String homeTown)
    {
      this.homeTown = homeTown;
      return this;
    }
  }

  private String birthYear;
  private String position;
  private String height;
  private String weight;
  private String homeTown;

  private PlayerBio() {}

  /**
   * Gets the player's birth year.
   *
   * @return the player's birth year, or an empty string if the birth year is
   * unknown
   */
  public String getBirthYear()
  {
    return birthYear;
  }

  /**
   * Gets the player's position.
   *
   * @return the player's position
   */
  public String getPosition()
  {
    return position;
  }

  /**
   * Gets the player's height.
   *
   * @return the player's height
   */
  public String getHeight()
  {
    return height;
  }

  /**
   * Gets the player's home town.
   *
   * @return the player's home town
   */
  public String getHomeTown()
  {
    return homeTown;
  }

  /**
   * Gets the player's weight.
   *
   * @return the player's weight
   */
  public String getWeight()
  {
    return weight;
  }
}
