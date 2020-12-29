package byog.lab5;
import byog.TileEngine.Position;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.*;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /**
     * Return a TERenderer instance and initial with world.
     * The width of canvas equals the width of world * 16 pixel.
     * The height of canvas equals the height of world * 16 pixel.
     * Each Tile in canvas is 16 pixel * 16 pixel.
     * Initialize the canvas with Tileset.NOTHING.
     * @param world 2 dimensions TETile array
     * @return a TERenderer instance
     */
    public static TERenderer getRenderer(TETile[][] world) {
        TERenderer ter = new TERenderer();
        int width, height;
        width = world.length;
        height = world[0].length;
        ter.initialize(width, height);

        for (int r = 0; r < world.length; r++) {
            for (int c = 0; c < world[0].length; c++) {
                world[r][c] = Tileset.NOTHING;
            }
        }
        return ter;
    }

    /**
     * Add a hexagon to the given position of canvas.
     * @param world the canvas
     * @param p     the position of the hexagon to be drawn at
     * @param size  the size of the hexagon
     * @param t     what kind of TETile the hexagon is composed of
     */
    public void addHexagon(TETile[][] world, Position p, int size, TETile t) {
        if (size < 2) throw new IllegalArgumentException("size must be greater than 1.");

        setHexagon(world, p.x, p.y, size, t);
    }

    /**
     * Draw each lines of the hexagon from bottom to top.
     * @param world the canvas
     * @param x     the x coordinate in canvas
     * @param y     the y coordinate in canvas
     * @param size  the size of the hexagon
     */
    private void setHexagon(TETile[][] world, int x, int y, int size, TETile t) {
        int xMax = world.length;
        int yMax = world[0].length;
        int oldX = x;
        int tileCnt = size;
        for (int yDis = y; yDis < Math.min(y + size, yMax); yDis++) {               /* 遍历下半部分的行 **/
            for (int xDis = x; xDis < Math.min(x + tileCnt, xMax); xDis++) {        /* 遍历下半部分的列 **/
                world[xDis][yDis] = t;                                              /* stdraw 的 x: 是距离y轴的距离，y:距离x轴的距离 **/
            }
            x -= 1;
            tileCnt += 2;
        }
        y += size;
        x = oldX - (size - 1);
        tileCnt = size + (size - 1) * 2;
        for (int yDis = y; yDis < Math.min(y + size, yMax); yDis++) {               /* 遍历上半部分的行 **/
            for (int xDis = x; xDis < Math.min(x + tileCnt, xMax); xDis++) {        /* 遍历上半部分的列 **/
                world[xDis][yDis] = t;
            }
            x += 1;
            tileCnt -= 2;
        }
    }

    /**
     * Return a List consists of the bottom left connor of the neighbors of the hexagon at the given position.
     * @param p     the left connor of a hexagon
     * @param size  the size of a hexagon
     * @param world the canvas
     * @return      a list of position
     */
    static List<Position> getNeighbor(Position p, int size, TETile[][] world) {
        int x = p.x;
        int y = p.y;
        /* position of bottom left **/
        int[][] pos = new int[][]{
                {x, y + (size - 1) * 2 + 2},        /* top          **/
                {x + 2 * size - 1, y + size},       /* top right    **/
                {x + 2 * size - 1, y - size},       /* bottom right **/
                {x, y - 2 * (size - 1) - 2},        /* bottom       **/
                {x - 2 * size + 1, y - size},       /* bottom left  **/
                {x - 2 * size + 1, y + size},       /* top left     **/

        };
        List<Position> res = new ArrayList<>();
        for (int[] item : pos) {
            int xx = item[0];
            int yy = item[1];
            if (0 <= xx - size + 1 && xx - size + 1 < world.length
                    && 0 <= xx + size - 1 && xx + size - 1 < world.length
                    && 0 <= yy && yy < world[0].length
                    && 0 <= yy + 2 * size && yy + 2 * size < world[0].length) {
                Position tmp = new Position(xx, yy);
                res.add(tmp);
            }
        }
        return res;
    }

    /**
     * Draw a number of total hexagons on world at position origin.
     * @param world     the canvas
     * @param origin    position of the middle hexagon
     * @param size      the size of hexagon
     * @param total     the number of hexagons to be drawn on canvas
     */
    public void drawTessalation(TETile[][] world, Position origin, int size, int total) {
        Deque<Position> que = new ArrayDeque<>();
        TETile[] tiles = new TETile[] {
                Tileset.WALL, Tileset.MOUNTAIN, Tileset.UNLOCKED_DOOR,
                Tileset.WATER, Tileset.LOCKED_DOOR, Tileset.TREE
        };

        int x = origin.x;
        int y = origin.y;
//        int size = 3;
        Position middle = new Position(x, y);
        addHexagon(world, middle, size, Tileset.FLOWER);
        que.offer(middle);
        int count = 1;

        int cur = 0;
        while (!que.isEmpty() && count < total) {
            Position current = que.pollFirst();
            List<Position> neighbors = getNeighbor(current, size, world);
            for (Position n : neighbors) {
                if (world[n.x][n.y].equals(Tileset.NOTHING)) {
                    addHexagon(world, n, size, tiles[cur]);
                    count += 1;
                    if (count == total) break;
                    cur += 1;
                    que.addLast(n);
                    if (cur == tiles.length) cur = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        TETile[][] world = new TETile[60][60];

        TERenderer ter = getRenderer(world);
        HexWorld hw = new HexWorld();
        Position middle = new Position(world.length / 2, world[0].length / 2);
        int size = 3;
        int total = 19;
        hw.drawTessalation(world, middle, size, total);

        ter.renderFrame(world);
    }


}
