package cog;

import java.awt.Graphics;

import javax.swing.JPanel;

public class SelectGamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SelectGamePanel() {
		setLayout(null);
	}
	public void paintComponent(Graphics g) {
		g.setColor(Main.selectGamePanelColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
