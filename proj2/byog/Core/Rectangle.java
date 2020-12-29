package byog.Core;

import byog.TileEngine.Position;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rectangle {
    /**
     * public需要修改？？
     * leftDown     the left bottom position of rectangle
     * rightDown    the right bottom position of rectangle
     * leftUp       the left upper position of rectangle
     * rightUp      the right upper position of rectangle
     */
    public Position leftDown, rightDown, leftUp, rightUp;

    /**
     * width     width of rectangle
     * height    height of rectangle
     */
    private int width, height;

    /**
     * up           the usage of the upper side
     * right        the usage of the right side
     * bottom       the usage of the bottom side
     * left         the usage of the left side
     */
    public boolean up, right, bottom, left;

    /**
     * Initial a rectangle with the left bottom corner position.
     * @param p     the left bottom corner of the rectangle
     * @param w     the width of the rectangle
     * @param h     the height of the rectangle
     */
    public Rectangle(Position p, int w, int h) {
        width = w;
        height = h;
        leftDown = p;
        rightDown = new Position(p.x + width - 1, p.y);
        leftUp = new Position(p.x, p.y + height - 1);
        rightUp = new Position(p.x + width - 1, p.y + height - 1);
        up = right = bottom = left = false;
    }

    /**
     * Getter method of width.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter method of height.
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Check usages of the sides of a rectangle. Return the unused sides.
     * @return
     */
    public List<Integer> checkSidesUsage() {
        List<Integer> unusedSides = new ArrayList<>();
        if (!up) {
            unusedSides.add(0);
        }
        if (!right) {
            unusedSides.add(1);
        }
        if (!bottom) {
            unusedSides.add(2);
        }
        if (!right) {
            unusedSides.add(3);
        }
        return unusedSides;
    }

    @Override
    public String toString() {
        return "Rectangle{"
                + "leftDown=" + leftDown
                + ", rightDown=" + rightDown
                + ", leftUp=" + leftUp
                + ", rightUp=" + rightUp
                + ", width=" + width
                + ", height=" + height
                + '}';
    }

    public static void main(String[] args) {
        TETile[][] world = new TETile[60][30];
        TERenderer ter = HexWorld.getRenderer(world);

        Random r = new Random();
        Rectangle rtg1 = new Rectangle(new Position(30, 15), 10, 5);
        Rectangle rtg2 = new Rectangle(new Position(40, 5), 10, 20);
        DrawUtls.drawRectangle(world, r, rtg1, Tileset.FLOWER, Tileset.FLOOR);
        DrawUtls.drawRectangle(world, r, rtg2, Tileset.FLOWER, Tileset.FLOOR);
        world[39][17] = world[40][17] = Tileset.FLOOR;


        ter.renderFrame(world);
    }
}
