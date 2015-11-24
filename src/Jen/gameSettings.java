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

    public boolean mazeWalls;
    public boolean warpWalls;
    public int numBlocks;
    public int screenX;
    public int screenY;
    public int gameSpeed;


    public gameSettings(boolean mazeWalls, boolean warpWalls, int numBlocks, int screenX, int screenY, int gameSpeed) {
        this.mazeWalls = mazeWalls;
        this.warpWalls = warpWalls;
        this.numBlocks = numBlocks;
        this.screenX = screenX;
        this.screenY = screenY;
        this.gameSpeed = gameSpeed;
    }

    public boolean isMazeWalls() {
        return mazeWalls;
    }

    public void setMazeWalls(boolean mazeWalls) {
        this.mazeWalls = mazeWalls;
    }

    public boolean isWarpWalls() {
        return warpWalls;
    }

    public void setWarpWalls(boolean warpWalls) {
        this.warpWalls = warpWalls;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
}
