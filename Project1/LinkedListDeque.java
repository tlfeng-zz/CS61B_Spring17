public class LinkedListDeque {
    public class IntNode {
        private int item;
        private IntNode next, prev;
        private IntNode(int i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /** The first item (if exists) is at sentinel.next */
    private IntNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new IntNode(63, null,null);
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

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        IntNode p = new IntNode(x, sentinel.next, sentinel);
        sentinel.next.prev = p;
        sentinel.next = p;
        if (isEmpty())
            sentinel.prev = p;
        size += 1;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        IntNode p = new IntNode(x, sentinel, sentinel.prev);
        sentinel.prev.next = p;
        sentinel.prev = p;
        if (isEmpty())
            sentinel.next = p;
        size += 1;
    }

    public int removeFirst() {
        if (isEmpty())
            return -1;
        else {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return sentinel.next.item;
        }
    }

    public int removeLast() {
        if (isEmpty())
            return -1;
        else {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return sentinel.prev.item;
        }
    }

    /** Retrieves the front item from the list. */
    public int get(int index) {
        IntNode p = sentinel.next;
        for (int i=0; i<index; i++) {
            p = p.next;
        }
        if (isEmpty())
            return -1;
        else
            return p.item;
    }

    public int getRecursiveHelper(int index, IntNode p) {
        if (index == 0)
            return p.item;
        else
            return getRecursiveHelper (index-1, p.next);
    }

    public int getRecursive(int index) {
        if (index > this.size)
            return -1;
        else
            return getRecursiveHelper (index, this.sentinel.next);
    }

    /** Crashes when you call addLast on an empty SLList. Fix it. */
    public static void main(String[] args) {
        LinkedListDeque x = new LinkedListDeque();
        x.addFirst(5);
        x.addLast(10);
        x.addLast(15);
        //x.removeFirst();
        //x.removeLast();
        System.out.println(x.get(0));
        System.out.println(x.getRecursive(0));
        //System.out.println(x.isEmpty());
    }
}
