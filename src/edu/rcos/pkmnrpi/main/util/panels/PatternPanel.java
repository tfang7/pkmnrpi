package edu.rcos.pkmnrpi.main.util.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PatternPanel extends JPanel implements ComponentListener {
	public static final int TEXT_AREA = 0, NAME_AREA = 1, FIELD_AREA = 2, BATTLE_AREA = 3;
	public static final Color blackish = new Color(90, 90, 90);
	public static final Color redish = new Color(237, 49, 49);
	public static final Color pinkish = new Color(255, 156, 165);
	public int x = 100, y = 100, pattern, lbuf = 50;

	public PatternPanel(int style) {
		super();
		pattern = style;
		addComponentListener(this);
		setOpaque(false);
	}

	public void paint(Graphics g) {
		super.paintComponents(g);
		// Graphics2D g = (Graphics2D) gg;
		if (pattern == TEXT_AREA) {
			paintTextArea(g, x, y);
		} else if (pattern == NAME_AREA) {
			paintNameArea(g, x, y);
		} else if (pattern == FIELD_AREA) {
			paintFieldArea(g, x, y, lbuf);
		} else if (pattern == BATTLE_AREA) {
			paintBattleArea(g, x, y);
		}
	}

	public static void paintBattleArea(Graphics g, int x, int y) {
		int gap = 3;
		g.setColor(Color.black);
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		g.setColor(Color.white);
		gap = 4;
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		g.setColor(Color.black);
		gap = 5;
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		g.setColor(Color.white);
		gap = 6;
		g.fillRoundRect(gap, gap + 2, x - 2 * gap, y - 2 * gap - 2, 5, 5);
	}

	public static void paintFieldArea(Graphics g, int x, int y, int lbuf) {
		g.setColor(blackish);
		g.fillRoundRect(0, 0, x, y, 5, 5);
		g.setColor(Color.lightGray);
		int gap = 3;
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		int rbuf = 10;
		g.setColor(Color.white);
		g.fillRect(gap + lbuf + 2, gap + 1, x - 2 * gap - lbuf - rbuf - 4, y - 2 * gap - 2);
	}

	public static void paintNameArea(Graphics g, int x, int y) {
		g.setColor(blackish);
		g.fillRoundRect(0, 0, x, y, 5, 5);
		g.setColor(redish);
		int gap = 3;
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		g.setColor(pinkish);
		int lbuf = 50, rbuf = 10;
		g.fillRect(gap + lbuf, gap, x - 2 * gap - lbuf - rbuf, y - 2 * gap);
		g.setColor(Color.white);
		g.fillRect(gap + lbuf + 2, gap + 1, x - 2 * gap - lbuf - rbuf - 4, y - 2 * gap - 2);
	}

	public static void paintTextArea(Graphics g, int x, int y) {
		g.setColor(blackish);
		g.fillRoundRect(0, 0, x, y, 5, 5);
		g.setColor(redish);
		int gap = 3;
		g.fillRoundRect(gap, gap, x - 2 * gap, y - 2 * gap, 5, 5);
		g.setColor(pinkish);
		int buff = 10;
		g.fillRect(gap + buff, gap, x - 2 * gap - 2 * buff, y - 2 * gap);
		g.setColor(Color.white);
		g.fillRect(gap + buff + 2, gap + 1, x - 2 * gap - 2 * buff - 4, y - 2 * gap - 2);
	}

	public void componentHidden(ComponentEvent e) {}

	public void componentMoved(ComponentEvent e) {}

	public void componentResized(ComponentEvent e) {
		x = getWidth();
		y = getHeight();
		repaint();
	}

	public void componentShown(ComponentEvent e) {}

	public static void main(String[] args) {
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(400, 200, 300, 300);
		PatternPanel p = new PatternPanel(3);
		f.add(p);
		f.setVisible(true);

	}

}
