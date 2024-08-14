package grafics.board;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;

public class SpielTimerPanel extends JPanel implements ActionListener {
    
    private int zeit;
    private int zeitInSec;
    private Timer timer;
    private JButton timerButton;

    SpielTimerPanel(int zeitBegrenzung, int increment, String name) {

        zeitInSec = zeitBegrenzung / 60;
        zeit += zeitInSec;
        setName(name);
        setLayout(new BorderLayout());
        setTimer();
        setSize(600, 300);
        setVisible(true);
        add(timerButton, BorderLayout.CENTER);
    }

    private void setTimer() {
        
        timerButton = new JButton();
        timerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //switch zwischen Spielern + increment
            }
        });
        timer = new Timer(1000, this);
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        zeit--;
        if(zeit>=1) {
            int minuten = zeit / 60;
            int sekunden = zeit % 60;
            String sec ="" + sekunden;

            if(sekunden < 10) {
                sec = "0" + sec;
            }

            timerButton.setText("Zeit:" + minuten+ ":"+ sec );
        }else {
            timerButton.setText("00:00");
            timer.stop();
        }
    }
}
