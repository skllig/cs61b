/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {

    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("/Users/kkkk/Desktop/cs61b/library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator cc = new OffByOne();

//        while (!in.isEmpty()) {
//            String word = in.readString();
//            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
//                System.out.println(word);
//            }
//        }

//        while (!in.isEmpty()) {
//            String word = in.readString();
//            if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
//                System.out.println(word);
//            }
//        }

        cc = new OffByN(5);
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                System.out.println(word);
            }
        }
    }
}
