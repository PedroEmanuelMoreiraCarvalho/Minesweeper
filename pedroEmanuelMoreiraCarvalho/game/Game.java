package pedroEmanuelMoreiraCarvalho.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pedroEmanuelMoreiraCarvalho.entities.Tile;

public class Game extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int RECOIL = 76;
	public static int minesweeper_widht = 10, minesweeper_height = 10;
	public static final int WEIGHT = minesweeper_widht * Tile.getSize() + 16, HEIGHT = minesweeper_height * Tile.getSize() + RECOIL;
	public static ArrayList<Tile> minesweeper = new ArrayList<Tile>();

	public static JFrame frame = new JFrame("Minesweeper");
	
	public static void main(String[] args) {
		initMinesweeper();
		initNeightborhood();

		frame.setSize(WEIGHT, HEIGHT);
		
		Game game = new Game();

		frame.add(game);
		frame.addMouseListener(game);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void initMinesweeper() {
		for(int y = 0; y < minesweeper_height; y++) {
			for(int x = 0; x < minesweeper_widht; x++) {
				Tile tile = new Tile(x*Tile.getSize(),y*Tile.getSize()+RECOIL);
				tile.setMinesAround(getRandomNumber(0,8));
				minesweeper.add(tile);
			}
		}
	}
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public static void initNeightborhood() {
		for(int i = 0; i < minesweeper.size(); i++) {
			Tile tile = getTile(i);
			//upper
			tile.setTileAround(0, getTile(i - minesweeper_widht - 1));
			tile.setTileAround(1, getTile(i - minesweeper_widht));
			tile.setTileAround(2, getTile(i - minesweeper_widht + 1));
			//sides
			if(i % minesweeper_widht == 0 ) tile.setTileAround(3, null);
			else tile.setTileAround(3, getTile(i - 1));
			if(i % minesweeper_widht == 9 ) tile.setTileAround(4, null);
			else tile.setTileAround(4, getTile(i + 1));
			//lower
			tile.setTileAround(5, getTile(i + minesweeper_widht - 1));
			tile.setTileAround(6, getTile(i + minesweeper_widht));
			tile.setTileAround(7, getTile(i + minesweeper_widht + 1));
		}
	}
	
	public static Tile getTile(int pos) {
		if(pos < 0 || pos >= minesweeper.size()) return null;
		return minesweeper.get(pos);
	}
	
	public static Tile getTileByPosition(int x, int y) {
		for(Tile tile: minesweeper) {
			if(tile.getX() < x && (tile.getX() + Tile.getSize()) > x
			&& tile.getY() < y && (tile.getY() + Tile.getSize() > y)) {
				return tile;
			}
		}
		return null;
	}
	
	@Override
	public void paint(Graphics g) {
	    super.paintComponents(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setColor(Color.YELLOW);
		
		for(Tile tile: minesweeper) {
			tile.render(g2d,this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	int mouse_offset_x = 8, mouse_offset_y = 32;

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			Tile tile = getTileByPosition(e.getX() - mouse_offset_x, e.getY() - mouse_offset_y);
			if(tile != null && !tile.isSuspect()) {
				tile.reveal();
				frame.repaint();
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			Tile tile = getTileByPosition(e.getX() - mouse_offset_x, e.getY() - mouse_offset_y);
			if(tile != null) {
				tile.suspect();
				frame.repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	//By Ierokirykas
}
