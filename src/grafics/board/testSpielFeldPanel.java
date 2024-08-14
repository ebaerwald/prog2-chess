package grafics.board;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;

public class testSpielFeldPanel extends JFrame
{
    private int counter = 0;
    private int[] startPosition = new int[2];
    private int[] targetPosition = new int[2];
    private String[][][] board = new String[8][8][3];
    private String[][][] white_board = {
        {{"white", "black_rook.png", "0,0"}, {"black", "black_knight.png", "0,1"}, {"white", "black_bishop.png", "0,2"}, {"black", "black_queen.png", "0,3"}, {"white", "black_king.png", "0,4"}, {"black", "black_bishop.png", "0,5"}, {"white", "black_knight.png", "0,6"}, {"black", "black_rook.png", "0,7"}},
        {{"black", "black_pawn.png", "1,0"}, {"white", "black_pawn.png", "1,1"}, {"black", "black_pawn.png", "1,2"}, {"white", "black_pawn.png","1,3"}, {"black", "black_pawn.png", "1,4"}, {"white", "black_pawn.png", "1,5"}, {"black", "black_pawn.png", "1,6"}, {"white", "black_pawn.png", "1,7"}},
        {{"white", "", "2,0"}, {"black", "", "2,1"}, {"white", "", "2,2"}, {"black", "", "2,3"}, {"white", "", "2,4"}, {"black", "", "2,5"}, {"white", "", "2,6"}, {"black", "", "2,7"}},
        {{"black", "", "3,0"}, {"white", "", "3,1"}, {"black", "", "3,2"}, {"white", "", "3,3"}, {"black", "", "3,4"}, {"white", "", "3,5"}, {"black", "", "3,6"}, {"white", "", "3,7"}},
        {{"white", "", "4,0"}, {"black", "", "4,1"}, {"white", "", "4,2"}, {"black", "", "4,3"}, {"white", "", "4,4"}, {"black", "", "4,5"}, {"white", "", "4,6"}, {"black", "", "4,7"}},
        {{"black", "", "5,0"}, {"white", "", "5,1"}, {"black", "", "5,2"}, {"white", "", "5,3"}, {"black", "", "5,4"}, {"white", "", "5,5"}, {"black", "", "5,6"}, {"white", "", "5,7"}},
        {{"white", "white_pawn.png", "6,0"}, {"black", "white_pawn.png", "6,1"}, {"white", "white_pawn.png", "6,2"}, {"black", "white_pawn.png", "6,3"}, {"white", "white_pawn.png", "6,4"}, {"black", "white_pawn.png", "6,5"}, {"white", "white_pawn.png", "6,6"}, {"black", "white_pawn.png", "6,7"}},
        {{"black", "white_rook.png", "7,0"}, {"white", "white_knight.png", "7,1"}, {"black", "white_bishop.png", "7,2"}, {"white", "white_queen.png", "7,3"}, {"black", "white_king.png", "7,4"}, {"white", "white_bishop.png", "7,5"}, {"black", "white_knight.png", "7,6"}, {"white", "white_rook.png", "7,7"}}
        };
    private String[][][] black_board = new String[8][8][3];
    
    private JPanel spielbrett;
    private JButton[][] spielFeldButtons = new JButton[8][8];

    public testSpielFeldPanel()
    {
        black_board();
        r();
        this.add(spielbrett);
        this.pack();
        this.setVisible(true);
    }
    public static void main(String[] args) 
    {
        testSpielFeldPanel r1 = new testSpielFeldPanel();
    }

    public void black_board()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                black_board[i][j] = white_board[7 - i][7 - j];
            }
        }
    }

    public void r()
    {
        board = black_board;
        spielbrett = new JPanel(new GridLayout(8, 8));
        for(int i=0; i<8; i++) {
            for(int j=0; j<8; j++) {
                spielFeldButtons[i][j] = new JButton();
                spielFeldButtons[i][j].setPreferredSize(new Dimension(80, 80));
                String[] element = board[i][j];
                if(!element[1].equals(""))
                {
                    ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "\\src\\images\\" + element[1]);
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);
                    spielFeldButtons[i][j].setIcon(icon);
                }

                if(element[0].equals("black")) 
                {
                    spielFeldButtons[i][j].setBackground(new Color(180, 125, 73));
                }
                else 
                {
                    spielFeldButtons[i][j].setBackground(new Color(245, 245, 220));
                }
                JButton button = spielFeldButtons[i][j];
                spielFeldButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!element[1].equals("") || counter == 1)
                        {
                            count();
                            String[] elmt = element[2].split(",");
                            if(counter == 2)
                            {
                                counter = 0;
                                targetPosition[0] = Integer.parseInt(elmt[0]);
                                targetPosition[1] = Integer.parseInt(elmt[1]);
                            }
                            else
                            {
                                startPosition[0] = Integer.parseInt(elmt[0]);
                                startPosition[1] = Integer.parseInt(elmt[1]);
                                if(element[0].equals("black"))
                                {
                                    button.setBackground(new Color(180,125,123));
                                }
                                else
                                {
                                    button.setBackground(new Color(205,205,230));
                                }
                            }
                        }
                    }
                });
                spielbrett.add(spielFeldButtons[i][j]);
            }
        }
    }

    public void count()
    {
        counter += 1;
    }
}