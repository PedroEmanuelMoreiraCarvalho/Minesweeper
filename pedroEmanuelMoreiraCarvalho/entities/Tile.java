package pedroEmanuelMoreiraCarvalho.entities;

import java.awt.Graphics2D;

import pedroEmanuelMoreiraCarvalho.game.Game;

public class Tile {
	
	private final static int SIZE = 30, RECOIL = 0;
	private int x,y;
	private int mines_around;
	private boolean revealed = false;
	private boolean flagged = false;
	private boolean suspect = false;
	private boolean has_mine = false;
	private Controller menu;
	private Tile[] tiles_around = new Tile[8];
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setMenu(Controller controller) {
		menu = controller;
	}
	
	public void render(Graphics2D g,Game observer){
		if(revealed) {
			if(this.hasMine()) g.drawImage(Game.bomb_img,x,y+RECOIL,SIZE,SIZE,observer);
			else g.drawImage(Game.getImageMinesAround(mines_around),x,y+RECOIL,SIZE,SIZE,observer);
		}else if(flagged){
			g.drawImage(Game.flag_img,x,y+RECOIL,SIZE,SIZE,observer);
		}else if(suspect){
			g.drawImage(Game.sus_img,x,y+RECOIL,SIZE,SIZE,observer);
		}else {
			g.drawImage(Game.mine_img,x,y+RECOIL,SIZE,SIZE,observer);
		}
	}
	
	public boolean isRevealed() {
		return revealed;
	}
	
	public void revealAround() {
		if(this.getFlagsAround() != mines_around) return;
		
		for(Tile tile: tiles_around) {
			if(tile != null && !tile.isFlagged() && !tile.isRevealed()) {					
				tile.reveal();
			}
		}
	}
	
	public void reveal() {		
		if(this.revealed) {
			if(!this.has_mine) {				
				this.revealAround();
			}
			return;
		}else {
			if(this.isFlagged()) return;
			this.revealed = true;
			this.suspect = false;			
			Game.mines_left--;
		}
		
		if(this.has_mine) {
			menu.gameOver();
			return;
		}
		
		if(this.mines_around == 0) {
			for(Tile tile: tiles_around) {
				if(tile != null && !tile.isRevealed()) {					
					tile.reveal();
				}
			}
		}
	}
	
	public void suspect() {
		if(this.revealed) return;
		if(!flagged && !suspect) {
			flagged = true;
			suspect = false;
			menu.decrementCounter();
		}else if(flagged && !suspect){
			flagged = false;
			suspect = true;
			menu.incrementCounter();
		}else {
			flagged = false;
			suspect = false;
		}
		
	}
	
	public boolean isSuspect(){
		return suspect;
	}
	
	public boolean isFlagged(){
		return flagged;
	}
	
	public static int getSize() {
		return SIZE;
	}
	
	public void initNumber() {
		int mines_around = 0;
		
		for (Tile tile: tiles_around){
			if(tile != null && tile.has_mine) {
				mines_around++;
			}
		}
		
		this.setMinesAround(mines_around);
	}
	
	public boolean isNeighboor(Tile tile) {
		for (Tile t: tiles_around) {
			if(t == tile) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setMine() {
		has_mine = true;
	}
	
	public boolean hasMine() {
		return has_mine;
	}
	
	public void setMinesAround(int mines) {
		mines_around = mines;
	}
	
	public void setTileAround(int position, Tile tile) {
		tiles_around[position] = tile;
	}
	
	public int getFlagsAround() {
		int flags = 0;
		for (Tile tile: tiles_around){
			if(tile != null && tile.isFlagged()) {
				flags++;
			}			
		}
		return flags;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
