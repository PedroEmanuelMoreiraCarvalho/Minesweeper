package pedroEmanuelMoreiraCarvalho.entities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import pedroEmanuelMoreiraCarvalho.game.Game;


public class Tile {
	
	private final static int SIZE = 40;
	private int x,y;
	private int mines_around;
	private boolean revealed = false;
	private boolean suspect = false;
	private Tile[] tiles_around = new Tile[8];
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void render(Graphics2D g,Game observer){
		Image img;
		
		if(revealed) {			
			img = Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\LEG-8\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\"+mines_around+".png");
		}else if(suspect){
			img = Toolkit.getDefaultToolkit().getImage(
					"C:\\Users\\LEG-8\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\sus.png");
		}else {
			img = Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\LEG-8\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\mine.png");
		}
		g.drawImage(img,x,y,SIZE,SIZE,observer);
	}
	
	public void reveal() {
		this.revealed = true;
	}
	
	public void suspect() {
		if(this.revealed) return;
		suspect = !suspect;
	}
	
	public boolean isSuspect(){
		return suspect;
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	public void setMinesAround(int mines) {
		mines_around = mines;
	}
	
	public void setTileAround(int position, Tile tile) {
		tiles_around[position] = tile;
	}
	
	public int getMinesAround() {
		return mines_around;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
