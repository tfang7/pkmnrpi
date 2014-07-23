package battle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pokemon.Pokemon;

public class BattleHUD extends JPanel {
	public static final int width = 220, height = 150;
	public static final Font font = new Font("Pokemon GB", Font.TRUETYPE_FONT, 20);
	public static final Font small = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public static final double[] xpos = { .03, .89, .92, .92, .87, .87, .82, .82, .15, .15, .03 };
	public static final double[] ypos = { .9, .9, .87, .65, .65, .84, .84, .88, .88, .83, .9 };
	public static final double[] xpos2 = { .92, .92, .9, .8, .8, .27, .27, .15, .15, .87, .87, .92 };
	public static final double[] ypos2 = { .7, .54, .5, .5, .56, .56, .5, .5, .58, .58, .7, .7 };
	public static final ImageIcon level = new ImageIcon("src/tilesets/level.png");
	public static final ImageIcon hp = new ImageIcon("src/tilesets/hp.png");
	public static final int offsetx = 0, offsety = 0;
	public static final Color blueish = new Color(28, 140, 255);
	public static final Color greenish = new Color(0, 187, 0);
	public static final Color redish = new Color(255, 3, 3);
	public static final Color yellowish = new Color(255, 169, 11);

	public Pokemon focus;
	public Polygon shape, hpbar;

	public BattleHUD() {
		super();
		setBackground(Color.white);
		int[] x = new int[xpos.length];
		int[] y = new int[ypos.length];
		for (int i = 0; i < xpos.length; ++i) {
			x[i] = (int) (xpos[i] * width) + offsetx;
			y[i] = (int) (ypos[i] * height) + offsety;
		}
		int[] a = new int[xpos2.length];
		int[] b = new int[ypos2.length];
		shape = new Polygon(x, y, x.length);
		for (int i = 0; i < xpos2.length; ++i) {
			a[i] = (int) (xpos2[i] * width) + offsetx;
			b[i] = (int) (ypos2[i] * height) + offsety;
		}
		hpbar = new Polygon(a, b, a.length);
		Dimension d = new Dimension(width, height);
		setPreferredSize(d);
		// setBackground(Color.blue.brighter());
	}

	public void drawHPBar(Graphics g) {
		if (focus.stats.max_health == 0 || focus.stats.current_health == 0)
			return;
		double dif = ((double) focus.stats.current_health) / ((double) focus.stats.max_health);
		g.setColor(greenish);
		if (dif <= .5)
			g.setColor(yellowish);
		else if (dif <= .2)
			g.setColor(redish);
		g.fillRect((int) (xpos2[5] * width), (int) ((ypos[5] - .32) * height), (int) (width * (xpos2[4] - xpos2[5]) * dif + .5), 3);
	}

	public void drawEXPBar(Graphics g) {
		int exp = focus.expToNextLevel();
		int next = Pokemon.levelToEXP(focus.stats.level, focus.stats.growth_rate);
		g.setColor(blueish);
		focus.stats.total_exp = Math.max(next, focus.stats.total_exp);
		focus.stats.exp = focus.stats.total_exp - next;
		int bitgap = (int) (width * xpos[6] - width * xpos[8]);
		int gap = exp - next;
		g.fillRect((int) (width * xpos[8] + bitgap * (1 - (double) focus.stats.exp / gap)), (int) (height * ypos[6]), (int) (bitgap * ((double) focus.stats.exp / gap) + .5), 3);
	}

	public void setPokemon(Pokemon p) {
		focus = p;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillPolygon(shape);
		if (focus == null)
			return;
		g.setFont(font);
		g.fillPolygon(hpbar);
		g.drawImage(hp.getImage(), (int) (xpos2[7] * width) + 5, (int) (ypos2[7] * height) + 3, null);
		g.drawString(focus.name.toUpperCase(), 20, 30);
		g.drawImage(level.getImage(), width / 2, (int) (height * .3), null);
		g.drawString("" + focus.stats.level, width / 2 + 20, (int) (height * .4));
		// g.setFont(small);
		String hp = focus.stats.current_health + "/ " + focus.stats.max_health;
		g.drawString(hp, width / 8 * 7 - (g.getFontMetrics().stringWidth(hp)), (int) (height * .75));
		drawHPBar(g);
		drawEXPBar(g);
	}
}
