package com.sm.gwid.desktop.ui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen extends JComponent implements ActionListener {

	public static interface Listener {
		boolean onLoginAttempt(String login, String password);
	}

	private static final String COMMAND_LOGIN = "login";
	
	private Listener listener;
	private JLabel loginLabel;
	private JTextField loginTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	
	public LoginScreen(Listener listener) {
		this.listener = listener;
	}
	
	private void initialize() {
		loginLabel = new JLabel("Login");
		passwordLabel = new JLabel("password");
		loginTextField = new JTextField("");
		passwordTextField = new JPasswordField("");
		
		loginButton = new JButton();
		loginButton.setText("Login");
		loginButton.setActionCommand(COMMAND_LOGIN);
		loginButton.addActionListener(this);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.weightx = 0.3d;
		gbc.weighty = 0.3d;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(loginLabel, gbc);
		
		gbc.weightx = 0.7d;
		gbc.weighty = 0.3d;
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.add(loginTextField, gbc);
		
		gbc.weightx = 0.3d;
		gbc.weighty = 0.3d;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(passwordLabel, gbc);
		
		gbc.weightx = 0.7d;
		gbc.weighty = 0.3d;
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add(passwordTextField, gbc);
		
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(loginButton, gbc);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand()!= null) {
			if (e.getActionCommand().equals(COMMAND_LOGIN)) {
				boolean success = listener.onLoginAttempt(loginTextField.getText(), passwordTextField.getText());
			}
		}
	}
	
	public static void main(String [] args) {
		LoginScreen ls = new LoginScreen(new Listener() {
			public boolean onLoginAttempt(String login, String password) {
				System.out.println("login:[" + login + "]\r\npassword:[" + password + "]");
				return false;
			}
		});
		ls.initialize();
		
		JFrame frame = new JFrame();
		frame.add(ls);
		frame.pack();
		frame.setVisible(true); 
		frame.setBounds(0, 0, 350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
}
