import java.util.ArrayList;

public class Lab5 {
    // A Table class

    int rowNum;
    ArrayList<int[]> rowList;
    public Lab5 () {
        rowNum = 0;
        rowList = new ArrayList<>();
    }

    public void addRow(int[] rowArr) {
        this.rowList.add(rowArr);
    }

    public static void main (String args[]) {
        Lab5 tableT1 = new Lab5();
        // add rows
        int[] row = new int[]{2,5};
        tableT1.addRow(row);
        row = new int[]{8,3};
        tableT1.addRow(row);
        row = new int[]{13,7};
        tableT1.addRow(row);

        // Show table content
        for (int[] rowx: tableT1.rowList) {
            for (int i=0; i<rowx.length; i++) {
                System.out.print(rowx[i]+" ");
            }
            System.out.println();
        }

    }
}
