package Jen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 *
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {
	
	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	boolean addBlock = false;
	private Snake snake;
	private Kibble kibble;
	private Score score;
	private List<Block> blocks;

	public static ArrayList<Block> getGameWalls() {
		return gameWalls;
	}

	private final static ArrayList<Block> gameWalls = new ArrayList<Block>();

	public void changeAddBlock(){
        addBlock = !addBlock;
//        if(!addBlock) {
//            blocks = new ArrayList<Block>();
//        }
        displayBlocks(getGraphics());
	}
	
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc, List b){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
		this.blocks = b;
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.gameSettings.screenX, SnakeGame.gameSettings.screenY);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
			case 1: {
				displayInstructions(g);
				break;
			}
			case 2 : {
				displayGame(g);
				break;
			}
			case 3: {
				displayGameOver(g);
				break;
			}
			case 4: {
				displayGameWon(g);
				break;
			}
			case 5:{
				displayOptions(g);
				break;
			}
		}
        
        
        
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100, 100, 350, 350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100, 100, 350, 350);
		g.drawString("GAME OVER", 150, 150);

		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);

		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		
    			
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
        displayBlocks(g);
//		for(Block block : blocks){
//			block.displayBlock(g);
//		}
//		if (addBlock == true){
//			block.displayBlock(g);
//		}
//		block.displayBlock(g);
	}
    private void displayBlocks(Graphics g){
        if(addBlock) {
            for (Block block : blocks) {
                block.displayBlock(g);
            }
        }
    }
	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.gameSettings.screenX;
		int maxY= SnakeGame.gameSettings.screenY;
		int squareSize = SnakeGame.squareSize;
		
		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.RED);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}

        for(Block b : gameWalls){
            b.displayBlock(g);
        }
	}

	private void displayKibble(Graphics g) {

		//Draw the kibble in green
		g.setColor(Color.GREEN);

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
		
	}

//	private void displayBlock(Graphics g){ //To make maz block
//		g.setColor(Color.BLUE);
//
//		int x = block.getBlockX() * SnakeGame.squareSize;
//		int y = block.getBlockY() * SnakeGame.squareSize;
//		g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);
////		for (int z = 1 ; z < 3 ; z++) {
////			g.fillRect(x + z, y + z, SnakeGame.squareSize - z, SnakeGame.squareSize - z);
////		}
//        if (v_or_h == 'v') {
//            // if vertical, x is 3px wide and y changes
//            g.fillRect(xPos-1, yPos, 3, linelength);
//        } else {
//            // if horizontal, y is 3px wide and x changes
//            g.fillRect(xPos, yPos-1, linelength, 3);
//        }
//	}


	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();
		
		//Draw head in grey
		g.setColor(Color.LIGHT_GRAY);
		Point head = coordinates.pop();
		g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake in black
		g.setColor(Color.BLACK);
		for (Point p : coordinates) {
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}

	}

	private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!", 100, 200);
        g.drawString("Press q to quit the game", 100, 300);
		g.drawString("Press p to go to options", 100, 350);

    	}
	private void displayOptions(Graphics g) {
		g.drawString("Turn on maze walls",100,200);
		g.drawString("Press q to quit the game",100,300);

	}
    
}

