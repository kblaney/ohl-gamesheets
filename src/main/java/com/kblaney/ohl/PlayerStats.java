package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

/**
 * Stores the statistics for one player.
 * <p>
 * Note that games played, goals, assists, points, penalty minutes, power
 * play goals, and shorthanded goals can not be negative. Any time a
 * negative number is passed into any method accepting one of these values,
 * an <code>IllegalArgumentException</code> is thrown.
 */
public class PlayerStats
{
   private int numGamesPlayed;
   private int numGoals;
   private int numAssists;
   private int numPoints;
   private int plusMinus;
   private int numPenaltyMinutes;
   private int numPowerPlayGoals;
   private int numShorthandedGoals;

   /**
    * Constructs a <code>PlayerStats</code> object with all fields
    * zeroed.
    */
   public PlayerStats()
   {
      final int initialStatValue = 0;
      setNumGamesPlayed( initialStatValue );
      setNumGoals( initialStatValue );
      setNumAssists( initialStatValue );
      setNumPoints( initialStatValue );
      setPlusMinus( initialStatValue );
      setNumPenaltyMinutes( initialStatValue );
      setNumPowerPlayGoals( initialStatValue );
      setNumShorthandedGoals( initialStatValue );
   }

   /**
    * Constructs a <code>PlayerStats</code> object based on specified
    * values.
    * @param numGamesPlayed the number of games played
    * @param numGoals the number of goals
    * @param numAssists the number of assists
    * @param numPoints the number of points
    * @param plusMinus the plus minus
    * @param numPenaltyMinutes the number of penalty minutes
    * @param numPowerPlayGoals the number of power play goals
    * @param numShorthandedGoals the number of shorthanded goals
    */
   public PlayerStats( final int numGamesPlayed, final int numGoals,
         final int numAssists, final int numPoints, final int plusMinus,
         final int numPenaltyMinutes, final int numPowerPlayGoals,
         final int numShorthandedGoals )
   {
      setNumGamesPlayed( numGamesPlayed );
      setNumGoals( numGoals );
      setNumAssists( numAssists );
      setNumPoints( numPoints );
      setPlusMinus( plusMinus );
      setNumPenaltyMinutes( numPenaltyMinutes );
      setNumPowerPlayGoals( numPowerPlayGoals );
      setNumShorthandedGoals( numShorthandedGoals );
   }

   /**
    * Constructs a <code>PlayerStats</code> based on another
    * <code>PlayerStats</code>.
    * @param playerStats the other <code>PlayerStats</code>
    */
   public PlayerStats( final PlayerStats playerStats )
   {
      setNumGamesPlayed( playerStats.numGamesPlayed );
      setNumGoals( playerStats.numGoals );
      setNumAssists( playerStats.numAssists );
      setNumPoints( playerStats.numPoints );
      setPlusMinus( playerStats.plusMinus );
      setNumPenaltyMinutes( playerStats.numPenaltyMinutes );
      setNumPowerPlayGoals( playerStats.numPowerPlayGoals );
      setNumShorthandedGoals( playerStats.numShorthandedGoals );
   }

   /**
    * Sets the number of games played.
    * @param numGamesPlayed the number of games played
    */
   public void setNumGamesPlayed( final int numGamesPlayed )
   {
      ArgChecker.checkIfNegative( numGamesPlayed, "numGamesPlayed" );

      this.numGamesPlayed = numGamesPlayed;
   }

   /**
    * Gets the number of games played.
    * @return the number of games played
    */
   public int getNumGamesPlayed()
   {
      return this.numGamesPlayed;
   }

   /**
    * Sets the number of goals.
    * @param numGoals the number of goals
    */
   public void setNumGoals( final int numGoals )
   {
      ArgChecker.checkIfNegative( numGoals, "numGoals" );

      this.numGoals = numGoals;
   }

   /**
    * Gets the number of goals
    * @return the number of goals
    */
   public int getNumGoals()
   {
      return this.numGoals;
   }

   /**
    * Sets the number of assists.
    * @param numAssists the number of assists
    */
   public void setNumAssists( final int numAssists )
   {
      ArgChecker.checkIfNegative( numAssists, "numAssists" );

      this.numAssists = numAssists;
   }

   /**
    * Gets the number of assists.
    * @return the number of assists
    */
   public int getNumAssists()
   {
      return this.numAssists;
   }

   /**
    * Sets the number of points.
    * @param numPoints the number of points
    */
   public void setNumPoints( final int numPoints )
   {
      ArgChecker.checkIfNegative( numPoints, "numPoints" );

      this.numPoints = numPoints;
   }

   /**
    * Gets the number of points.
    * @return the number of points
    */
   public int getNumPoints()
   {
      return this.numPoints;
   }

   /**
    * Sets the plus minus.
    * @param plusMinus the plus minus
    */
   public void setPlusMinus( final int plusMinus )
   {
      this.plusMinus = plusMinus;
   }

   /**
    * Gets the plus minus.
    * @return the plus minus
    */
   public int getPlusMinus()
   {
      return this.plusMinus;
   }

   /**
    * Sets the number of penalty minutes.
    * @param numPenaltyMinutes the number of penalty minutes
    */
   public void setNumPenaltyMinutes( final int numPenaltyMinutes )
   {
      ArgChecker.checkIfNegative( numPenaltyMinutes, "numPenaltyMinutes" );

      this.numPenaltyMinutes = numPenaltyMinutes;
   }

   /**
    * Gets the number of penalty minutes.
    * @return the number of penalty minutes
    */
   public int getNumPenaltyMinutes()
   {
      return this.numPenaltyMinutes;
   }

   /**
    * Sets the number of power play goals.
    * @param numPowerPlayGoals the number of power play goals
    */
   public void setNumPowerPlayGoals( final int numPowerPlayGoals )
   {
      ArgChecker.checkIfNegative( numPowerPlayGoals, "numPowerPlayGoals" );

      this.numPowerPlayGoals = numPowerPlayGoals;
   }

   /**
    * Gets the number of power play goals.
    * @return the number of power play goals
    */
   public int getNumPowerPlayGoals()
   {
      return this.numPowerPlayGoals;
   }

   /**
    * Sets the number of shorthanded goals.
    * @param numShorthandedGoals the number of shorthanded goals
    */
   public void setNumShorthandedGoals( final int numShorthandedGoals )
   {
      ArgChecker.checkIfNegative( numShorthandedGoals, "numShorthandedGoals" );

      this.numShorthandedGoals = numShorthandedGoals;
   }

   /**
    * Gets the number of shorthanded goals.
    * @return the number of shorthanded goals
    */
   public int getNumShorthandedGoals()
   {
      return this.numShorthandedGoals;
   }
}
