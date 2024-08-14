package mainPrograms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class TimerWindow extends JFrame implements ActionListener
{  
    private int start = 1;
    private JButton jbtn; 
    private JButton increment;
    private Timer swingtimer; 

    TimerWindow(int tm)
    {
        start += tm;
        setTitle("Timer Window");
        setLayout(new FlowLayout());
        setTimer();
        setSize(700,350);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setTimer()
    {
        jbtn = new JButton("Starting Timer...");
        add(jbtn);
        increment = new JButton("+ 2");
        increment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				start += 2;
	        }
		});
        add(increment);
        swingtimer = new Timer(1000, this);
        swingtimer.start();
    }
    public void actionPerformed(ActionEvent evnt)
    {
        start--;
        if(start>=1)
        {   
            int minutes = start / 60;
            int seconds = start % 60;
            String sec = "" + seconds;
            if(seconds < 10)
            {
                sec = "0" + sec;
            }
            jbtn.setText("Time : " + minutes + ":" + sec);
        }
        else
        {
            jbtn.setText("Time is up!");
            swingtimer.stop();
        }
    }
}

public class TimerInSwing 
{
    public static void main(String[] args) 
    {
        TimerWindow tw = new TimerWindow(120);
    }
}