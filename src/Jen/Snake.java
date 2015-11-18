package Jen;

import java.awt.*;
import java.util.LinkedList;

public class Snake {

	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers. 
    final static int NOT_A_SEGMENT = 0;
	private Blocks blocks;
	private boolean hitWall = false;
	private boolean ateTail = false;
	private boolean hitBlock = false;
	private int snakeSquares[][];  //represents all of the squares on the screen
	//NOT pixels!
	//A 0 means there is no part of the snake in this square
	//A non-zero number means part of the snake is in the square
	//The head of the snake is 1, rest of segments are numbered in order

	private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
	private int lastHeading;    //Last confirmed movement of snake. See moveSnake method
	
	private int snakeSize;   //size of snake - how many segments?

	private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble
    private Color colorOfHead;
    private Color colorOfBody;
	private int justAteMustGrowThisMuch = 0;

	private int maxX, maxY, squareSize;  
	private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

//	public Snake(int maxX, int maxY, int squareSize){ //constructor
public Snake(int maxX, int maxY){
		this.maxX = maxX;
		this.maxY = maxY;
		this.squareSize = squareSize;
		//Create and fill snakeSquares with 0s 
		snakeSquares = new int[maxX][maxY];
		fillSnakeSquaresWithZeros();
		createStartSnake();
    colorOfHead = Color.PINK;
    colorOfBody = Color.RED;
	}
    public void setSnakeColor(int color) {
        this.colorOfHead = Color.PINK;
        this.colorOfBody = Color.RED;
    }

	protected void createStartSnake(){
		//snake starts as 3 horizontal squares in the center of the screen, moving left
		int screenXCenter = (int) maxX/2;  //Cast just in case we have an odd number
		int screenYCenter = (int) maxY/2;  //Cast just in case we have an odd number
//		System.out.println("max x " + maxX);
//		System.out.println("max y " + maxY);
//		Scanner scanner = null;
//		int things = scanner.nextInt();

		snakeSquares[screenXCenter][screenYCenter] = 1;
		snakeSquares[screenXCenter+1][screenYCenter] = 2;
		snakeSquares[screenXCenter+2][screenYCenter] = 3;

		snakeHeadX = screenXCenter;
		snakeHeadY = screenYCenter;

		snakeSize = 3;

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		
		justAteMustGrowThisMuch = 0;
	}

	private void fillSnakeSquaresWithZeros() {
		for (int x = 0; x < this.maxX; x++){
			for (int y = 0 ; y < this.maxY ; y++) {
				snakeSquares[x][y] = 0;
			}
		}
	}

	public LinkedList<Point> segmentsToDraw(){
		//Return a list of the actual x and y coordinates of the top left of each snake segment
		//Useful for the Panel class to draw the snake
		LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
		for (int segment = 1 ; segment <= snakeSize ; segment++ ) {
			//search array for each segment number
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++) {
					if (snakeSquares[x][y] == segment){
						//make a Point for this segment's coordinates and add to list
						Point p = new Point(x * squareSize , y * squareSize);
						segmentCoordinates.add(p);
					}
				}
			}
		}
		return segmentCoordinates;

	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) {
            return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

//	public void	eatKibble(){
//		//record how much snake needs to grow after eating food
//		justAteMustGrowThisMuch += growthIncrement;
//	}

	protected void moveSnake(){
		//Called every clock tick
		
		//Must check that the direction snake is being sent in is not contrary to current heading
		//So if current heading is down, and snake is being sent up, then should ignore.
		//Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
        if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
            currentHeading = DIRECTION_UP; //keep going the same way
        }
        if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
            currentHeading = DIRECTION_DOWN; //keep going the same way
        }
        if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
            currentHeading = DIRECTION_RIGHT; //keep going the same way
        }
        if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
            currentHeading = DIRECTION_LEFT; //keep going the same way
        }
		
		//Did you hit the wall, snake? 
		//Or eat your tail? Don't move. 

		if (this.didHitWall() == true || this.didEatTail() == true || this.didHitMazeWall() == true) {
//		if (ateTail == true){
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;

		}

		//Use snakeSquares array, and current heading, to move snake

		//Put a 1 in new snake head square
		//increase all other snake segments by 1
		//set tail to 0 if snake did not just eat
		//Otherwise leave tail as is until snake has grown the correct amount 

		//Find the head of the snake - snakeHeadX and snakeHeadY

		//Increase all snake segments by 1
		//All non-zero elements of array represent a snake segment

		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (snakeSquares[x][y] != 0) {
					snakeSquares[x][y]++;
				}
			}
		}

		//now identify where to add new snake head
        if (currentHeading == DIRECTION_UP) {
            //Subtract 1 from Y coordinate so head is one square up
            snakeHeadY--;
        }
        if (currentHeading == DIRECTION_DOWN) {
            //Add 1 to Y coordinate so head is 1 square down
            snakeHeadY++;
        }
        if (currentHeading == DIRECTION_LEFT) {
            //Subtract 1 from X coordinate so head is 1 square to the left
            snakeHeadX--;
        }
        if (currentHeading == DIRECTION_RIGHT) {
            //Add 1 to X coordinate so head is 1 square to the right
            snakeHeadX++;
        }

		//Does this make snake hit the wall?
