package db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // deal with the colStr is *
        if(colStr.equals("*"))
            return tbl;

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

    public static Table selectwithExprs(Table tbl, String[] operands, String arOp, String newColName) {
        Table newTbl = new Table(tbl.getName());
        int oldColIndex = -1;
        String oldColType = null;
        for (int i=0; i<tbl.getColNum(); i++) {
            // find the column to be adjusted
            if (tbl.getColList().get(i).getName().equals(operands[0])) {
                oldColIndex = i;
                oldColType = tbl.getColList().get(i).getType();
            }
        }
        // create new column;
        Column newCol = new Column();
        newCol.setName(newColName);
        newTbl.getColList().add(newCol);
        newTbl.setColNum(1);
        //fill the content depend on the parameters
        String operand2ndType = judgeSecondOperand(operands[1]);

        // Because operands[1] is constant, put those same elements in the Array
        String[] operand2ndArr = new String[tbl.getRowNum()];
        for (int i = 0; i < tbl.getRowNum(); i++) {
            operand2ndArr[i] = operands[1];
        }

        if(operand2ndType.equals("columnName")) {
            // when the type is column name, check and transfer to other three type
            int oldCol2Index = -1;
            String oldCol2Type = null;
            for (int i=0; i<tbl.getColNum(); i++) {
                // find the column to be adjusted
                if (tbl.getColList().get(i).getName().equals(operands[1])) {
                    oldCol2Index = i;
                    oldCol2Type = tbl.getColList().get(i).getType();
                }
            }

            // prepare the type and elements for below
            operand2ndType = oldCol2Type;
            for (int i = 0; i < tbl.getRowNum(); i++) {
                operand2ndArr[i] = tbl.getRowList().get(i).getRowEList().get(oldCol2Index).toString();
            }
        }

        if (operand2ndType.equals("int") || operand2ndType.equals("float")) {
            if( oldColType.equals("string")) {
                System.err.println("Error: perform operations where one operand is a string, and the other is an int or float.");
                return null;
            }
            else {
                // begin to construct row
                for (int i = 0; i < tbl.getRowNum(); i++) {
                    List<Object> newRowEList = new ArrayList<>();
                    String oldColElement = tbl.getRowList().get(i).getRowEList().get(oldColIndex).toString();
                    float element = -1;
                    if (arOp.equals("plus"))
                        element = Float.parseFloat(oldColElement) + Float.parseFloat(operand2ndArr[i]);
                    else if (arOp.equals("minus"))
                        element = Float.parseFloat(oldColElement) - Float.parseFloat(operand2ndArr[i]);
                    else if (arOp.equals("multiply"))
                        element = Float.parseFloat(oldColElement) * Float.parseFloat(operand2ndArr[i]);
                    else if (arOp.equals("devide"))
                        element = Float.parseFloat(oldColElement) / Float.parseFloat(operand2ndArr[i]);

                    // if exists float, the result will be float
                    // set column type
                    if (oldColType.equals("float") || operand2ndType.equals("float")) {
                        newTbl.getColList().get(0).setType("float");
                        newRowEList.add(element);
                    } else {
                        newTbl.getColList().get(0).setType("int");
                        newRowEList.add((int) element);
                    }

                    Row row = new Row(newRowEList);
                    // add new row to table
                    newTbl.addRow(row);
                }

            }
        }
        else if (operand2ndType.equals("string")) {
            // set column type
            newTbl.getColList().get(0).setType("string");

            if( oldColType.equals("int") || oldColType.equals("float")) {
                System.err.println("Error: perform operations where one operand is a string, and the other is an int or float.");
                return null;
            }
            else if (!arOp.equals("plus")) {
                System.err.println("Error: when the second operand is a string, operator '+' is only allowed.");
                return null;
            }
            else {
                // begin to concat string
                for (int i = 0; i < tbl.getRowNum(); i++) {
                    List<Object> newRowEList = new ArrayList<>();

                    String oldColElement = filterQuote(tbl.getRowList().get(i).getRowEList().get(oldColIndex).toString());
                    String operand2nd = filterQuote(operand2ndArr[i]);
                    StringBuilder sb = new StringBuilder("'");
                    sb.append(oldColElement);
                    sb.append(operand2nd);
                    sb.append("'");
                    String newColElement = sb.toString();

                    newRowEList.add(newColElement);
                    Row row = new Row(newRowEList);
                    // add new row to table
                    newTbl.addRow(row);
                }
            }
        }

        return newTbl;
    }

    public static String judgeSecondOperand(String str) {
        Pattern p1 = Pattern.compile("^-?\\d+$");
        Matcher m1 = p1.matcher(str);

        String regex = "^(-?\\d+)(\\.\\d+)?$";
        Matcher m2 = Pattern.compile(regex).matcher(str);

        Pattern p3 = Pattern.compile("\'(.*?)\'");
        Matcher m3 = p3.matcher(str);
        if (m1.matches())
            return "int";
        else if (m2.matches())
            return "float";
        else if (m3.matches())
            return "string";
        else
            return "columnName";
    }

    public static String filterQuote(String str) {
        Pattern p = Pattern.compile("\'(.*?)\'");
        Matcher m = p.matcher(str);
        while (m.find()) {
            //System.out.println(m.group(1));
            return m.group(1);
        }
        return null;
    }

    public static Table selectwithConds(Table tbl, String[] operands, String conds) {
        // parse the Comparison Operators


        // deal with the colStr is *
        if(colStr.equals("*"))
            return tbl;

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
