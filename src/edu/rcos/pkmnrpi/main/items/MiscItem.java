package edu.rcos.pkmnrpi.main.items;

import java.util.ArrayList;

public class MiscItem extends Item {

	public MiscItem() {}

	public MiscItem(ArrayList<String> data) {
		super(data);
	}

	public MiscItem(String data) {
		super(data + "," + Item.MISC);
	}

	@Override
	public String toString() {
		return null;
	}

	public static ArrayList<MiscItem> loadAll(ArrayList<String> data) {
		return null;
	}
}