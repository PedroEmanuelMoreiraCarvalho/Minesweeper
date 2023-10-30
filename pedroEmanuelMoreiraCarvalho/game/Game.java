package pedroEmanuelMoreiraCarvalho.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pedroEmanuelMoreiraCarvalho.entities.Controller;
import pedroEmanuelMoreiraCarvalho.entities.Tile;

public class Game extends JPanel implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	public final static int RECOIL = 80;
	public static int minesweeper_widht = 30, minesweeper_height = 16, mines = 99, mines_left;
	public static final int WEIGHT = minesweeper_widht * Tile.getSize() + (RECOIL/5), HEIGHT = (minesweeper_height + 1) * Tile.getSize() + RECOIL;
	
	public static final String PATHNAME = getPathName();
	
	public static ArrayList<Tile> minesweeper = new ArrayList<Tile>();
	public static boolean start = false;
	public boolean game_over = false;
	public Controller menu = new Controller();
	
	public static JFrame frame = new JFrame("Minesweeper");
	
	static String getPathName() {
        File f = new File("program");
        String absolute = f.getAbsolutePath();
        String[] ab = absolute.split("program");
        return ab[0];
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.initMinesweeper();
		game.initNeightborhood();
		frame.setSize(WEIGHT, HEIGHT+8); // 8 = disconsidering the opperation bar
		
		frame.add(game);
		frame.addMouseListener(game);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void restart() {
		start = false;
		game_over = false;
		minesweeper.clear();
		menu.restart();
		this.initMinesweeper();
		this.initNeightborhood();
		frame.repaint();
	}
	
	public void initMinesweeper() {
		for(int y = 0; y < minesweeper_height; y++) {
			for(int x = 0; x < minesweeper_widht; x++) {
				Tile tile = new Tile(x*Tile.getSize(),y*Tile.getSize()+RECOIL);
				tile.setMenu(menu);
				minesweeper.add(tile);
			}
		}
		mines_left = minesweeper_widht * minesweeper_height;
	}
	
	public static void initMines(Tile initial_tile) {
		ArrayList<Integer> mines_indices = new ArrayList<Integer>();
		
		for(int i = 0; i < mines; i++) {
			Integer num = (Integer) getRandomNumber(0, (minesweeper_widht * minesweeper_height));
			while(mines_indices.contains(num) || minesweeper.get(num).isNeighboor(initial_tile) || minesweeper.get(num) == initial_tile) {
				num = getRandomNumber(0, (minesweeper_widht * minesweeper_height));
			}
			
			mines_indices.add(num);
		}
		
		for(int i: mines_indices) {
			minesweeper.get(i).setMine();
		}
	}
	
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	
	public static void initNumbers() {
		for(Tile tile: minesweeper) {
			tile.initNumber();
		}
	}
	
	public void initNeightborhood() {
		for(int i = 0; i < minesweeper.size(); i++) {
			Tile tile = getTile(i);
			//upper
			tile.setTileAround(1, getTile(i - minesweeper_widht));
			//sides
			if(i % minesweeper_widht == 0 ) {
				tile.setTileAround(0, null);
				tile.setTileAround(3, null);
				tile.setTileAround(5, null);
			}
			else{
				tile.setTileAround(0, getTile(i - minesweeper_widht - 1));
				tile.setTileAround(3, getTile(i - 1));
				tile.setTileAround(5, getTile(i + minesweeper_widht - 1));
			}
			if(i % minesweeper_widht == (minesweeper_widht - 1)) {
				tile.setTileAround(2, null);
				tile.setTileAround(4, null);
				tile.setTileAround(7, null);
			}
			else {
				tile.setTileAround(2, getTile(i - minesweeper_widht + 1));
				tile.setTileAround(4, getTile(i + 1));
				tile.setTileAround(7, getTile(i + minesweeper_widht + 1));
			}
			//lower
			tile.setTileAround(6, getTile(i + minesweeper_widht));
		}
	}
	
	public void win() {
		menu.win();
		for(Tile tile: minesweeper) {
			if(!tile.isRevealed() && !tile.isFlagged()) {
				if(tile.isSuspect()) tile.suspect();
				tile.suspect();
			}
		}
	}
	
	public void gameOver() {
		for(Tile tile: minesweeper) {
			if(tile.hasMine()) {
				tile.reveal();
			}
		}
		game_over = true;
		start = false;
	}
	
	public static Tile getTile(int pos) {
		if(pos < 0 || pos >= minesweeper.size()) return null;
		return minesweeper.get(pos);
	}
	
	public static Tile getTileByPosition(int x, int y) {
		for(Tile tile: minesweeper) {
			if(tile.getX() < x && (tile.getX() + Tile.getSize()) > x && tile.getY() < y && (tile.getY() + Tile.getSize()) > y) {
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
		
		menu.render(g2d, this);

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
			if(menu.checkClick(e.getX(),e.getY())) {
				this.restart();
			}
			
			if(tile == null) return;
			if(tile.isFlagged() || tile.isSuspect()) return;
			if(!tile.isRevealed() && game_over || tile.isRevealed() && game_over) return;
			
			if(!start) {
				start = true;
				game_over = false;
				initMines(tile);
				initNumbers();
				tile.reveal();
				frame.repaint();
				return;
			}
			
			if(!tile.isSuspect()) {
				tile.reveal();
				if(menu.isOver()) {
					gameOver();
				}else if(mines_left == mines) {
					win();
				}
				frame.repaint();
			}
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(game_over) return;
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
