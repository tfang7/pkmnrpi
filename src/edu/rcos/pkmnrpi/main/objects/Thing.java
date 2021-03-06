package edu.rcos.pkmnrpi.main.objects;

import edu.rcos.pkmnrpi.main.animations.Sprite;

public abstract class Thing {
	public static final int NORTH = 0, WEST = 1, SOUTH = 2, EAST = 3;
	public int x, y;
	public int direction;
	public String mapname;
	public Sprite sprite;

	public abstract String toString();

	public static Thing loadFromID(String id) {
		return null;
	}
}
