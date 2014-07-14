package pokemon.moves;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import pokemon.Pokemon;

//TODO: determine how to calculate status changes and special effects.
public class Move implements Comparable<Move> {
	public static Move[] all_moves;

	public int type, damage, pp, pp_max, lvl_req, tm;
	public String name, category, description, effect;
	public double crit_chance, hit_chance;

	public Move(ArrayList<String> data) {
		name = Pokemon.stripLabel(data.get(0));
		type = Pokemon.getType(Pokemon.stripLabel(data.get(1)));
		category = Pokemon.stripLabel(data.get(2));
		damage = Integer.parseInt(Pokemon.stripLabel(data.get(3)));
		hit_chance = Double.parseDouble(Pokemon.stripLabel(data.get(4))) / 100.0;
		// TODO crit_chance = ?
		// TODO TM#
		pp = pp_max = Integer.parseInt(Pokemon.stripLabel(data.get(5)));
		description = Pokemon.stripLabel(data.get(6));
		effect = Pokemon.stripLabel(data.get(7));
	}

	public static void init() {
		String filename = "src/data/Move_Data.txt";
		ArrayList<Move> all = new ArrayList<Move>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			ArrayList<String> lst = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if (Pokemon.isUniform(line, '-')) {
					if (lst.size() == 0)
						continue;
					all.add(new Move(lst));
					lst = new ArrayList<String>();
				} else
					lst.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		all_moves = order_moves(all);
	}

	private static Move[] order_moves(ArrayList<Move> all) {
		Collections.sort(all);
		Move[] mo = new Move[all.size()];
		int index = 0;
		for (Move m : all)
			mo[index++ ] = m;
		return mo;
	}

	public static ArrayList<Move> loadAll(ArrayList<String> data) {
		ArrayList<Move> all = new ArrayList<Move>();
		for (String str : data)
			all.add(lookup(Integer.parseInt(str)));
		return all;
	}

	public static Move lookup(int id) {
		for (Move m : all_moves)
			if (m.tm == id)
				return m;
		return null;
	}

	public static Move lookup(String name) {
		for (Move m : all_moves)
			if (m.name.equalsIgnoreCase(name))
				return m;
		return null;
	}

	@Override
	public int compareTo(Move a) {
		return name.compareTo(a.name);
	}
}
