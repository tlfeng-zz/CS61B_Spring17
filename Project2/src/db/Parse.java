package db;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.StringJoiner;

public class Parse {
    // Various common constructs, simplifies parsing.
    private static final String REST  = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD   = Pattern.compile("load " + REST),
            STORE_CMD  = Pattern.compile("store " + REST),
            DROP_CMD   = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD  = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
            "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
            SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                    "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                    "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                    "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
            CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                    SELECT_CLS.pattern()),
            INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                    "\\s*(?:,\\s*.+?\\s*)*)");

    public static void main(String[] args, Map<String,Table> tblMap) {
        if (args.length != 1) {
            System.err.println("Expected a single query argument");
            return;
        }

        eval(args[0], tblMap);
    }

    private static void eval(String query, Map<String,Table> tblMap) {
        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            createTable(m.group(1), tblMap);
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            loadTable(m.group(1), tblMap);
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            storeTable(m.group(1), tblMap);
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            dropTable(m.group(1), tblMap);
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            insertRow(m.group(1), tblMap);
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            printTable(m.group(1), tblMap);
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            select(m.group(1), tblMap);
        } else {
            System.err.printf("Malformed query: %s\n", query);
        }
    }

    private static void createTable(String expr, Map<String,Table> tblMap) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            //createNewTable(m.group(1), m.group(2).split(COMMA));
            Table tbl = CRD.create(m.group(1),m.group(2));
            // save the table in map variable
            tblMap.put(tbl.getName(),tbl);
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {
            System.err.printf("Malformed create: %s\n", expr);
        }
    }

    private static void createNewTable(String name, String[] cols) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < cols.length-1; i++) {
            joiner.add(cols[i]);
        }

        String colSentence = joiner.toString() + " and " + cols[cols.length-1];
        System.out.printf("You are trying to create a table named %s with the columns %s\n", name, colSentence);
    }

    private static void createSelectedTable(String name, String exprs, String tables, String conds) {
        System.out.printf("You are trying to create a table named %s by selecting these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", name, exprs, tables, conds);
    }

    private static void loadTable(String name, Map<String,Table> tblMap) {
        System.out.printf("You are trying to load the table named %s\n", name);
        // load the file
        Table tbl = DataIO.load(name);
        // add to tables map
        if (tbl != null)
            tblMap.put(tbl.getName(), tbl);
        else
            System.out.printf("ERROR: TBL file not found: %s.tbl\n", name);
    }

    private static void storeTable(String name, Map<String,Table> tblMap) {
        System.out.printf("You are trying to store the table named %s\n", name);
        Table tbl = tblMap.get(name);
        if (tbl != null)
            DataIO.store(tbl);
        else
            System.err.printf("ERROR: No such table: %s\n", name);
    }

    private static void dropTable(String name, Map<String,Table> tblMap) {
        System.out.printf("You are trying to drop the table named %s\n", name);
        Table tbl;
        if (tblMap.containsKey(name))
            // remove from tables map
            tbl = tblMap.remove(name);
        else
            System.err.printf("ERROR: No such table: %s\n", name);
    }

    private static void insertRow(String expr, Map<String,Table> tblMap) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return;
        }

        System.out.printf("You are trying to insert the row \"%s\" into the table %s\n", m.group(2), m.group(1));
        // insert
        Table tbl;
        if (tblMap.containsKey(m.group(1))) {
            // remove from tables map
            tbl = tblMap.get(m.group(1));
            CRD.insert(tbl, m.group(2));
            tblMap.put(m.group(1), tbl);
        }
        else
            System.err.printf("ERROR: No such table: %s\n", m.group(1));

    }

    private static void printTable(String name, Map<String,Table> tblMap) {
        System.out.printf("You are trying to print the table named %s\n", name);
        // print
        Table tbl = tblMap.get(name);
        if (tbl != null)
            System.out.println(DataIO.print(tbl));
        else
            System.err.printf("ERROR: No such table: %s\n", name);
    }

    private static void select(String expr, Map<String,Table> tblMap) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return;
        }

        // validate table name
        String[] tblNames = m.group(2).split(",");
        Table tbl;
        for (String tblName: tblNames) {
            if (!tblMap.containsKey(tblName)) {
                System.err.printf("ERROR: No such table: %s\n", tblName);
                return;
            }
        }
        select(m.group(1), m.group(2), m.group(3), tblMap);
    }

    private static void select(String exprs, String tables, String conds, Map<String,Table> tblMap) {
        System.out.printf("You are trying to select these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", exprs, tables, conds);

        // select <column exprs> from <table names>, without <conditions>
        if (conds == null) {
            // when there is a Arithmetic Operator
            // filter all the space
            exprs = exprs.replaceAll("\\s*", "");
            String[] aoExprs = exprs.split("[\\+\\-\\*\\/]");
            if (aoExprs.length == 2) {
                String[] operands = {aoExprs[0], aoExprs[1].split("as")[0]};
                String newColName = aoExprs[1].split("as")[1];
                //System.out.println("'"+operands[0]+"','"+operands[1]+"','"+newColName+"'");
                Table newTbl = CRD.selectwithExprs(tblMap.get(tables), operands, newColName);
            }
            //when there is no Arithmetic Operators
            // select <column names> from <table names>, without <conditions>
            else {
                String[] tblNames = tables.split(",");
                Table newTbl = null;
                Table joinedTbl = null;
                if (tblNames.length == 1)
                    newTbl = CRD.select(tblMap.get(tables), exprs);
                if (tblNames.length == 2) {
                    joinedTbl = tblMap.get(tblNames[0]).join(tblMap.get(tblNames[1]));
                    newTbl = CRD.select(joinedTbl, exprs);
                }
                System.out.println(DataIO.print(newTbl));
            }
        }
        // select <column names> from <table> where <conditions>
        else {

        }

    }
}