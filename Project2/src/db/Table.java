package db;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    int rowNum;
    int colNum;
    List<Row> rowList;
    List<Column> colList;
    List<String> colNameList;
    List<String> colTypeList;

    public Table(String[] nameArr, String[] typeArr) {
        rowNum = 0;
        colNum = 0;
        rowList = new ArrayList<>();
        colList = new ArrayList<>();
        colNameList = Arrays.asList(nameArr);
        colTypeList = Arrays.asList(typeArr);
    }

    public void addRow(Row row) {
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
            int rowElement = (int)row.rowEList.get(i);
            colList.get(i).colEList.add(rowElement);
        }

        rowNum++;
    }

    public Table join(Table t2) {
        // find common column name
        // create list to storage the name
        List<String> commonNameList = new ArrayList<>();
        for(int i=0; i<colNameList.size(); i++) {
            for(int j=0; j<t2.colNameList.size(); j++) {
                if( colNameList.get(i).equals(t2.colNameList.get(j)) ) {
                    commonNameList.add( colNameList.get(i) );
                }
            }
        }

        // create new table
        int newColNum = colNameList.size() + t2.colNameList.size() - commonNameList.size();
        String[] newNameArr = new String[newColNum];
        String[] newTypeArr = new String[newColNum];

        // when no common columns,
        // the result of the join is the cartesian product of the two tables
        if ( commonNameList.size() == 0) {
            // add name to colNameList
            for (int i=0; i<colNameList.size(); i++) {
                newNameArr[i] = colNameList.get(i);
            }
            int startIndex = colNameList.size();
            for (int i=startIndex; i<startIndex+t2.colNameList.size(); i++) {
                newNameArr[i] = t2.colNameList.get(i-startIndex);
            }

            // add column types
            for (int i=0; i<newColNum; i++) {
                newTypeArr[i] = "int";
            }
            // create new table
            Table resultTbl = new Table(newNameArr, newTypeArr);
            // nature join
            for(int i=0; i<rowNum; i++) {
                for (int j=0; j<t2.rowNum; j++) {
                    List<Object> newRowEList = new ArrayList<>();
                    for(int k=0; k<rowList.get(i).rowEList.size(); k++) {
                        newRowEList.add(rowList.get(i).rowEList.get(k));
                    }
                    for(int l=0; l<t2.rowList.get(j).rowEList.size(); l++) {
                        newRowEList.add(t2.rowList.get(j).rowEList.get(l));
                    }
                    // add row to table
                    Row newRow = new Row(newRowEList);
                    resultTbl.addRow(newRow);
                }

            }
            return resultTbl;
        }

        // add new column names
        // add common names
        for (int i=0; i<commonNameList.size(); i++) {
            newNameArr[i] = commonNameList.get(i);
        }
        int newNameindex = commonNameList.size();

        // add the rest names
        for (String name: colNameList) {
            boolean flag = false;
            for (String commonName: commonNameList) {
                if (name.equals(commonName)) {
                    flag = true;
                }
            }
            if (flag == false) {
                newNameArr[newNameindex] = name;
                newNameindex++;
            }

        }
        for (String name: t2.colNameList) {
            boolean flag = false;
            for (String commonName: commonNameList) {
                if (name.equals(commonName)) {
                    flag = true;
                }
            }
            if (flag == false) {
                newNameArr[newNameindex] = name;
                newNameindex++;
            }

        }
        // add column types
        for (int i=0; i<newColNum; i++) {
            newTypeArr[i] = "int";
        }

        Table resultTbl = new Table(newNameArr, newTypeArr);

        // record the index of the common column in each db;
        int[] commonColIndex1 = new int[commonNameList.size()];
        int[] commonColIndex2 = new int[commonNameList.size()];
        for(int i=0; i<commonNameList.size(); i++) {
            for(int j=0; j<colNameList.size(); j++) {
                if (commonNameList.get(i).equals(colNameList.get(j)))
                    commonColIndex1[i] = j;
            }
        }
        for(int i=0; i<commonNameList.size(); i++) {
            for(int j=0; j<t2.colNameList.size(); j++) {
                if (commonNameList.get(i).equals(t2.colNameList.get(j)))
                    commonColIndex2[i] = j;
            }
        }

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
