package db;

import java.util.ArrayList;
import java.util.List;

public class Column<T extends Comparable>  {
    String name;
    String type;
    List<T> colEList;

    public Column() {
        colEList = new ArrayList<>();
    }
}
