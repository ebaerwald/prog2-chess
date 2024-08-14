package grafics.board;

import mainPrograms.*;
import server_client.clientMain;
import server_client.serverMain;

import javax.swing.*;

import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SpielBrettFrame extends JFrame {

    //gesamter Frame
    private JPanel spielBrettPanel;
    private JPanel spielAnzeigePanel ;

    // spielAnzeigePanel
    private JPanel spielerEinsPanel;
    private JPanel spielerZweiPanel;
    private JPanel spielOptionenPanel;

    // spielOptionenPanel
    private JPanel spielOptionenButtonsPanel;
    private JPanel spielOptionenButtonsSubPanel;
    private JButton remisAnbietenButton;
    private JButton spielAufgebenButton;
    private JButton spielSpeichernButton;

    // spielBrettPanel
    private JPanel spielFeldPanel;
    private JPanel seitenBeschriftungBuchstabenPanel;
    private JLabel seitenBeschriftungBuchstabenLabel;
    private JPanel seitenBeschriftungZahlenPanel;
    private JButton[][] spielFeldButtons = new JButton[8][8];
    
    private String path;
    private String game;
    private boolean online;
    private String colour;
    private serverMain server = null;
    private clientMain client = null;
    private int[] startPosition;
    private int[] targetPosition;
    private int counter;
    private String changeColour;

    private String[][][] board = {
        {{"white", "black_rook.png", "0,0"}, {"black", "black_knight.png", "0,1"}, {"white", "black_bishop.png", "0,2"}, {"black", "black_queen.png", "0,3"}, {"white", "black_king.png", "0,4"}, {"black", "black_bishop.png", "0,5"}, {"white", "black_knight.png", "0,6"}, {"black", "black_rook.png", "0,7"}},
        {{"black", "black_pawn.png", "1,0"}, {"white", "black_pawn.png", "1,1"}, {"black", "black_pawn.png", "1,2"}, {"white", "black_pawn.png","1,3"}, {"black", "black_pawn.png", "1,4"}, {"white", "black_pawn.png", "1,5"}, {"black", "black_pawn.png", "1,6"}, {"white", "black_pawn.png", "1,7"}},
        {{"white", "", "2,0"}, {"black", "", "2,1"}, {"white", "", "2,2"}, {"black", "", "2,3"}, {"white", "", "2,4"}, {"black", "", "2,5"}, {"white", "", "2,6"}, {"black", "", "2,7"}},
        {{"black", "", "3,0"}, {"white", "", "3,1"}, {"black", "", "3,2"}, {"white", "", "3,3"}, {"black", "", "3,4"}, {"white", "", "3,5"}, {"black", "", "3,6"}, {"white", "", "3,7"}},
        {{"white", "", "4,0"}, {"black", "", "4,1"}, {"white", "", "4,2"}, {"black", "", "4,3"}, {"white", "", "4,4"}, {"black", "", "4,5"}, {"white", "", "4,6"}, {"black", "", "4,7"}},
        {{"black", "", "5,0"}, {"white", "", "5,1"}, {"black", "", "5,2"}, {"white", "", "5,3"}, {"black", "", "5,4"}, {"white", "", "5,5"}, {"black", "", "5,6"}, {"white", "", "5,7"}},
        {{"white", "white_pawn.png", "6,0"}, {"black", "white_pawn.png", "6,1"}, {"white", "white_pawn.png", "6,2"}, {"black", "white_pawn.png", "6,3"}, {"white", "white_pawn.png", "6,4"}, {"black", "white_pawn.png", "6,5"}, {"white", "white_pawn.png", "6,6"}, {"black", "white_pawn.png", "6,7"}},
        {{"black", "white_rook.png", "7,0"}, {"white", "white_knight.png", "7,1"}, {"black", "white_bishop.png", "7,2"}, {"white", "white_queen.png", "7,3"}, {"black", "white_king.png", "7,4"}, {"white", "white_bishop.png", "7,5"}, {"black", "white_knight.png", "7,6"}, {"white", "white_rook.png", "7,7"}}
        };

    public SpielBrettFrame(String path, String game, boolean online) throws IOException, ParseException {
        super("Schach");
        this.path = path;
        this.game = game;
        this.online = online;
        this.colour = "white";
    }

    public SpielBrettFrame(String path, String game, boolean online, String colour, serverMain server)
    {
        super("Schach");
        this.path = path;
        this.game = game;
        this.online = online;
        this.colour = colour;
        this.server = server;
    }

    public SpielBrettFrame(String path, String game, boolean online, String colour, clientMain client)
    {
        super("Schach");
        this.path = path;
        this.game = game;
        this.online = online;
        this.colour = colour;
        this.client = client;
    }

    public int[] run() throws IOException, ParseException
    {
        loadInterface();
        int[] move = new int[4];
        return move;
    }

    private void loadInterface() throws IOException, ParseException {
        initializeSpielBrettPanel();
        initializeSpielAnzeigePanel();

        this.setLayout(new BorderLayout());
		this.add(spielBrettPanel, BorderLayout.WEST);
		// this.add(spielAnzeigePanel, BorderLayout.EAST);
		
		this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void initializeSpielAnzeigePanel() throws IOException, ParseException {
    	initializeSpielerEinsPanel();
        initializeSpielerZweiPanel();
        initializeSpielOptionenPanel();

        spielAnzeigePanel = new JPanel(new BorderLayout());
        spielAnzeigePanel.add(spielerEinsPanel, BorderLayout.NORTH);
        spielAnzeigePanel.add(spielerZweiPanel, BorderLayout.SOUTH);
        // spielAnzeigePanel.add(spielOptionenPanel, BorderLayout.CENTER);
    }

    private void initializeSpielerEinsPanel() throws IOException, ParseException {
        
        String name = "Spieler 1";
        name = json.get_JSONDetail(path, game, "playerName");

        int zeitBegrenzung = 2;
        // zeitBegrenzung = Integer.parseInt(json.get_JSONDetail(path, game, "time"));

        int increment = 0;
        // increment = Integer.parseInt(json.get_JSONDetail(path, game, "incremet"));

        spielerEinsPanel = new SpielTimerPanel(zeitBegrenzung, increment, name);
    }

    private void initializeSpielerZweiPanel() throws IOException, ParseException {
    	
        String opponentName = "Spieler 2";
        opponentName = json.get_JSONDetail(path, game, "opponentName");

        int zeitBegrenzung = 2;
        // zeitBegrenzung = Integer.parseInt(json.get_JSONDetail(path, game, "zeitBegrenzung"));

        int increment = 0;
        // increment = Integer.parseInt(json.get_JSONDetail(path, game, "incremet"));

        spielerZweiPanel = new SpielTimerPanel(zeitBegrenzung, increment, opponentName);
    }

    private void initializeSpielOptionenPanel() {
        
        ImageIcon iconRemisAnbieten = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\remisAnbieten.png");
        remisAnbietenButton = new JButton(iconRemisAnbieten);
        
        ImageIcon iconSpielSpeichern = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\spielSpeichern.jpg");
        spielSpeichernButton = new JButton(iconSpielSpeichern);
        
        ImageIcon iconSpielAufgeben = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\spielAufgeben.jpg");
        spielAufgebenButton = new JButton(iconSpielAufgeben);

        spielOptionenButtonsSubPanel = new JPanel(new BorderLayout());
        spielOptionenButtonsSubPanel.add(remisAnbietenButton, BorderLayout.WEST);
        spielOptionenButtonsSubPanel.add(spielAufgebenButton, BorderLayout.CENTER);
        spielOptionenButtonsSubPanel.add(spielSpeichernButton, BorderLayout.EAST);

        spielOptionenButtonsPanel = new JPanel(new BorderLayout());
        spielOptionenButtonsPanel.add(spielOptionenButtonsSubPanel, BorderLayout.CENTER);
    }



    private void initializeSpielBrettPanel() {

        spielFeldPanel = new JPanel(new GridLayout(8, 8));

        if(colour.equals("black"))
        {
            switchBoard();
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                spielFeldButtons[i][j] = new JButton();
                spielFeldButtons[i][j].setPreferredSize(new Dimension(80, 80));
                if(board[i][j][0].equals("white"))
                {
                    spielFeldButtons[i][j].setBackground(new Color(245, 245, 220));
                }
                else
                {
                    spielFeldButtons[i][j].setBackground(new Color(180, 125, 73));
                }
                if(!board[i][j][1].equals(""))
                {
                    ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\" + board[i][j][1]);
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    icon.setDescription(board[i][j][1]);
                    spielFeldButtons[i][j].setIcon(icon);
                }
                spielFeldPanel.add(spielFeldButtons[i][j]);
            }
        }

        spielBrettPanel = new JPanel(new BorderLayout());
        spielBrettPanel.add(spielFeldPanel, BorderLayout.CENTER);
        spielBrettPanel.add(getSeitenBeschriftungBuchstabenPanel(), BorderLayout.SOUTH);
        spielBrettPanel.add(getSeitenBeschriftungZahlenPanel(), BorderLayout.WEST);
        addListener();
    }

    private JPanel getSeitenBeschriftungZahlenPanel(){
    	seitenBeschriftungZahlenPanel = new JPanel(new GridLayout(8, 1));
		
		for(int i=8;i>0;i--){
			seitenBeschriftungZahlenPanel.add(new JLabel(String.valueOf( (i) + " "),JLabel.RIGHT));	
		}
		
		seitenBeschriftungZahlenPanel.setPreferredSize(new Dimension(25, 50));
		return seitenBeschriftungZahlenPanel; 
	}

    private JPanel getSeitenBeschriftungBuchstabenPanel(){
    	seitenBeschriftungBuchstabenPanel = new JPanel(new BorderLayout());
		seitenBeschriftungBuchstabenLabel = new JLabel("                    A                        B                        C                        D                        E                        F                        G                        H");
		seitenBeschriftungBuchstabenLabel.setVerticalAlignment(JLabel.CENTER);
		seitenBeschriftungBuchstabenPanel.add(seitenBeschriftungBuchstabenLabel,BorderLayout.PAGE_START);
		seitenBeschriftungBuchstabenPanel.setPreferredSize(new Dimension(50, 25));
		
		return seitenBeschriftungBuchstabenPanel;	
	}

    public void getBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                ImageIcon icon = (ImageIcon) spielFeldButtons[i][j].getIcon();
                if(icon != null)
                {
                    board[i][j][1] = icon.getDescription();
                }
                else
                {
                    board[i][j][1] = "";
                }
            }
        }
    }

    public void switchBoard()
    {
        if(colour.equals("white"))
        {
            colour = "black";
            seitenBeschriftungBuchstabenLabel.setText("                    H                        G                        F                        E                        D                        C                       B                        A");
        }
        else
        {
            colour = "white";
            seitenBeschriftungBuchstabenLabel.setText("                    A                        B                        C                        D                        E                        F                        G                        H");
        }
        String[][][] b = new String[8][8][3];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                spielFeldButtons[i][j].setIcon(null);
                b[i][j] = board[i][j]; 
            }
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                board[i][j] = b[7 - i][7 - j];
            }
        }
    }

    public void addListener()
    {
        if(colour.equals("black"))
        {
            if(server != null)
            {
                try 
                {
                    String mov = server.waiting();
                    String[] m1 = mov.split(".");
                    int[] start = new int[2];
                    int[] target = new int[2];
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            if(board[i][j][2].equals(m1[0]))
                            {
                                start[0] = i;
                                start[1] = j;
                            }
                            if(board[i][j][2].equals(m1[1]))
                            {
                                target[0] = i;
                                target[1] = j;
                            }
                        }
                    }
                    spielFeldButtons[target[0]][target[1]].setIcon(spielFeldButtons[start[0]][start[1]].getIcon());
                    spielFeldButtons[start[0]][start[1]].setIcon(null);
                    getBoard();
                    updateBoard();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            else if(client != null)
            {
                try 
                {
                    String mov = client.waiting();
                    String[] m1 = mov.split(".");
                    int[] start = new int[2];
                    int[] target = new int[2];
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            if(board[i][j][2].equals(m1[0]))
                            {
                                start[0] = i;
                                start[1] = j;
                            }
                            if(board[i][j][2].equals(m1[1]))
                            {
                                target[0] = i;
                                target[1] = j;
                            }
                        }
                    }
                    spielFeldButtons[target[0]][target[1]].setIcon(spielFeldButtons[start[0]][start[1]].getIcon());
                    spielFeldButtons[start[0]][start[1]].setIcon(null);
                    getBoard();
                    updateBoard();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                } 
            }
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                int[] position = {i, j};
                spielFeldButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        counter += 1;
                        if(counter == 2)
                        {
                            counter = 0;
                            targetPosition = position;
                            if(changeColour.equals("white"))
                            {
                                spielFeldButtons[startPosition[0]][startPosition[1]].setBackground(new Color(180, 125, 73));
                            }
                            else
                            {
                                spielFeldButtons[startPosition[0]][startPosition[1]].setBackground(new Color(245, 245, 220));
                            }
                            try 
                            {
                                move1 m = new move1(board, startPosition, targetPosition, game, online);
                                String[][][] moveBoard = m.run();
                                if(moveBoard != null)
                                {
                                   board = moveBoard;
                                    if(!online)
                                    {
                                        if(json.get_JSONDetail(path, game, "status").equals("mate") || json.get_JSONDetail(path, game, "status").equals("patt"))
                                        {
                                            dispose();
                                        }
                                        switchBoard(); 
                                    }
                                    updateBoard();
                                    if(server != null)
                                    {
                                        server.send(board[startPosition[0]][startPosition[1]][2] + "." + board[targetPosition[0]][targetPosition[1]][2] + "." + json.get_JSONDetail(path, game, "status"));
                                        String mov = server.waiting();
                                        String[] m1 = mov.split(".");
                                        if(m1[2].equals("mate") || m1[2].equals("patt"))
                                        {
                                            dispose();
                                        }
                                        int[] start = new int[2];
                                        int[] target = new int[2];
                                        for(int i = 0; i < 8; i++)
                                        {
                                            for(int j = 0; j < 8; j++)
                                            {
                                                if(board[i][j][2].equals(m1[0]))
                                                {
                                                    start[0] = i;
                                                    start[1] = j;
                                                }
                                                if(board[i][j][2].equals(m1[1]))
                                                {
                                                    target[0] = i;
                                                    target[1] = j;
                                                }
                                            }
                                        }
                                        spielFeldButtons[target[0]][target[1]].setIcon(spielFeldButtons[start[0]][start[1]].getIcon());
                                        spielFeldButtons[start[0]][start[1]].setIcon(null);
                                        getBoard();
                                        updateBoard();
                                    }
                                    else if(client != null)
                                    {
                                        client.send(board[startPosition[0]][startPosition[1]][2] + "." + board[targetPosition[0]][targetPosition[1]][2]+ "." + json.get_JSONDetail(path, game, "status"));
                                        String mov = client.waiting();
                                        String[] m1 = mov.split(".");
                                        if(m1[2].equals("mate") || m1[2].equals("patt"))
                                        {
                                            dispose();
                                        }
                                        int[] start = new int[2];
                                        int[] target = new int[2];
                                        for(int i = 0; i < 8; i++)
                                        {
                                            for(int j = 0; j < 8; j++)
                                            {
                                                if(board[i][j][2].equals(m1[0]))
                                                {
                                                    start[0] = i;
                                                    start[1] = j;
                                                }
                                                if(board[i][j][2].equals(m1[1]))
                                                {
                                                    target[0] = i;
                                                    target[1] = j;
                                                }
                                            }
                                        }
                                        spielFeldButtons[target[0]][target[1]].setIcon(spielFeldButtons[start[0]][start[1]].getIcon());
                                        spielFeldButtons[start[0]][start[1]].setIcon(null);
                                        getBoard();
                                        updateBoard();
                                    }
                                }
                            } 
                            catch (IOException | ParseException e1) 
                            {  
                                e1.printStackTrace();
                            } 
                        }
                        else
                        {
                            startPosition = position;
                            if(board[startPosition[0]][startPosition[1]][0].equals("white"))
                            {
                                spielFeldButtons[position[0]][position[1]].setBackground(new Color(205,205,250));
                                changeColour = "black";
                            }
                            else
                            {
                                spielFeldButtons[position[0]][position[1]].setBackground(new Color(180,125,143));
                                changeColour = "white";
                            }
                            startPosition = position;
                        }
                    }
                });
            }
        }
    }

    public void updateBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[i][j][0].equals("white"))
                {
                    spielFeldButtons[i][j].setBackground(new Color(245, 245, 220));
                }
                else
                {
                    spielFeldButtons[i][j].setBackground(new Color(180, 125, 73));
                }
                if(!board[i][j][1].equals(""))
                {
                    ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\" + board[i][j][1]);
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    icon.setDescription(board[i][j][1]);
                    spielFeldButtons[i][j].setIcon(icon);
                }
            }
        }
    }
}
