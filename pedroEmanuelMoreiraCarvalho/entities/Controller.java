package pedroEmanuelMoreiraCarvalho.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import pedroEmanuelMoreiraCarvalho.game.Game;

public class Controller {
	
	private int state = 0;
	
	private final int 	SCREEN_MIDDLE_X = (Game.minesweeper_widht * Tile.getSize()) / 2,
						SCREEN_MIDDLE_Y = Game.RECOIL / 2,
						SIZE = 40,
						OFFSET_COUNTER = (Game.minesweeper_widht * Tile.getSize());
	private int mine_counter;
	
	public Controller() {
		mine_counter = 0;
	}
	
	public void render(Graphics2D g,Game observer){
		g.setColor(Color.gray);
		g.fillRect(0, 0, Game.minesweeper_widht * Tile.getSize(), Game.RECOIL);
		
		Image img;
		switch(state) {
			case 1:
				img = Toolkit.getDefaultToolkit().getImage(
						"C:\\Users\\pedro\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\game_over.png");
				break;
			case 2:
				img = Toolkit.getDefaultToolkit().getImage(
						"C:\\Users\\pedro\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\win.png");
				break;
			default:
				img = Toolkit.getDefaultToolkit().getImage(
						"C:\\Users\\pedro\\eclipse-workspace\\Minesweeper\\src\\pedroEmanuelMoreiraCarvalho\\images\\face.png");
				break;
		}
		
		g.drawImage(img,SCREEN_MIDDLE_X-(SIZE/2),SCREEN_MIDDLE_Y-(SIZE/2),SIZE,SIZE,observer);
		g.setColor(Color.RED);
		g.setFont(new Font("Courier", Font.BOLD,SIZE));
		g.drawString(String.valueOf(mine_counter), OFFSET_COUNTER-(SIZE*2),SCREEN_MIDDLE_Y + (SIZE/2) - 10);
	}
	
	public void incrementCounter() {
		this.setCounter(mine_counter + 1);
	}
	
	public void decrementCounter() {
		this.setCounter(mine_counter - 1);
	}
	
	public void restart() {
		state = 0;
		mine_counter = 0;
	}
	
	public void gameOver() {
		state = 1;
	}
	
	public void win() {
		state = 2;
	}
	
	public boolean isOver() {
		return state == 1;
	}
	
	public void setCounter(int new_count) {
		mine_counter = new_count;
	}
	
}
