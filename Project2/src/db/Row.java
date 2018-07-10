package db;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Row {
    List<Integer> rowEList;

    public Row(int[] rowArr) {
        rowEList = new ArrayList<>();
        for(int e: rowArr) {
            rowEList.add(e);
        }
        //rowEList = Arrays.asList(rowArr);
    }
}
