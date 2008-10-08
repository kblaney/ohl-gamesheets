package com.kblaney.ohl;

import com.kblaney.commons.lang.ArgChecker;

public class GoalieStats
{
   private int numGamesPlayed;
   private int numMinutesPlayed;
   private int numGoalsAgainst;
   private int numShutouts;
   private double goalsAgainstAverage;
   private int numWins;
   private int numLosses;
   private int numOvertimeLosses;
   private int numShootoutLosses;
   private int numShotsAgainst;
   private int numSaves;
   private double savePercentage;
   
   public GoalieStats( final int numGamesPlayed, final int numMinutesPlayed,
           final int numGoalsAgainst, final int numShutouts,
           final double goalsAgainstAverage, final int numWins,
           final int numLosses, final int numOvertimeLosses,
           final int numShootoutLosses, final int numShotsAgainst,
           final int numSaves, final double savePercentage )
   {
      setNumGamesPlayed( numGamesPlayed );
      setNumMinutesPlayed( numMinutesPlayed );
      setNumGoalsAgainst( numGoalsAgainst );
      setNumShutouts( numShutouts );
      setGoalsAgainstAverage( goalsAgainstAverage );
      setNumWins( numWins );
      setNumLosses( numLosses );
      setNumOvertimeLosses( numOvertimeLosses );
      setNumShootoutLosses( numShootoutLosses );
      setNumShotsAgainst( numShotsAgainst );
      setNumSaves( numSaves );
      setSavePercentage( savePercentage );
   }

   public GoalieStats( final GoalieStats goalieStats )
   {
      setNumGamesPlayed( goalieStats.numGamesPlayed );
      setNumMinutesPlayed( goalieStats.numMinutesPlayed );
      setNumGoalsAgainst( goalieStats.numGoalsAgainst );
      setNumShutouts( goalieStats.numShutouts );
      setGoalsAgainstAverage( goalieStats.goalsAgainstAverage );
      setNumWins( goalieStats.numWins );
      setNumLosses( goalieStats.numLosses );
      setNumOvertimeLosses( goalieStats.numOvertimeLosses );
      setNumShootoutLosses( goalieStats.numShootoutLosses );
      setNumShotsAgainst( goalieStats.numShotsAgainst );
      setNumSaves( goalieStats.numSaves );
      setSavePercentage( goalieStats.savePercentage );
   }

   public double getGoalsAgainstAverage()
   {
      return this.goalsAgainstAverage;
   }

   public void setGoalsAgainstAverage( final double goalsAgainstAverage )
   {
      ArgChecker.checkIfNegative( goalsAgainstAverage,
            "goalsAgainstAverage" );

      this.goalsAgainstAverage = goalsAgainstAverage;
   }

   public int getNumGamesPlayed()
   {
      return this.numGamesPlayed;
   }

   public void setNumGamesPlayed( final int numGamesPlayed )
   {
      ArgChecker.checkIfNegative( numGamesPlayed, "numGamesPlayed" );

      this.numGamesPlayed = numGamesPlayed;
   }

   public int getNumGoalsAgainst()
   {
      return this.numGoalsAgainst;
   }

   public void setNumGoalsAgainst( final int numGoalsAgainst )
   {
      ArgChecker.checkIfNegative( numGoalsAgainst, "numGoalsAgainst" );

      this.numGoalsAgainst = numGoalsAgainst;
   }

   public int getNumLosses()
   {
      return this.numLosses;
   }

   public void setNumLosses( final int numLosses )
   {
      ArgChecker.checkIfNegative( numLosses, "numLosses" );

      this.numLosses = numLosses;
   }

   public int getNumMinutesPlayed()
   {
      return this.numMinutesPlayed;
   }

   public void setNumMinutesPlayed( final int numMinutesPlayed )
   {
      ArgChecker.checkIfNegative( numMinutesPlayed, "numMinutesPlayed" );

      this.numMinutesPlayed = numMinutesPlayed;
   }

   public int getNumSaves()
   {
      return this.numSaves;
   }

   public void setNumSaves( final int numSaves )
   {
      ArgChecker.checkIfNegative( numSaves, "numSaves" );

      this.numSaves = numSaves;
   }

   public int getNumShotsAgainst()
   {
      return this.numShotsAgainst;
   }

   public void setNumShotsAgainst( final int numShotsAgainst )
   {
      ArgChecker.checkIfNegative( numShotsAgainst, "numShotsAgainst" );

      this.numShotsAgainst = numShotsAgainst;
   }

   public int getNumShutouts()
   {
      return this.numShutouts;
   }

   public void setNumShutouts( final int numShutouts )
   {
      ArgChecker.checkIfNegative( numShutouts, "numShutouts" );

      this.numShutouts = numShutouts;
   }

   public int getNumOvertimeLosses()
   {
      return this.numOvertimeLosses;
   }

   public void setNumOvertimeLosses( final int numOvertimeLosses )
   {
      ArgChecker.checkIfNegative( numOvertimeLosses, "numOvertimeLosses" );

      this.numOvertimeLosses = numOvertimeLosses;
   }

   public int getNumShootoutLosses()
   {
      return this.numShootoutLosses;
   }

   public void setNumShootoutLosses( final int numShootoutLosses )
   {
      ArgChecker.checkIfNegative( numShootoutLosses, "numShootoutLosses" );

      this.numShootoutLosses = numShootoutLosses;
   }

   public int getNumWins()
   {
      return this.numWins;
   }

   public void setNumWins( final int numWins )
   {
      ArgChecker.checkIfNegative( numWins, "numWins" );

      this.numWins = numWins;
   }

   public double getSavePercentage()
   {
      return this.savePercentage;
   }

   public void setSavePercentage( final double savePercentage )
   {
      ArgChecker.checkIfNegative( savePercentage, "savePercentage" );
      ArgChecker.checkIfTooLarge( savePercentage, 1, "savePercentage" );

      this.savePercentage = savePercentage;
   }
}
