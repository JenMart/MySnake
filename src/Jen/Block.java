package Jen;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jen Mart on 11/13/2015.
 */
public class Block {

    private int blockX;
    private int blocky;
    public Block(Snake e){
        makeBlock(e);
    }

    protected void makeBlock(Snake e) {
        Random random = new Random(); //Creates random location
        boolean snakeHitBlock = true; //Confirms that snake hitting block will cause game over
        while (snakeHitBlock == true) {
            blockX = random.nextInt(SnakeGame.xSquares);
            blocky = random.nextInt(SnakeGame.ySquares);
            snakeHitBlock = e.isSnakeSegment(blockX, blocky);
        }
    }
    public void displayBlock(Graphics g){ //To make block
        g.setColor(Color.BLUE);
        int x = blockX * SnakeGame.squareSize;
        int y = blocky * SnakeGame.squareSize;
        g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);
    }



    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blocky;
    }
}
