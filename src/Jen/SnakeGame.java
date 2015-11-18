package Jen;

import javax.swing.*;
import java.util.Scanner;
import java.util.Timer;


public class SnakeGame {
	Scanner scanner;
	public static int xPixelMaxDimension = 501;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
	public static int yPixelMaxDimension = 501;
	public static int xSquares ;
	public static int ySquares ;
	public final static int squareSize = 50;
	protected static Snake snake ;
	protected static Blocks block;
	protected static Kibble kibble;
	protected static Score score;
	static final int BEFORE_GAME = 1;
	static final int DURING_GAME = 2;
	static final int GAME_OVER = 3;
	static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
	static final int GAME_OPTIONS = 5;
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables

	private static boolean hasWarpWalls = false; // AMD: variable to help implement warp walls.
	private static boolean hasBlocks = false;  // AMD: variable to help implement maze walls
	//using statements such as SnakeGame.GAME_OVER 
	protected static final int numBlockWalls = 3;
	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change its value

	protected static long clockInterval = 500; //controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1 second.

	static JFrame snakeFrame;
	static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	private static void createAndShowGUI() {  //Defines the size of the window and what shows up.
		//Create and set up the window.
		snakeFrame = new JFrame();
		snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(true); //hide title bar
		snakeFrame.setVisible(true);
		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, score, block); //added block
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);
	}

	private static void initializeGame() { //sets up the bits that are shown
		//set up score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize; //Defines size of squares
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares, squareSize);
		kibble = new Kibble(snake);
		score = new Score();
		block = new Blocks(snake);

		gameStage = BEFORE_GAME;
	}

	protected static void newGame() { //Starts clock when new game begins.
		Timer timer = new Timer();
		GameClock clockTick = new GameClock(snake, kibble, score, snakePanel, block);
		timer.scheduleAtFixedRate(clockTick, 0 , clockInterval);
		DrawSnakeGamePanel.getGameWalls().clear();
		for (int i = 0; i < numBlockWalls; i++) {
			DrawSnakeGamePanel.getGameWalls().add(new Blocks(snake));
		}
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				createAndShowGUI();
			}
		});
	}
	public static void changeSize(){ //Need to learn how to refresh.
		System.out.println("things");
		xPixelMaxDimension +=50;
		yPixelMaxDimension +=50;
		snakePanel = new DrawSnakeGamePanel(snake, kibble, score, block);
	}


	public static int getGameStage() {
		return gameStage;
	}

	public static boolean gameEnded() {
		if (gameStage == GAME_OVER || gameStage == GAME_WON){
			return true;
		}
		return false;
	}

	public static void setGameStage(int gameStage) {
		SnakeGame.gameStage = gameStage;
	}

	public static boolean isHasWarpWalls() {
		return hasWarpWalls;
	}

	public static void setHasWarpWalls(boolean hasWarpWalls) {
		SnakeGame.hasWarpWalls = hasWarpWalls;
	}

	public static boolean isHasBlocks() {
		return hasBlocks;
	}

	public static void setHasBlocks(boolean hasBlocks) {
		SnakeGame.hasBlocks = hasBlocks;
	}
}
