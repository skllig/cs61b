package byog.Core;

import byog.TileEngine.Position;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int NAVIGATION_HEIGHT = 2;  // 顶部的navigation bar
    private static boolean ALIVE = true;
    private static String fileName = "canvas.txt";  // 需要keep以前版本吗？
    private static Player player;
    private static TETile[][] worldState;
    private static int targetX, targetY;            // x, y coordinate of the target
    private static String VICTORY = "Well done! You win the game!";

    /**
     * Handle action keys press (arrows).
     */
    private void handleKeyPress() {
        // set font control all tiles (except the start page)
        // StdDraw.setFont(DrawUtls.smallFont);
        if (StdDraw.isKeyPressed(37)) {
            player.moveLeft(worldState, Tileset.DOT);
            StdDraw.pause(80);
        } else if (StdDraw.isKeyPressed(38)) {
            player.moveUp(worldState, Tileset.DOT);
            StdDraw.pause(80);
        } else if (StdDraw.isKeyPressed(39)) {
            player.moveRight(worldState, Tileset.DOT);
            StdDraw.pause(80);
        } else if (StdDraw.isKeyPressed(40)) {
            player.moveDown(worldState, Tileset.DOT);
            StdDraw.pause(80);
        }
    }

    /**
     * Handle physical keys.
     * 'a' :right
     * 'd' : left
     * 'w' : up
     * 's' : down
     * ":q", ":Q": save and quit
     */
    private void handleKeyType() {
        if (StdDraw.hasNextKeyTyped()) {
            // set font control all tiles (except the start page)
            // StdDraw.setFont(DrawUtls.smallFont);

            // a: left d: right w: up s: down
            char key = StdDraw.nextKeyTyped();
            if (key == 'a' || key == 'A') {
                player.moveLeft(worldState, Tileset.DOT);
            } else if (key == 'd' || key == 'D') {
                player.moveRight(worldState, Tileset.DOT);
            } else if (key == 'w' || key == 'W') {
                player.moveUp(worldState, Tileset.DOT);
            } else if (key == 's' || key == 'S') {
                player.moveDown(worldState, Tileset.DOT);
            } else if (key == ':') {
                System.out.println("game.java  line 61");
                awaitingNextChar('q');
                // save and quit
                save(worldState);
                System.exit(0);
            }
        }
    }

    /**
     * Return true if player hit the target, false otherwise.
     * @return  true if player hit the target, false otherwise
     */
    private boolean playerHitTarget() {
        boolean hit = false;
        if (player.getX() == targetX && Math.abs(player.getY() - targetY) == 1) {
            System.out.println(player + " " + "target x: " + targetX + " target y: " + targetY );
            hit = true;
        } else if (player.getY() == targetY && Math.abs(player.getX() - targetX) == 1) {
            System.out.println(player + " " + "target x: " + targetX + " target y: " + targetY );
            hit = true;
        }
        return hit;
    }

    /**
     * Awaiting user input
     * @param expected
     */
    private void awaitingNextChar(char expected) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == expected || key == Character.toUpperCase(expected)) {
                    return;
                } else {
                    throw new RuntimeException("The given key is not expected as " + expected);
                }
            }
        }
    }

    /**
     * Save(serialize) existing worldState and player into file
     * when user press ":Q" / ":q".
     */
    private void save(TETile[][] canvas) {
        try (   // 写在() 里的资源，会自动关闭，无需finally
                FileOutputStream file = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(file);
        ) {
            out.writeObject(canvas);
            out.writeObject(player);
        } catch (IOException e) {
            System.out.println("game.java line 106 IOException");
        }
    }

    /**
     * Quit.
     */
    private void quit() {
        System.exit(0);
    }

    /**
     * Return true if input string contains :q, false otherwise
     * @param input     input string
     * @return          true if input string contains :q, false otherwise
     */
    private boolean isSaveAndQuit(String input) {
        return input.toLowerCase().contains(":q");
    }

    /**
     * Return true if user request to load old canvas, false otherwise.
     * @param input     user input
     * @return return true if user request to load old canvas
     */
    private boolean isUserRequestToLoad(String input) {
        return input.toLowerCase().startsWith("l");
    }

    /**
     * Load old canvas from local file. Return null f no serialization file is found.
     * Set player properly if old canvas is loaded successfully.
     * @return      old canvas if serialization file exists, null otherwise.
     * @throws IOException
     */
    private TETile[][] loadCanvas() throws IOException{
        TETile[][] canvas = null;
        try (
                FileInputStream file = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(file);
        ) {
            canvas = (TETile[][]) in.readObject();
            player = (Player) in.readObject();
            System.out.println("try to load canvas: ");
            System.out.println(canvas);
        } catch (ClassNotFoundException e) {
            System.out.println("game.java line 117 IOException");
        }
        return canvas;
    }


    /***
     * Return old canvas if there exists an old version canvas can be loaded, null otherwise.
     * If old canvas is loaded successfully, player will be loaded properly.
     * @return          old canvas if there exists an old version canvas can be loaded, null otherwise.
     */
    private TETile[][] readyToloadCanvas() {
        TETile[][] canvas;
        try {
            canvas = (TETile[][]) loadCanvas();
        } catch (IOException e) {
            canvas = null;
        }
        return canvas;
    }


    /**
     * If 'l' / 'L' exists in user input string, try to load old canvas
     * from local file, null otherwise and set player accordingly.
     * However, if user does request to load old canvas ('l' / 'L' in input string),
     * but there doesn't exist an old canvas, quit directly.
     * @param input
     * @return
     */
    private TETile[][] load(String input) {
        TETile[][] oldCanvas;
        if (isUserRequestToLoad(input)) {
            oldCanvas = readyToloadCanvas();
            if (oldCanvas == null) {
                quit();
            }
        } else {
            oldCanvas = null;
        }
        return oldCanvas;
    }


    /**
     * Randomly generate and return the starter rectangle base on the given width
     * and height of the canvas.
     * @param r     pseudorandom generator
     * @return      starter rectangle
     */
    private Rectangle genStartRectangle(Random r) {
        int startX = RandomUtils.uniform(r, Math.round(WIDTH * 2 / 5), Math.round(WIDTH * 3 / 5));
        int startY = RandomUtils.uniform(r, Math.round(HEIGHT * 2 / 5), Math.round(HEIGHT * 3 / 5));
        int startW = RandomUtils.uniform(r, Math.round(WIDTH / 12), Math.round(WIDTH / 8));
        int startH = RandomUtils.uniform(r, Math.round(HEIGHT / 8), Math.round(HEIGHT / 4));
        // Rectangle starter = new Rectangle(new Position(10, 15), 10, 5);
        // generate starter rectangle
        Rectangle starter = new Rectangle(new Position(startX, startY), startW, startH);
        return starter;
    }

    /**
     * Generate and draw a open door as target in the last rectangle.
     * Check the free sides of the last rectangle and randomly pick one.
     * @param world     canvas
     * @param last      the last rectangle
     * @param r         pseudorandom generator
     * @param door      tile type of the door
     * @return
     */
    private void generateTarget(TETile[][] world, Rectangle last, Random r, TETile door) {
        List<Integer> sideUsage = last.checkSidesUsage();
        if (sideUsage.size() != 0) {
            int side = RandomUtils.uniform(r, 0, sideUsage.size());
            int x = 0, y = 0;
            switch (side) {
                case 0: // top
                    x = RandomUtils.uniform(r, last.leftUp.x + 1, last.rightUp.x);
                    y = last.leftUp.y;
                    break;
                case 1: // right
                    x = last.rightUp.x;
                    y = RandomUtils.uniform(r, last.rightDown.y + 1, last.rightUp.y);
                    break;
                case 2: // bottom
                    x = RandomUtils.uniform(r, last.leftDown.x + 1, last.rightDown.x);
                    y = last.leftDown.y;
                    break;
                case 3:
                    x = last.leftUp.x;
                    y = RandomUtils.uniform(r, last.leftDown.y + 1, last.leftUp.y);
            }
            world[x][y] = door;
            targetX = x;
            targetY = y;
            System.out.println("target X: " + targetX + " targetY: " + targetY);
        }
    }

    /**
     * Generate some connected rectangles base on the starter rectangle.
     * Target to generate 20 rectangles base on the starter. If there are available side to generate neighbors
     * but couldn't generate any this time, retry a few times according to the number of existing rectangles.
     * Generate a locked door in the last rectangle.
     *
     * @param world     canvas
     * @param r         pseudorandom generator
     * @param starter   starter rectangle
     */
    private void genRectangleNeighbors(TETile[][] world, Random r, Rectangle starter) {
        // que for generating rectangle
        Deque<Rectangle> que = new ArrayDeque<>();
        que.addLast(starter);
        int count = 1;  // count of rectangles

        while (!que.isEmpty()) {
            Rectangle current = que.pollFirst();
            // try to generate neighbors
            List<Rectangle> neighbors = DrawUtls.drawNeighbors(world, r, current, Tileset.ASTERISK, Tileset.DOT);

            // if there are available sides of the current rectangle, but no any neighbor is generated this time, retry.
            int retry = count < 20 ? 5: 3;
            if (neighbors.size() == 0 && current.checkSidesUsage().size() > 0) {
                for (int i = 0; i < retry; i++ ) {
                    neighbors = DrawUtls.drawNeighbors(world, r, current, Tileset.ASTERISK, Tileset.DOT);
                    if (neighbors.size() > 0) {
                        break;
                    }
                }
            }
            // generate and draw a open door as target in the last rectangle
            if (que.size() == 0 && neighbors.size() == 0) {
                generateTarget(world, current, r, Tileset.TARGET);
            }

            for (Rectangle rec : neighbors) {
                que.addLast(rec);
            }
            count += que.size();
        }
    }

    /**
     * Extract and return the seed in the given input string.
     * e.g. the seed of input string "N999SWWWSSSDDD" is 999.
     * @param input     input string
     * @return          string of seed
     */
    private String getSeed(String input) {
        int idxOfS = input.toLowerCase().indexOf("s");
        System.out.println("game.java idxofs " + idxOfS);
        return input.substring(1, idxOfS);
    }

    /**
     * Extract and return the movements action in the given input string from the given index.
     * If input string is "N999SDDD", "DDD" will be returned.
     * If input string is "LDDD" when user try to load old canvas and move right three times,
     * return "DDD".
     * @param input     input string
     * @param from      index to retrieve movements from in the given string
     * @return          movements string
     */
    private String getMovement(String input, int from) {
        String res = "";
        if (from < input.length()) {
            if (isSaveAndQuit(input)) {
                int quit = getQuitIndex(input);
                res = input.substring(from, quit);
            } else {
                res = input.substring(from);
            }
        }
        return res;
    }

    /**
     * Handle player movements with input string.
     * @param movements     movements to be taken
     */
    private void handPlayerMovement(TETile[][] world, String movements) {
        int cur = 0;
        while (cur < movements.length()){

            if (movements.charAt(cur) == 'a' || movements.charAt(cur) == 'A') {
                player.moveLeft(world, Tileset.DOT);
            } else if (movements.charAt(cur) == 'd' || movements.charAt(cur) == 'D') {
                player.moveRight(world, Tileset.DOT);
            } else if (movements.charAt(cur) == 'w' || movements.charAt(cur) == 'W') {
                player.moveUp(world, Tileset.DOT);
            } else if (movements.charAt(cur) == 's' || movements.charAt(cur) == 'S') {
                player.moveDown(world, Tileset.DOT);
            }
            cur += 1;
        }
    }

    /**
     * Return index of ":Q".
     * @param input     input string
     * @return          index of ":Q".
     */
    private int getQuitIndex(String input) {
        return input.toLowerCase().indexOf(":q");
    }

    /**
     * Base on the given input string, extract the seed and generate
     * a pseudorandom generator.
     * @param input     input string
     * @return          pseudorandom generator
     */
    private Random getPseudorandomGenerator(String input) {
        // seed
        String seedString = getSeed(input);
        long seed = Long.parseLong(seedString);
        System.out.println("game.java seedString: " + seedString);

        // pseudorandom generator
        Random r = new Random(seed);
        return r;
    }

    /**
     * Generate starter rectangle and generate neighbor rectangles randomly,
     * randomly place player in the starter rectangle and execute the given movements.
     * @param canvas    canvas
     * @param r         pseudorandom generator
     */
    private void generateCanvasAndPlacePlayer(TETile[][] canvas, String input, Random r) {
        // starter rectangle
        Rectangle starter = genStartRectangle(r);

        // place player in the starter rectangle and execute pre-defined movements if any
        DrawUtls.drawRectangle(canvas, r, starter, Tileset.ASTERISK, Tileset.DOT);
        player = new Player(r, starter, Tileset.PLAYER);
        player.placePlayer(canvas, starter, r);
        System.out.println("game.java " + player.toString());

        // pre-defined movements N999SWWWWW
        String movements = getMovement(input, input.toLowerCase().indexOf("s") + 1);
        System.out.println("game.java movements: " + movements);

        // generate other rectangle base on the starter rectangle
        genRectangleNeighbors(canvas, r, starter);
        handPlayerMovement(canvas, movements);
    }

    /**
     * Check if player hits the target. If player does so, stop the game and show victory
     * in naviagtion bar.
     */
    private void checkHitStatus(TETile[][] world) {
        if (playerHitTarget()) {
            ter.renderFrame(world, VICTORY);
            ALIVE = false;
            System.out.println("Win!!!!!!!!!!");
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     * After the start page is displayed and seed with format "N#####S" is given, latter
     * chars after 'S' won't be captured.
     * If user type "l" / "L" (load game) instead of "N" (new game), and if old canvas exists,
     * load old canvas accordingly, close the start page and quit otherwise.
     */
    public void playWithKeyboard() {
        // start page

        // for navigation bar 顶部留空5个unit
        DrawUtls.initialStartFrame(WIDTH, HEIGHT + NAVIGATION_HEIGHT);   // NAVIGATION_HEIGHT just to align with canvas page. Aesthetics purpose.\
        DrawUtls.drawStartFrame(WIDTH, HEIGHT + NAVIGATION_HEIGHT);

        // await and get input from user
        String userInput = DrawUtls.userInput(WIDTH, HEIGHT + NAVIGATION_HEIGHT);

        // Try to load old canvas. Quit if there is no old canvas but try to load it.
        worldState = load(userInput);

        // If user doesn't mean to load old canvas, generate new canvas instead.
        if (worldState == null) {
            worldState = playWithInputString("N" + userInput + "S");
        }

        while (ALIVE) {
            // draw canvas. Note: canvas will be clear after renderFrame()  handle navigation bar display
            StdDraw.setFont(DrawUtls.smallFont);

            ter.renderFrame(worldState, "");

            // capture action key stroke
            handleKeyPress();

            // capture key press
            handleKeyType();

            // check if player has found the target, show victory info and stop. Will update ALIVE once player hits target.
            checkHitStatus(worldState);

            StdDraw.show();
            StdDraw.pause(50);
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * No Interactivity in this method.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame; // 其实可以和playwithkeyboard直接share，但是既然定义在这里了，就用着吧。

        // if user try to load old version canvas, check whether can load the old version one properly?
        finalWorldFrame = load(input);

        // load old canvas and handle movements if any
        if (finalWorldFrame != null) {
            // movements, skip 'L' char   LWWWW
            String movements = getMovement(input, 1);
            System.out.println("game.java movements: " + movements);
            handPlayerMovement(finalWorldFrame, movements);
        }
        // generate new canvas and handle movements if any
        else {
            // initial canvas
            finalWorldFrame = new TETile[Game.WIDTH][Game.HEIGHT];
            DrawUtls.initialWorldWithoutRenderer(finalWorldFrame);

           // pseudorandom generator
            Random r = getPseudorandomGenerator(input);

            // generate canvas and place player in the starter rectangle and execute movements accordingly.
            generateCanvasAndPlacePlayer(finalWorldFrame, input, r);
        }

        // save but don't quit in playWithInputString
        System.out.println("Game.java is quit  " + isSaveAndQuit(input));
        if (isSaveAndQuit(input)) {
            save(finalWorldFrame);
        }
//        System.out.println(TETile.toString(finalWorldFrame));

        return finalWorldFrame;
    }
}
