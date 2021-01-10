package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {
    private int x, y;
    private TETile sign;

    public Player(Random r, Rectangle starter, TETile player) {
        int playerX = RandomUtils.uniform(
                r, Math.round(starter.leftUp.x + 1), Math.round(starter.rightUp.x));
        int playerY = RandomUtils.uniform(
                r, Math.round(starter.leftDown.y + 1), Math.round(starter.leftUp.y));
        this.x = playerX;
        this.y = playerY;
        this.sign = player;
    }

    /**
     * Place the player in the given rectangle.
     * @param worldState    canvas
     * @param rtg           rectangle to place the player
     * @param r             pseudorandom generator
     */
    public void placePlayer(TETile[][] worldState, Rectangle rtg, Random r) {
        worldState[this.x][this.y] = this.sign;
    }

    @Override
    public String toString() {
        return "Player{" + "x=" + x + ", y=" + y + '}';
    }

    /**
     * Return x coordinate of player.
     * @return x coordinate of player.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return y coordinate of player.
     * @return y coordinate of player.
     */
    public int getY() {
        return this.y;
    }


    /**
     * Player takes one step up.
     * @param worldState    canvas
     * @param allow         valid TETile type to step up
     */
    public void moveUp(TETile[][] worldState, TETile allow) {
        int newY = this.y + 1;
        if (worldState[this.x][newY].equals(allow)) {
            worldState[this.x][this.y] = allow;
            worldState[this.x][newY] = this.sign;
            this.y = newY;
        } else {
            System.out.println("Player.java line 42: Cautions: Hit walls");
        }
    }

    /**
     * player takes one step down.
     * @param worldState    canvas
     * @param allow         valid TETile type to step down
     */
    public void moveDown(TETile[][] worldState, TETile allow) {
        int newY = this.y - 1;
        if (worldState[this.x][newY].equals(allow)) {
            worldState[this.x][this.y] = allow;
            worldState[this.x][newY] = this.sign;
            this.y = newY;
            System.out.println("player.java down " + this.toString());
        } else {
            System.out.println("Player.java line 42: Cautions: Hit walls");
        }
    }

    /**
     * player takes one step to the left.
     * @param worldState    canvas
     * @param allow         valid TETile type to step left
     */
    public void moveLeft(TETile[][] worldState, TETile allow) {
        int newX = this.x - 1;
        if (worldState[newX][this.y].equals(allow)) {
            worldState[this.x][this.y] = allow;
            worldState[newX][this.y] = this.sign;
            this.x = newX;
        } else {
            System.out.println("Player.java line 42: Cautions: Hit walls");
        }
    }

    /**
     * player takes one step to the right.
     * @param worldState    canvas
     * @param allow         valid TETile type to step right
     */
    public void moveRight(TETile[][] worldState, TETile allow) {
        int newX = this.x + 1;
        if (worldState[newX][this.y].equals(allow)) {
            worldState[this.x][this.y] = allow;
            worldState[newX][this.y] = this.sign;
            this.x = newX;
        } else {
            System.out.println("Player hit walls at x: " + newX + " y: " + this.y);
        }
    }


}
