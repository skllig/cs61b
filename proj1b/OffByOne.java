/**
 * This class defines a method to check whether characters that are
 * different by exactly one.
 * @author skllig
 */
public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}