//		if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0 ) {
//			hitWall = true;
//			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
////			snakeHeadX = maxX/2;
////			snakeHeadY = maxY/2;
//			return;
//		}
//		if (snakeHeadX >= maxX){
//			snakeHeadX = 0;
//		}
//		if (snakeHeadY >= maxY){
//			snakeHeadY = 0;
//		}
//		if (snakeHeadX < 0){
//			snakeHeadX = maxX-1;
//		}
//		if (snakeHeadY < 0){
//			snakeHeadY = maxY-1;
//		}
        if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0) {
            if (!SnakeGame.getHasWarpWalls()) {  // AMD: End the game if the warpwalls are turned off
                hitWall = true;
                SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            } else {
                //AMD: otherwise, move the snake's head to the other side of the board:
                hitWall = false;
                // AMD: Need to adjust coordinates based on which wall the snake hit:
                if (snakeHeadX >= maxX || snakeHeadX < 0) {
                    // AMD: if it hit the side walls, adjust the X coordinate
                    snakeHeadX = maxX - Math.abs(snakeHeadX);
                }
                else {
                    // AMD: otherwise, it hit the top/bottom wall and we adjust the Y coordinate
                    snakeHeadY = maxY - Math.abs(snakeHeadY);
                }
                // AMD: Show the new head
                snakeSquares[snakeHeadX][snakeHeadY] = 1;
                // AMD: The tip of the tail gets left behind for the head to run into later... how to
                // fix this? - by checking that all elements larger than snake size get flipped to 0 in
                // the "if (justAteMustGrowThisMuch == 0) {...}" code below.
            }
            return;
        }


		//Does this make the snake eat its tail?

		if (snakeSquares[snakeHeadX][snakeHeadY] != 0) {

			ateTail = true;
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		//Otherwise, game is still on. Add new head
		snakeSquares[snakeHeadX][snakeHeadY] = 1;

		//If snake did not just eat, then remove tail segment
		//to keep snake the same length.
		//find highest number, which should now be the same as snakeSize+1, and set to 0
		
		if (justAteMustGrowThisMuch == 0) {
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++){
					if (snakeSquares[x][y] == snakeSize+1) {
						snakeSquares[x][y] = 0;
					}
				}
			}
		}
		else {
			//Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
			justAteMustGrowThisMuch -- ;
			snakeSize ++;
		}
		
		lastHeading = currentHeading; //Update last confirmed heading

	}

	protected boolean didHitWall() {
		// AMD: Adding warpWalls means that if warpWalls are on, the snake should never hit the wall.
		return hitWall && !SnakeGame.getHasWarpWalls();

	}

	protected boolean didEatTail(){
		return ateTail;
	} //Never used

	public boolean isSnakeSegment(int bitsX, int bitsY) { //changed from 'kibble' to 'bits' to avoid confusion
        //I believe this keeps the kibble from appearing where the snake is
		if (snakeSquares[bitsX][bitsY] == 0) {
			return false;
		}
		return true;
	}

    public boolean didHitBlock(Blocks blocks){
        if (blocks.getBlockX() == snakeHeadX && blocks.getBlockY() == snakeHeadY){
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            return true;
        }
        return false;
    }

	public boolean didEatKibble(Kibble kibble) {
		//Is this kibble in the snake? It should be in the same square as the snake's head
		if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY){
			justAteMustGrowThisMuch += growthIncrement;
			return true;
		}
		return false;
	}

	public String toString(){
        StringBuffer strBuf = new StringBuffer();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                strBuf.append(snakeSquares[x][y]);
            }
            strBuf.append("\n");
        }
        return strBuf.toString();
