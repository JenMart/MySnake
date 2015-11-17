package Jen;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jen Mart on 11/13/2015.
 */
public class Blocks {

    private int blockX;
    private int blocky;
    private char v_or_h;  // char that is either 'v' or 'h'
    public Blocks (Snake e){

        makeBlock(e);
    }

    protected void makeBlock(Snake e) {
        Random random = new Random();
        boolean snakeHitBlock = true;
        while (snakeHitBlock == true) {
            blockX = random.nextInt(SnakeGame.xSquares);
            blocky = random.nextInt(SnakeGame.ySquares);
            snakeHitBlock = e.isSnakeSegment(blockX, blocky);
            /////////
            int pickHorV = random.nextInt(2);
            if (pickHorV == 0) {
                this.v_or_h = 'v';
            } else {
                this.v_or_h = 'h';
            }

        }
    }
    public void displayBlock(Graphics g){ //To make maz block
        g.setColor(Color.BLUE);

        int x = blockX * SnakeGame.squareSize;
        int y = blocky * SnakeGame.squareSize;
        g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);
//		for (int z = 1 ; z < 3 ; z++) {
//			g.fillRect(x + z, y + z, SnakeGame.squareSize - z, SnakeGame.squareSize - z);
//		}
        if (v_or_h == 'v') {
            // if vertical, x is 3px wide and y changes
            g.fillRect(x-1, y , 3, SnakeGame.squareSize);
        } else {
            // if horizontal, y is 3px wide and x changes
            g.fillRect(x, y-1, SnakeGame.squareSize, 3);
        }
    }



    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blocky;
    }
}
