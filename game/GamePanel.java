package game;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import animations.Sprite;

public class GamePanel extends JPanel {
	public ArrayList<Sprite> sprites;

	public GamePanel() {
		super();
		sprites = new ArrayList<Sprite>();
		setOpaque(false);
	}

	public void painter(Graphics g) {
		for (Sprite s : sprites)
			s.paint(g);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		for (Sprite s : sprites)
			s.paint(g);
	}
}
