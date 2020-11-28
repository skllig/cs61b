/**
 * This class defines a method to check whether characters
 * that are different by exactly N.
 * @author skllig
 */
public class OffByN implements CharacterComparator {
    /** N is characters difference. */
    private int n;

    /**
     * Construction method to set n.
     * @param N
     */
    public OffByN(int N) {
        this.n = N;
    }
    /**
     * Returns true if characters are equal
     * by the rules of the implementing class.
     *
     * @param x
     * @param y
     */
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == this.n;
    }
}
