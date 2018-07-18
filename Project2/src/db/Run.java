package db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Run {
    public static void main(String[] args) {
        Table t1 = DataIO.load("t1");
        Table t2 = DataIO.load("t2");
        Table t4 = DataIO.load("seasonRatios");
        System.out.println(DataIO.print(t1));
        System.out.println(DataIO.print(t2));
        System.out.println(DataIO.print(t4));

        Table t3 = t1.join(t2);
        t3.setName("t3");
        DataIO.store(t3);
    }
}
