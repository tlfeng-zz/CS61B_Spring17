public class LinkedListDeque {
    public class IntNode {
        public int item;
        public IntNode next, prev;
        public IntNode(int i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /** The first item (if exists) is at sentinel.next */
    private IntNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new IntNode(63, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(int x) {
        sentinel = new IntNode(63, null, null);
        sentinel.next = new IntNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next, sentinel);
        size += 1;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        // When list is empty?
        return sentinel.next.item;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        sentinel.prev = new IntNode(x, sentinel.next, sentinel.prev);
        sentinel.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        if (sentinel.next == sentinel)
            return true;
        else
            return false;
    }

    //public int removeFirst() {

    //}

    //public int removeLast() {

    //}

    //public int get(int index) {

    //}

    //public int getRecursive(int index) {

    //}

    /** Crashes when you call addLast on an empty SLList. Fix it. */
    public static void main(String[] args) {
        LinkedListDeque x = new LinkedListDeque();
        //x.addFirst(5);
        x.addLast(10);
        x.addLast(15);
        System.out.println(x.getFirst());
        System.out.println(x.isEmpty());
    }
}
