package edu.rcos.pkmnrpi.main.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import edu.rcos.pkmnrpi.main.objects.TileMap;

public class Library {
	public static final ImageIcon blank = new ImageIcon("data/blank.png");
	public static final Color background_color = new Color(34, 134, 34);
	public static final int bitwise_background_color = background_color.getRGB();
	public static final int GRAY = 0, GREEN = 1, BLACK = 2, VIOLET = 3, YELLOW = 4, BROWN = 5, RED = 6, LAVENDER = 7, PURPLE = 8, LIME = 9, GOLD = 10, ICE = 11, BEIGE = 12,
			MAGENTA = 13, PINK = 14, SAND = 15, WHITE = 16, SKY = 17, gray = GRAY, green = GREEN, violet = VIOLET, yellow = YELLOW, brown = BROWN, red = RED, lavender = LAVENDER,
			purple = PURPLE, lime = LIME, gold = GOLD, ice = ICE, beige = BEIGE, magenta = MAGENTA, pink = PINK, sand = SAND, white = WHITE, sky = SKY;
	public static Map<Integer, Integer> national_numbers = new HashMap<Integer, Integer>();

	public static void init() {
		List<String> data = FileParser.parseFile("data/game/number_map.txt");
		for (String line : data) {
			String[] a = line.split(",");
			national_numbers.put(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
		}
		// Load All Maps Here
		new TileMap("data/maps/ALL.map");
	}
}