package Jen;

/**
 * Created by jensinamart on 11/23/15.
 */
public class gameSettings { //POJO!
    //mazewalls
    //number of blocks for mazewall
    //warp walls
    //grid x & y.
    //game speed

    public boolean mazeWalls = false;
    public boolean warpWalls = false;
    public int numBlocks = 0;
    public int gridX = 501;
    public int gridY = 501;
    public int gameSpeed = 500;


    public gameSettings(boolean mazeWalls, boolean warpWalls, int numBlocks, int gridX, int gridY, int gameSpeed) {
        this.mazeWalls = mazeWalls;
        this.warpWalls = warpWalls;
        this.numBlocks = numBlocks;
        this.gridX = gridX;
        this.gridY = gridY;
        this.gameSpeed = gameSpeed;
    }
}
