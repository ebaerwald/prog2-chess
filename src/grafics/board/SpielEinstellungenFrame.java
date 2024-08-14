package grafics.board;

import javax.imageio.ImageIO;
import javax.swing.*;

import grafics.SpringUtilities;
import mainPrograms.run;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpielEinstellungenFrame extends JFrame {

    private JPanel einstellungenPanel;

    private JPanel bannerPanel;
    private JPanel bannerSubPanel;
    private JLabel bannerLabel;
    private JPanel bannerButtonsPanel;
    private JPanel gameModePanel;
    private JRadioButton onlineRadioButton;
    private JRadioButton offlineRadioButton;

    private JPanel settingsPanel;

    private JPanel gameSettingsPanel;
    private JPanel gameSettingsSubPanel;
    private JPanel timerSettingsPanel;
    private JLabel timerModeLabel;
    private JPanel timerModeRadioButtonsPanel;
    private JRadioButton countdownRadioButton;
    private JRadioButton stopwatchRadioButton;
    private JPanel timeLimitPanel;
    private JFormattedTextField timeLimitFormattedTextField;
    private JLabel timeLimitUnitLabel;
    private JLabel timeLimitLabel;
    private JPanel timeIncrementPanel;
    private JLabel timeIncrementUnitLabel;
    private JFormattedTextField timeIncrementFormattedTextField;
    private JLabel timeIncrementLabel;

    private JPanel networkSettingsPanel;
    private JPanel networkSettingsSubPanel;
    private JPanel networkModePanel;
    private JRadioButton hostGameRadioButton;
    private JRadioButton joinGameRadioButton;
    private JPanel playerNamePanel;
    private JLabel playerNameLabel;
    private JTextField playerNameTextField;
    private JPanel connectionPanel;
    private JPanel ipAndPortPanel;
    private JLabel hostIPLabel;
    private JTextField hostIPTextField;
    private JLabel hostPortLabel;
    private JFormattedTextField hostPortFormattedTextField;

    private JPanel colourSelectionPanel;
    private JPanel whiteButtonPanel;
    private JPanel randomButtonPanel;
    private JPanel blackButtonPanel;
    private JButton white;
    private JButton black;
    private JButton random;
    private JButton ok;
    private JButton cancel;

    private int online = 0;
    private int host = 0;
    private int timeLimit = 0;
    private int counter = 0;

    public SpielEinstellungenFrame() {
        super("Spiel-Einstellungen");
        loadInterface();
        
    }

    private void loadInterface() {
        initializeBannerPanel();
        initializeSettingsPanel();

        einstellungenPanel = new JPanel(new BorderLayout());
        einstellungenPanel.add(bannerPanel, BorderLayout.PAGE_START);
        einstellungenPanel.add(settingsPanel, BorderLayout.CENTER);
        einstellungenPanel.setPreferredSize(new Dimension(600, 400));

        this.add(einstellungenPanel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
    
    private void initializeBannerPanel() {
    	bannerPanel = new JPanel();
    	bannerPanel.setLayout(new BorderLayout());
    	bannerPanel.setPreferredSize(new Dimension(600, 150));
    	bannerPanel.setBackground(Color.LIGHT_GRAY);
    	
    	initializeBanner();
    	bannerPanel.add(bannerSubPanel, BorderLayout.NORTH);
    	
    	initializeButtons();
    	bannerPanel.add(bannerButtonsPanel, BorderLayout.SOUTH);
    	
    }
    
    private void initializeBanner() {
    	ImageIcon icon = new ImageIcon (System.getProperty("user.dir") + "\\src\\images\\Spieleinstellungen.jpeg");
    	bannerLabel = new JLabel(icon);
    	bannerSubPanel = new JPanel();
    	
    	bannerSubPanel.add(bannerLabel);
        bannerSubPanel.setPreferredSize(new Dimension(600, 120)); 
        bannerSubPanel.setBackground(Color.LIGHT_GRAY);
    	}

    private void initializeButtons() {
    	onlineRadioButton = new JRadioButton("Online");
        onlineRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameSettingsEnabled(!joinGameRadioButton.isSelected());
                setNetworkSettingsEnabled(true);
                if(counter == 0)
                {
                    addOkButton();
                    counter = 1;
                }
                setColourSelectionVisible(true);
                online = 1;
            }
        });
        offlineRadioButton = new JRadioButton("Offline");
        offlineRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameSettingsEnabled(true);
                setNetworkSettingsEnabled(false);
                if(counter == 0)
                {
                    addOkButton();
                    counter = 1;
                }
                setColourSelectionVisible(false);
                online = 2;
            }
        });
        ButtonGroup gameModeButtonGroup = new ButtonGroup();
        gameModeButtonGroup.add(onlineRadioButton);
        gameModeButtonGroup.add(offlineRadioButton);
        gameModePanel = new JPanel();
        gameModePanel.add(onlineRadioButton);
        gameModePanel.add(offlineRadioButton);
        gameModePanel.setBackground(Color.LIGHT_GRAY);
        
        bannerButtonsPanel = new JPanel(new BorderLayout());
        bannerButtonsPanel.setPreferredSize(new Dimension(600, 30));
        bannerButtonsPanel.setBackground(Color.LIGHT_GRAY);
        bannerButtonsPanel.add(gameModePanel, BorderLayout.WEST);
    }

	private void initializeSettingsPanel() {
        initializeGameSettingsPanel();
        initializeNetworkSettingsPanel();
        initializeColourSelectionPanel();

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        settingsPanel.add(gameSettingsPanel);
        settingsPanel.add(networkSettingsPanel);
        settingsPanel.add(colourSelectionPanel);
    }

    private void initializeGameSettingsPanel() {
        // timer settings
        timerModeLabel = new JLabel("Zeitbegrenzung: ");
        countdownRadioButton = new JRadioButton("begrenzt");
        countdownRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLimitPanel.setVisible(true);
                timeIncrementPanel.setVisible(true);
                timeLimit = 1;
            }
        });
        stopwatchRadioButton = new JRadioButton("unbegrenzt");
        stopwatchRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLimitPanel.setVisible(false);
                timeIncrementPanel.setVisible(false);
                timeLimit = 2;
            }
        });
        ButtonGroup timerModeButtonGroup = new ButtonGroup();
        timerModeButtonGroup.add(countdownRadioButton);
        timerModeButtonGroup.add(stopwatchRadioButton);
        timeLimitUnitLabel = new JLabel("Zeit:  ");
        timeLimitFormattedTextField = new JFormattedTextField(10);
        timeLimitLabel = new JLabel(" min  ");
        timeLimitPanel = new JPanel(new BorderLayout());
        timeLimitPanel.add(timeLimitUnitLabel, BorderLayout.WEST);
        timeLimitPanel.add(timeLimitFormattedTextField);
        timeLimitPanel.add(timeLimitLabel, BorderLayout.EAST);
        timeLimitPanel.setVisible(false);
        timeIncrementUnitLabel = new JLabel("|  Ink: ");
        timeIncrementFormattedTextField = new JFormattedTextField(0);
        timeIncrementLabel = new JLabel(" sec");
        timeIncrementPanel = new JPanel(new BorderLayout());
        timeIncrementPanel.add(timeIncrementUnitLabel, BorderLayout.WEST);
        timeIncrementPanel.add(timeIncrementFormattedTextField);
        timeIncrementPanel.add(timeIncrementLabel, BorderLayout.EAST);
        timeIncrementPanel.setVisible(false);
        timerModeRadioButtonsPanel = new JPanel(new GridLayout(1, 4));
        timerModeRadioButtonsPanel.add(stopwatchRadioButton);
        timerModeRadioButtonsPanel.add(countdownRadioButton);
        timerModeRadioButtonsPanel.add(timeLimitPanel);
        timerModeRadioButtonsPanel.add(timeIncrementPanel);

        timerSettingsPanel = new JPanel(new SpringLayout());
        timerModeLabel.setLabelFor(timerModeRadioButtonsPanel);
        timerSettingsPanel.add(timerModeLabel);
        timerSettingsPanel.add(timerModeRadioButtonsPanel);
        SpringUtilities.makeCompactGrid(timerSettingsPanel, 1, 2, 8, 0, 0, 0);

        gameSettingsSubPanel = new JPanel(new BorderLayout());
        gameSettingsSubPanel.add(timerSettingsPanel, BorderLayout.PAGE_END);
        gameSettingsPanel = new JPanel();
        gameSettingsPanel.add(gameSettingsSubPanel);
        gameSettingsPanel.setBorder(BorderFactory.createTitledBorder("Spiel-Einstellungen"));

    }

    private void initializeNetworkSettingsPanel() {
        // network mode
        joinGameRadioButton = new JRadioButton("Beitreten");
        joinGameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameSettingsEnabled(false);
                hostIPTextField.setEditable(true);
                hostIPTextField.setText("");
                onlineRadioButton.setSelected(true);
                if(counter == 0)
                {
                    addOkButton();
                    counter = 1;
                }
                setColourSelectionVisible(false);
                host = 2;
                online = 1;
            }
        });
        hostGameRadioButton = new JRadioButton("Hosten");
        hostGameRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String ip = "";
                try 
                {
                    InetAddress ip1 = InetAddress.getLocalHost();
                    ip += ip1;
                    String[] ip2 = ip.split("/");
                    ip = ip2[1];
                } 
                catch (UnknownHostException e1) 
                { 
                    e1.printStackTrace();
                }
                setGameSettingsEnabled(true);
                hostIPTextField.setText(ip);
                hostIPTextField.setEditable(false);
                onlineRadioButton.setSelected(true);
                if(counter == 0)
                {
                    addOkButton();
                    counter = 1;
                }
                setColourSelectionVisible(true);
                host = 1;
                online = 1;
            }
        });
        ButtonGroup networkModeButtonGroup = new ButtonGroup();
        networkModeButtonGroup.add(joinGameRadioButton);
        networkModeButtonGroup.add(hostGameRadioButton);
        playerNameLabel = new JLabel("Name:");
        playerNameTextField = new JTextField(6);
        playerNameLabel.setLabelFor(playerNameTextField);
        playerNamePanel = new JPanel();
        playerNamePanel.add(playerNameLabel);
        playerNamePanel.add(playerNameTextField);
        networkModePanel = new JPanel();
        networkModePanel.add(joinGameRadioButton);
        networkModePanel.add(hostGameRadioButton);
        networkModePanel.add(playerNamePanel);

        // connection panel
        hostIPLabel = new JLabel("Host IP:");
        hostIPTextField = new JTextField(10);
        hostPortLabel = new JLabel(":");
        hostPortFormattedTextField = new JFormattedTextField("9332");
        hostPortFormattedTextField.setEditable(false);
        connectionPanel = new JPanel(new SpringLayout());

        ipAndPortPanel = new JPanel();
        ipAndPortPanel.add(hostIPTextField);
        ipAndPortPanel.add(hostPortLabel);
        ipAndPortPanel.add(hostPortFormattedTextField);
        hostIPLabel.setLabelFor(ipAndPortPanel);

        connectionPanel.add(hostIPLabel);
        connectionPanel.add(ipAndPortPanel);
        SpringUtilities.makeCompactGrid(connectionPanel, 1, 2, 8, 0, 0, 0);

        networkSettingsSubPanel = new JPanel(new BorderLayout());
        networkSettingsSubPanel.add(networkModePanel, BorderLayout.PAGE_START);
        networkSettingsSubPanel.add(connectionPanel, BorderLayout.CENTER);
        networkSettingsPanel = new JPanel();
        networkSettingsPanel.add(networkSettingsSubPanel);
        networkSettingsPanel.setBorder(BorderFactory.createTitledBorder("Netzwerk-Einstellungen"));
    }

    private void initializeColourSelectionPanel()
    {
        ImageIcon blackIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\black_king.png");
        Image blackImage = blackIcon.getImage();
        Image blackNewimg = blackImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        blackIcon = new ImageIcon(blackNewimg);
        black = new JButton(blackIcon);
        black.setBackground(Color.WHITE);
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input i = new input(host, online, timeLimit, playerNameTextField.getText() , hostIPTextField.getText(), timeLimitFormattedTextField.getText(), timeIncrementFormattedTextField.getText(), "black");
                if (i.checkSettings())
                {
                    dispose();
                    String game = i.safeSettings();
                    run r = new run();
                    r.secondMain(game);
                }
            }
        });

        blackButtonPanel = new JPanel();
        blackButtonPanel.add(black);

        ImageIcon randomIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\ColourSelection.png");
        Image randomImage = randomIcon.getImage();
        Image randomNewimg = randomImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        randomIcon = new ImageIcon(randomNewimg);
        random = new JButton(randomIcon);
        random.setBackground(Color.WHITE);
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input i = new input(host, online, timeLimit, playerNameTextField.getText() , hostIPTextField.getText(), timeLimitFormattedTextField.getText(), timeIncrementFormattedTextField.getText(), "random");
                if (i.checkSettings())
                {
                    dispose();
                    String game = i.safeSettings();
                    run r = new run();
                    r.secondMain(game);
                }
            }
        });

        randomButtonPanel = new JPanel();
        randomButtonPanel.add(random);

        ImageIcon whiteIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\white_king.png");
        Image whiteImage = whiteIcon.getImage();
        Image whiteNewimg = whiteImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        whiteIcon = new ImageIcon(whiteNewimg);
        white = new JButton(whiteIcon);
        white.setBackground(Color.WHITE);
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input i = new input(host, online, timeLimit, playerNameTextField.getText() , hostIPTextField.getText(), timeLimitFormattedTextField.getText(), timeIncrementFormattedTextField.getText(), "white");
                if (i.checkSettings())
                {
                    dispose();
                    String game = i.safeSettings();
                    run r = new run();
                    r.secondMain(game);
                }
            }
        });

        whiteButtonPanel = new JPanel();
        whiteButtonPanel.add(white);

        colourSelectionPanel = new JPanel();
        colourSelectionPanel.add(whiteButtonPanel);
        colourSelectionPanel.add(randomButtonPanel);
        colourSelectionPanel.add(blackButtonPanel);
    }

    private void setGameSettingsEnabled(boolean b) {
        timerModeLabel.setEnabled(b);
        stopwatchRadioButton.setEnabled(b);
        countdownRadioButton.setEnabled(b);
        timeLimitFormattedTextField.setEnabled(b);
        timeLimitUnitLabel.setEnabled(b);
        timeLimitPanel.setEnabled(b);
        timeLimitLabel.setEnabled(b);
        timeIncrementUnitLabel.setEnabled(b);
        timeIncrementFormattedTextField.setEnabled(b);
        timeIncrementLabel.setEnabled(b);
        timeIncrementPanel.setEnabled(b);
        gameSettingsPanel.setEnabled(b);
    }

    private void setNetworkSettingsEnabled(boolean b) {
        joinGameRadioButton.setEnabled(b);
        hostGameRadioButton.setEnabled(b);
        playerNameLabel.setEnabled(b);
        playerNameTextField.setEnabled(b);
        hostIPLabel.setEnabled(b);
        hostIPTextField.setEnabled(b);
        hostIPTextField.setEditable(b);
        hostPortLabel.setEnabled(b);
        hostPortFormattedTextField.setEnabled(b);
        networkSettingsPanel.setEnabled(b);
    }

    public void addOkButton()
    {
        cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LaunchFrame lFrame = new LaunchFrame();
            }
        });
        ok = new JButton("ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                input i = new input(host, online, timeLimit, playerNameTextField.getText() , hostIPTextField.getText(), timeLimitFormattedTextField.getText(), timeIncrementFormattedTextField.getText(), "undifined");
                if (i.checkSettings())
                {
                    dispose();
                    String game = i.safeSettings();
                    run r = new run();
                    r.secondMain(game);
                }
            }
        });
        colourSelectionPanel.add(cancel);
        colourSelectionPanel.add(ok);
    }

    public void setColourSelectionVisible(boolean b)
    {
        white.setVisible(b);
        random.setVisible(b);
        black.setVisible(b);
        cancel.setVisible(!b);
        ok.setVisible(!b);
    }
}

