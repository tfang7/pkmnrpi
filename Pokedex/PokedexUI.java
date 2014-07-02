package Pokedex;

import java.awt.Color;

import javax.swing.ImageIcon;

import util.ImageLibrary;

public interface PokedexUI {
	public static final ImageIcon blank = ImageLibrary.getSolidColor(Color.white, 7, 14);
	public static final String valid_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,.:$ ";
	public static final String bad_chars = "";
}