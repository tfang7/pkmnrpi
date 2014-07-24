package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This is a database class designed specifically to hold the static sprite data. However this class also helps with some of the text organization.
 */
public class ImageLibrary extends Library {
	public static ImageIcon[] icons, front_sprites, back_sprites, small_sprites;
	public static int[] pixel_width = { 16, 16, 16, 16, 56, 56 };
	public static int[] start_counts = { 0, 700, 1137, 1304 };
	public static int[] icon_counts = { 699, 437, 167, 290, 251, 251 };
	public static String[] image_sheet_names = { "src/tilesets/misc_tiles.png", "src/tilesets/day_roofs.png", "src/tilesets/day_buildings.png", "src/tilesets/day_landscape.png",
			"src/tilesets/front_sprites.png", "src/tilesets/back_sprites.png" };
	public static BufferedImage[] image_sheets, player;
	public static boolean[] walk_tiles;

	public static final int DEFAULT_ICON = icon_counts[0];

	// This method simply creates a solid color background sprite.
	public static BufferedImage bufferSolidColor(Color c, int w, int h) {
		BufferedImage base = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = base.getGraphics();
		g.setColor(c);
		g.fillRect(0, 0, w, h);
		return base;
	}

	public static ImageIcon getSolidColor(Color c, int w, int h) {
		return new ImageIcon(bufferSolidColor(c, w, h));
	}

	public static ImageIcon getScaledIcon(ImageIcon i, int w, int h) {
		return new ImageIcon(i.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	// This method is used to ensure transparency for sprites and animations.
	public static BufferedImage removeBackground(BufferedImage base, int background_RGB) {
		int w = base.getWidth();
		int h = base.getHeight();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < w; ++i)
			for (int j = 0; j < h; ++j) {
				if (background_RGB == base.getRGB(i, j)) {
					image.setRGB(i, j, 0);
				} else
					image.setRGB(i, j, base.getRGB(i, j));
			}
		return image;
	}

	public static void init() {
		// Initilize the image sheet array
		image_sheets = new BufferedImage[image_sheet_names.length + 1];
		// Load each image sheet from the list of names
		ArrayList<ArrayList<BufferedImage>> lst = new ArrayList<ArrayList<BufferedImage>>();
		for (int i = 0; i < image_sheet_names.length; ++i) {
			// Load the image
			Image im = (new ImageIcon(image_sheet_names[i])).getImage();
			int w = im.getWidth(null), h = im.getHeight(null);
			// Create the new buffered image
			image_sheets[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			// Draw the image on to the buffered image
			image_sheets[i].getGraphics().drawImage(im, 0, 0, w, h, 0, 0, w, h, null);
			if (pixel_width[i] == 56)
				image_sheets[i] = removeBackground(image_sheets[i], bitwise_background_color);
			// Now the sheets are cut up into the icons
			lst.add(Tileizer.cutter(image_sheets[i], w, h, pixel_width[i], pixel_width[i], icon_counts[i]));
		}

		int total = 1;
		for (int i = 0; i < icon_counts.length; ++i) {
			// FOR DEBUGGING ONLY
			assert (icon_counts[i] == lst.get(i).size());
			total += icon_counts[i];
		}
		total -= 500;

		// Create icons, the array of image icon
		icons = new ImageIcon[total];
		int index = 0;
		for (BufferedImage im : lst.get(0))
			icons[index++ ] = new ImageIcon(im);
		icons[index++ ] = blank;
		icon_counts[0]++ ;
		for (BufferedImage im : lst.get(1))
			icons[index++ ] = new ImageIcon(im);
		for (BufferedImage im : lst.get(2))
			icons[index++ ] = new ImageIcon(im);
		for (BufferedImage im : lst.get(3))
			icons[index++ ] = new ImageIcon(im);

		// Create front sprites, an array of image icons
		front_sprites = new ImageIcon[icon_counts[4]]; // One for each pokemon
		for (int i = 0; i < icon_counts[4]; ++i)
			front_sprites[i] = new ImageIcon(lst.get(4).get(i));
		// Create back sprites, an array of image icons
		back_sprites = new ImageIcon[icon_counts[5]]; // One for each pokemon
		for (int i = 0; i < icon_counts[5]; ++i)
			back_sprites[i] = new ImageIcon(lst.get(5).get(i));

		// Load player sprites
		Image im = (new ImageIcon("src/tilesets/player_motion.png")).getImage();
		int w = im.getWidth(null);
		int h = im.getHeight(null);
		BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		buf.getGraphics().drawImage(im, 0, 0, null);
		buf = removeBackground(buf, bitwise_background_color);
		player = new BufferedImage[20];
		int temp = 0;
		for (BufferedImage b : Tileizer.cutter(buf, w, h, 16, 16, 20))
			player[temp++ ] = b;

		// Create the walk_tiles lookup table
		walk_tiles = new boolean[icons.length];
		for (int i = 0; i < icons.length; ++i)
			walk_tiles[i] = false;
		for (int i : valid_walk_tiles)
			walk_tiles[i] = true;
	}
}
