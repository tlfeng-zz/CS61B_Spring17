package db;

import java.util.ArrayList;
import java.util.List;

public class CRD {
    public static Table create(String name, String tblDef) {
        // parse the definition
        String[] colDefArr = tblDef.split(",");
        String[] nameArr = new String[colDefArr.length];
        String[] typeArr = new String[colDefArr.length];
        for (int i = 0; i < colDefArr.length; i++) {
            nameArr[i] = colDefArr[i].split(" ")[0];
            typeArr[i] = colDefArr[i].split(" ")[1];
        }
        // create new table
        Table tbl = new Table(name);
        // set column name and type
        Column col;
        for (int i = 0; i < nameArr.length; i++) {
            col = new Column(nameArr[i], typeArr[i]);
            tbl.getColList().add(col);
            tbl.setColNum(tbl.getColNum() + 1);
        }

        return tbl;
    }

    public static Table insert(Table tbl, String tblRowStr) {
        // parse the content
        String[] rowArr = tblRowStr.split(",");
        List<Object> rowEList = new ArrayList<>();
        for (int i = 0; i < tbl.getColNum(); i++) {
            Column col = tbl.getColList().get(i);
            // change to right type
            if (col.getType().equals("int"))
                rowEList.add(Integer.parseInt(rowArr[i]));
            else if (col.getType().equals("float"))
                rowEList.add(Float.parseFloat(rowArr[i]));
            else if (col.getType().equals("string"))
                rowEList.add(rowArr[i]);
        }
        Row row = new Row(rowEList);
        // add row to table
        tbl.addRow(row);

        return tbl;
    }

    public static Table select(Table tbl, String colStr) {
        Table newTbl = new Table(tbl.getName());
        String[] colNameArr = colStr.split(",");
        // find the index of the needed columns
        List<Integer> neededColIndexList = new ArrayList<>();
        for (int i=0; i<tbl.getColNum(); i++) {
            for (int j=0; j<colNameArr.length; j++) {
                if ( tbl.getColList().get(i).getName().equals(colNameArr[j]) ) {
                    neededColIndexList.add(i);
                    // add column
                    Column oldCol = tbl.getColList().get(i);
                    Column newCol = new Column(oldCol.getName(), oldCol.getType());
                    newTbl.getColList().add(newCol);
                }
            }
        }
        //set colNum
        newTbl.setColNum(neededColIndexList.size());

        // fill in the row content
        for(int i=0; i<tbl.getRowNum(); i++) {
            List<Object> newRowEList = new ArrayList<>();
            for (int j=0; j<tbl.getRowList().get(i).getRowEList().size(); j++) {
                for (int k=0; k<neededColIndexList.size(); k++) {
                    if (j == neededColIndexList.get(k)) {
                        newRowEList.add(tbl.getRowList().get(i).getRowEList().get(j));
                    }
                }
            }
            Row row = new Row(newRowEList);
            // add new row to table
            newTbl.addRow(row);
        }

        return newTbl;
    }

    public static Table selectwithConds(Table tbl, String colStr, String conds) {
        Table newTbl = new Table(tbl.getName());
        String[] colNameArr = colStr.split(",");
        // find the index of the needed columns
        List<Integer> neededColIndexList = new ArrayList<>();
        for (int i=0; i<tbl.getColNum(); i++) {
            for (int j=0; j<colNameArr.length; j++) {
                if ( tbl.getColList().get(i).getName().equals(colNameArr[j]) ) {
                    neededColIndexList.add(i);
                    // add column
                    Column oldCol = tbl.getColList().get(i);
                    Column newCol = new Column(oldCol.getName(), oldCol.getType());
                    newTbl.getColList().add(newCol);
                }
            }
        }
        //set colNum
        newTbl.setColNum(neededColIndexList.size());

        // fill in the row content
        for(int i=0; i<tbl.getRowNum(); i++) {
            List<Object> newRowEList = new ArrayList<>();
            for (int j=0; j<tbl.getRowList().get(i).getRowEList().size(); j++) {
                for (int k=0; k<neededColIndexList.size(); k++) {
                    if (j == neededColIndexList.get(k)) {
                        newRowEList.add(tbl.getRowList().get(i).getRowEList().get(j));
                    }
                }
            }
            Row row = new Row(newRowEList);
            // add new row to table
            newTbl.addRow(row);
        }

        return newTbl;
    }
}
