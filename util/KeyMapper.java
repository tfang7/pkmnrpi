package util;

import game.GameBoard;
import game.GameEngine;
import game.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import objects.Person;

/**
 * This class handles key events for the entire board. It should support key
 * re-mapping.
 */
public class KeyMapper implements KeyListener {
	public GameEngine engine;
	private GameBoard board;
	private Person player;
	public static HashMap<Integer, String> map;

	public KeyMapper(GameEngine e) {
		engine = e;
		board = e.board;
		player = board.player;
		load_default();
	}

	// This loads the default key mapping
	public static void load_default() {
		map = new HashMap<Integer, String>();
		map.put(Person.UP, "Up");
		map.put(Person.DOWN, "Down");
		map.put(Person.RIGHT, "Right");
		map.put(Person.LEFT, "Left");
	}

	// This provides a portal for users to change key mapping.
	public static void change_map() {
		// TODO: float a window that lets you change key bindings.
	}

	@Override
	public void keyPressed(KeyEvent e) {
		String in = KeyEvent.getKeyText(e.getKeyCode());
		// Only listen for events when animations are done.
		if (GameState.clock.isAnimating())
			return;
		if (in == map.get(Person.UP)) {
			// Check for obstruction
			if (!board.canMove(Person.UP)) {
				player.set_direction(Person.UP);
				board.foreground.repaint();
				return;
			}
			GameState.clock.animateSprite(board.foreground, player, 0, -GameBoard.tsize, 6, Person.UP);
			// Dont move the board if you aren't in the middle.
			if (player.y < board.map.mapdata.length - GameBoard.buffer)
				GameState.clock.animateBackground(0, -GameBoard.tsize, 6);
		} else if (in == map.get(Person.DOWN)) {
			// Check for obstruction
			if (!board.canMove(Person.DOWN)) {
				player.set_direction(Person.DOWN);
				board.foreground.repaint();
				return;
			}
			GameState.clock.animateSprite(board.foreground, player, 0, GameBoard.tsize, 6, Person.DOWN);
			// Dont move the board if you aren't in the middle.
			if (player.y > GameBoard.buffer)
				GameState.clock.animateBackground(0, GameBoard.tsize, 6);
		} else if (in == map.get(Person.RIGHT)) {
			// Check for obstruction
			if (!board.canMove(Person.RIGHT)) {
				player.set_direction(Person.RIGHT);
				board.foreground.repaint();
				return;
			}
			GameState.clock.animateSprite(board.foreground, player, GameBoard.tsize, 0, 6, Person.RIGHT);
			// Dont move the board if you aren't in the middle.
			if (player.x > GameBoard.buffer)
				GameState.clock.animateBackground(GameBoard.tsize, 0, 6);
		} else if (in == map.get(Person.LEFT)) {
			// Check for obstruction
			if (!board.canMove(Person.LEFT)) {
				player.set_direction(Person.LEFT);
				board.foreground.repaint();
				return;
			}
			GameState.clock.animateSprite(board.foreground, player, -GameBoard.tsize, 0, 6, Person.LEFT);
			// Dont move the board if you aren't in the middle.
			if (player.x < board.map.mapdata[0].length - GameBoard.buffer)
				GameState.clock.animateBackground(-GameBoard.tsize, 0, 6);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}