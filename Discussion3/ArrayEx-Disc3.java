/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

public class ArrayEx {
    /**  Alternately, System.arraycopy */
    public static int[] insert(int[] x, int item, int position) {
        int[] newlist = x;
        if(position>=x.length-1) // or math.min
            position = x.length-1;
        for (int i=x.length-1; i>position; i--)
            newlist[i] = x[i-1];
        newlist[position] = item;
        for (int i=0; i<position; i++)
            newlist[i] = x[i];
        return newlist;
    }

    public static void reverse(int[] x) {
        for(int i=0; i<=x.length/2; i++) {
            int temp = x[i];
            x[i] = x[x.length-i-1];
            x[x.length-i-1] = temp;
        }
    }

    public static int[] xify(int[] x) {
        int num = 0;
        int index = 0;
        for (int i=0; i<x.length; i++) //(int item:x)
            num += x[i];
        int[] newlist = new int[num];
        for (int i=0; i<x.length; i++) {
            for (int j=0; j<x[i]; j++) {
                newlist[index] = x[i];
                index++;
            }
        }
        return newlist;
    }

    public static void main(String[] args) {
        int[] x = new int[5];
        x[0]=5; x[1]=9; x[2]=14; x[3]=15;
        //int[] y = insert(x,6,2);
        //reverse(x);
        int[] z = xify(x);
    }
} 