package db;

import java.util.ArrayList;
import java.util.List;

public class Column<T extends Comparable>  {
    private String name;
    private String type;
    private List<T> colEList;

    public Column() {
        colEList = new ArrayList<>();
    }

    public Column(String name, String type) {
        colEList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getColEList() {
        return colEList;
    }

    public void setColEList(List<T> colEList) {
        this.colEList = colEList;
    }
}
