package com.kblaney.ohl;

import com.kblaney.commons.io.IOUtil;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import mseries.Calendar.MDefaultPullDownConstraints;
import mseries.ui.MDateEntryField;
import mseries.ui.MSimpleDateFormat;

public class CreateGamesheetsFrame extends JFrame implements ActionListener
{
   private final GridLayout gridLayout;
   private final JLabel homeTeamStatusLabel;
   private final JLabel homeTeamPlayerLabel;
   private final JLabel roadTeamStatusLabel;
   private final JLabel roadTeamPlayerLabel;
   private final JComboBox roadTeamComboBox;
   private final JComboBox homeTeamComboBox;
   private final MDateEntryField dateEntryField;
   private final JLabel fileLocationLabel;

   private static final String CREATE_GAMESHEETS = "CreateGamesheets";

   public CreateGamesheetsFrame() throws IOException
   {
      super( "OHL Gamesheets" );

      setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

      final SortedSet<String> teamNames =
            new TreeSet<String>( Ohl.getTeamNames() );

      this.homeTeamComboBox = new JComboBox( teamNames.toArray() );
      this.roadTeamComboBox = new JComboBox( teamNames.toArray() );
      final SimpleDateFormat simpleDateFormat = new MSimpleDateFormat(
            "EEEE, MMMM d, yyyy" );
      this.dateEntryField = new MDateEntryField( simpleDateFormat );
      this.homeTeamComboBox.setMaximumRowCount( teamNames.size() );
      this.roadTeamComboBox.setMaximumRowCount( teamNames.size() );
      final JLabel atLabel = new JLabel( "AT", JLabel.CENTER );
      final JLabel onLabel = new JLabel( "ON", JLabel.CENTER );
      final JLabel roadTeamLabel = new JLabel( "Road team:  " );
      final JLabel homeTeamLabel = new JLabel( "Home team:  " );
      this.roadTeamStatusLabel = new JLabel( "Sault Ste. Marie Greyhounds" );
      this.homeTeamStatusLabel = new JLabel();
      this.roadTeamPlayerLabel = new JLabel();
      this.homeTeamPlayerLabel = new JLabel();
      final Dimension d = this.roadTeamStatusLabel.getPreferredSize();
      this.roadTeamStatusLabel.setText( "" );
      this.roadTeamStatusLabel.setSize( d );
      this.roadTeamStatusLabel.setMinimumSize( d );
      this.roadTeamStatusLabel.setPreferredSize( d );
      this.homeTeamStatusLabel.setSize( d );
      this.homeTeamStatusLabel.setMinimumSize( d );
      this.homeTeamStatusLabel.setPreferredSize( d );
      this.roadTeamPlayerLabel.setSize( d );
      this.roadTeamPlayerLabel.setMinimumSize( d );
      this.roadTeamPlayerLabel.setPreferredSize( d );
      this.homeTeamPlayerLabel.setSize( d );
      this.homeTeamPlayerLabel.setMinimumSize( d );
      this.homeTeamPlayerLabel.setPreferredSize( d );

      final JButton createGamesheetsButton = new JButton(
            "Create gamesheets!" );
      createGamesheetsButton.setActionCommand( CREATE_GAMESHEETS );
      createGamesheetsButton.addActionListener( this );

      final MDefaultPullDownConstraints defaultPullDownConstraints =
            new MDefaultPullDownConstraints();
      defaultPullDownConstraints.firstDay = Calendar.SUNDAY;
      defaultPullDownConstraints.todayBackground = Color.RED;
      this.dateEntryField.setConstraints( defaultPullDownConstraints );

      final int numRows = 9;
      final int numColumns = 1;
      final int horizontalGap = 0;
      final int verticalGap = 0;
      this.gridLayout = new GridLayout( numRows, numColumns, horizontalGap,
            verticalGap );

      final Container contentPane = this.getContentPane();
      contentPane.setLayout( this.gridLayout );

      final JPanel roadTeamPanel = new JPanel();
      roadTeamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      roadTeamPanel.setLayout( new GridLayout(1, 2) );
      roadTeamPanel.add( roadTeamLabel );
      roadTeamPanel.add( this.roadTeamComboBox );

      final JPanel homeTeamPanel = new JPanel();
      homeTeamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      homeTeamPanel.setLayout( new GridLayout(1, 2) );
      homeTeamPanel.add( homeTeamLabel );
      homeTeamPanel.add( this.homeTeamComboBox );

      final JPanel teamStatusPanel = new JPanel();
      teamStatusPanel.setLayout( new GridLayout(1, 2) );
      teamStatusPanel.add( this.roadTeamStatusLabel );
      teamStatusPanel.add( this.homeTeamStatusLabel );

      final JPanel playerStatusPanel = new JPanel();
      playerStatusPanel.setLayout( new GridLayout(1, 2) );
      playerStatusPanel.add( this.roadTeamPlayerLabel );
      playerStatusPanel.add( this.homeTeamPlayerLabel );

      this.fileLocationLabel = new JLabel();

      contentPane.add( roadTeamPanel );
      contentPane.add( atLabel );
      contentPane.add( homeTeamPanel );
      contentPane.add( onLabel );
      contentPane.add( this.dateEntryField );
      contentPane.add( createGamesheetsButton );
      contentPane.add( teamStatusPanel );
      contentPane.add( playerStatusPanel );
      contentPane.add( this.fileLocationLabel );

      this.pack();
   }

