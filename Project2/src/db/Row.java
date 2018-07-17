package db;

import java.util.List;
import java.util.ArrayList;

public class Row<T extends Comparable> {
    private List<T> rowEList;

    public Row(List<T> rowEList) {
        this.rowEList = new ArrayList<>();
        this.rowEList = rowEList;
    }

    public List<T> getRowEList() {
        return rowEList;
    }

    public void setRowEList(List<T> rowEList) {
        this.rowEList = rowEList;
    }
}
