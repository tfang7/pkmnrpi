package console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import util.Flag;
import util.Pair;

public class EditWindow extends JPanel implements MouseListener, MouseMotionListener {

	class Canvas extends JPanel {
		public static final int BW = 200;

		public BufferedImage projection;
		public int width, height;
		public EditWindow window;
		public Rectangle viewposition, drawposition;
		public long lastupdate;

		public Canvas(EditWindow w) {
			window = w;
		}

		private void update() {
			width = window.editor.tmap.mapdata[0].length * 16;
			height = window.editor.tmap.mapdata.length * 16;
			setPreferredSize(new Dimension(width, height));
			viewposition = window.view.getViewport().getViewRect();
			drawposition = bound(new Rectangle(viewposition.x - BW, viewposition.y - BW, viewposition.width + BW * 2, viewposition.height + BW * 2));
			projection = window.editor.tmap.getSubMap(drawposition);
			lastupdate = System.currentTimeMillis();
		}

		public void paint(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);

			if (System.currentTimeMillis() - lastupdate > 100)
				update();
			g.fillRect(drawposition.x, drawposition.y, drawposition.width, drawposition.height);
			g.drawImage(projection, drawposition.x, drawposition.y, null);

			if (menu.operating) {
				g.setColor(new Color(64, 128, 128, 128));
				g.fillRect(menu.start.x * 16, menu.start.y * 16, (menu.current.x - menu.start.x + 1) * 16, (menu.current.y - menu.start.y + 1) * 16);
			}
		}

		private Rectangle bound(Rectangle r) {
			int x = Math.max(0, Math.min(width - 1, r.x));
			int y = Math.max(0, Math.min(height - 1, r.y));
			int w = Math.max(1, Math.min(width - x, r.width));
			int h = Math.max(1, Math.min(height - y, r.height));
			return new Rectangle(x, y, w, h);
		}
	}

	public MapEditor editor;
	public JScrollPane view;
	public Canvas background;
	public ContextMenu menu;
	public int flag = 0;

	public EditWindow(MapEditor e) {
		editor = e;
		setLayout(null);
		background = new Canvas(this);
		menu = new ContextMenu(editor);

		view = new JScrollPane(background);
		view.getViewport().setViewPosition(new Point(0, 0));
		view.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		view.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		background.addMouseListener(this);
		background.addMouseListener(menu);
		background.addMouseMotionListener(this);

		add(view);
		view.setBounds(0, 0, 200, 200);
	}

	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		view.setBounds(0, 0, d.width, d.height);
	}

	public void repaint() {
		if (editor != null && editor.tmap != null && background != null) {
			background.update();
		}
		super.repaint();
	}

	private static boolean isIn(ArrayList<Point> ls, Point p) {
		for (Point o : ls) {
			if (o.x == p.x && o.y == p.y)
				return true;
		}
		return false;
	}

	public void fillFrom(Point p, Pair<String, Integer, Integer> pa) {
		ArrayList<Point> current = new ArrayList<Point>();
		current.add(p);
		Pair<String, Integer, Integer> type = editor.tmap.mapdata[p.y][p.x];
		ArrayList<Point> next = addNeighboors(p, current, type);
		while (!next.isEmpty()) {
			ArrayList<Point> extra = new ArrayList<Point>();
			for (Point a : next) {
				current.add(a);
				extra.addAll(addNeighboors(a, current, type));
			}
			next = extra;
		}

		for (Point a : current) {
			editor.tmap.mapdata[a.y][a.x] = editor.paint_bucket;
		}
	}

	public boolean isValid(Point p) {
		return p.x >= 0 && p.y >= 0 && p.x < editor.tmap.mapdata[0].length && p.y < editor.tmap.mapdata.length;
	}

	public ArrayList<Point> addNeighboors(Point p, ArrayList<Point> ls, Pair<String, Integer, Integer> type) {
		Point a = new Point(p.x + 1, p.y);
		Point b = new Point(p.x - 1, p.y);
		Point c = new Point(p.x, p.y + 1);
		Point d = new Point(p.x, p.y - 1);
		ArrayList<Point> next = new ArrayList<Point>();
		if (isValid(a) && !isIn(ls, a) && type.compareTo(editor.tmap.mapdata[a.y][a.x]) == 0) {
			ls.add(a);
			next.add(a);
		}
		if (isValid(b) && !isIn(ls, b) && type.compareTo(editor.tmap.mapdata[b.y][b.x]) == 0) {
			ls.add(b);
			next.add(b);
		}
		if (isValid(c) && !isIn(ls, c) && type.compareTo(editor.tmap.mapdata[c.y][c.x]) == 0) {
			ls.add(c);
			next.add(c);
		}
		if (isValid(d) && !isIn(ls, d) && type.compareTo(editor.tmap.mapdata[d.y][d.x]) == 0) {
			ls.add(d);
			next.add(d);
		}
		return next;
	}

	public void paintTo(int x, int y, Pair<String, Integer, Integer> p) {
		if (flag != 0) {
			if (flag == 1) {
				editor.tmap.centerx = x;
				editor.tmap.centery = y;
				flag = 0;
			} else if (flag == 2) {
				flag = 0;
				Flag f = new Flag(editor.tmap.name + "," + x + "," + y + ",0," + editor.clipboard);
				Flag.addFlag(f);
			} else if (flag == 4) {
				flag = 0;
				Flag f = new Flag(editor.tmap.name + "," + x + "," + y + ",1," + editor.doorx + ":" + editor.doory + ":" + editor.clipboard);
				Flag.addFlag(f);
			}
			return;
		}
		if (editor.bucketfill)
			fillFrom(new Point(x, y), p);
		editor.tmap.mapdata[y][x] = p;
		repaint();
	}

	public Point press = new Point(0, 0);

	private double radius(Point a) {
		return Math.sqrt((a.x - press.x) * (a.x - press.x) + (a.y - press.y) * (a.y - press.y));
	}

	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		if (radius(e.getPoint()) > 8)
			;
		int x = e.getX();
		int y = e.getY();
		if (x != 0)
			x /= 16;
		if (y != 0)
			y /= 16;
		if (y < 0 || x < 0 || y >= editor.tmap.mapdata.length || x >= editor.tmap.mapdata[0].length)
			return;
		paintTo(x, y, editor.paint_bucket);
	}

	public void mouseMoved(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		press = e.getPoint();
		int x = e.getX();
		int y = e.getY();
		if (x != 0)
			x /= 16;
		if (y != 0)
			y /= 16;
		if (y < 0 || x < 0 || y >= editor.tmap.mapdata.length || x >= editor.tmap.mapdata[0].length)
			return;
		paintTo(x, y, editor.paint_bucket);
		if (e.getClickCount() == 2) {
			fillFrom(new Point(x, y), editor.paint_bucket);
			fillFrom(new Point(x + 1, y), editor.paint_bucket);
			fillFrom(new Point(x, y + 1), editor.paint_bucket);
			fillFrom(new Point(x - 1, y), editor.paint_bucket);
			fillFrom(new Point(x, y - 1), editor.paint_bucket);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			return;
		press = e.getPoint();
	}
}