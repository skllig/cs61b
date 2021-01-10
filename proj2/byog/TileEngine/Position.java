package byog.TileEngine;

public class Position {
    public int x, y;
    public Position(int xVal, int yVal) {
        x = xVal;
        y = yVal;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
