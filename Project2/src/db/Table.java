package db;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private String name;
    private int rowNum;
    private int colNum;

    private List<Row> rowList;
    private List<Column> colList;

    public Table(String name) {
        rowNum = 0;
        colNum = 0;
        rowList = new ArrayList<>();
        colList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public List<Row> getRowList() {
        return rowList;
    }

    public void setRowList(List<Row> rowList) {
        this.rowList = rowList;
    }

    public List<Column> getColList() {
        return colList;
    }

    public void setColList(List<Column> colList) {
        this.colList = colList;
    }

    public <T extends Comparable> void addRow(Row row) {
        // initialize colNum
        if(colNum == 0) {
            colNum = row.rowEList.size();
            for(int i=0; i<colNum; i++)
                colList.add(new Column());
        }

        // Add row to rowList
        rowList.add(row);
        // Add row elements to column;
        for(int i=0; i<colList.size(); i++) {
            T rowElement = (T)row.rowEList.get(row.rowEList.size()-1);
            colList.get(i).getColEList().add(rowElement);
        }

        rowNum++;
    }

    public <T extends Comparable> Table join(Table t2) {
        // find common column name
        // create list to storage the name
        List<String> commonNameList = new ArrayList<>();
        for(int i=0; i<colList.size(); i++) {
            for(int j=0; j<t2.colList.size(); j++) {
                if( colList.get(i).getName().equals(t2.colList.get(j).getName()) ) {
                    commonNameList.add( colList.get(i).getName() );
                }
            }
        }

        // create new table
        int newColNum = colList.size() + t2.colList.size() - commonNameList.size();
        String[] newNameArr = new String[newColNum];
        String[] newTypeArr = new String[newColNum];

        // when no common columns,
        // the result of the join is the cartesian product of the two tables
        if ( commonNameList.size() == 0) {
            // add name to colNameList
            for (int i=0; i<colList.size(); i++) {
                newNameArr[i] = colList.get(i).getName();
            }
            int startIndex = colList.size();
            for (int i=startIndex; i<startIndex+t2.colList.size(); i++) {
                newNameArr[i] = t2.colList.get(i-startIndex).getName();
            }

            // add column types
            for (int i=0; i<newColNum; i++) {
                newTypeArr[i] = "int";
            }
            // create new table
            Table resultTbl = new Table("");
            // nature join
            for(int i=0; i<rowNum; i++) {
                for (int j=0; j<t2.rowNum; j++) {
                    List<T> newRowEList = new ArrayList<>();
                    for(int k=0; k<rowList.get(i).rowEList.size(); k++) {
                        newRowEList.add((T)rowList.get(i).rowEList.get(k));
                    }
                    for(int l=0; l<t2.rowList.get(j).rowEList.size(); l++) {
                        newRowEList.add((T)t2.rowList.get(j).rowEList.get(l));
                    }
                    // add row to table
                    Row newRow = new Row(newRowEList);
                    resultTbl.addRow(newRow);
                }

            }
            return resultTbl;
        }

        // record the index of the common column in each db;
        int[] commonColIndex1 = new int[commonNameList.size()];
        int[] commonColIndex2 = new int[commonNameList.size()];
        for(int i=0; i<commonNameList.size(); i++) {
            for(int j=0; j<colList.size(); j++) {
                if (commonNameList.get(i).equals(colList.get(j).getName()))
                    commonColIndex1[i] = j;
            }
        }
        for(int i=0; i<commonNameList.size(); i++) {
            for(int j=0; j<t2.colList.size(); j++) {
                if (commonNameList.get(i).equals(t2.colList.get(j).getName()))
                    commonColIndex2[i] = j;
            }
        }

        // add new column names and types
        // add common names and types
        for (int i=0; i<commonNameList.size(); i++) {
            newNameArr[i] = commonNameList.get(i);
            newTypeArr[i] = colList.get(commonColIndex1[i]).getType();
        }
        int newNameindex = commonNameList.size();

        // add the rest names and types
        for (Column col: colList) {
            boolean flag = false;
            for (String commonName: commonNameList) {
                if (col.getName().equals(commonName)) {
                    flag = true;
                }
            }
            if (flag == false) {
                newNameArr[newNameindex] = col.getName();
                newTypeArr[newNameindex] = col.getType();
                newNameindex++;
            }

        }
        for (Column col: t2.colList) {
            boolean flag = false;
            for (String commonName: commonNameList) {
                if (col.getName().equals(commonName)) {
                    flag = true;
                }
            }
            if (flag == false) {
                newNameArr[newNameindex] = col.getName();
                newTypeArr[newNameindex] = col.getType();
                newNameindex++;
            }

        }

        Table resultTbl = new Table("");

        // nature join -- nested loop join
        for(int i=0; i<rowNum; i++) {
            for (int j=0; j<t2.rowNum; j++) {
                // compare the elements in the specific column
                boolean matchSign = true;
                for(int k=0; k<commonNameList.size(); k++) {
                    // match the join condition
                    if (! rowList.get(i).rowEList.get(commonColIndex1[k])
                            .equals(t2.rowList.get(j).rowEList.get(commonColIndex2[k]))) {
                        matchSign = false;
                    }
                }
                // start join
                if ( matchSign == true ) {
                    // build new joined row
                    List<Object> newRowEList = new ArrayList<>();
                    // put common column in
                    for(int k=0; k<commonNameList.size(); k++) {
                        newRowEList.add(rowList.get(i).rowEList.get(commonColIndex1[k]));
                    }
                    // put other in table1
                    for (int l = 0; l < colNum; l++) {
                        boolean commonSign = false;
                        for(int k=0; k<commonNameList.size(); k++) {
                            if (l == commonColIndex1[k]) {
                                commonSign = true;
                            }
                        }
                            if (commonSign == false) {
                                newRowEList.add(rowList.get(i).rowEList.get(l));
                            }
                    }
                    // put other in table2
                    for (int l = 0; l < colNum; l++) {
                        boolean commonSign = false;
                        for(int k=0; k<commonNameList.size(); k++) {
                            if (l == commonColIndex2[k]) {
                                commonSign = true;
                            }
                        }
                            if (commonSign == false) {
                                newRowEList.add(t2.rowList.get(j).rowEList.get(l));
                            }
                    }

                    Row newRow = new Row(newRowEList);
                    resultTbl.addRow(newRow);
                }
            }
        }
        return resultTbl;
    }

}
