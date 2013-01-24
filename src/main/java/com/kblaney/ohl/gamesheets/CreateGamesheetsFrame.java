package com.kblaney.ohl.gamesheets;

import com.google.inject.Inject;
import com.kblaney.ohl.ProgressIndicator;
import com.kblaney.ohl.StatsProvider;
import com.kblaney.ohl.Team;
import com.kblaney.ohl.Teams;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

@SuppressWarnings("serial")
public final class CreateGamesheetsFrame extends JFrame implements ActionListener, ProgressIndicator
{
  static final String HOME_TEAM_COMBO_BOX_NAME = "HomeTeam";
  static final String ROAD_TEAM_COMBO_BOX_NAME = "RoadTeam";

  private final JLabel playerLabel;
  private final JComboBox roadTeamComboBox;
  private final JComboBox homeTeamComboBox;
  private final MDateEntryField dateEntryField;
  private final JLabel fileLocationLabel;
  private final GamesheetsGetter htmlGamesheetsGetter;
  private final GamesheetsWriter htmlGamesheetsWriter;
  private final Teams teams;
  private static final String CREATE_GAMESHEETS = "CreateGamesheets";

  @Inject
  public CreateGamesheetsFrame(final StatsProvider statsProvider, final GamesheetsGetter htmlGamesheetsGetter,
        final GamesheetsWriter htmlGamesheetsWriter) throws IOException
  {
    super("OHL Gamesheets");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.htmlGamesheetsGetter = htmlGamesheetsGetter;
    this.htmlGamesheetsWriter = htmlGamesheetsWriter;
    this.teams = statsProvider.getTeams();
    final String[] sortedTeamNames = getSortedTeamNames(teams);

    homeTeamComboBox = new JComboBox(sortedTeamNames);
    homeTeamComboBox.setName(HOME_TEAM_COMBO_BOX_NAME);
    homeTeamComboBox.setMaximumRowCount(sortedTeamNames.length);

    roadTeamComboBox = new JComboBox(sortedTeamNames);
    roadTeamComboBox.setName(ROAD_TEAM_COMBO_BOX_NAME);
    roadTeamComboBox.setMaximumRowCount(sortedTeamNames.length);

    final SimpleDateFormat simpleDateFormat = new MSimpleDateFormat("EEEE, MMMM d, yyyy");
    dateEntryField = new MDateEntryField(simpleDateFormat);
    final MDefaultPullDownConstraints defaultPullDownConstraints = new MDefaultPullDownConstraints();
    defaultPullDownConstraints.firstDay = Calendar.SUNDAY;
    defaultPullDownConstraints.todayBackground = Color.RED;
    dateEntryField.setConstraints(defaultPullDownConstraints);

    final JLabel atLabel = new JLabel("AT", JLabel.CENTER);
    final JLabel onLabel = new JLabel("ON", JLabel.CENTER);
    final JLabel roadTeamLabel = new JLabel("Road team:  ");
    final JLabel homeTeamLabel = new JLabel("Home team:  ");
    playerLabel = new JLabel();
    playerLabel.setHorizontalAlignment(JLabel.CENTER);

    final JButton createGamesheetsButton = new JButton("Create gamesheets!");
    createGamesheetsButton.setActionCommand(CREATE_GAMESHEETS);
    createGamesheetsButton.addActionListener(this);

    final int numRows = 8;
    final int numColumns = 1;
    final int horizontalGap = 0;
    final int verticalGap = 0;
    final GridLayout gridLayout = new GridLayout(numRows, numColumns, horizontalGap, verticalGap);
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
              final Gamesheets htmlGamesheets = htmlGamesheetsGetter.getGamesheets(homeTeam, roadTeam, gameDate,
                    CreateGamesheetsFrame.this);
              htmlGamesheetsWriter.write(htmlGamesheets);
              CreateGamesheetsFrame.this.fileLocationLabel.setText("<html>Gamesheets written to <i>" +
                    htmlGamesheetsWriter.getDescription());
            }
            catch (final Exception e)
            {
              JOptionPane.showMessageDialog(CreateGamesheetsFrame.this, e.getMessage());
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
    final String selectedHomeTeamName = (String) homeTeamComboBox.getSelectedItem();
    return teams.getTeamWithName(selectedHomeTeamName);
  }

  private Team getSelectedRoadTeam()
  {
    final String selectedRoadTeamName = (String) roadTeamComboBox.getSelectedItem();
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
