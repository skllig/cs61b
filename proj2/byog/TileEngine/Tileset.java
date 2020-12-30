package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile PLAYER = new TETile('κ', new Color(255,69,0), new Color(13,45,21), "player");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray, "wall");
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black, "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black, "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black, "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
    public static final TETile ASTERISK = new TETile('❃', new Color(230,230,250), new Color(47,79,79), "wall");
    public static final TETile DOT = new TETile('•', new Color(255,250,240), new Color(13,45,21), "floor");
    public static final TETile TARGET = new TETile('★', new Color(252,74,26), new Color(74,189,172), "target");
}



