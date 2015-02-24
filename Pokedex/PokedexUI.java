package Pokedex;

import java.awt.Font;

import javax.swing.ImageIcon;

public interface PokedexUI {
	public static final String bad_chars = "/";
	public static final ImageIcon portrait_icon = new ImageIcon("src/tilesets/pokedex_background.png");
	public static Font font = new Font("Pokemon GB", Font.TRUETYPE_FONT, 12);
	public static Font largefont = new Font("Pokemon GB", Font.TRUETYPE_FONT, 16);
	public static Font smallfont = new Font("Pokemon GB", Font.TRUETYPE_FONT, 9);
}
