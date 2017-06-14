public class Palindrome {

    public static Deque<Character> wordToDequeHelper(Deque deque,String word, int index) {
        if (index == word.length())
            return deque;
        else {
            deque.addLast(word.charAt(index));
            return wordToDequeHelper(deque, word, index+1);
        }
    }

    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDequeSolution<>();
        return wordToDequeHelper(deque, word, 0);

        /* Iterative method
        Deque<Character> deque = new ArrayDequeSolution<>();
        for (int i=0; i<word.length(); i++)
            deque.addLast(word.charAt(i));
        return deque; */
    }

    public static boolean isPalindrome(String word) {
        for (int i = 0; i < word.length() / 2; i++) {
            if ( word.charAt(i) != word.charAt(word.length()-i-1) )
                return false;
        }
        // The condition of one character
        if (word.length() == 1)
            return false;

        return true;
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(word.length() - i - 1)))
                return false;
        }
        // The condition of one character
        if (word.length() == 1)
            return false;

        return true;
    }

    public static void main(String[] args) {
        Deque<Character> deque = wordToDeque("abcba");
        deque.printDeque();
        System.out.println(isPalindrome("abcba"));
        CharacterComparator OffBy1 = new OffByOne();
        System.out.println(isPalindrome("adcbb",OffBy1));
    }
}
