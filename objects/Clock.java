package objects;

import game.GameBoard;
import game.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * This class is used as a generic animation timer class. The reason for having
 * a generic class is to handle a large number of animations simultaniously
 * without lag
 */
public class Clock extends Timer implements ActionListener {
	public static final int FRAME_WAIT = 20;
	public static final int BACK = 0, FORE = 1;

	public boolean[] flags = { false, false };
	private int x, y, frames, sf, sx, sy, direction, inc;
	private GameBoard board;
	private GamePanel panel;
	private Person player;

	// Currently the instance of clock is never used, but the constructor is
	// necessary to provide a valid board class to access.
	public Clock(GameBoard b) {
		super(FRAME_WAIT, null);
		addActionListener(this);
		board = b;
	}

	// This is just an accessory method to ensure that multiple animations are
	// not attempted at once.
	public boolean isAnimating() {
		for (boolean b : flags)
			if (!b)
				return false;
		return true;
	}

	// This is specifically used for animating a player walking on screen
	public void animateSprite(GamePanel pan, Person p, int x, int y, int f, int dir) {
		sx = x;
		sy = y;
		sf = f;
		player = p;
		flags[FORE] = true;
		direction = dir;
		panel = pan;
		inc = 0;
		start();
	}

	// This method is used to animate the background movement, so that the
	// player
	// does not walk off screen.
	public void animateBackground(int x, int y, int f) {
		this.x = x;
		this.y = y;
		assert (f > 0);
		frames = f;
		flags[BACK] = true;
		start();
	}

	public void actionPerformed(ActionEvent e) {
		// Each if block handles a separate type of animateion.
		if (flags[BACK]) {
			// Calculate how many pixels to move this time.
			int a = x / frames;
			int b = y / frames;
			x -= a;
			y -= b;
			frames--;
			// If this is the last frame, reset the flag
			if (frames == 0) {
				flags[BACK] = false;
				// If there are no active flags, pause the timer
				if (is_alone(BACK))
					stop();
			}
			board.movePanel(a, b);
		}
		if (flags[FORE]) {
			// Calculate how many pixels to move this time.
			int a = sx / sf;
			int b = sy / sf;
			sx -= a;
			sy -= b;
			sf--;
			// If this is the last frame, reset the flag
			if (sf == 0) {
				flags[FORE] = false;
				// Additionally increment the x,y location of the player.
				if (direction == Person.UP) {
					player.y--;
				} else if (direction == Person.DOWN) {
					player.y++;
				} else if (direction == Person.RIGHT) {
					player.x++;
				} else if (direction == Person.LEFT) {
					player.x--;
				}
				// If there are no active flags, pause the timer
				if (is_alone(FORE))
					stop();
			}
			board.moveSprite(a, b, player.sprite);
			// Loop through the correct sprites for walking and direction
			if (direction == Person.UP) {
				player.sprite.setImage(player.walk[7 + inc++ % 3]);
			} else if (direction == Person.DOWN) {
				player.sprite.setImage(player.walk[4 + inc++ % 3]);
			} else if (direction == Person.RIGHT) {
				player.sprite.setImage(player.walk[inc++ % 2]);
			} else if (direction == Person.LEFT) {
				player.sprite.setImage(player.walk[2 + inc++ % 2]);
			}
			panel.repaint();
		}
	}

	private boolean is_alone(int i) {
		for (int j = 0; j < flags.length; ++j)
			if (j != i && flags[j])
				return false;
		return true;
	}
}