   /** {@inheritDoc} */
   public void actionPerformed( final ActionEvent actionEvent )
   {
      if (actionEvent.getActionCommand().equals( CREATE_GAMESHEETS ))
      {
         try
         {
            final Calendar gameDate = getSelectedGameDate();
            final String homeTeamName = getSelectedHomeTeamName();
            final String roadTeamName = getSelectedRoadTeamName();

            this.roadTeamStatusLabel.setText( roadTeamName );
            this.homeTeamStatusLabel.setText( homeTeamName );

            final Thread thread = new Thread()
            {
               /** {@inheritDoc} */
               @Override
               public void run()
               {
                  try
                  {
                     final Gamesheets gamesheets = Ohl.getGamesheets(
                           homeTeamName, roadTeamName, gameDate,
                           CreateGamesheetsFrame.this );

                     final String directory = System.getProperty( "user.home" );
                     final File homeTeamGamesheetFile = new File(
                           directory, "home.html" );
                     final File roadTeamGamesheetFile = new File(
                           directory, "road.html" );
                     IOUtil.setContents( homeTeamGamesheetFile, gamesheets.
                           getHomeTeamGamesheet() );
                     IOUtil.setContents( roadTeamGamesheetFile, gamesheets.
                           getRoadTeamGamesheet() );
                     CreateGamesheetsFrame.this.fileLocationLabel.setText(
                           "<html>Gamesheets written to <i>" +
                           new File( directory ).getAbsolutePath() );
                  }
                  catch (final Exception e)
                  {
                     JOptionPane.showMessageDialog( CreateGamesheetsFrame.this,
                           e.getMessage() );
                     System.exit( 0 );
                  }
               }
            };
            thread.start();
         }
         catch (final Exception e)
         {
            JOptionPane.showMessageDialog( this, e.getMessage() );
            System.exit( 0 );
         }
      }
   }

   public void setHomeTeamPlayer( final String playerName )
   {
      final Runnable runnable = new Runnable()
      {
         /** {@inheritDoc} */
         public void run()
         {
            CreateGamesheetsFrame.this.homeTeamPlayerLabel.setText(
                  playerName );
         }
      };

      SwingUtilities.invokeLater( runnable );
   }

   public void setRoadTeamPlayer( final String playerName )
   {
      final Runnable runnable = new Runnable()
      {
         /** {@inheritDoc} */
         public void run()
         {
            CreateGamesheetsFrame.this.roadTeamPlayerLabel.setText(
                  playerName );
         }
      };

      SwingUtilities.invokeLater( runnable );
   }

   private String getSelectedHomeTeamName()
   {
      return (String) this.homeTeamComboBox.getSelectedItem();
   }

   private String getSelectedRoadTeamName()
   {
      return (String) this.roadTeamComboBox.getSelectedItem();
   }

   private Calendar getSelectedGameDate() throws ParseException
   {
      final Date selectedDate = this.dateEntryField.getValue();
      GregorianCalendar gameDate = new GregorianCalendar();
      gameDate.setTime( selectedDate );

      return gameDate;
   }

   public static void main( final String[] args ) throws Exception
   {
      final CreateGamesheetsFrame frame = new CreateGamesheetsFrame();
      frame.setVisible( true );
   }
}
