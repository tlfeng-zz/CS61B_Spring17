/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

public class ArrayDeque {
    private int[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = new int[8];
        size = 0;
        nextFirst=0; nextLast=0;
    }

    public int minusOne(int index) {
        int before = index-1;
        if (before < 0)
            before += items.length;
        return before;
    }

    public int plusOne(int index) {
        int after = index+1;
        if (after > items.length-1)
            after -= items.length;
        return after;
    }

    /** Inserts X into the front of the list. */
    public void addFirst(int x) {
        if(nextLast != 0)
            nextFirst = minusOne(nextFirst);

        items[nextFirst] = x;
        size ++;
        nextFirst = minusOne(nextFirst);
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        if(nextFirst != 0)
            nextLast = plusOne(nextLast);
        
        items[nextLast] = x;
        size ++;
        nextLast = plusOne(nextLast);
    }

    /** Gets the ith item in the list (0 is the front). */
    public int get(int index) {
        index += plusOne(nextFirst);
        if (index>items.length)
            index -= items.length;
        return items[index];
    }

    public boolean isEmpty() {
        if (size ==0)
            return true;
        else
            return false;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public void printDeque() {
        int index = plusOne(nextFirst);
        for (int i=0; i<size(); i++) {
            System.out.print(items[index]+" ");
            index = plusOne(index);
        }
    }

    public int removeFirst() {
        nextFirst ++;
        size --;
        return items[plusOne(nextFirst)];
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public int removeLast() {
        nextLast --;
        size --;
        return items[minusOne(nextLast)];
    }

    public static void main(String[] args) {
        ArrayDeque x = new ArrayDeque();
        x.addFirst(3);
        x.addFirst(6);
        x.addLast(2);
        x.get(2);
    }
} 