package com.kblaney.ohl;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public final class PlayerBioTest
{
  private String birthYear;
  private String position;
  private String height;
  private String weight;
  private String birthplace;
  private PlayerBio bio;

  @Before
  public void setUp()
  {
    birthYear = "1993";
    position = "RW";
    height = "5.11";
    weight = "198";
    birthplace = "Belleville, ON";
    bio = new PlayerBio.Builder().setBirthYear(birthYear).
          setPosition(position).setHeight(height).setWeight(weight).
          setBirthplace(birthplace).build();
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
  public void nullBirthplace()
  {
    new PlayerBio.Builder().setBirthplace(null);
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
  public void getBirthplace()
  {
    assertEquals(birthplace, bio.getBirthplace());
  }
}
