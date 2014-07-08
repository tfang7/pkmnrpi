package pokedex;

import game.GameEngine;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.ImageLibrary;
import util.panels.PatternPanel;

/**
 * This class is in charge of listening to and animating the search bar of the
 * pokedex.
 */
public class PokedexSearchBar extends JPanel implements KeyListener, PokedexUI, MouseListener, ActionListener {
	public static final int pixel_height = 22;
	public static final String default_message = "Click here to search";

	private JLabel[] text;
	private int max, idx;
	private Pokedex pokedex;
	private String current = "";
	public int color = ImageLibrary.black;
	public PatternPanel background = new PatternPanel(0);
	public Timer time;
	public boolean flicker;

	public PokedexSearchBar(int width, Pokedex s) {
		super();
		time = new Timer(300, this);
		pokedex = s;
		max = width / 7 - 4;
		int gap = (width - 7 * max) / 2;
		// Count is the number of digets that can be displayed.
		assert (max > 0);
		setLayout(null);
		setPreferredSize(new Dimension(width, pixel_height));
		addMouseListener(this);

		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, max, 0, 0));
		center.setPreferredSize(new Dimension(width - 2 * gap, pixel_height - 6));
		center.setOpaque(false);

		text = new JLabel[max];
		for (int i = 0; i < max; ++i) {
			text[i] = new JLabel(blank);
			center.add(text[i]);
		}
		add(center);
		add(background);
		background.setBounds(0, 0, width, pixel_height);
		center.setBounds(gap, 3, width - 2 * gap, pixel_height - 6);
		setDefaultText();
	}

	public void keyPressed(KeyEvent e) {
		// Catch enter key
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			// System.out.println("ENTER");
			if (pokedex == null)
				return;
			pokedex.search(current);
			loseFocus();
		}
		// Catch backspace key
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			// System.out.println("BACKSPACE");
			if (idx <= 0)
				return;
			text[--idx].setIcon(blank);
			current = current.substring(0, current.length() - 1);
		}
		char c = e.getKeyChar();
		// System.out.println(c);
		int index = valid_chars.indexOf(c);
		if (index == -1)
			return;
		if (idx >= max - 1)
			return;
		// new TestFrame(ImageLibrary.text[color][index]);
		text[idx++].setIcon(ImageLibrary.text[color][index]);
		current += c;
	}

	// This replaces all text with blanks
	public void clearText() {
		current = "";
		idx = 0;
		for (JLabel l : text)
			l.setIcon(blank);
	}

	// This gains the key focus from the game engine, as well
	// as clearing text, and starting the flicker
	public void setFocus() {
		pokedex.engine.focusPokedex();
		clearText();
		flicker = true;
		time.start();
	}

	// This returns key focus to the game board, stops the flicker, and
	// sets the default text on the search bar.
	public void loseFocus() {
		time.stop();
		addMouseListener(this);
		pokedex.engine.focusBoard();
		setDefaultText();
	}

	// This clears all current text, then writes the default message onto
	// the search bar.
	public void setDefaultText() {
		clearText();
		current = default_message;
		for (char c : default_message.toCharArray()) {
			int index = valid_chars.indexOf(c);
			text[idx++].setIcon(ImageLibrary.text[color][index]);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		new GameEngine();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (flicker)
			text[idx].setIcon(black);
		else
			text[idx].setIcon(blank);
		flicker = !flicker;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.removeMouseListener(this);
		setFocus();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