//		String textsnake = "";
//		//This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
//		for (int y = 0 ; y < maxY ; y++) {
//			for (int x = 0 ; x < maxX ; x++){
//				textsnake = textsnake + snakeSquares[x][y];
//			}
//			textsnake += "\n";
//		}
//		return textsnake;
	}
    public void draw(Graphics q) {
        // AMD: moved from DrawSnakeGamePanel
        LinkedList<Point> coordinates = this.segmentsToDraw();

        //Draw head in head color
        q.setColor(colorOfHead);
        Point head = coordinates.pop();
        q.fillRect((int) head.getX(), (int) head.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());

        //Draw rest of snake in body color
        q.setColor(colorOfBody);
        for (Point p : coordinates) {
            q.fillRect((int) p.getX(), (int) p.getY(), SnakeGame.getSquareSize(), SnakeGame.getSquareSize());
        }
    }
	public boolean wonGame() {

		//If all of the squares have snake segments in, the snake has eaten so much kibble 
		//that it has filled the screen. Win!
		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (snakeSquares[x][y] == 0) {
					//there is still empty space on the screen, so haven't won
					return false;
				}
			}
		}
		//But if we get here, the snake has filled the screen. win!
		SnakeGame.setGameStage(SnakeGame.GAME_WON);
		
		return true;
	}

	public void reset() {
		hitWall = false;
		ateTail = false;
		fillSnakeSquaresWithZeros();
		createStartSnake();

	}

	public boolean isGameOver() { //THIS IS NEVER USED EVER EVER EVER
		if (hitWall == true || ateTail == true){
//		if ( ateTail == true){
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return true;
			
		}
		return false;
	}
    public boolean didHitMazeWall() {
        // AMD: are we even using mazewalls?
        if (!SnakeGame.getHasBlocks()) { return false; }
        // AMD: has the snake hit the maze wall?
        boolean didHit = false;
        //MazeWall mw = DrawSnakeGamePanel.mw1;
        for (Blocks blk : DrawSnakeGamePanel.getGameWalls()) {
            // AMD: our decision depends partly on the snake's direction & partly on the line's orientation
            switch (currentHeading) {
                case DIRECTION_UP: {
                    if (blk.getV_or_h() == 'h' && blk.getGridX() == this.snakeHeadX && blk.getGridY() == this.snakeHeadY) {
                        didHit = true;
                    }
                    break;
                }
                case DIRECTION_DOWN: {
                    if (blk.getV_or_h() == 'h' && blk.getGridX() == this.snakeHeadX && blk.getGridY() == this.snakeHeadY + 1) {
                        didHit = true;
                    }
                    break;
                }
                case DIRECTION_LEFT: {
                    if (blk.getV_or_h() == 'v' && blk.getGridX() == this.snakeHeadX && blk.getGridY() == this.snakeHeadY) {
                        didHit = true;
                    }
                    break;
                }
                case DIRECTION_RIGHT: {
                    if (blk.getV_or_h() == 'v' && blk.getGridX() == this.snakeHeadX + 1 && blk.getGridY() == this.snakeHeadY) {
                        didHit = true;
                    }
                    break;
                }
                default: {
                    //FINDBUGS: another switch case with no default.  This one is fine, four clear situations.
                    // Ultimately, shouldn't get here: leave something here that can be turned on for debugging:
                    // System.out.println("Invalid direction detected in Snake.didhitmazewall()!");
                    break;
                }
            }
            // if the current wall under consideration has been hit, we need to exit the loop.
            if (didHit) { break; }
        }
        return didHit;
    }
	public void refactor() {
		// AMD: Allows refactoring of snake variables after options reset
		this.maxX = SnakeGame.getxSquares();
		this.maxY = SnakeGame.getySquares();
		snakeSquares = new int[this.maxX][this.maxY];
		fillSnakeSquaresWithZeros();
		createStartSnake();
	}

}


