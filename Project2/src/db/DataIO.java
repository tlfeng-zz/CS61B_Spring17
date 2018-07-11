package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataIO<T> {

    public static String print(Table tbl) {
        // Stringbuilder
        StringBuilder sb = new StringBuilder();
        // print the definition
        for(int i=0; i<tbl.colNum; i++) {
            sb.append(tbl.colNameList.get(i)+" ");
            sb.append(tbl.colTypeList.get(i));
            if (i<tbl.colNum-1)
                sb.append(",");
        }
        sb.append("\n");
        // print the content
        for(int i=0; i<tbl.rowNum; i++) {
            for(int j=0; j<tbl.colNum; j++) {
                sb.append(tbl.rowList.get(i).rowEList.get(j));
                if (j<tbl.colNum-1)
                    sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Table load(String fileName) {
        // create local variable to store when reading
        String tblStr = null;
        List<String> tblStrList = new ArrayList<>();

        // read file
        File file = new File("/Users/ftl/"+fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            int line = 1;
            // read once for a line, until read to null
            while ((tblStr = reader.readLine()) != null) {
                // store line content into list
                tblStrList.add(tblStr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        // Parse the file
        // Split the definition and content
        String tblDef = tblStrList.get(0);
        String[] tblRowStrArr = new String[tblStrList.size()-1];
        for (int i=0; i<tblStrList.size()-1; i++) {
            tblRowStrArr[i] = tblStrList.get(i+1);
        }

        // parse the definition
        String[] colDefArr = tblDef.split(",");
        String[] nameArr = new String[colDefArr.length];
        String[] typeArr = new String[colDefArr.length];
        for (int i=0; i<colDefArr.length; i++) {
            nameArr[i] = colDefArr[i].split(" ")[0];
            typeArr[i] = colDefArr[i].split(" ")[1];
        }
        // create new table
        Table tbl = new Table(nameArr,typeArr);

        // parse the content
        for (String tblRowStr: tblRowStrArr) {
            //System.out.println(tblRowStr);
            String[] rowArr = tblRowStr.split(",");
            // change type
            List<Integer> rowEList = new ArrayList<>();
            for(int i=0; i<rowArr.length; i++) {
                rowEList.add(Integer.parseInt(rowArr[i]));
            }
            Row row = new Row(rowEList);
            // add row to table
            tbl.addRow(row);
        }

        return tbl;
    }
}
