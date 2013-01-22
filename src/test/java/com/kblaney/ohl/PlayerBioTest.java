package com.kblaney.ohl;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerBioTest
{
  private String birthYear;
  private String position;
  private String height;
  private String weight;
  private String hometown;
  private String gameByGameFilePath;
  private PlayerBio bio;

  @Before
  public void setUp()
  {
    birthYear = "1993";
    position = "RW";
    height = "5.11";
    weight = "198";
    hometown = "Belleville, ON";
    gameByGameFilePath = "roster/show/id/6640";
    bio = new PlayerBio.Builder().setBirthYear(birthYear).setPosition(position).setHeight(height).setWeight(weight)
          .setHometown(hometown).setGameByGameFilePath(gameByGameFilePath).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullBirthYear()
  {
    new PlayerBio.Builder().setBirthYear(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullPosition()
  {
    new PlayerBio.Builder().setPosition(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullHeight()
  {
    new PlayerBio.Builder().setHeight(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullWeight()
  {
    new PlayerBio.Builder().setWeight(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullHometown()
  {
    new PlayerBio.Builder().setHometown(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullGameByGameFilePath()
  {
    new PlayerBio.Builder().setGameByGameFilePath(null);
  }

  @Test
  public void noSetMethodsCalled()
  {
    final PlayerBio playerBio = new PlayerBio.Builder().build();
    assertEquals(StringUtils.EMPTY, playerBio.getBirthYear());
    assertEquals(StringUtils.EMPTY, playerBio.getHeight());
    assertEquals(StringUtils.EMPTY, playerBio.getHometown());
    assertEquals(StringUtils.EMPTY, playerBio.getPosition());
    assertEquals(StringUtils.EMPTY, playerBio.getWeight());
    assertEquals(StringUtils.EMPTY, playerBio.getGameByGameFilePath());
  }

  @Test
  public void getBirthYear()
  {
    assertEquals(birthYear, bio.getBirthYear());
  }

  @Test
  public void getPosition()
  {
    assertEquals(position, bio.getPosition());
  }

  @Test
  public void getHeight()
  {
    assertEquals(height, bio.getHeight());
  }

  @Test
  public void getWeight()
  {
    assertEquals(weight, bio.getWeight());
  }

  @Test
  public void getHometown()
  {
    assertEquals(hometown, bio.getHometown());
  }

  @Test
  public void getGameByGameFilePath()
  {
    assertEquals(gameByGameFilePath, bio.getGameByGameFilePath());
  }
}
