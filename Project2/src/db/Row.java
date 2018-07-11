package db;

import java.util.List;
import java.util.ArrayList;

public class Row<T extends Comparable> {
    List<T> rowEList;

    public Row(List<T> rowEList) {
        this.rowEList = new ArrayList<>();
        this.rowEList = rowEList;
    }
}
