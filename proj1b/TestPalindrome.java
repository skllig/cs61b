import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String word = "racecar";
        assertTrue(palindrome.isPalindrome(word));
        word = "noon";
        assertTrue(palindrome.isPalindrome(word));
        word = "";
        assertTrue(palindrome.isPalindrome(word));
        word = "A";
        assertTrue(palindrome.isPalindrome(word));
        word = "aaa";
        assertTrue(palindrome.isPalindrome(word));
        word = "AaaaaaaaaA";
        assertTrue(palindrome.isPalindrome(word));

        word = "horse";
        assertFalse(palindrome.isPalindrome(word));
        word = "aaaaab";
        assertFalse(palindrome.isPalindrome(word));
        word = "Aa";
        assertFalse(palindrome.isPalindrome(word));
        word = "AaaaaaaaAA";
        assertFalse(palindrome.isPalindrome(word));
    }

    @Test
    public void testisPalindrome() {
        CharacterComparator cc = new OffByOne();

        String word = "flake";
        assertTrue(palindrome.isPalindrome(word, cc));
        word = "acegfdb";
        assertTrue(palindrome.isPalindrome(word, cc));
        word = "%acb&";
        assertTrue(palindrome.isPalindrome(word, cc));

        word = "acegcd";
        assertFalse(palindrome.isPalindrome(word, cc));

    }

}
