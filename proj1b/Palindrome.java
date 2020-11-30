/**
 * @author skillig
 */
public class Palindrome {
    /**
     * return a Deque where the characters appear
     * in the same orders as in the String.
     * @param word word to be inserted in deque
     * @return a deque contains the characters appear
     * in the same orders as in the String
     */
    public Deque<Character> wordToDeque(String word) {
        Deque que = new LinkedListDeque();
        for (int i = 0; i < word.length(); i++) {
            que.addLast(word.charAt(i));
        }
        return que;
    }

    /**
     * Return true if a word is the same whether
     * it is read forwards or backwards.
     * @param word word to be checked whether is palindrome
     * @return true if word is palindrome
     */
    public boolean isPalindrome(String word) {
        for (int i = 0; i < word.length() / 2; i++) {
            int j = word.length() - 1 - i;
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true if a word is the same whether
     * it is read forwards or backwards in a recursive way.
     * @param word word to be checked whether is palindrome
     * @return true if word is palindrome
     */
    private boolean isPalindromeRecursive(String word) {
        return isTwoCharTheSame(word, 0, word.length() - 1);
    }

    /**
     * Helper function of method isPalindromeRecursive.
     * Check whether the given two characters are the same.
     * @param word the word to be checked
     * @param lo index of word at the left hand side
     * @param hi index of word at the right hand side
     * @return true if the given two characters are the same character
     */
    private boolean isTwoCharTheSame(String word, int lo, int hi) {
        if (lo >= hi) {
            return true;
        }
        if (word.charAt(lo) != word.charAt(hi)) {
            return false;
        }
        return isTwoCharTheSame(word, lo + 1, hi - 1);
    }

    /**
     * Return true if the word is a palindrome according to the character
     * comparison test provided by the CharacterComparator cc.
     * @param word word to be checked
     * @param cc the CharacterComparator
     * @return
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length() / 2; i++) {
            int j = word.length() - 1 - i;
            if (!cc.equalChars(word.charAt(i), word.charAt(j))) {
                return false;
            }
        }
        return true;
    }

}
