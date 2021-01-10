package byog.Core;

import byog.TileEngine.Position;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utilities class for drawing shapes.
 * @author skllig
 */
public class DrawUtls {
    /** font for start page. */
    public static final Font BIG_FONT = new Font(Font.SERIF, Font.BOLD, 40);
    /** font for navigation bar*/
    public static final Font MID_FONT = new Font(Font.SERIF, Font.BOLD, 30);
    /** font of title */
    public static final Font SMALL_FONT = new Font(Font.SERIF, Font.BOLD, 18);
    /** color for start page */
    public static final Color PALE_TURQUOISE_3 = new Color(150, 205, 205);
    /** color for start page */
    public static final Color GOLD = new Color(255, 215, 0);
    /** color of navigation bar */
    public static final Color VERMILLION = new Color(252, 74, 26);

    /**
     * Initial canvas without renderer.
     * This method is used in playWithInputString method.
     * @param world     cavas
     */
    public static void initialWorldWithoutRenderer(TETile[][] world) {
        for (int r = 0; r < world.length; r++) {
            for (int c = 0; c < world[0].length; c++) {
                world[r][c] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Draw a horizontal line at the given position
     * @param world     canvas
     * @param p         the left corner of line
     * @param length    length of line
     * @param t         Tile type of line
     */
    public static void drawHorizontalLine(TETile[][] world, Random r, Position p,
                                          int length, TETile t) {
        for (int i = p.x; i < p.x + length; i++) {
            world[i][p.y] = t;
            t = TETile.colorVariant(t, 15, 15, 15, r);
        }
    }

    /**
     * Draw a vertical line at the given positions.
     * @param world     cancas
     * @param p         the bottom corner of line
     * @param length    length of line
     * @param t         Tile type of line
     */
    public static void drawVerticalLine(TETile[][] world, Random r, Position p,
                                        int length, TETile t) {
        for (int i = p.y; i < p.y + length; i++) {
            world[p.x][i] = t;
            t = TETile.colorVariant(t, 15, 15, 15, r);
        }
    }

    /**
     * Fill the inner area of a rectangle.
     * @param world     canvas
     * @param rtg       rectangle to be drawn
     * @param t         Tile of the inner of rectangle
     */
    public static void drawRectangleInner(TETile[][] world, Random r, Rectangle rtg,
                                          TETile t) {
        for (int i = rtg.leftDown.y + 1; i < rtg.leftUp.y; i++) {
            drawHorizontalLine(
                    world, r, new Position(rtg.leftDown.x + 1, i), rtg.getWidth() - 2, t);
            t = TETile.colorVariant(t, 15, 15, 15, r);
        }
    }

    /**
     * Draw a rectangle at the given position.
     * @param world     canvas
     * @param rtg       rectangle to be drawn
     * @param t         Tile of rectangle
     * @param r         pseudorandom generator
     * @return          the current rectangle
     */
    public static Rectangle drawRectangle(TETile[][] world, Random r, Rectangle rtg, TETile t,
                                          TETile fill) {
        /** draw the horizontal lines*/
        drawHorizontalLine(world, r, rtg.leftDown, rtg.getWidth(), t);
        drawHorizontalLine(world, r, rtg.leftUp, rtg.getWidth(), t);
        /** draw the vertical lines */
        drawVerticalLine(world, r, rtg.leftDown, rtg.getHeight(), t);
        drawVerticalLine(world, r, rtg.rightDown, rtg.getHeight(), t);
        /** draw inner area of a rectangle */
        drawRectangleInner(world, r, rtg, fill);
        return rtg;
    }

    /**
     * Randomly select the width of the next rectangle.
     *      u             next_width
     *      -1 < u        3
     *      1 < u < 2.5   3 ~ 2 * width
     *      u < 2.5       3 ~ 4 * width
     * @param r           pseudorandom generator
     * @param width       width of current rectangle
     * @return
     */
    public static int nextWidth(Random r, int width) {
        double u = RandomUtils.gaussian(r);
        int nextWidth;
        if (u < -1) {
            nextWidth = 3;
        } else if (1 < u && u < 2.5) {
            nextWidth = RandomUtils.uniform(r, width, width * 2);
        } else if (u > 2.5) {
            nextWidth = RandomUtils.uniform(r, 2 * width, width * 5);
        } else {
            nextWidth = RandomUtils.uniform(r, 3, 2 * width);
        }
        return nextWidth;
    }

    /**
     * Randomly select the width of the next rectangle.
     *      u             next_width
     *      -1 < u        3
     *      1 < u < 2.5   3 * width
     *      u < 2.5       3 ~ 4 * width
     * @param r           pseudorandom generator
     * @param height      height of the current rectangle
     * @return
     */
    public static int nextHeight(Random r, int height) {
        double u = RandomUtils.gaussian(r);
        int nextHeight;
        if (u < -1) {
            nextHeight = 3;
        } else if (1 < u && u < 2.5) {
            nextHeight = RandomUtils.uniform(r, height, height * 2);
        } else if (u > 2.5) {
            nextHeight = RandomUtils.uniform(r, 2 * height, height * 5);
        } else {
            nextHeight = RandomUtils.uniform(r, 3, 2 * height);
        }
        return nextHeight;
    }

    /**
     * Randomly select a x or y point in the given range.
     * @param r         pseudorandom generator
     * @param from      start point
     * @param to        end point
     * @return          a x or y point in the given range
     */
    public static int getLeftBottom(Random r, int from, int to) {
        if (from == to) {
            return -1;
        }
        int start = RandomUtils.uniform(r, from, to);
        return start;
    }

    /**
     * Check if a new rectangle can be placed in canvas.
     * @return true if a new rectangle can be placed on top of the old rectangle.
     */
    public static boolean verifyRectangle(Rectangle rtg) {
        if (rtg.leftUp.x < 0 || rtg.leftUp.x >= Game.WIDTH
                || rtg.leftUp.y < 0 || rtg.leftUp.y >= Game.HEIGHT) {
            return false;
        }
        if (rtg.rightUp.x < 0 || rtg.rightUp.x >= Game.WIDTH
                || rtg.rightUp.y < 0 || rtg.rightUp.y >= Game.HEIGHT) {
            return false;
        }
        if (rtg.leftDown.x < 0 || rtg.leftDown.x >= Game.WIDTH
                || rtg.leftDown.y < 0 || rtg.leftDown.y >= Game.HEIGHT) {
            return false;
        }
        return true;
    }

    /**
     * Return true if the given position has been occupied.
     * @param world     canvas
     * @param p         left corner of line
     * @param width     width of line
     * @return
     */
    public static boolean isHorizontalLineOverLap(TETile[][] world, Position p, int width) {
        for (int i = p.x; i < p.x + width; i++) {
            if (!world[i][p.y].equals(Tileset.NOTHING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if the given position has been occupied.
     * @param world     canvas
     * @param p         bottom corner of line
     * @param width     width of line
     * @return
     */
    public static boolean isVerticalLineOverLap(TETile[][] world, Position p, int width) {
        for (int i = p.y + 1; i < p.y + width - 1; i++) {
            if (!world[p.x][i].equals(Tileset.NOTHING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if the given position has been occupied.
     * Check the LHS, RHS and the bottom of rtg, skip the top side of rtg.
     * @param world
     * @param rtg
     * @return
     */
    public static boolean isRectangleOverlapSkipTop(TETile[][] world, Rectangle rtg) {
        if (isHorizontalLineOverLap(world, rtg.leftUp, rtg.getWidth())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.leftDown, rtg.getHeight())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.rightDown, rtg.getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * Return true if the given position has been occupied.
     * Check the LHS, RHS and the top of rtg, skip the bottom side of rtg.
     * @param world
     * @param rtg
     * @return
     */
    public static boolean isRectangleOverlapSkipDown(TETile[][] world, Rectangle rtg) {
        if (isHorizontalLineOverLap(world, rtg.leftDown, rtg.getWidth())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.leftDown, rtg.getHeight())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.rightDown, rtg.getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * Return true if the given position has been occupied.
     * Check the LHS, the bottom and the top of rtg, skip the top side of RHS.
     * @param world
     * @param rtg
     * @return
     */
    public static boolean isRectangleOverlapSkipRight(TETile[][] world, Rectangle rtg) {
        if (isHorizontalLineOverLap(world, rtg.leftDown, rtg.getWidth())) {
            return true;
        }
        if (isHorizontalLineOverLap(world, rtg.leftUp, rtg.getWidth())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.rightDown, rtg.getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * Return true if the given position has been occupied.
     * Check the RHS and the bottom and the top of rtg, skip the LHS of rtg.
     * @param world
     * @param rtg
     * @return
     */
    public static boolean isRectangleOverlapSkipLeft(TETile[][] world, Rectangle rtg) {
        if (isHorizontalLineOverLap(world, rtg.leftDown, rtg.getWidth())) {
            return true;
        }
        if (isHorizontalLineOverLap(world, rtg.leftUp, rtg.getWidth())) {
            return true;
        }
        if (isVerticalLineOverLap(world, rtg.leftDown, rtg.getHeight())) {
            return true;
        }
        return false;
    }

    /**
     * Join two rectangle at the shared side.
     * @param world     canvas
     * @param top       rectangle at the top
     * @param bottom    rectangle at the bottom
     */
    public static void joinTop(TETile[][] world, Random r, Rectangle top,
                               Rectangle bottom, TETile fill) {
        int start = Math.max(top.leftDown.x, bottom.leftUp.x);
        int end = Math.min(top.rightDown.x, bottom.rightUp.x);
        int pickX = RandomUtils.uniform(r, start + 1, end);
        world[pickX][top.leftDown.y] = fill;
        world[pickX][bottom.rightUp.y] = fill;
    }

    /**
     * Join two rectangle at the shared side.
     * @param world     canvas
     * @param left      rectangle at the left hand side
     * @param right     rectangle at the right hand side
     */
    public static void joinRight(TETile[][] world, Random r, Rectangle left,
                                 Rectangle right, TETile fill) {
        int start = Math.max(left.rightDown.y, right.leftDown.y);
        int end = Math.min(left.rightUp.y, right.leftUp.y);
        int pickY = RandomUtils.uniform(r, start + 1, end);
        world[left.rightUp.x][pickY] = fill;
        world[right.leftUp.x][pickY] = fill;
    }

    /**
     * Join two rectangle at the shared side.
     * @param world     canvas
     * @param top       rectangle at the top
     * @param bottom    rectangle at the bottom
     */
    public static void joinBottom(TETile[][] world, Random r, Rectangle top,
                                  Rectangle bottom, TETile fill) {
        int start = Math.max(top.leftDown.x, bottom.leftUp.x);
        int end = Math.min(top.rightDown.x, bottom.rightUp.x);
        int pickX = RandomUtils.uniform(r, start + 1, end);
        world[pickX][top.leftDown.y] = fill;
        world[pickX][bottom.leftUp.y] = fill;
    }

    /**
     * Join two rectangle at the shared side.
     * @param world     canvas
     * @param left      rectangle at the top
     * @param right     rectangle at the bottom
     */
    public static void joinLeft(TETile[][] world, Random r, Rectangle left,
                                Rectangle right, TETile fill) {
        int start = Math.max(left.rightDown.y, right.leftDown.y);
        int end = Math.min(left.rightUp.y, right.leftUp.y);
        int pickY = RandomUtils.uniform(r, start + 1, end);
        world[left.rightUp.x][pickY] = fill;
        world[right.leftUp.x][pickY] = fill;
    }

    /**
     * Generate a new rectangle at the top of rtg and connect them.
     * @param world     canvas
     * @param r         pseudorandom generator
     * @param rtg       existing rectangle
     * @param width     the width of the new rectangle
     * @param height    the height of the new rectangle
     * @param t         Tile type of sides of the new rectangle
     * @param fill      Tile type of the inner area of the new rectangle
     * @return          the new rectangle
     */
    public static Rectangle generateTopNeighbor(TETile[][] world, Random r, Rectangle rtg,
            int width, int height, TETile t, TETile fill) {
        int x = getLeftBottom(r, rtg.leftUp.x - width + 3, rtg.rightUp.x - 2);
        int y = getLeftBottom(r, rtg.leftUp.y, rtg.leftUp.y + 2);

        Rectangle topNeighbor = new Rectangle(new Position(x, y), width, height);

        if (verifyRectangle(topNeighbor) && !isRectangleOverlapSkipTop(world, topNeighbor)) {
            drawRectangle(world, r, topNeighbor, t, fill);
            rtg.up = true;   // the top side of rtg has been occupied
            joinTop(world, r, topNeighbor, rtg, fill);
            return topNeighbor;
        }
        return null;
    }

    /**
     * Generate a new rectangle at the right hand side of rtg and connect them.
     * @param world     canvas
     * @param r         pseudorandom generator
     * @param rtg       existing rectangle
     * @param width     the width of the new rectangle
     * @param height    the height of the new rectangle
     * @param t         Tile type of sides of the new rectangle
     * @param fill      Tile type of the inner area of the new rectangle
     * @return          the new rectangle
     */
    public static Rectangle generateRightNeighbor(TETile[][] world,
            Random r, Rectangle rtg, int width, int height, TETile t, TETile fill) {
        int x = getLeftBottom(r, rtg.rightDown.x, rtg.rightDown.x + 2);
        int y = getLeftBottom(r, rtg.rightDown.y - height + 3, rtg.rightUp.y - 2);

        Rectangle rightNeighbor = new Rectangle(new Position(x, y), width, height);

        if (verifyRectangle(rightNeighbor) && !isRectangleOverlapSkipRight(world, rightNeighbor)) {
            drawRectangle(world, r, rightNeighbor, t, fill);
            rtg.right = true;
            joinRight(world, r, rtg, rightNeighbor, fill);
            return rightNeighbor;
        }
        return null;
    }

    /**
     * Generate a new rectangle at the bottom of rtg and connect them.
     * @param world     canvas
     * @param r         pseudorandom generator
     * @param rtg       existing rectangle
     * @param width     the width of the new rectangle
     * @param height    the height of the new rectangle
     * @param t         Tile type of sides of the new rectangle
     * @param fill      Tile type of the inner area of the new rectangle
     * @return          the new rectangle
     */
    public static Rectangle generateBottomNeighbor(TETile[][] world, Random r,
            Rectangle rtg, int width, int height, TETile t, TETile fill) {
        int x = getLeftBottom(r, rtg.leftDown.x - width + 3, rtg.rightDown.x - 2);
        int y = getLeftBottom(r, rtg.leftDown.y - height, rtg.leftDown.y - height + 2);

        Rectangle bottomNeighbor = new Rectangle(new Position(x, y), width, height);

        if (verifyRectangle(bottomNeighbor) && !isRectangleOverlapSkipDown(world, bottomNeighbor)) {
            drawRectangle(world, r, bottomNeighbor, t, fill);
            rtg.bottom = true;
            joinBottom(world, r, rtg, bottomNeighbor, fill);
            return bottomNeighbor;
        }
        return null;
    }

    /**
     * Generate a new rectangle at the left hand side of rtg and connect them.
     * @param world     canvas
     * @param r         pseudorandom generator
     * @param rtg       existing rectangle
     * @param width     the width of the new rectangle
     * @param height    the height of the new rectangle
     * @param t         Tile type of sides of the new rectangle
     * @param fill      Tile type of the inner area of the new rectangle
     * @return          the new rectangle
     */
    public static Rectangle generateLeftNeighbor(TETile[][] world,
            Random r, Rectangle rtg, int width, int height, TETile t, TETile fill) {
        int x = getLeftBottom(r, rtg.leftDown.x - width, rtg.leftDown.x - width + 2);
        int y = getLeftBottom(r, rtg.leftDown.y - height + 3, rtg.leftUp.y - 2);

        Rectangle leftNeighbor = new Rectangle(new Position(x, y), width, height);

        if (verifyRectangle(leftNeighbor) && !isRectangleOverlapSkipLeft(world, leftNeighbor)) {
            drawRectangle(world, r, leftNeighbor, t, fill);
            rtg.left = true;
            joinLeft(world, r, leftNeighbor, rtg, fill);
            return leftNeighbor;
        }
        return null;
    }

    /**
     * Generate and return the next Rectangle to draw a new rectangle.
     * If four sides of the current rectangle have been occupied, return null;
     * @param world         canvas
     * @param rtg           the current rectangle
     * @param t             the Tile type of the next rectangle
     * @return              a list of new created retangles
     */
    public static List<Rectangle> drawNeighbors(TETile[][] world, Random r,
                                                Rectangle rtg, TETile t, TETile fill) {
        List<Integer> unusedSides = rtg.checkSidesUsage();
        if (unusedSides.size() == 0) {
            return null;
        }
        List<Rectangle> newRectangles = new ArrayList<>();
        for (Integer side : unusedSides) {
            int nextW = nextWidth(r, rtg.getWidth());
            int nextH = nextHeight(r, rtg.getHeight());
            switch (side) {
                case 0:         // top side is available
                    Rectangle topNeighbor =
                            generateTopNeighbor(world, r, rtg, nextW, nextH, t, fill);
                    if (topNeighbor != null) {
                        newRectangles.add(topNeighbor);
                    }
                    break;
                case 1:         // right
                    Rectangle rightNeighbor =
                            generateRightNeighbor(world, r, rtg, nextW, nextH, t, fill);
                    if (rightNeighbor != null) {
                        newRectangles.add(rightNeighbor);
                    }

                    break;
                case 2:         // bottom
                    Rectangle bottomNeighbor =
                            generateBottomNeighbor(world, r, rtg, nextW, nextH, t, fill);
                    if (bottomNeighbor != null) {
                        newRectangles.add(bottomNeighbor);
                    }
                    break;
                case 3:         // left
                    Rectangle leftNeighbor =
                            generateLeftNeighbor(world, r, rtg, nextW, nextH, t, fill);
                    if (leftNeighbor != null) {
                        newRectangles.add(leftNeighbor);
                    }
            }
        }
        return newRectangles;
    }

    /**
     * Initial start page with default setting.
     * @param width     width of canvas
     * @param height    height of canvas
     */
    public static void initialStartFrame(int width, int height) {
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.setFont(BIG_FONT);
        StdDraw.setPenColor(PALE_TURQUOISE_3);
        StdDraw.enableDoubleBuffering();
//        StdDraw.clear(Color.BLACK);
    }

    /**
     * Draw the start page of the game.
     * *************************************
     * *          CS61B: The Game          *
     * *          New Game (N)             *
     * *          Load Game(L)             *
     * *          Quit (Q)                 *
     * *************************************
     * @param width     width of canvas
     * @param height    height of canvas
     * @return seed of the game
     */
    public static void drawStartFrame(int width, int height) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(BIG_FONT);
        drawText("CS61B: The Game", Math.round(width / 2), Math.round(height / 14 * 10));
        StdDraw.setFont(MID_FONT);
        StdDraw.setPenColor(PALE_TURQUOISE_3);
        drawText("New Game (N)", Math.round(width / 2), Math.round(height / 14 * 7));
        drawText("Load Game(L)", Math.round(width / 2), Math.round(height / 14 * 6));
        drawText("Quit (Q)", Math.round(width / 2), Math.round(height / 14 * 5));
    }

    /**
     * Draw the given string s at the given position
     * @param s     string to be drawn
     * @param x     x coordinate of text
     * @param y     y coordinate of text
     */
    public static void drawText(String s, int x, int y) {
        StdDraw.text(x, y, s);
        StdDraw.show();
    }

    /**
     * Await and get the input seed from keyboard.
     * width and height are relative to display user input.
     * Only digits will be captured as seed when user type 'n' / 'N'.
     * Only 'L' / 'l' will be captured when user type 'L' / 'l'.
     * @param width     width of canvas
     * @param height    height of canvas
     * @return
     */
    public static String userInput(int width, int height) {
        String input = "";
        boolean seedStart = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'l' || key == 'L') {
                    input = "l"; // 是否需要draw frame?
                    break;
                } else if (!seedStart) {
                    if (key == 'n' || key == 'N') {
                        seedStart = true;
                        StdDraw.setPenColor(GOLD);
                        drawText("Please enter seed:", Math.round(width / 2),
                                Math.round(height / 14 * 4));
                    } else {
                        continue;
                    }
                } else if (seedStart && (key == 's' || key == 'S')) {
                    break;
                } else if (seedStart && ('0' <= key && key <= '9')) {
                    input += String.valueOf(key);
                    StdDraw.setPenColor(PALE_TURQUOISE_3);
                    drawStartFrame(width, height);
                    drawText(input, Math.round(width / 2), Math.round(height / 14 * 4));
                }
            }
        }
        return input;
    }
}
