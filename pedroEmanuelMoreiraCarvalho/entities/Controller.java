package pedroEmanuelMoreiraCarvalho.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import pedroEmanuelMoreiraCarvalho.game.Game;

public class Controller {
	
	private int state = 0;
	
	private final int 	SIZE = 40,
						SCREEN_MIDDLE_X = (Game.minesweeper_widht * Tile.getSize()) / 2,
						SCREEN_MIDDLE_Y = Game.RECOIL / 2,
						OFFSET_B_X = SCREEN_MIDDLE_X-(SIZE/2),
						OFFSET_B_Y = SCREEN_MIDDLE_Y-(SIZE/2),
						OFFSET_COUNTER_X = (int)(SCREEN_MIDDLE_X * 1.5),
						OFFSET_COUNTER_Y = (int)((SCREEN_MIDDLE_Y * 4) / 3);
	private int mine_counter;
	
	public Controller() {
		mine_counter = Game.mines;
	}
	
	public boolean checkClick(int x, int y){
		x -= 8;
		y -= 32;// disconsidering the opperation bar
		return (x > OFFSET_B_X && x < (OFFSET_B_X + SIZE) && y > OFFSET_B_Y && y < (OFFSET_B_Y + SIZE));
	}
	
	public void render(Graphics2D g,Game observer){
		g.setColor(Color.gray);
		g.fillRect(0, 0, Game.minesweeper_widht * Tile.getSize(), Game.RECOIL);
		
		switch(state) {
			case 1:
				g.drawImage(Game.game_over_img,OFFSET_B_X,OFFSET_B_Y,SIZE,SIZE,observer);
				break;
			case 2:
				g.drawImage(Game.win_img,OFFSET_B_X,OFFSET_B_Y,SIZE,SIZE,observer);
				break;
			default:
				g.drawImage(Game.face_img,OFFSET_B_X,OFFSET_B_Y,SIZE,SIZE,observer);
				break;
		}
		g.setColor(Color.RED);
		g.setFont(new Font("Fira Code Retina", Font.BOLD,SIZE));
		g.drawString(String.valueOf(mine_counter), OFFSET_COUNTER_X ,OFFSET_COUNTER_Y);
	}
	
	public void incrementCounter() {
		this.setCounter(mine_counter + 1);
	}
	
	public void decrementCounter() {
		this.setCounter(mine_counter - 1);
	}
	
	public void restart() {
		state = 0;
		mine_counter = Game.mines;
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
