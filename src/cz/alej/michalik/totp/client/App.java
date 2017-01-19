package cz.alej.michalik.totp.client;

import java.awt.Dimension;
import javax.swing.*;

import cz.alej.michalik.totp.utility.TOTP;

public class App {

	public static void main(String[] args) {
		JFrame window = new JFrame("TOTP");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(250, 150));
		window.setLayout(new BoxLayout(window.getContentPane(), BoxLayout.Y_AXIS));
		window.setLocationByPlatform(true);
		
		JLabel pass = new JLabel("",JLabel.CENTER);
		pass.setFont(pass.getFont().deriveFont(64.0f));
		window.add(pass);
		window.setVisible(true);
		
		while (true) {
			pass.setText(new TOTP().setSecret("12345678901234567890".getBytes()).toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
