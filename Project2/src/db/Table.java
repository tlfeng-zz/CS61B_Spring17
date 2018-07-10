package db;

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
        // Add row to rowList
        rowList.add(row);
        // Add row elements to column;
        for(int i=0; i<colList.size(); i++) {
            int rowElement = (int)row.rowEList.get(i);
            colList.get(i).colEList.add(rowElement);
        }

        rowNum++;
    }

    public void join(Table t2) {
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
        // find the same elements in the common column pair
        // assume only one common column now
        Column col1 = null;
        Column col2 = null;
        for(int i=0; i<colNameList.size(); i++) {
            if( colNameList.get(i).equals(commonNameList.get(0)) ) {
                // locate the column
                col1 = colList.get(i);
            }
            if( t2.colNameList.get(i).equals(commonNameList.get(0)) ) {
                // locate the column
                col2 = t2.colList.get(i);
            }
        }

        // create list to storage the same element
        List<Integer> commonEList = new ArrayList<>();
        for(int i=0; i<col1.colEList.size(); i++) {
            for(int j=0; i<col2.colEList.size(); j++) {
                if ( col1.colEList.get(i).equals(col2.colEList.get(j)) ) {
                    commonEList.add( col1.colEList.get(i) );
                }
            }
        }

        // create new table
        int newColNum = colNameList.size() + t2.colNameList.size() - commonNameList.size();
        String[] newNameArr = new String[newColNum];
        String[] newTypeArr = new String[newColNum];

        // add new column names
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
            }

        }

        for (int i=0; i<newColNum; i++) {
            newTypeArr[i] = "int";
        }

        //Table result = new Table();

    }
}
