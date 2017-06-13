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
        boolean flag = true;
        for (int i = 0; i < word.length() / 2 + 1; i++) {
            if ( word.charAt(i) != word.charAt(word.length()-i-1) )
                flag = false;
        }
        return flag;
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {

    }

    public static void main(String[] args) {
        Deque<Character> deque = wordToDeque("abcba");
        deque.printDeque();
        System.out.println(isPalindrome("abcba"));
    }
}
