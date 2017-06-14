public class PalindromeTest {

    public static void main(String[] args) {
        Palindrome pa = new Palindrome();
        Deque<Character> deque = pa.wordToDeque("abcba");
        deque.printDeque();
        System.out.println(pa.isPalindrome("abcba"));
        CharacterComparator OffBy1 = new OffByOne();
        System.out.println(pa.isPalindrome("adcbb",OffBy1));
        CharacterComparator OffBy3 = new OffByN(3);
        System.out.println(pa.isPalindrome("aecbd",OffBy3));
        /** TFT */
    }
}
