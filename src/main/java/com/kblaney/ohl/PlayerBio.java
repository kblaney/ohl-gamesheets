package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

/**
 * Stores the biographical information for one player.
 * <p>
 * Note that any strings passed to methods in this class can not be null.
 * Any time a null string is passed into any method, an
 * <code>IllegalArgumentException</code> is thrown.
 */
public class PlayerBio
{
   private String birthDate;
   private String position;
   private String height;
   private String weight;
   private String homeTown;

   /**
    * Constructs a <code>PlayerBio</code> object based on specified
    * values.
    * @param birthDate the player's birthdate (in the form YYYY-MM-DD)
    * @param position the player's position
    * @param height the player's height
    * @param weight the player's weight
    * @param homeTown the player's home town
    */
   public PlayerBio( final String birthDate,
         final String position, final String height,
         final String weight, final String homeTown )
   {
      this.setBirthDate( birthDate );
      this.setPosition( position );
      this.setHeight( height );
      this.setWeight( weight );
      this.setHomeTown( homeTown );
   }

   /**
    * Constructs a <code>PlayerBio</code> based on another
    * <code>PlayerBio</code>.
    *
    * @param playerBio the other <code>PlayerBio</code>, which can't be null
    */
   public PlayerBio( final PlayerBio playerBio )
   {
      this( playerBio.birthDate, playerBio.position, playerBio.height,
              playerBio.weight, playerBio.homeTown );
   }

   /**
    * Gets the player's birth year.
    * @return the player's birth year
    */
   public String getBirthYear()
   {
      String birthYear = null;
      
      final int birthYearStartIndex = 0;
      final int birthYearEndIndex = 4;
      if (this.birthDate.length() >= birthYearEndIndex)
      {
         birthYear = this.birthDate.substring( birthYearStartIndex,
               birthYearEndIndex );
      }
      else
      {
         birthYear = "";
      }
      
      return birthYear;
   }

   /**
    * Gets the player's birth date (in the form YYYY-MM-DD)
    * @return the player's birth date
    */
   public String getBirthDate()
   {
      return this.birthDate;
   }

   /**
    * Sets the player's birth date.
    * @param birthDate the player's birth date
    */
   public void setBirthDate( final String birthDate )
   {
      ArgChecker.checkIfNull( birthDate, "birthDate" );
      
      this.birthDate = birthDate;
   }

   /**
    * Gets the player's position
    * @return the player's position
    */
   public String getPosition()
   {
      return this.position;
   }

   /**
    * Sets the player's position.
    * @param position the player's position
    */
   public void setPosition( final String position )
   {
      ArgChecker.checkIfNull( position, "position" );
      
      this.position = position;
   }

   /**
    * Gets the player's height
    * @return the player's height
    */
   public String getHeight()
   {
      return this.height;
   }

   /**
    * Sets the player's height.
    * @param height the player's height
    */
   public void setHeight( final String height )
   {
      ArgChecker.checkIfNull( height, "height" );
      
      this.height = height;
   }

   /**
    * Gets the player's home town
    * @return the player's home town
    */
   public String getHomeTown()
   {
      return this.homeTown;
   }

   /**
    * Sets the player's home town.
    * @param homeTown the player's home town
    */
   public void setHomeTown( final String homeTown )
   {
      ArgChecker.checkIfNull( homeTown, "homeTown" );
      
      this.homeTown = homeTown;
   }

   /**
    * Gets the player's weight
    * @return the player's weight
    */
   public String getWeight()
   {
      return this.weight;
   }

   /**
    * Sets the player's weight.
    * @param weight the player's weight
    */
   public void setWeight( final String weight )
   {
      ArgChecker.checkIfNull( weight, "weight" );
      
      this.weight = weight;
   }
}
