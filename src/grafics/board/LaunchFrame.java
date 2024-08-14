package grafics.board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LaunchFrame extends JFrame{

	private JPanel logoScreenPanel;
	private JLabel logoScreenLabel;
	private JPanel buttonPanel;
	private JPanel neuesSpielPanel;
	private JButton neuesSpielButton;
	
	public LaunchFrame() {
		super("Schach");
		loadInterface();
	}

	private void loadInterface() {
		initiliazeLogoScreenPanel();
		initializeButtonPanel();
		
		this.setLayout(new BorderLayout());
		this.add(logoScreenPanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
	}

	private void initializeButtonPanel() {
		neuesSpielButton = new JButton("Neues Spiel");
		neuesSpielButton.setBackground(Color.WHITE);
		neuesSpielButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				SpielEinstellungenFrame spielEinstellungenFrame = new SpielEinstellungenFrame();
	        }
		});
		
		neuesSpielPanel = new JPanel(new GridLayout(1, 1));
		neuesSpielPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 25));
		neuesSpielPanel.setBackground(Color.LIGHT_GRAY);
		neuesSpielPanel.add(neuesSpielButton);
		
		buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.setPreferredSize(new Dimension(600, 150));
		buttonPanel.add(neuesSpielPanel);
	}

	private void initiliazeLogoScreenPanel() {
		ImageIcon icon = new ImageIcon (System.getProperty("user.dir") + "\\src\\images\\LaunchFrame.jpeg");
		logoScreenLabel = new JLabel(icon);
		logoScreenPanel = new JPanel();
		logoScreenPanel.add(logoScreenLabel);
		logoScreenPanel.setPreferredSize(new Dimension(600, 250));
	}
}
