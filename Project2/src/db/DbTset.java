package db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DbTset {
    String[] colName1 = {"X","Y"};
    String[] colName2 = {"X","Z"};
    String[] colType1 = {"int","int"};
    String[] colType2 = {"int","int"};
    Table t1 = new Table(colName1, colType1);
    Table t2 = new Table(colName2, colType2);

    @Before
    public void init(){
        int[] rowArr1 = {2,5};
        int[] rowArr2 = {8,3};
        int[] rowArr3 = {13,7};
        int[] rowArr4 = {2,4};
        int[] rowArr5 = {8,9};
        int[] rowArr6 = {10,1};
        Row r = new Row(rowArr1);
        t1.addRow(r);
        r = new Row(rowArr2);
        t1.addRow(r);
        r = new Row(rowArr3);
        t1.addRow(r);

        r = new Row(rowArr4);
        t2.addRow(r);
        r = new Row(rowArr5);
        t2.addRow(r);
        r = new Row(rowArr6);
        t2.addRow(r);
    }

    @Test
    public void testAddRow() {
        assertEquals(9, (int)t2.rowList.get(1).rowEList.get(1));
    }
}
