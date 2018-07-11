package db;

public class Run {
    public static void main(String[] args) {
        Table t1 = DataIO.load("tbl1.tbl");
        Table t2 = DataIO.load("tbl2.tbl");
        System.out.println(DataIO.print(t1));
        System.out.println(DataIO.print(t2));
    }
}
