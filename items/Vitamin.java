package items;

import java.util.ArrayList;

public class Vitamin extends Item {

	public Vitamin() {}

	public Vitamin(ArrayList<String> data) {
		super(data);
	}

	public Vitamin(String data) {
		super(data + "," + Item.HOLD);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<Vitamin> loadAll(ArrayList<String> data) {
		return null;
	}
}
