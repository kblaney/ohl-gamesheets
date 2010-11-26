package com.kblaney.ohl.gamesheets;

import com.kblaney.ohl.Team;
import com.kblaney.ohl.Teams;
import com.kblaney.ohl.website.Website;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
import org.apache.commons.io.FileUtils;

final class CreateGamesheetsFrame extends JFrame
      implements ActionListener, ProgressIndicator
{
  private final JLabel playerLabel;
  private final JComboBox roadTeamComboBox;
  private final JComboBox homeTeamComboBox;
  private final MDateEntryField dateEntryField;
  private final JLabel fileLocationLabel;
  private final Teams teams;
  private static final String CREATE_GAMESHEETS = "CreateGamesheets";

  public CreateGamesheetsFrame() throws IOException
  {
    super("OHL Gamesheets");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    teams = new Website().getTeams();
    final String[] sortedTeamNames = getSortedTeamNames(teams);

    homeTeamComboBox = new JComboBox(sortedTeamNames);
    homeTeamComboBox.setMaximumRowCount(sortedTeamNames.length);

    roadTeamComboBox = new JComboBox(sortedTeamNames);
    roadTeamComboBox.setMaximumRowCount(sortedTeamNames.length);

    final SimpleDateFormat simpleDateFormat = new MSimpleDateFormat(
          "EEEE, MMMM d, yyyy");
    dateEntryField = new MDateEntryField(simpleDateFormat);
    final MDefaultPullDownConstraints defaultPullDownConstraints =
          new MDefaultPullDownConstraints();
    defaultPullDownConstraints.firstDay = Calendar.SUNDAY;
    defaultPullDownConstraints.todayBackground = Color.RED;
    dateEntryField.setConstraints(defaultPullDownConstraints);

    final JLabel atLabel = new JLabel("AT", JLabel.CENTER);
    final JLabel onLabel = new JLabel("ON", JLabel.CENTER);
    final JLabel roadTeamLabel = new JLabel("Road team:  ");
    final JLabel homeTeamLabel = new JLabel("Home team:  ");
    playerLabel = new JLabel();
    playerLabel.setHorizontalAlignment(JLabel.CENTER);

    final JButton createGamesheetsButton = new JButton(
          "Create gamesheets!");
    createGamesheetsButton.setActionCommand(CREATE_GAMESHEETS);
    createGamesheetsButton.addActionListener(this);

    final int numRows = 8;
    final int numColumns = 1;
    final int horizontalGap = 0;
    final int verticalGap = 0;
    final GridLayout gridLayout = new GridLayout(numRows, numColumns,
          horizontalGap, verticalGap);
    final Container contentPane = getContentPane();
    contentPane.setLayout(gridLayout);

    final JPanel roadTeamPanel = new JPanel();
    roadTeamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    roadTeamPanel.setLayout(new GridLayout(1, 2));
    roadTeamPanel.add(roadTeamLabel);
    roadTeamPanel.add(roadTeamComboBox);

    final JPanel homeTeamPanel = new JPanel();
    homeTeamLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    homeTeamPanel.setLayout(new GridLayout(1, 2));
    homeTeamPanel.add(homeTeamLabel);
    homeTeamPanel.add(homeTeamComboBox);

    fileLocationLabel = new JLabel();

    contentPane.add(roadTeamPanel);
    contentPane.add(atLabel);
    contentPane.add(homeTeamPanel);
    contentPane.add(onLabel);
    contentPane.add(dateEntryField);
    contentPane.add(createGamesheetsButton);
    contentPane.add(playerLabel);
    contentPane.add(fileLocationLabel);

    pack();
  }

  private String[] getSortedTeamNames(final Teams teams)
  {
    final List<String> sortedTeamNames = teams.getSortedTeamNames();
    return sortedTeamNames.toArray(new String[sortedTeamNames.size()]);
  }

  /** {@inheritDoc} */
  public void actionPerformed(final ActionEvent actionEvent)
  {
    if (actionEvent.getActionCommand().equals(CREATE_GAMESHEETS))
    {
      try
      {
        final Calendar gameDate = getSelectedGameDate();
        final Team homeTeam = getSelectedHomeTeam();
        final Team roadTeam = getSelectedRoadTeam();

        final Thread thread = new Thread()
        {
          /** {@inheritDoc} */
          @Override
          public void run()
          {
            try
            {
              final HtmlGamesheets htmlGamesheets =
                    new HtmlGamesheetsGetter().getGamesheets(
                    homeTeam, roadTeam, gameDate, CreateGamesheetsFrame.this);

              final String directory = System.getProperty("user.home");
              final File homeTeamGamesheetFile = new File(
                    directory, "home.html");
              final File roadTeamGamesheetFile = new File(
                    directory, "road.html");
              FileUtils.writeStringToFile(homeTeamGamesheetFile,
                    htmlGamesheets.getHomeTeamGamesheet());
              FileUtils.writeStringToFile(roadTeamGamesheetFile,
                    htmlGamesheets.getRoadTeamGamesheet());
              CreateGamesheetsFrame.this.fileLocationLabel.setText(
                    "<html>Gamesheets written to <i>" + new File(directory).
                    getAbsolutePath());
            }
            catch (final Exception e)
            {
              e.printStackTrace();
              JOptionPane.showMessageDialog(CreateGamesheetsFrame.this,
                    e.getMessage());
            }
          }
        };
        thread.start();
      }
      catch (final Exception e)
      {
        JOptionPane.showMessageDialog(this, e.getMessage());
      }
    }
  }

  private Team getSelectedHomeTeam()
  {
    final String selectedHomeTeamName =
          (String) homeTeamComboBox.getSelectedItem();
    return teams.getTeamWithName(selectedHomeTeamName);
  }

  private Team getSelectedRoadTeam()
  {
    final String selectedRoadTeamName =
          (String) roadTeamComboBox.getSelectedItem();
    return teams.getTeamWithName(selectedRoadTeamName);
  }

  private Calendar getSelectedGameDate() throws ParseException
  {
    final GregorianCalendar gameDate = new GregorianCalendar();
    gameDate.setTime(dateEntryField.getValue());
    return gameDate;
  }

  /** {@inheritDoc} */
  public void setPlayerInProgress(final String playerName)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      /** {@inheritDoc} */
      public void run()
      {
        CreateGamesheetsFrame.this.playerLabel.setText(playerName);
      }
    });
  }
}
