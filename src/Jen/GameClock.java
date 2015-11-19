package Jen;

import java.util.List;
import java.util.TimerTask;

public class GameClock extends TimerTask { //TimerTask defines when a task needs to be run.

	Snake snake;
	Kibble kibble;
	Score score;
	List<Block> blocks;
	DrawSnakeGamePanel gamePanel;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel, List block){ //constructor
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
		this.blocks = block;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
						
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				//
				snake.moveSnake();
				if (snake.didEatKibble(kibble) == true) {		 //When snake eats kibble
					//tell kibble to update
					kibble.moveKibble(snake); //changes kibble location
					for(Block block : blocks){
						block.makeBlock(snake);
					}
					Score.increaseScore(); //increases score
				}
				if (snake.didHitBlock(blocks) && gamePanel.addBlock){
					SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				}

				break;
			}
			case SnakeGame.GAME_OVER: {
				this.cancel();		//Stop the Timer	
				break;	
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop timer
				break;
			}
			
		
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen
		
	}
}
