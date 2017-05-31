public class SLList {
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first; 

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }    

    /** Insert an item into the list. */
    public void insert (int item, int position) {
        IntNode p = this.first;

        if (position == 0) { // head position
            this.first = new IntNode(item, first);
            return;
        }

        for (int i=0; i<position-1; i++) {
            p = p.next;

            if(p.next == null) { // exceed index
                break;
            }
        }
        p.next = new IntNode(item, p.next);
    }

    /** reverse by AddFirst method */
    public void reverse() {
        // point at first element
        IntNode p = this.first;
        // next node before operation
        IntNode next = null;
        // new node after operation
        IntNode prev = null;
        while (p != null) {
            next = p.next;
            p.next = prev;
            prev = p;
            p = next;
        }
        first = prev;
    }

    /** recursive not understand yet */
    public void reverseReHelper(IntNode front) {
        if (front.next == null)
            return front;
        IntNode reversed = reverseReHelper(front.next);
        front.next.next = front;
        front.next = null;
        return reversed;
    }

    public void reverseRe() {
        first = reverseReHelper(first);
    }

    /** Test. */
    public static void main(String[] args) {
        SLList x = new SLList();
        x.addFirst(15);
        x.addFirst(10);
        x.addFirst(5);
        x.reverse();
    }
}