package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import javax.xml.stream.events.StartDocument;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);                           // seed是为了再现程序
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16); // 说的是比例，画布的像素
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);       // 说的坐标 take note，下面各个方法设置的x,y位置的范围
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);             // 清空画布
        StdDraw.enableDoubleBuffering();        // 启用缓存机制

        //TODO: Initialize random number generator
        this.rand = new Random(seed);           // 初始化伪随机生成器

    }

    /**
     * Randomly generate a n length string.
     * @param n length of string
     * @return  the randomly generated string
     */
    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int ord = rand.nextInt(25);
            sb.append(CHARACTERS[ord]);
        }
        return sb.toString();
    }

    /**
     * Draw String s in the center of the canvas
     * @param s
     */
    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        Double x = this.width * 0.5;    // x 坐标
        Double y = this.height * 0.5;   // y 坐标
        Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        Font smallFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        StdDraw.setPenColor(new Color(150, 205, 205));
        StdDraw.clear(Color.BLACK);     // 每次画图，清空画布

        // 头部bar，显示游戏信息
        if (!gameOver) {
            StdDraw.line(0, height - 2, width, height - 2);
            StdDraw.setFont(smallFont);
            String roundInfo = "Round: " + round;
            StdDraw.textLeft(1, height - 1, roundInfo);  // textLeft(x, y, msg) 是(x,y)在mgs的左边
//            StdDraw.text(3, height - 1, roundInfo);         // text（x, y, msg) 是以(x,y)为中心显示msg
            String action = playerTurn? "Type!" : "Watch!";   // 提醒玩家现在是观察状态还是输入状态
            StdDraw.text(x, height - 1, action);
            String encourage = ENCOURAGEMENT[round % ENCOURAGEMENT.length];
            StdDraw.textRight(width - 1, height - 1, encourage);    // textRight是 (x,y)在msg的右边
        }
        StdDraw.setFont(bigFont);
        StdDraw.text(x, y, s);
        StdDraw.show();                                       // 和 enableDoubleBuffering 是成对出现的
    }

    /**
     * Take the input string and displays one character at a time centered
     * on the screen. Each character should be visible on the screen for
     * 1 second and there should be a brief 0.5 second break between
     * characters where the screen is blank.
     * @param letters
     */
    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.pause(500);
        }
    }

    /**
     * Read n keystrokes using StdDraw and returns the string corresponding to those keystrokes.
     * If a backspace is pressed, remove the last char and minus cnt by 1.
     * @param n
     * @return
     */
    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int cnt = 0;
        String res = "";
        while (cnt < n) {
            if (StdDraw.hasNextKeyTyped()) {
                StdDraw.clear();
                char key = StdDraw.nextKeyTyped();
                if (StdDraw.isKeyPressed(8)) {   // rollback
                    res = res.substring(0, res.length() - 1);
                    cnt -= 1;
                } else {
                    res += String.valueOf(key);
                    cnt += 1;
                }
                drawFrame(res);
            }
        }
        StdDraw.pause(500);
        return res;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        gameOver = false;
        round = 1;
        String failMsg = "Game Over! You made it to round: ";
        String requested;

        //TODO: Establish Game loop
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);                   // 第几轮游戏
            StdDraw.pause(1500);

            requested = generateRandomString(round);          // 生成随机round长字符串
            flashSequence(requested);                         // 闪烁字符串

            playerTurn = true;
            String userInput = solicitNCharsInput(round);     // 用户输入round长字符串
            System.out.println("userinput " + userInput);
            if (requested.equals(userInput)) {                // 字符串相等，开始下一轮
                round += 1;
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
            } else {                                          // 字符串不同，游戏停止
                failMsg += round;
                drawFrame(failMsg);
                gameOver = true;
            }
        }
    }

}
